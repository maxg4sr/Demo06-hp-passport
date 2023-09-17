package tw.hp.demo05.hp.passport.web;

/**
 * 業務狀態碼(常量)的類
 */
public final class ServiceCode {

    /**
     * 成功
     */
    public static final Integer OK = 20000;

    /**
     * 錯誤：請求格式有誤
     */
    public static final Integer ERR_BAD_REQUEST = 40000;

    /**
     * 錯誤：JWT數據錯誤 - 可能被惡意篡改
     */
    public static final Integer ERR_JWT_INVALID = 40001;

    /**
     * 錯誤：JWT過期
     */
    public static final Integer ERR_JWT_EXPIRED = 40300;

    /**
     * 錯誤：不存在(不存在數據)
     */
    public static final Integer ERR_NOT_FOUND = 40400;

    /**
     * 錯誤：衝突(出現重複的數據)
     */
    public static final Integer ERR_CONFLICT = 40900;


    /**
     * 錯誤：插入失敗
     */
    public static final Integer ERR_INSERT = 50000;
    /**
     * 錯誤：刪除失敗
     */
    public static final Integer ERR_DELETE = 50001;
    /**
     * 錯誤：更新失敗
     */
    public static final Integer ERR_UPDATE = 50002;
    /**
     * 錯誤：未處理的異常
     */
    public static final Integer ERR_UNKNOWN = 59999;

}
