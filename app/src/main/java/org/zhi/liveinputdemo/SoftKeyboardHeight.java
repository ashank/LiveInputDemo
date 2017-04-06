package org.zhi.liveinputdemo;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhiyahan on 2017/3/1.
 */

public class SoftKeyboardHeight {

    private  int keyboardHeight = 0;
    private boolean isVisiableForLast1 = false;
    private Context context;


    public SoftKeyboardHeight(Context context) {
        this.context=context;
    }


    /**
     * 获取键盘的高度
     * @param decorView
     * @return
     */
    public int getSoftKeyHeight(View decorView){
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        //计算出可见屏幕的高度
        int displayHight = rect.bottom - rect.top;
        //获得屏幕整体的高度
        int hight = decorView.getHeight();
        boolean visible = (double) displayHight / hight < 0.8;
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(visible&&visible!= isVisiableForLast1){
            //获得键盘高度，键盘有高度，则代表弹出
            keyboardHeight = hight - displayHight-statusBarHeight-getVirtualBarHeigh();
            Log.e("e","height==="+keyboardHeight);
            return keyboardHeight;
        }
        isVisiableForLast1 = visible;

        return 0;
    }


    /**获取虚拟功能键高度 */
    public int getVirtualBarHeigh() {
        int vh = 0;
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    /**
     * 保存高度
     */
    public void saveHeight(){
        SharedPreferencesHelper.getInstance(context).putIntegerValue("height",keyboardHeight);
    }


    /**
     * 获取高度
     * @return
     */
    public int getHeight(){
        return SharedPreferencesHelper.getInstance(context).getIntegerValue("height");
    }



    public boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }


}
