package study.sang.androidbasestudy.utils;

import android.content.Context;
import android.util.TypedValue;

import java.math.BigDecimal;

/**
 * 作者： ${桑小年} on 2016/5/22.
 * 努力，为梦长留
 */
public class Utils {

    /**
     * 获取两位小数
     *
     * @param data
     * @return
     */
    public static float get2Double(double data) {
        BigDecimal b = new BigDecimal(data);
//        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //   b.setScale(2,   BigDecimal.ROUND_HALF_UP)   表明四舍五入，保留两位小数
        float v = (float) Double.parseDouble(String.format("%.2f", data));

        return v;
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    public static int dp2px(int dpVal, Context c) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, c.getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(int spVal, Context c) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, c.getResources().getDisplayMetrics());

    }
}
