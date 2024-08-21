package clinica.med.CaEntreNos.domain.responsavel;

public record DTOListaResponsavel(Long id, String nome, String email) {

    public DTOListaResponsavel(Responsavel responsavel) {
        this(responsavel.getId(), responsavel.getNome(), responsavel.getEmail());
    }
}
