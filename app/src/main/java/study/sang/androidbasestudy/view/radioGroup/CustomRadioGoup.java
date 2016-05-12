package study.sang.androidbasestudy.view.radioGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

/**
 * 一个自定的动态添加控件的radioGroup，需要自己定义一个RadioButton，并且调用setChild设置
 */
public class CustomRadioGoup extends RadioGroup {

    private int startX = 0, startY = 0, rowNm = 0;

    private List<String> childs = new ArrayList<>();

    private int childId =-1;

    /**
     * 横向间距
     */
    private int childMarginHorizontal = 10;
    /**
     * 纵向间距
     */
    private int childMarginVertical = 10;


    public CustomRadioGoup(Context context) {
        super(context);
        for (int i = 0; i < getChildCount(); i++) {
            RadioButton rb = getChild();
            addView(rb);

            //设置值
            if (childs.size() > i && i > 0) {
                rb.setText(childs.get(i));
            }


        }
    }

    public CustomRadioGoup(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < getChildCount(); i++) {
            RadioButton rb = getChild();
            addView(rb);

            //设置值
            if (childs.size() > i && i > 0) {
                rb.setText(childs.get(i));
            }


        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        if (getChildCount() > 0) {
            startX = 0;
            //父控件高度

            rowNm = 0;
            for (int i = 0; i < getChildCount(); i++) {

                RadioButton rb = (RadioButton) getChildAt(i);
                //测量子控件
                measureChild(rb, widthMeasureSpec, heightMeasureSpec);

                //子控件宽度+起始位置坐标，如果大于父控件高度，就换行
                int w = rb.getMeasuredWidth() + 2 * childMarginHorizontal + startX + getPaddingLeft() + getPaddingRight();
                if (w > getMeasuredWidth()) {
                    startX = 0;
                    rowNm++;
                }
                //否则起始位置后移
                startX += rb.getMeasuredWidth() + 2 * childMarginHorizontal;
                height = (rowNm + 1) * (rb.getMeasuredHeight() + 2 * childMarginVertical) + getPaddingBottom() + getPaddingTop();

            }
        }
        JLog.i(startX + "测量的高度" + height);
        setMeasuredDimension(getMeasuredWidth(), height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        startX = 0;
        startY = 0;
        rowNm = 0;
        for (int i = 0; i < getChildCount(); i++) {

            RadioButton rb = (RadioButton) getChildAt(i);
            int w = rb.getMeasuredWidth() + 2 * childMarginHorizontal + startX + getPaddingLeft() + getPaddingRight();
            if (w > getMeasuredWidth()) {
                startX = 0;
                rowNm++;
            }
            startY = rowNm * (rb.getMeasuredHeight() + 2 * childMarginVertical);
            JLog.i("=====onLayout=====" + startX + "===" + rowNm + "==" + startY);
            //绘制每个子控件的位置
            rb.layout(startX, startY, startX + rb.getMeasuredWidth(), startY + rb.getMeasuredHeight());
            startX += rb.getMeasuredWidth() + 2 * childMarginHorizontal;


        }


    }

    private RadioButton getChild() {
        if (childId==-1){
            throw new RuntimeException("没有设置子控件");
        }
        return (RadioButton) LayoutInflater.from(getContext()).inflate(
                childId, this, false);
    }

    /**
     * 设置子控件，最好为根节点为RadioButton 的layout
     * @param layout_id 子控件的Id
     */
    public void setChild(int layout_id){
        this.childId=layout_id;
    }

    /**
     * 添加一个名字为str的控件
     * @param str
     */
    public void addView(String str) {
        childs.add(str);
        RadioButton child = getChild();
        child.setText(str);
        addView(child);
        postInvalidate();
    }
}
