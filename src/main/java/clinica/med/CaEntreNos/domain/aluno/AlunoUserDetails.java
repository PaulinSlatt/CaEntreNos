package clinica.med.CaEntreNos.domain.aluno;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class AlunoUserDetails implements UserDetails {
    private final Aluno aluno;

    public AlunoUserDetails(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(aluno.getTipoUsuario()));
    }

    @Override
    public String getPassword() {
        return aluno.getSenha(); // Assumindo que você tem um campo senha
    }

    @Override
    public String getUsername() {
        return aluno.getLogin(); // Ou outro campo único, como login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return aluno.getAtivo();
    }



}