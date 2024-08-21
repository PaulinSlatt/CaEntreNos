package clinica.med.CaEntreNos.domain.admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Page<Admin> findAllByAtivoTrue(Pageable paginacao);

    Optional<Admin> findByLogin(String login);

}
