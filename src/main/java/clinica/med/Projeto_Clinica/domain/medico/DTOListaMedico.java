package clinica.med.Projeto_Clinica.domain.medico;

public record DTOListaMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DTOListaMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}
