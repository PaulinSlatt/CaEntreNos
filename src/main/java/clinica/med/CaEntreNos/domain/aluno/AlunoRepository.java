package clinica.med.CaEntreNos.domain.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long>{

    Page<Aluno> findAllByAtivoTrue(Pageable paginacao);

    Optional<Aluno> findByLogin(String login);
}
