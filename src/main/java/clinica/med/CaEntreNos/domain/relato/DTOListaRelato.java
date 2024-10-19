package clinica.med.CaEntreNos.domain.relato;

import java.time.LocalDateTime;

public record DTOListaRelato(String idFormatado, String descricao, String status, Tipo tipo, Long alunoId, String nomeAluno, LocalDateTime data, int curtidas) {

    public DTOListaRelato(Relato relato) {
        this(String.format("#%05d", relato.getId()), relato.getDescricao(), relato.getStatus(), relato.getTipo(), relato.getAluno().getId(), relato.getAluno().getNome(), relato.getData(), relato.getCurtidas());
    }

}
