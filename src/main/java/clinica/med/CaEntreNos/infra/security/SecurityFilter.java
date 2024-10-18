package clinica.med.CaEntreNos.infra.security;

import clinica.med.CaEntreNos.domain.admin.AdminRepository;
import clinica.med.CaEntreNos.domain.aluno.AlunoRepository;
import clinica.med.CaEntreNos.domain.admin.AdminUserDetails;
import clinica.med.CaEntreNos.domain.aluno.AlunoUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AdminRepository adminRepository;

    // Definimos rotas públicas que não exigem autenticação
    private static final List<String> PUBLIC_ROUTES = List.of(
            "/relatos",  // GET e POST são públicos
            "/swagger-ui", "/v3/api-docs"  // Documentação pública
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                System.out.println("Token JWT encontrado, autenticando...");
                String tipoUsuario = tokenService.getTipoUsuario(tokenJWT);
                autenticarUsuario(tipoUsuario, tokenJWT);
                System.out.println("Autenticação final no contexto: " + SecurityContextHolder.getContext().getAuthentication());
            } catch (Exception e) {
                System.out.println("Erro ao autenticar o token: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhum token JWT encontrado");
        }

        filterChain.doFilter(request, response);
    }

    // Verifica se a rota atual é pública
    private boolean isPublicRoute(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_ROUTES.stream().anyMatch(path::startsWith);
    }

    // Autentica o usuário com base no tipo
    private void autenticarUsuario(String tipoUsuario, String tokenJWT) {
        String subject = tokenService.decodeToken(tokenJWT).getSubject();

        if ("ADMIN".equals(tipoUsuario)) {
            var admin = adminRepository.findByLogin(subject)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin não encontrado"));
            var adminUserDetails = new AdminUserDetails(admin);
            var authentication = new UsernamePasswordAuthenticationToken(
                    adminUserDetails, null, adminUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Admin autenticado: " + admin.getLogin());
        } else if ("ALUNO".equals(tipoUsuario)) {
            var aluno = alunoRepository.findByLogin(subject)
                    .orElseThrow(() -> new UsernameNotFoundException("Aluno não encontrado"));
            var alunoUserDetails = new AlunoUserDetails(aluno);
            var authentication = new UsernamePasswordAuthenticationToken(
                    alunoUserDetails, null, alunoUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Aluno autenticado: " + aluno.getLogin());
        } else {
            System.out.println("Tipo de usuário desconhecido: " + tipoUsuario);
        }
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }


}