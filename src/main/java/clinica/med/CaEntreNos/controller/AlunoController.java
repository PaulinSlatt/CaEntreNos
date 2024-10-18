package clinica.med.CaEntreNos.controller;
import clinica.med.CaEntreNos.domain.aluno.*;
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
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @CrossOrigin(origins = "http://192.168.0.16:8081")
    @PostMapping
    @Transactional
    public ResponseEntity<DTOListaAluno> Cadastrar(@RequestBody @Valid DTOCadastroAluno dados, UriComponentsBuilder uriComponentsBuilder) {
        Aluno aluno = alunoService.cadastrarAluno(dados);
        var uri = uriComponentsBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOListaAluno(aluno));
    }


    @CrossOrigin(origins = "http://192.168.0.16:8081")
    @PostMapping("/login")
    public ResponseEntity<DTOLoginResponse> login(@RequestBody @Valid DTOAutenticacaoAluno dados) {
        var aluno = alunoService.autenticarAluno(dados);
        var tokenJWT = tokenService.GerarTokenAluno(aluno);
        return ResponseEntity.ok(new DTOLoginResponse(tokenJWT, new DTOListaAluno(aluno)));
    }

    // Solicitação de recuperação de senha para Aluno
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> solicitarRecuperacaoSenha(@RequestParam String login) {
        var aluno = alunoRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        String token = tokenService.GerarTokenAluno(aluno);
        return ResponseEntity.ok("Token de recuperação: " + token);
    }

    // Redefinição de senha para Aluno
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestParam String token,
            @RequestParam String novaSenha) {
        String login = tokenService.decodeToken(token).getSubject();
        var aluno = alunoRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        aluno.setSenha(passwordEncoder.encode(novaSenha));
        alunoRepository.save(aluno);

        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
    @CrossOrigin(origins = "http://192.168.0.16:8081")
    @GetMapping
    public ResponseEntity<Page<DTOListaAluno>> Listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = alunoService.listarTodos(paginacao).map(DTOListaAluno::new);
        return ResponseEntity.ok(page);
    }

    @CrossOrigin(origins = "http://192.168.0.16:8081")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DTOListaAluno> Atualizar(@RequestBody @Valid DTOAttAluno dados) {
        var aluno = alunoService.atualizarAluno(dados);
        return ResponseEntity.ok(new DTOListaAluno(aluno));
    }

    @CrossOrigin(origins = "http://192.168.0.16:8081")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> Excluir(@PathVariable Long id) {
        alunoService.excluirAluno(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://192.168.0.16:8081")
    @GetMapping("/{id}")
    public ResponseEntity<DTOListaAluno> Detalhar(@PathVariable Long id) {
        var aluno = alunoService.buscarAlunoPorId(id);
        return ResponseEntity.ok(new DTOListaAluno(aluno));
    }

}




