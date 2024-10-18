package clinica.med.CaEntreNos.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs stateless
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define o modo de sessão como stateless
                .authorizeHttpRequests(req -> {
                    // Define permissões para várias rotas
                    req.requestMatchers(HttpMethod.POST, "/alunos").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/alunos/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/admin").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/admin/login").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/relatos").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/relatos/responder/**").hasAuthority("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/relatos").permitAll();
                    req.requestMatchers("/alunos/**").hasAnyAuthority("ALUNO", "ADMIN");
                    req.requestMatchers("/admin/**").hasAuthority("ADMIN");
                    req.requestMatchers("/error").permitAll();
                    req.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();

                    // Exige autenticação para todas as outras requisições
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Aqui você insere o filtro
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}