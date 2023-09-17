package tw.hp.demo05.hp.passport.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdminDetails extends User {

    private Long id;

    public AdminDetails(
            String username, String password, boolean enable, Collection<? extends GrantedAuthority> authorities){
        super(username,password,enable,true,true,true,authorities);
    }

}