package study.sang.androidbasestudy.error;

/**
 * 作者： ${桑小年} on 2016/4/24.
 * 努力，为梦长留
 */
public class MyThrowError extends RuntimeException {
    public MyThrowError(String error){
        super(error);
    };
    public MyThrowError(){};
}
