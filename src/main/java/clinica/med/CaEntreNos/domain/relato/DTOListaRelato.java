package clinica.med.CaEntreNos.domain.relato;

import java.time.LocalDateTime;

public record DTOListaRelato(Long id, String descricao, String status, Tipo tipo, Long alunoId, LocalDateTime data) {

    public DTOListaRelato(Relato relato) {
        this(relato.getId(), relato.getDescricao(), relato.getStatus(), relato.getTipo(), relato.getAluno().getId(), relato.getData());
    }

}
