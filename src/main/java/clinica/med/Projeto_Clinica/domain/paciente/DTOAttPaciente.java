package clinica.med.Projeto_Clinica.domain.paciente;

import clinica.med.Projeto_Clinica.domain.endereco.DTOEndereco;
import jakarta.validation.constraints.NotNull;

public record DTOAttPaciente(@NotNull Long id, String nome, String telefone, DTOEndereco endereco) {


}
