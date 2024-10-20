package clinica.med.CaEntreNos.controller;

import clinica.med.CaEntreNos.domain.admin.*;
import clinica.med.CaEntreNos.infra.security.DTOTokenJWT;
import clinica.med.CaEntreNos.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DTOListaAdmin> cadastrarAdmin(@RequestBody @Valid DTOCadastroAdmin dados, UriComponentsBuilder uriComponentsBuilder) {
        Admin admin = adminService.cadastrarAdmin(dados);
        var uri = uriComponentsBuilder.path("/admin/{id}").buildAndExpand(admin.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOListaAdmin(admin));
    }

    @PostMapping("/login")
    public ResponseEntity<DTOTokenJWT> login(@RequestBody @Valid DTOAutenticacaoAdmin dados) {
        var admin = adminService.autenticarAdmin(dados);
        var tokenJWT = tokenService.GerarTokenAdmin(admin);
        return ResponseEntity.ok(new DTOTokenJWT(tokenJWT));
    }

    // Solicitação de recuperação de senha para Admin
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> solicitarRecuperacaoSenha(@RequestParam String login) {
        var admin = adminRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Admin não encontrado"));

        String token = tokenService.GerarTokenAdmin(admin);
        return ResponseEntity.ok("Token de recuperação: " + token);
    }

    // Redefinição de senha para Admin
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestParam String token,
            @RequestParam String novaSenha) {
        String login = tokenService.decodeToken(token).getSubject();
        var admin = adminRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Admin não encontrado"));

        admin.setSenha(passwordEncoder.encode(novaSenha));
        adminRepository.save(admin);

        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Page<DTOListaAdmin>> listarAdmins(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = adminService.listarTodos(paginacao).map(DTOListaAdmin::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DTOListaAdmin> atualizarAdmin(@PathVariable Long id, @RequestBody @Valid DTOAttAdmin dados) {
        dados = new DTOAttAdmin(id, dados.nome(), dados.login(), dados.senha()); // Ajustar conforme sua DTO
        var admin = adminService.atualizarAdmin(dados);
        return ResponseEntity.ok(new DTOListaAdmin(admin));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirAdmin(@PathVariable Long id) {
        adminService.excluirAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOListaAdmin> detalharAdmin(@PathVariable Long id) {
        var admin = adminService.buscarAdminPorId(id);
        return ResponseEntity.ok(new DTOListaAdmin(admin));
    }
}