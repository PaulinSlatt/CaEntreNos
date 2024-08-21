package clinica.med.CaEntreNos.domain.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOCadastroAdmin(@NotBlank String nome, @NotBlank @Email String login, @NotBlank String senha) {


}
