package clinica.med.CaEntreNos.controller;

import clinica.med.CaEntreNos.domain.responsavel.*;
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
@RequestMapping("/responsavel")
public class ResponsavelController {

    @Autowired
    private ResponsavelRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity Cadastrar(@RequestBody @Valid DTOCadastroResponsavel dados, UriComponentsBuilder uriComponentsBuilder) {
        var responsavel = new Responsavel(dados);
        repository.save(responsavel);
        var uri = uriComponentsBuilder.path("/responsavel/{id}").buildAndExpand(responsavel.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOListaResponsavel(responsavel));
    }

    @GetMapping
    public ResponseEntity<Page<DTOListaResponsavel>> Listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DTOListaResponsavel::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity Atualizar(@RequestBody @Valid DTOAttResponsavel dados) {
        var responsavel = repository.getReferenceById(dados.id());
        responsavel.AtualizarInformacoes(dados);
        return ResponseEntity.ok(new DTOListaResponsavel(responsavel));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity Excluir(@PathVariable Long id) {
        var responsavel = repository.getReferenceById(id);
        responsavel.Excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity Detalhar(@PathVariable Long id) {
        var responsavel = repository.getReferenceById(id);
        responsavel.Excluir();
        return ResponseEntity.ok(new DTOListaResponsavel(responsavel));
    }


}
