package clinica.med.Projeto_Clinica.domain.medico;

import clinica.med.Projeto_Clinica.domain.endereco.DTOEndereco;
import jakarta.validation.constraints.NotNull;

public record DTOAttMedico(@NotNull Long id, String nome, String telefone, DTOEndereco endereco) {




}
