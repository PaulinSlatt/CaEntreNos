package clinica.med.CaEntreNos.domain.admin;

public record DTOListaAdmin(Long id, String nome, String login) {

    public DTOListaAdmin(Admin admin) {
        this(admin.getId(), admin.getNome(), admin.getLogin());
    }
}
