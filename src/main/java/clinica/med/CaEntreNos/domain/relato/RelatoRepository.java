package clinica.med.CaEntreNos.domain.relato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Classe que pega os dados de medico e faz a persistencia com o banco de dados

public interface RelatoRepository extends JpaRepository<Relato, Long> {

    Page<Relato> findAllByAtivoTrue(Pageable paginacao);
    Page<Relato> findByTipoNotInAndAtivoTrue(List<Tipo> tipos, Pageable pageable);
}
