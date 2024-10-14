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

        // Se a rota for pública, não precisa validar token
        if (isPublicRoute(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Se o token existir, valida o tipo de usuário e autentica
        if (tokenJWT != null) {
            String tipoUsuario = tokenService.getTipoUsuario(tokenJWT);
            autenticarUsuario(tipoUsuario);
        }

        filterChain.doFilter(request, response);
    }

    // Verifica se a rota atual é pública
    private boolean isPublicRoute(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_ROUTES.stream().anyMatch(path::startsWith);
    }

    // Autentica o usuário com base no tipo
    private void autenticarUsuario(String tipoUsuario) {
        String tokenJWT = recuperarToken((HttpServletRequest) SecurityContextHolder.getContext().getAuthentication().getDetails());
        String subject = tokenService.decodeToken(tokenJWT).getSubject();

        if ("ALUNO".equals(tipoUsuario)) {
            var aluno = alunoRepository.findByLogin(subject)
                    .orElseThrow(() -> new UsernameNotFoundException("Aluno não encontrado"));
            var alunoUserDetails = new AlunoUserDetails(aluno);
            var authentication = new UsernamePasswordAuthenticationToken(
                    alunoUserDetails, null, alunoUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else if ("ADMIN".equals(tipoUsuario)) {
            var admin = adminRepository.findByLogin(subject)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin não encontrado"));
            var adminUserDetails = new AdminUserDetails(admin);
            var authentication = new UsernamePasswordAuthenticationToken(
                    adminUserDetails, null, adminUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    // Recupera o token JWT do cabeçalho Authorization
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}