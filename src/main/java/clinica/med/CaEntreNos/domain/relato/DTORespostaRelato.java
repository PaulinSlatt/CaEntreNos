package clinica.med.CaEntreNos.domain.relato;


import jakarta.validation.constraints.NotNull;


public record DTORespostaRelato(@NotNull String resposta) {

    public DTORespostaRelato(Relato relato) {
        this(relato.getResposta());
    }



}
