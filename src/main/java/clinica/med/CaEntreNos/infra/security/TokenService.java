package clinica.med.CaEntreNos.infra.security;

//tudo relacionado a token fica nessa classe

import clinica.med.CaEntreNos.domain.admin.Admin;
import clinica.med.CaEntreNos.domain.aluno.Aluno;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;



    public String GerarTokenAluno(Aluno aluno) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("CaEntreNos")
                    .withSubject(aluno.getLogin())
                    .withClaim("TipoUsuario", "ALUNO") // Adiciona o tipo de usuário no token
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String GerarTokenAdmin(Admin admin) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("CaEntreNos")
                    .withSubject(admin.getLogin())
                    .withClaim("TipoUsuario", "ADMIN")
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    /*public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("CaEntreNos")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }*/

    public DecodedJWT decodeToken(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("CaEntreNos")
                    .build()
                    .verify(tokenJWT);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    public String getTipoUsuario(String tokenJWT) {
        DecodedJWT decodedJWT = decodeToken(tokenJWT);
        return decodedJWT.getClaim("TipoUsuario").asString();
    }



    private Instant expirationDate() {
        return LocalDateTime.now().plusYears(50).toInstant(ZoneOffset.of("-03:00"));
    }

}
