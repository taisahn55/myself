package com.hongguang.jaia_utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DateSharedPreferences {
	private SharedPreferences.Editor editor;
	private SharedPreferences settings;
	private static DateSharedPreferences instance = null;

	private DateSharedPreferences() {
	};

	public static DateSharedPreferences getInstance() {
		if (instance == null) {
			instance = new DateSharedPreferences();
		}
		return instance;
	}

	// 保存字符串
	public void save(Context context, String name, String content) {
		dateshared(context, "displaymetrics"); // 创建缓存 名字是displaymetrics
		editor.putString(name, content);
		editor.commit();
	}

	// 读取字符串
	public String get(Context context, String name, String defaultName) {
		dateshared(context, "displaymetrics"); // 创建缓存 名字是displaymetrics
		return settings.getString(name, defaultName);
	}

	public void saveLogin(Context context, String loginJson) {
		dateshared(context, "Salesman"); // 创建缓存 Salesman
		editor.putString("saleman", loginJson); // 保存登陆缓存
		editor.commit();
	}

	public String getLogin(Context context) {
		dateshared(context, "Salesman"); // 创建缓存 Salesman
		return settings.getString("saleman", null); // 获取登陆数据
	}

	public void clearLogin(Context context) {
		dateshared(context, "Salesman"); // 创建缓存 Salesman
		editor.clear(); // 清空缓存所有数据
		editor.commit(); // 提交
	}

	private void dateshared(Context context, String dbname) {
		settings = context.getSharedPreferences(dbname, 0);
		editor = settings.edit();
	}
}
