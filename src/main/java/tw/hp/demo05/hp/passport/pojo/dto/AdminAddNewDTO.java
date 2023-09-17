package tw.hp.demo05.hp.passport.pojo.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminAddNewDTO {

    /**
     * 用戶名
     */
    @ApiModelProperty(value = "用戶名")
    private String username;
    /**
     * 密碼
     */
    @ApiModelProperty(value = "密碼")
    private String password;
    /**
     * 暱稱
     */
    @ApiModelProperty(value = "暱稱")
    private String nickname;
    /**
     * 頭像URL
     */
    @ApiModelProperty(value = "頭像url")
    private String avatar;
    /**
     * 手機號
     */
    @ApiModelProperty(value = "手機號碼")
    private String phone;
    /**
     * 郵箱
     */
    @ApiModelProperty(value = "電子郵箱")
    private String email;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 是否啟用 0-未啟用 1-啟用
     */
    @ApiModelProperty(value = "是否啟用，1=啟用，0=未啟用")
    private Integer enable;

}