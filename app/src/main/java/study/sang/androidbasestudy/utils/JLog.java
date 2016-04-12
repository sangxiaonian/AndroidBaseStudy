package study.sang.androidbasestudy.utils;

import android.util.Log;

import java.util.Hashtable;

/**
 * 作者： ${桑小年} on 2016/4/4.
 * 努力，为梦长留
 */
public class JLog {

    private final static boolean logFlag = true;

    public final static String tag = "PING";
    private final static int logLevel = Log.VERBOSE;
    private static Hashtable<String, JLog> sLoggerTable = new Hashtable<String, JLog>();
    private static String mClassName;

    private static JLog jlog;
    private static JLog klog;

    private static final String JAMES = "@james@ ";
    private static final String KESEN = "@kesen@ ";

    private JLog(String name) {
        mClassName = name;
    }

    /**
     * @param className
     * @return
     */
    @SuppressWarnings("unused")
    private static JLog getLogger(String className) {
        JLog classLogger = (JLog) sLoggerTable.get(className);
        if (classLogger == null) {
            classLogger = new JLog(className);
            sLoggerTable.put(className, classLogger);
        }
        return classLogger;
    }

    /**
     * Purpose:Mark user one
     *
     * @return
     */
    public static JLog kLog() {
        if (klog == null) {
            klog = new JLog(KESEN);
        }
        return klog;
    }

    /**
     * Purpose:Mark user two
     *
     * @return
     */
    public static JLog jLog() {
        if (jlog == null) {
            jlog = new JLog(JAMES);
        }
        return jlog;
    }

    /**
     * Get The Current Function Name
     *
     * @return
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            return mClassName + "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + " >>>>> "
                    + st.getMethodName() +  " >>>>>> "+ st.getLineNumber() +" ]";
        }
        return null;
    }

    /**
     * The Log Level:i
     *
     * @param str
     */
    public static void i(Object str) {
        if (logFlag) {
            if (logLevel <= Log.INFO) {
                String name = getFunctionName();
                if (name != null) {
                    Log.i(tag, name + " - " + str);
                } else {
                    Log.i(tag, str.toString());
                }
            }
        }

    }

    /**
     * The Log Level:d
     *
     * @param str
     */
    public static void d(Object str) {
        if (logFlag) {
            if (logLevel <= Log.DEBUG) {
                String name = getFunctionName();
                if (name != null) {
                    Log.d(tag, name + " - " + str);
                } else {
                    Log.d(tag, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:V
     *
     * @param str
     */
    public static void v(Object str) {
        if (logFlag) {
            if (logLevel <= Log.VERBOSE) {
                String name = getFunctionName();
                if (name != null) {
                    Log.v(tag, name + " - " + str);
                } else {
                    Log.v(tag, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:w
     *
     * @param str
     */
    public static void w(Object str) {
        if (logFlag) {
            if (logLevel <= Log.WARN) {
                String name = getFunctionName();
                if (name != null) {
                    Log.w(tag, name + " - " + str);
                } else {
                    Log.w(tag, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param str
     */
    public static void e(Object str) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                String name = getFunctionName();
                if (name != null) {
                    Log.e(tag, name + " - " + str);
                } else {
                    Log.e(tag, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param ex
     */
    public static void e(Exception ex) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                Log.e(tag, "error", ex);
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param log
     * @param tr
     */
    public static void e(String log, Throwable tr) {
        if (logFlag) {
            String line = getFunctionName();
            Log.e(tag, "{Thread:" + Thread.currentThread().getName() + "}"
                    + "[" + mClassName + line + ":] " + log + "\n", tr);
        }
    }
}

