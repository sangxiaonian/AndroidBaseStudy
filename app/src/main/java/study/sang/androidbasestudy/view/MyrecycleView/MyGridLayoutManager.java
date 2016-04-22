package study.sang.androidbasestudy.view.MyrecycleView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 *
 * Created by ping on 2016/4/22.
 */
public class MyGridLayoutManager extends GridLayoutManager {

    private int mSpanCount;
    private int mHeardCont;
    private int mFootCount;

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        spanSizeLookup  = new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                //如果是在头布局，占满，否则就占一个位置
                if (position>=mHeardCont||position<getChildCount()-mFootCount-1){
                    return 1;
                }else
                return getSpanCount();
            }
        };
        super.setSpanSizeLookup(spanSizeLookup);
    }

    /**
     * 设置头布局个数
     * @param mHeardCont 头布局数量
     */
    public void setmHeardCont(int mHeardCont){
        this.mHeardCont =mHeardCont;
    }

    /**
     * 设置脚布局数量
     * @param mFootCount
     */
    public void setmFootCount(int mFootCount){
        this.mFootCount=mFootCount;
    }
}
