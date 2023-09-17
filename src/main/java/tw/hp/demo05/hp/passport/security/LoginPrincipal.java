package tw.hp.demo05.hp.passport.security;

import lombok.Data;

import java.io.Serializable;

/**
 * 當前登入的當事人
 */
@Data
public class LoginPrincipal implements Serializable {
    /**
     * 當前登錄的用戶id
     */
    private Long id;

    /**
     * 當前登錄的用戶名
     */
    private String username;
}
