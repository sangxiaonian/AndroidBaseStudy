package study.sang.androidbasestudy.view.index;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/18 10:17
 */
public class LinePagerIndex extends BasePagerIndex {


    public LinePagerIndex(Context context) {
        super(context);
    }

    public LinePagerIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinePagerIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    public void drawIndex(Canvas canvas, int maxChildWidth) {
        super.drawIndex(canvas, maxChildWidth);
        canvas.drawLine(maxChildWidth * currentIndex, getHeight(), maxChildWidth * currentIndex + maxChildWidth, getHeight(), indexPaint);
    }

    @Override
    public void addIndexView(final String indexName) {
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(getContext());
        textView.setText(indexName);
        textView.setTextColor(Color.BLACK);
        mDatas.add(textView);
        textView.setTag(mDatas.size() - 1);
        textView.setGravity(Gravity.CENTER);
        setGravity(Gravity.CENTER);
        textView.setClickable(true);
        addView(textView, params);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = (int) v.getTag();
                ((TextView) v).setTextColor(Color.RED);
                for (int i = 0; i < LinePagerIndex.this.getChildCount(); i++) {
                    if (index == i) {
                        postAni(index);
                        continue;
                    } else {
                        ((TextView) LinePagerIndex.this.getChildAt(i)).setTextColor(Color.BLACK);
                    }

                }


            }
        });
    }



}
