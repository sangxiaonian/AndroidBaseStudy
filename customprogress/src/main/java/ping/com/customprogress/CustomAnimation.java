package ping.com.customprogress;

import android.animation.ValueAnimator;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/9/22 9:43
 */
public class CustomAnimation {

    /**
     * 阻尼系数,默认情况下四个周期后停止,从0开始增加
     * 通过使用${s}
     */
    public static final int DAMP=1;

    public static final int normal = 2;

    private int cycle = 2;


    private static CustomAnimation animation;

    private CustomAnimation(){

    }

    public static CustomAnimation getInstance(){
        if (animation ==null){
            animation = new CustomAnimation();
        }
        return animation;
    }

    /**
     * 根据状态创建不同的值
     * @param state
     * @return
     */
    public ValueAnimator creatAnimotion(final int state, final AnimotionListener listener){
        ValueAnimator creatAnimotion = ValueAnimator.ofFloat(0f, 1f);
        creatAnimotion.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = animation.getAnimatedFraction();
                float result = value;
                switch (state){
                    case DAMP:
                         result= (float) ((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * cycle / 180-90*Math.PI/180)));
                        break;
                    case normal:
                        result=value;
                        break;

                    default:
                        break;

                }

                if (listener!=null){
                    listener.onUpData(result);
                }

            }
        });
        return creatAnimotion;
    }

    public interface AnimotionListener{
        void onUpData(float value);
    }

}
