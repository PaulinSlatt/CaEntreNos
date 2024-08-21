package clinica.med.CaEntreNos.domain.aluno;

import jakarta.validation.constraints.NotNull;

public record DTOAttAluno(@NotNull Long id, String nome, String login, String ano, String senha) {


}
