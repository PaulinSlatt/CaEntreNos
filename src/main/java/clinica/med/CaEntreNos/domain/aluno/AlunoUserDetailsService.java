package clinica.med.CaEntreNos.domain.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AlunoUserDetailsService implements UserDetailsService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Aluno aluno = alunoRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Aluno não encontrado"));

        // Verifique se o login é realmente de um aluno
        if (!aluno.getTipoUsuario().equals("ALUNO")) {
            throw new UsernameNotFoundException("Usuário não é um Aluno");
        }

        return new AlunoUserDetails(aluno);
    }
}