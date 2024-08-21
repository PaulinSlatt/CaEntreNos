package clinica.med.CaEntreNos.domain.aluno;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "alunos")
@Entity(name = "Alunos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String matricula;
    private String ano;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;


    @Column(name = "ativo")
    private Boolean ativo;

    public Aluno(DTOCadastroAluno dados) {
        super();
        this.ativo = true;
        this.nome = dados.nome();
        this.login = dados.login();
        this.ano = dados.ano();
        this.senha = dados.senha();
        this.matricula = dados.matricula();
        this.tipoUsuario = "ALUNO";

    }


    public Aluno(Long id) {
        this.id = id;
    }


    public void AtualizarInformacoes(DTOAttAluno dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }

    }

    public void Excluir() {

        this.ativo = false;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return "ALUNO";
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
