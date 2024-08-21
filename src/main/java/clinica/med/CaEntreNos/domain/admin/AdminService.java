package clinica.med.CaEntreNos.domain.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    public AdminService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Admin cadastrarAdmin(DTOCadastroAdmin dados) {
        Admin admin = new Admin(dados);
        admin.setSenha(passwordEncoder.encode(dados.senha())); // Criptografe a senha
        admin.setTipoUsuario("ADMIN"); // Definindo explicitamente o tipo de usuário
        return adminRepository.save(admin);
    }

    public Page<Admin> listarTodos(Pageable paginacao) {
        return adminRepository.findAllByAtivoTrue(paginacao);
    }

    public Admin atualizarAdmin(DTOAttAdmin dados) {
        Admin admin = adminRepository.findById(dados.id())
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));

        // Atualize as informações do admin
        admin.AtualizarInformacoes(dados);

        // Se a senha estiver presente, criptografe-a
        if (dados.senha() != null && !dados.senha().isEmpty()) {
            admin.setSenha(passwordEncoder.encode(dados.senha()));
        }

        return adminRepository.save(admin);
    }

    public void excluirAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));
        admin.Excluir();
        adminRepository.save(admin);
    }

    public Admin buscarAdminPorId(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));
    }

    public Admin autenticarAdmin(DTOAutenticacaoAdmin dados) {
        Admin admin = adminRepository.findByLogin(dados.login())
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));

        if (!passwordEncoder.matches(dados.senha(), admin.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return admin;
    }
}