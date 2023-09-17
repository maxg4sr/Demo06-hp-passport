package tw.hp.demo05.hp.passport.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理員的登錄數據
 */
@Data
public class AdminLoginDTO implements Serializable {

    /**
     * 管理員用戶名
     */
    @ApiModelProperty("用戶名")
    private String username;

    /**
     * 管理員密碼
     */
    @ApiModelProperty("密碼")
    private String password;

}