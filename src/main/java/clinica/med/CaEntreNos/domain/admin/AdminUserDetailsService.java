package clinica.med.CaEntreNos.domain.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin não encontrado"));

        // Verifique se o login é realmente de um admin
        if (!admin.getTipoUsuario().equals("ADMIN")) {
            throw new UsernameNotFoundException("Usuário não é um Admin");
        }

        return new AdminUserDetails(admin);
    }
}