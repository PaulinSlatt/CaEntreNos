package clinica.med.CaEntreNos.controller;
import clinica.med.CaEntreNos.domain.admin.AdminUserDetails;
import clinica.med.CaEntreNos.domain.relato.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;


// Classe que recebe as requisições do CRUD
@RestController
@RequestMapping("/relatos")
public class RelatoController {

    @Autowired
    private RelatoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DTORelato> Cadastrar(@RequestBody @Valid DTORelato dados, UriComponentsBuilder uriComponentsBuilder) {
        var relato = new Relato(dados);
        repository.save(relato);
        var uri = uriComponentsBuilder.path("/relatos/{id}").buildAndExpand(relato.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTORelato(relato));
    }

    @PostMapping("/responder/{id}")
    @Transactional
    public ResponseEntity<DTORespostaRelato> responderRelato(@PathVariable Long id, @RequestBody @Valid DTORespostaRelato dados) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Verifica se o usuário é um Admin (baseado na implementação de UserDetails)
        boolean isAdmin = userDetails instanceof AdminUserDetails;

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        // Busca o relato pelo ID
        var relato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));

        // Adiciona a resposta e altera o status para "respondido"
        relato.setResposta(dados.resposta());
        relato.setStatus("respondido");

        // Salva as alterações no banco de dados
        repository.save(relato);

        // Retorna a resposta com os dados atualizados
        return ResponseEntity.ok(new DTORespostaRelato(relato));
    }

    @GetMapping
    public ResponseEntity<Page<DTOListaRelato>> Listar(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DTOListaRelato::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DTOListaRelato> Atualizar(@RequestBody @Valid DTOAttRelato dados) {
        var relato = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));
        relato.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DTOListaRelato(relato));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Excluir(@PathVariable Long id) {
        var relato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));
        relato.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOListaRelato> Detalhar(@PathVariable Long id) {
        var relato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));
        return ResponseEntity.ok(new DTOListaRelato(relato));
    }
}