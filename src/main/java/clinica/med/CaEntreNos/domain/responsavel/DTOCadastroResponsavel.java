package clinica.med.CaEntreNos.domain.responsavel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DTOCadastroResponsavel(@NotBlank String nome, @NotBlank @Email String email) {



}
