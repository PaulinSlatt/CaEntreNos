package clinica.med.Projeto_Clinica.controller;

import clinica.med.Projeto_Clinica.domain.usuario.DTOAutenticacao;
import clinica.med.Projeto_Clinica.domain.usuario.Usuario;
import clinica.med.Projeto_Clinica.infra.security.DTOTokenJWT;
import clinica.med.Projeto_Clinica.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DTOAutenticacao dados) {
        var AuthenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(AuthenticationToken);

        //getprincipal retorna usuariologado
        var tokenJWT = tokenService.GerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DTOTokenJWT(tokenJWT));
    }
}
