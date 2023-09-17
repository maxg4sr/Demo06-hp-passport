package tw.hp.demo05.hp.passport.ex.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.hp.demo05.hp.passport.ex.ServiceException;
import tw.hp.demo05.hp.passport.web.JsonResult;
import tw.hp.demo05.hp.passport.web.ServiceCode;

import java.util.List;
import java.util.StringJoiner;

/**
 * 統一異常處理類
 */
// @ControllerAdvice
// @ResponseBody
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e){
        log.error("統一處理ServiceException異常，將向客戶端響應:{}",e.getMessage());
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handleBindException(BindException e){
        log.error("統一處理BindException異常，將向客戶端響應:{}",e.getMessage());
        // 多個錯誤
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringJoiner joiner = new StringJoiner("; ","錯誤提示：","");
        for(FieldError fieldError : fieldErrors){
            joiner.add(fieldError.getDefaultMessage());
        }
        String message = joiner.toString();
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, message);
    }

    @ExceptionHandler
    public JsonResult handleThrowable(Throwable e){
        log.error("統一處理未明確的異常【{}】，將向客戶端響應:{}",e.getClass().getName(),e.getMessage());
        String message = "服務器忙，請聯繫管理員！";
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN,message);
    }

}