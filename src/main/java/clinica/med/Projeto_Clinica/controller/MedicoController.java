package clinica.med.Projeto_Clinica.controller;

import clinica.med.Projeto_Clinica.domain.medico.*;
import clinica.med.Projeto_Clinica.domain.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

//classe que recebe as requisicoes do crud
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional //para inserir dados no bd
public ResponseEntity Cadastrar(@RequestBody @Valid DTOMedico dados, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        //retorna 201
        return ResponseEntity.created(uri).body(new DTODetalheMedico(medico));
    }

@GetMapping
public ResponseEntity <Page<DTOListaMedico>> Listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao ){
        //Fazendo conversao de medico para DtoMedico, pois quero que liste somente determinadas coisas do medico.
    var page = repository.findAllByAtivoTrue(paginacao).map(DTOListaMedico::new);
    // http 200ok trazendo a paginacao com os medicos
    return ResponseEntity.ok(page);
}

@PutMapping
@Transactional
public ResponseEntity Atualizar(@RequestBody @Valid DTOAttMedico dados){
var medico = repository.getReferenceById(dados.id());
medico.atualizarInformacoes(dados);
//retorna 200ok com o detalhamento
return ResponseEntity.ok( new DTODetalheMedico(medico));
}

@Transactional
@DeleteMapping("/{id}")
public ResponseEntity Excluir(@PathVariable Long id){
    var medico = repository.getReferenceById(id);
    medico.Excluir();
   //retorna nocontent pois nao tem conteudo e foi processado com sucesso. http 204
    return ResponseEntity.noContent().build();

}

    @GetMapping("/{id}")
    public ResponseEntity Detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.Excluir();
        //retorna http 200ok
        return ResponseEntity.ok(new DTODetalheMedico(medico));

    }

}
