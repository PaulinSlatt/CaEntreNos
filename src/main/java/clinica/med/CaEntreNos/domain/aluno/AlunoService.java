package clinica.med.CaEntreNos.domain.aluno;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    public AlunoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Aluno cadastrarAluno(DTOCadastroAluno dados) {
        Aluno aluno = new Aluno(dados);
        aluno.setTipoUsuario("ALUNO");
        aluno.setSenha(passwordEncoder.encode(dados.senha())); // Criptografe a senha
        return alunoRepository.save(aluno);
    }

    public Page<Aluno> listarTodos(Pageable paginacao) {
        return alunoRepository.findAllByAtivoTrue(paginacao);
    }

    public Aluno atualizarAluno(DTOAttAluno dados) {
        Aluno aluno = alunoRepository.findById(dados.id())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Atualize as informações do aluno
        aluno.AtualizarInformacoes(dados);

        // Se a senha estiver presente, criptografe-a
        if (dados.senha() != null && !dados.senha().isEmpty()) {
            aluno.setSenha(passwordEncoder.encode(dados.senha()));
        }

        return alunoRepository.save(aluno);
    }

    public void excluirAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        aluno.Excluir();
        alunoRepository.save(aluno);
    }

    public Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public Aluno autenticarAluno(DTOAutenticacaoAluno dados) {
        Aluno aluno = alunoRepository.findByLogin(dados.login())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (!passwordEncoder.matches(dados.senha(), aluno.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return aluno;
    }
}