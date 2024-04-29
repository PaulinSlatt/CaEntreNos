package clinica.med.Projeto_Clinica.domain.medico;

import clinica.med.Projeto_Clinica.domain.endereco.Endereco;

public record DTODetalheMedico(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) {

    public DTODetalheMedico (Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());

    }

}
