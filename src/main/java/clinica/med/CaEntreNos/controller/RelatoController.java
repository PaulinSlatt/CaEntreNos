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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/relatos")
public class RelatoController {

    @Autowired
    private RelatoRepository repository;

    // Método para cadastrar um novo relato
    @PostMapping
    @Transactional
    public ResponseEntity<DTORelato> cadastrar(
            @RequestBody @Valid DTORelato dados,
            UriComponentsBuilder uriComponentsBuilder) {

        var relato = new Relato(dados);
        repository.save(relato);

        var uri = uriComponentsBuilder.path("/relatos/{id}")
                .buildAndExpand(relato.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DTORelato(relato));
    }

    // Método para responder um relato (Apenas Admin)
    @PostMapping("/responder/{id}")
    @Transactional
    public ResponseEntity<DTORespostaRelato> responderRelato(
            @PathVariable Long id,
            @RequestBody @Valid DTORespostaRelato dados) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof AdminUserDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        var relato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));

        // Adiciona a resposta e altera o status para "respondido"
        relato.setResposta(dados.resposta());
        relato.setStatus("respondido");

        repository.save(relato); // Salva as mudanças no banco
        return ResponseEntity.ok(new DTORespostaRelato(relato));
    }

    // Método para listar relatos, com filtros baseados no tipo de usuário
    @GetMapping
    public ResponseEntity<Page<DTOListaRelato>> listar(
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getPrincipal() instanceof AdminUserDetails;

        Page<Relato> page;
        if (isAdmin) {
            page = repository.findAllByAtivoTrue(paginacao); // Admin vê todos
        } else {
            page = repository.findByTipoNotInAndAtivoTrue(
                    List.of(Tipo.ABUSO, Tipo.SEGURANÇA), paginacao); // Filtra para outros usuários
        }

        var dtoPage = page.map(DTOListaRelato::new);
        return ResponseEntity.ok(dtoPage);
    }

    // Método para atualizar um relato
    @PutMapping
    @Transactional
    public ResponseEntity<DTOListaRelato> atualizar(@RequestBody @Valid DTOAttRelato dados) {
        var relato = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));

        relato.atualizarInformacoes(dados); // Atualiza os campos do relato
        return ResponseEntity.ok(new DTOListaRelato(relato));
    }

    // Método para excluir (marcar como inativo) um relato
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var relato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));

        relato.excluir(); // Marca como inativo
        return ResponseEntity.noContent().build();
    }

    // Método para detalhar um relato específico
    @GetMapping("/{id}")
    public ResponseEntity<DTOListaRelato> detalhar(@PathVariable Long id) {
        var relato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relato não encontrado"));

        return ResponseEntity.ok(new DTOListaRelato(relato)); // Retorna o DTO com ID formatado
    }
}