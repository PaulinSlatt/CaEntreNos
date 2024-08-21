package clinica.med.CaEntreNos.domain.responsavel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long>{

    Page<Responsavel> findAllByAtivoTrue(Pageable paginacao);

}
