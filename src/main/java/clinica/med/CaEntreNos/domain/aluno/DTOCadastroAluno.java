package clinica.med.CaEntreNos.domain.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DTOCadastroAluno(@NotBlank String nome, @NotBlank @Email String login, @NotBlank String ano, @NotBlank @Pattern(regexp = "\\d{5}") String matricula,  @NotBlank String senha) {



}
