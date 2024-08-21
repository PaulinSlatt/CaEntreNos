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

//anotacao utilizada quando a classe nao tem um tipo, mas queremos que o spring carregue.
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AlunoRepository alunoRepository;

     @Autowired
     private AdminRepository adminRepository;



    // @Autowired
    // private ResponsavelRepository responsavelRepository;




    // A cada requisição, o filtro é acionado para verificar se a pessoa tem credenciais para acessar o recurso
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            String tipoUsuario = tokenService.getTipoUsuario(tokenJWT);

            // Verifica o tipo de usuário e permite que o ADMIN acesse qualquer rota
            if ("ALUNO".equals(tipoUsuario) && request.getRequestURI().startsWith("/alunos")) {
                autenticarUsuario(request, tipoUsuario);
            } else if ("ADMIN".equals(tipoUsuario)) {
                // ADMIN pode acessar qualquer rota
                autenticarUsuario(request, tipoUsuario);
            } else {
                // Se o TipoUsuario não for correspondente à rota, bloqueie a requisição
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(HttpServletRequest request, String tipoUsuario) {
        String tokenJWT = recuperarToken(request);
        String subject = tokenService.decodeToken(tokenJWT).getSubject();

        if ("ALUNO".equals(tipoUsuario)) {
            var aluno = alunoRepository.findByLogin(subject);
            if (aluno.isPresent()) {
                var alunoUserDetails = new AlunoUserDetails(aluno.get());
                var authentication = new UsernamePasswordAuthenticationToken(alunoUserDetails, null, alunoUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new UsernameNotFoundException("Aluno não encontrado");
            }
        } else if ("ADMIN".equals(tipoUsuario)) {
            var admin = adminRepository.findByLogin(subject);
            if (admin.isPresent()) {
                var adminUserDetails = new AdminUserDetails(admin.get());
                var authentication = new UsernamePasswordAuthenticationToken(adminUserDetails, null, adminUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new UsernameNotFoundException("Admin não encontrado");
            }
        }
    }



    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }



}
