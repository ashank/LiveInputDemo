package org.zhi.liveinputdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * **********************************************************
 *
 * @author liyq
 * @version V1.0.0
 * @ClassName: SharedPreferencesHelper
 * @Description: 保存数据到haredPreferences的帮助类
 * @date 2013-1-12 下午3:42:25
 * @Function List: 1. --- History: author time version desc
 * *********************************************************
 */
@SuppressLint("CommitPrefEdits")
public class SharedPreferencesHelper {
    private SharedPreferences sp;

    private SharedPreferences.Editor editor;

    public static final long DEFUALT_LONG_VALUES = 1L;

    private static final int DEFUALT_INTEGER_VALUES = 0;

//    private static final String PRES_NAME              = "HUANLV"; 用APPDefaultConstant.getSharedPreferencesName()代替

    public static final long VERSION_INTERNAL = 86400000L;

    private static SharedPreferencesHelper helper;

    public SharedPreferencesHelper(Context context) {
        sp = context.getSharedPreferences("huanlv_name", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public SharedPreferencesHelper(Context context, String shareName) {
        sp = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (helper == null) {
            helper = new SharedPreferencesHelper(context.getApplicationContext());
        }
        return helper;
    }

    public static SharedPreferencesHelper getInstance(Context context, String shareName) {
        if (helper == null) {
            helper = new SharedPreferencesHelper(context.getApplicationContext(), shareName);
        }
        return helper;
    }

    /**
     * @param @param key
     * @param @param value
     * @return void
     * @throws
     * @Title: putStringValue
     * @Description: 以键值对的形式保存字符串
     * @author liyq
     * @date 2013-1-8 下午4:12:04
     * @History: author time version desc
     */
    public void putStringValue(String key, String value) {
        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * @param @param key
     * @param @param value
     * @return void
     * @throws
     * @Title: putBooleanValue
     * @Description: 以键值对的形式保存boolean
     * @author liyq
     * @date 2013-1-8 下午4:12:04
     * @History: author time version desc
     */
    public void putBooleanValue(String key, Boolean value) {
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * @param @param key
     * @param @param value
     * @return void
     * @throws
     * @Title: putIntegerValue
     * @Description: 以键值对的形式保存Integer
     * @author liyq
     * @date 2013-1-8 下午4:15:48
     * @History: author time version desc
     */
    public void putIntegerValue(String key, Integer value) {
        editor = sp.edit();
        if (value == null) {
            value = DEFUALT_INTEGER_VALUES;
        }
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * @param @param key
     * @param @param value
     * @return void
     * @throws
     * @Title: putLongValue
     * @Description: 以键值对的形式保存Long
     * @author liyq
     * @date 2013-1-8 下午4:15:48
     * @History: author time version desc
     */
    public void putLongValue(String key, Long value) {
        editor = sp.edit();
        if (value == null) {
            value = DEFUALT_LONG_VALUES;
        }
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * @param @param  key
     * @param @return
     * @return Boolean
     * @throws
     * @Title: getBooleanValue
     * @Description: 获取Boolean数据
     * @author liyq
     * @date 2013-1-9 上午9:38:35
     * @History: author time version desc
     */
    public Boolean getBooleanValue(String key) {
        return sp.getBoolean(key, false);
    }

    /**
     * @param @param  key
     * @param @return
     * @return String
     * @throws
     * @Title: getStringValue
     * @Description: 获取String数据
     * @author liyq
     * @date 2013-1-9 上午9:39:10
     * @History: author time version desc
     */
    public String getStringValue(String key) {
        return sp.getString(key, "");
    }

    /**
     * @param @param  key
     * @param @return
     * @return Integer
     * @throws
     * @Title: getIntegerValue
     * @Description: 获取Integer数据
     * @author liyq
     * @date 2013-1-9 上午9:39:24
     * @History: author time version desc
     */
    public int getIntegerValue(String key) {
        return sp.getInt(key, DEFUALT_INTEGER_VALUES);
    }

    /**
     * @param @param  key
     * @param @return
     * @return Long
     * @throws
     * @Title: getLongValue
     * @Description: 获取Long数据
     * @author liyq
     * @date 2013-1-9 上午9:40:28
     * @History: author time version desc
     */
    public long getLongValue(String key) {
        return sp.getLong(key, DEFUALT_LONG_VALUES);
    }

    /**
     * 是否含有key
     *
     * @param key
     * @return
     */
    public boolean isContainsKey(String key) {
        return sp.contains(key);
    }

    public void clearPreference() {
        editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
