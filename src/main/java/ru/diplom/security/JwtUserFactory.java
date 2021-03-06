package security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.cinimex.scheduler.security.entity.AuthUser;
import ru.cinimex.scheduler.security.entity.Authority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(AuthUser authUser) {
        System.out.println(authUser.getAuthorities());
        return new JwtUser(
                authUser.getId(),
                authUser.getFirstname(),
                authUser.getLastname(),
                authUser.getEmail(),
                authUser.getPassword(),
                mapToGrantedAuthorities(new ArrayList(authUser.getAuthorities())),
                authUser.getActive(),
                authUser.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode().name()))//.name()))
                .collect(Collectors.toList());
    }
}
