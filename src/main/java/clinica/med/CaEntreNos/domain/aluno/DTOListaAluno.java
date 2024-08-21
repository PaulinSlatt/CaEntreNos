package clinica.med.CaEntreNos.domain.aluno;

public record DTOListaAluno(Long id, String nome, String login, String ano, String matricula) {

    public DTOListaAluno(Aluno aluno) {
        this(aluno.getId(), aluno.getNome(), aluno.getLogin(), aluno.getAno(), aluno.getMatricula());
    }
}
