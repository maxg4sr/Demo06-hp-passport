package tw.hp.demo05.hp.passport.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Admin implements Serializable {
    /**
     * 數據id
     */
    private Long id;
    /**
     * 用戶名
     */
    private String username;
    /**
     * 密碼
     */
    private String password;
    /**
     * 暱稱
     */
    private String nickname;
    /**
     * 頭像URL
     */
    private String avatar;
    /**
     * 手機號
     */
    private String phone;
    /**
     * 郵箱
     */
    private String email;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否啟用 0-未啟用 1-啟用
     */
    private Integer enable;
    /**
     * 最後登錄ip地址
     */
    private String lastLoginIp;
    /**
     * 累計登錄次數
     */
    private Integer loginCount;
    /**
     * 最後登陸時間
     */
    private LocalDateTime gmtLastLogin;
    /**
     * 創建時間
     */
    private LocalDateTime gmtCreate;
    /**
     * 修改時間
     */
    private LocalDateTime gmtModified;
}
