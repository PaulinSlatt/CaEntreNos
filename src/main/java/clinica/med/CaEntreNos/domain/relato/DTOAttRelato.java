package clinica.med.CaEntreNos.domain.relato;


import jakarta.validation.constraints.NotNull;

public record DTOAttRelato(@NotNull Long id, String descricao, String status, Tipo tipo) {






}
