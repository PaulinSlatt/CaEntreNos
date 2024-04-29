package clinica.med.Projeto_Clinica.domain.paciente;

public record DTOListaPaciente(Long id, String nome, String email, String cpf) {

    public DTOListaPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
