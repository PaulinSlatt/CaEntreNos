package clinica.med.CaEntreNos.domain.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "admin")
@Entity(name = "Admin")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String login;
    private String senha;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;

    public Admin(DTOCadastroAdmin dados) {
        super();
        this.ativo = true;
        this.nome = dados.nome();
        this.login = dados.login();
        this.senha = dados.senha();
        this.tipoUsuario = "ADMIN";

    }


    public void AtualizarInformacoes(DTOAttAdmin dados) {
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
        return "ADMIN";
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
