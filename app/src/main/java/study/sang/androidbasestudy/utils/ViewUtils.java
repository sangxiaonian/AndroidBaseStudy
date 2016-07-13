package study.sang.androidbasestudy.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/11 14:50
 */
public class ViewUtils {

    /**
     * 将View的内容转化成Bitmap的方法
     * @param v
     * @return
     */
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
