package study.sang.androidbasestudy.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public class ToastUtil {
	private static Toast toast = null;
	private static Toast cusToast=null;
	/**
	 * 一个基本的土司工具,不管在子线程还是在ui线程,都可以直接使用
	 * @param context  上下文
	 * @param msg 要显示的内容
	 */
	public static void showToast(final Context context, final String msg) {
		
		if (Looper.myLooper()==Looper.getMainLooper()) {
			showToastContent(context, msg);
		}else {
			((Activity)context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showToastContent(context, msg);
				}
			});
			
		}
	}


	/**
	 * 一个可以根据传入的布局Id来自定义土司的方法,可以在子线程中直接使用
	 * @param context 上下文
	 * @param layoutID 自定义布局Id
	 */
	public static void showToast(final Context context, final int layoutID) {
		if (Looper.myLooper()==Looper.getMainLooper()) {
			showToastContent(context,layoutID);
		}else {
			((Activity)context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showToastContent(context,layoutID);
				}
			});
		}
	}
	
	private static void showToastContent(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}
	
	private static void showToastContent(Context context , int layoutID) {
		View view = LayoutInflater.from(context).inflate(layoutID, null);
		if (cusToast!=null) {
			
		}
		if (cusToast == null) {
			cusToast =new Toast(context);
			cusToast.setView(view);
		} else {
			cusToast.setView(view);
		}
		cusToast.show();
	}
}
