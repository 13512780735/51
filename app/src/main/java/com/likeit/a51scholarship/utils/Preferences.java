package com.likeit.a51scholarship.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.likeit.a51scholarship.app.MyApplication;


public class Preferences {

	public static class Key {
		public final static String DEVICE_ID = "Guanai_Device_Id";
	}

	private static SharedPreferences instance;

	public static SharedPreferences getInstance(Context... contexts) {
		if (instance == null) {
			Context ctx = null;
			if (contexts == null || contexts.length == 0) {
				ctx = MyApplication.getInstance();
			} else {
				ctx = contexts[0];
			}
			instance = PreferenceManager.getDefaultSharedPreferences(ctx);
		}
		return instance;
	}

	/**
	 * 保存String类型值
	 * 
	 * @param key
	 * @param values
	 * @param context
	 */
	public static void saveString(String key, String values, Context... context) {
		SharedPreferences.Editor editor = getInstance(context).edit();
		editor.putString(key, values);
		editor.commit();
	}

	/**
	 * 获取String类型值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(String key, String defValue,
                                   Context... context) {
		return getInstance().getString(key, defValue);
	}

	/**
	 * 保存int类型值
	 * 
	 * @param context
	 * @param key
	 * @param values
	 */
	public static void saveInt(String key, int values, Context... context) {
		SharedPreferences.Editor editor = getInstance(context).edit();
		editor.putInt(key, values);
		editor.commit();

	}

	/**
	 * 获取int类型值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(String key, int defValue, Context... context) {
		return getInstance(context).getInt(key, defValue);
	}

	/**
	 * 保存boolean类型值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(String key, Boolean value,
                                   Context... context) {
		SharedPreferences.Editor editor = getInstance(context).edit();
		editor.putBoolean(key, value);
		editor.commit();

	}

	/**
	 * 获取boolean类型值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static Boolean getBoolean(String key, Boolean defValue,
                                     Context... context) {
		return getInstance(context).getBoolean(key, defValue);
	}

	/**
	 * 保存long类型值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveLong(String key, Long value, Context... context) {
		SharedPreferences.Editor editor = getInstance(context).edit();
		editor.putLong(key, value);
		editor.commit();

	}

	/**
	 * 获取long类型值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static Long getLong(String key, Long defValue, Context... context) {
		return getInstance(context).getLong(key, defValue);
	}
}
