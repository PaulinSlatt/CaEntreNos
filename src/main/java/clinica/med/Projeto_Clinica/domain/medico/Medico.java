package clinica.med.Projeto_Clinica.domain.medico;

import clinica.med.Projeto_Clinica.domain.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;


//classe para representar o bd e fazer a persistencia jpa

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    //anotação para saber que endereço fica em outra classe, mas faz parte da tabela Medico, sem ter que criar uma tabela endereço.
    @Embedded
    private Endereco endereco;

    private boolean ativo;

    public Medico(DTOMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.ativo = true;
        this.telefone = dados.telefone();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DTOAttMedico dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }

    }

    public void Excluir() {
        this.ativo = false;
    }
}
