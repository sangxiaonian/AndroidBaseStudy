package study.sang.androidbasestudy.view.shuibowen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description：模仿360流量使用量的圆球效果,主要是对自定义控件的联系
 *
 * @Author：桑小年
 * @Data：2016/7/7 9:59
 */
public class FlowView extends View {
    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
    }
}
