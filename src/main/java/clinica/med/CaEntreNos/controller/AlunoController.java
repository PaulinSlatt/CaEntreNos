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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;



    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<DTOListaAluno> Cadastrar(@RequestBody @Valid DTOCadastroAluno dados, UriComponentsBuilder uriComponentsBuilder) {
        Aluno aluno = alunoService.cadastrarAluno(dados);
        var uri = uriComponentsBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOListaAluno(aluno));
    }

    @PostMapping("/login")
    public ResponseEntity<DTOTokenJWT> login(@RequestBody @Valid DTOAutenticacaoAluno dados) {
        var aluno = alunoService.autenticarAluno(dados);
        var tokenJWT = tokenService.GerarTokenAluno(aluno);
        return ResponseEntity.ok(new DTOTokenJWT(tokenJWT));
    }

    @GetMapping
    public ResponseEntity<Page<DTOListaAluno>> Listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = alunoService.listarTodos(paginacao).map(DTOListaAluno::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DTOListaAluno> Atualizar(@RequestBody @Valid DTOAttAluno dados) {
        var aluno = alunoService.atualizarAluno(dados);
        return ResponseEntity.ok(new DTOListaAluno(aluno));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> Excluir(@PathVariable Long id) {
        alunoService.excluirAluno(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOListaAluno> Detalhar(@PathVariable Long id) {
        var aluno = alunoService.buscarAlunoPorId(id);
        return ResponseEntity.ok(new DTOListaAluno(aluno));
    }
}

