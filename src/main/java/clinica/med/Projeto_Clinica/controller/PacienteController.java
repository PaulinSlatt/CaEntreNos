package clinica.med.Projeto_Clinica.controller;
import clinica.med.Projeto_Clinica.domain.paciente.*;
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
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity Cadastrar(@RequestBody @Valid DTOCadastroPaciente dados, UriComponentsBuilder uriComponentsBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);
        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOListaPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity <Page<DTOListaPaciente>> Listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DTOListaPaciente::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity Atualizar(@RequestBody @Valid DTOAttPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.AtualizarInformacoes(dados);
        return ResponseEntity.ok(new DTOListaPaciente(paciente));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity Excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.Excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity Detalhar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.Excluir();
        return ResponseEntity.ok(new DTOListaPaciente(paciente));
    }


}
