package clinica.med.Projeto_Clinica.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //mostra que queremos carregar alguma regra de negocio, serviço.
// interface que fala que classe para autentificacao userdetails...
public class AutenticacaoService implements UserDetailsService    {

    @Autowired
    private UsuarioRepository repository;

//sempre que formos fazer login, spring chamará essa classe e esse metodo.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
