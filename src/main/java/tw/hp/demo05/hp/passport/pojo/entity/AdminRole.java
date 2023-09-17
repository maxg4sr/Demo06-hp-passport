package tw.hp.demo05.hp.passport.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdminRole implements Serializable {

    /**
     * 數據id
     */
    private Long id;
    /**
     * 管理員id
     */
    private Long adminId;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 創建時間
     */
    private LocalDateTime gmtCreate;
    /**
     * 最後修改時間
     */
    private LocalDateTime gmtModified;


}
