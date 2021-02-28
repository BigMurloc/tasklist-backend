package pl.tasklist.tasklistbackend.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.tasklist.tasklistbackend.entity.Authority;
import pl.tasklist.tasklistbackend.entity.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AppAuthentication extends AbstractAuthenticationToken {


    private final User authenticatedUser;

    public AppAuthentication(User authenticatedUser) {
        super(toGrantedAuthorities(authenticatedUser.getAuthority()));
        this.authenticatedUser = authenticatedUser;
        setAuthenticated(true);
    }

    private static Collection<? extends GrantedAuthority> toGrantedAuthorities(Set<Authority> authority) {
        return authority
                .stream()
                .map((it) -> it.getAuthority()).collect(Collectors.toSet())
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public Object getCredentials() {
        return authenticatedUser.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }
}
