package clinica.med.CaEntreNos.domain.relato;

import clinica.med.CaEntreNos.domain.aluno.Aluno;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// Classe para representar o BD e fazer a persistência JPA

@Table(name = "relatos")
@Entity(name = "Relato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Relato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    private String descricao;

    private LocalDateTime data = LocalDateTime.now();

    private String status;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private boolean ativo;

    private String resposta;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int curtidas = 0;




    // Construtor personalizado usando DTO
    public Relato(DTORelato dados) {
        this.aluno = new Aluno(dados.alunoId()); // Presumindo que o DTO tenha o ID do aluno
        this.descricao = dados.descricao();
        this.data = LocalDateTime.now();
        this.status = dados.status();
        this.tipo = dados.tipo();
        this.ativo = true;
    }

    // Método para atualizar as informações do relato
    public void atualizarInformacoes(DTOAttRelato dados) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
        if (dados.tipo() != null) {
            this.tipo = dados.tipo();
        }
    }

    // Método para "excluir" o relato, marcando-o como inativo
    public void excluir() {
        this.ativo = false;
    }

    // Método para obter o ID do aluno
    public Long getAlunoId() {
        return aluno != null ? aluno.getId() : null;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
        this.status = "respondido";
    }

    public String getFormattedId() {
        return String.format("#%05d", id);
    }

    public void curtir() {
        this.curtidas++;
    }
}

