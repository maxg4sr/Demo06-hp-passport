package tw.hp.demo05.hp.passport.web;

import lombok.Data;
import tw.hp.demo05.hp.passport.ex.ServiceException;


/**
 * 響應結果自定義的類
 */
@Data
public class JsonResult {

    /**
     * 業務狀態碼
     */
    private Integer code;

    /**
     * 錯誤時的消息
     */
    private String message;

    /**
     * 處理成功時，需要響應到客戶端的數據
     */
    private Object data;

    // 響應成功(不需要響應數據)
    public static JsonResult ok(){
        return ok(null);
    }

    // 響應成功(需要響應數據)
    public static JsonResult ok(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.code = ServiceCode.OK;
        jsonResult.data = data;
        return jsonResult;
    }

    public static JsonResult fail(ServiceException e){
        return fail(e.getServiceCode(),e.getMessage());
    }

    public static JsonResult fail(Integer code,String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.code = code;
        jsonResult.message = message;
        return jsonResult;
    }

}