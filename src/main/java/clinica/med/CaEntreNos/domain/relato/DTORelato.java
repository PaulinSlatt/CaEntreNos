package clinica.med.CaEntreNos.domain.relato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//cLASSE PARA TRANSFERENCIA DE DADOS
public record DTORelato( @NotNull
                         Tipo tipo,

                         @NotBlank
                         String descricao,

                         @NotNull
                         Long alunoId,

                         @NotBlank
                         String status){


    public DTORelato(Relato relato) {
        this(
                relato.getTipo(),
                relato.getDescricao(),
                relato.getAluno() != null ? relato.getAluno().getId() : null,
                relato.getStatus()
        );
    }
}
