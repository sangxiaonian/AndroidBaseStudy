package ping.com.customprogress.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * Description：一个获取设备相应信息的类
 *
 * @Author：桑小年
 * @Data：2016/7/11 14:07
 */
public class DeviceUtils {

    /**
     * 获取设备CPU核数,至少是1个
     * @return
     */
    public static int getCPUCount(){
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取屏幕的宽高
     * @param context 上下文
     * @return 返回宽高的int数据
     */
    public static int[] getWindowWidth(Activity context){
        WindowManager wm1 = context.getWindowManager();
        int width = wm1.getDefaultDisplay().getWidth();//屏幕的宽度
        int height= wm1.getDefaultDisplay().getHeight();//屏幕的宽度
        int[] a= {width,height};
        return a;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机最小滑动距离
     * @param context
     * @return
     */
    public static float getMinScroll(Context context){
        int slop = ViewConfiguration.get(context).getScaledTouchSlop();
        return slop;
    }
}
