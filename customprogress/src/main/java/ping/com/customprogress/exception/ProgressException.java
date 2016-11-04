package ping.com.customprogress.exception;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/10/26 17:00
 */
public class ProgressException extends RuntimeException{

    public ProgressException(){
        super();
    }

    public ProgressException(String msg){
        super(msg);
    }
}
