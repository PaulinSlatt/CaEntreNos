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


@Configuration //anotacao para classe de conficuracao
@EnableWebSecurity // mostra que as configuracoes serao de seguraça
public class SecurityConfiguration {



    @Autowired
    private SecurityFilter securityFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // Permite acesso público para cadastro e login de alunos
                    req.requestMatchers(HttpMethod.POST, "/alunos").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/alunos/login").permitAll();

                    // Permite acesso público para cadastro e login de admin
                    req.requestMatchers(HttpMethod.POST, "/admin").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/admin/login").permitAll();

                    // Permite acesso público para obter relatos
                    req.requestMatchers(HttpMethod.GET, "/relatos").permitAll();

                    // Permite acesso público para postar relatos
                    req.requestMatchers(HttpMethod.POST, "/relatos").permitAll();

                    // Permite apenas admins responder relatos
                    req.requestMatchers(HttpMethod.POST, "/relatos/responder").hasAuthority("ADMIN");

                    // Permite acesso para admins a todas as rotas específicas de alunos
                    req.requestMatchers("/alunos/**").hasAnyAuthority("ALUNO", "ADMIN");

                    // Permite acesso apenas para admins nas rotas de admin
                    req.requestMatchers("/admin/**").hasAuthority("ADMIN");

                    // Permite acesso público às rotas do Swagger
                    req.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();


                    // Exige autenticação para todas as outras requisições
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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
