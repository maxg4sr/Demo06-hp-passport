package tw.hp.demo05.hp.passport.ex;

/**
 * 業務異常
 */
public class ServiceException extends RuntimeException{

    private Integer serviceCode;

    public ServiceException(Integer serviceCode,String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

    public Integer getServiceCode(){
        return serviceCode;
    }

}