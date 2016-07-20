package study.sang.androidbasestudy.view.index;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import study.sang.androidbasestudy.utils.DeviceUtils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/19 15:45
 */
public class TrianglePagerIndex extends BasePagerIndex {

    private Path mPath;

    public TrianglePagerIndex(Context context) {
        super(context);
    }

    public TrianglePagerIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrianglePagerIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        mPath = new Path();
    }

    @Override
    public void drawIndex(Canvas canvas, int maxChildWidth) {
        super.drawIndex(canvas, maxChildWidth);
        mPath.moveTo(maxChildWidth*currentIndex+maxChildWidth/3,getHeight());
        mPath.lineTo(maxChildWidth*currentIndex+maxChildWidth*2/3,getHeight());
        mPath.lineTo(maxChildWidth*currentIndex+maxChildWidth/2,getHeight()- DeviceUtils.dip2px(getContext(),8));
        mPath.lineTo(maxChildWidth*currentIndex+maxChildWidth/3,getHeight());
        canvas.drawPath(mPath,indexPaint);
        mPath.reset();
    }

    @Override
    public void addIndexView(String indexName) {
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(getContext());
        textView.setText(indexName);
        textView.setTextColor(Color.BLACK);
        mDatas.add(textView);
        textView.setTag(mDatas.size() - 1);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        addView(textView, params);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = (int) v.getTag();
                ((TextView) v).setTextColor(Color.RED);
                for (int i = 0; i < TrianglePagerIndex.this.getChildCount(); i++) {
                    if (index == i) {
                        moveIndex(index);
                        continue;
                    } else {
                        ((TextView) TrianglePagerIndex.this.getChildAt(i)).setTextColor(Color.BLACK);
                    }

                }


            }
        });
    }
}
