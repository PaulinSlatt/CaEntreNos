package clinica.med.CaEntreNos.domain.relato;
import clinica.med.CaEntreNos.domain.admin.Admin;
import clinica.med.CaEntreNos.domain.aluno.Aluno;
import clinica.med.CaEntreNos.domain.responsavel.Responsavel;

import java.util.List;
import java.util.stream.Collectors;
public class RelatoService {

    private final RelatoRepository relatoRepository;

    public RelatoService(RelatoRepository relatoRepository) {
        this.relatoRepository = relatoRepository;
    }

    public List<Relato> listarRelatosParaAdmin(Admin admin) {

        return relatoRepository.findAll();
    }

    public List<Relato> listarRelatosParaAluno(Aluno aluno) {

        return relatoRepository.findAll().stream()
                .filter(relato -> relato.getTipo() != Tipo.ABUSO && relato.getTipo() != Tipo.SEGURANÇA)
                .collect(Collectors.toList());
    }

    public List<Relato> listarRelatosParaResponsavel(Responsavel responsavel) {

        return relatoRepository.findAll().stream()
                .filter(relato -> relato.getTipo() != Tipo.ABUSO && relato.getTipo() != Tipo.SEGURANÇA)
                .collect(Collectors.toList());
    }
}

