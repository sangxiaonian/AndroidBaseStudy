package study.sang.androidbasestudy.utils;

import android.util.Log;
public class JLog {
    /**
     *  tag
     */
    public static String tag = "PING";
    /**
     * 是否屏蔽log日志，当为false时，系统不再打印
     */
    private static boolean needLog = true;


    public static void i(String content) {
        if (needLog) {
            Log.i(tag, getLogInfo() + content);
        }
    }

    public static void i(String tag, String content) {
        if (needLog) {
            Log.i(tag, getLogInfo() + content);
        }
    }

    public static void d(String content) {
        if (needLog) {
            Log.d(tag, getLogInfo() + content);
        }
    }

    public static void d(String tag, String content) {
        if (needLog) {
            Log.d(tag, getLogInfo() + content);
        }
    }

    public static void e(String content) {
        if (needLog) {
            Log.e(tag, getLogInfo() + content);
        }
    }

    public static void e(String tag, String content) {
        if (needLog) {
            Log.e(tag, getLogInfo() + content);
        }
    }

    public static void v(String content) {
        if (needLog) {
            Log.v(tag, getLogInfo() + content);
        }
    }

    public static void v(String tag, String content) {
        if (needLog) {
            Log.v(tag, getLogInfo() + content);
        }
    }

    public static void w(String content) {
        if (needLog) {
            Log.w(tag, getLogInfo() + content);
        }
    }

    public static void w(String tag, String content) {
        if (needLog) {
            Log.w(tag, getLogInfo() + content);
        }
    }

    private static String getLogInfo() {
//            return getClassName() + "(" + getLineNumber() + ")" + "$" + getMethodName() + ": ";
        return getClassName()+"==>>"+getMethodName()+"==>>  "+getLineNumber()+":\n"+"       ";
    }

    /**
     * 鑾峰彇Log鎵�湪鐨勭被鍚�锛坓etStackTrace鐨勭储寮曟牴鎹皟鐢ㄧ殑椤哄簭鏉ュ喅瀹氾紝鍙�杩囨墦鍗癓og鏍堟潵鑾峰彇锛�
     * @return
     */
    private static String getClassName() {
        try {
            String classPath = Thread.currentThread().getStackTrace()[5].getClassName();
            return classPath.substring(classPath.lastIndexOf(".") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 鑾峰彇Log鎵�湪鐨勬柟娉曞悕
     * @return
     */
    private static String getMethodName() {
        try {
            return Thread.currentThread().getStackTrace()[5].getMethodName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 鑾峰彇Log鎵�湪鐨勮
     * @return
     */
    private static int getLineNumber() {
        try {
            return Thread.currentThread().getStackTrace()[5].getLineNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}