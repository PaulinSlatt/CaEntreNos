package clinica.med.CaEntreNos.domain.responsavel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "responsavel")
@Entity(name = "Responsavel")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Responsavel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    @Column(name = "ativo")
    private Boolean ativo;

    public Responsavel(DTOCadastroResponsavel dados) {
        super();
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();

    }


    public void AtualizarInformacoes(DTOAttResponsavel dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }

    }

    public void Excluir() {

        this.ativo = false;
    }

}
