package clinica.med.CaEntreNos.domain.admin;

import jakarta.validation.constraints.NotNull;

public record DTOAttAdmin(@NotNull Long id, String nome, String login, String senha) {


}
