﻿package com.hongguang.jaia_utils;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

/**
 * @author ads
 *
 */
/**
 * @author ads
 *
 */
/**
 * @author ads
 *
 */
/**
 * @author ads
 *
 */
/**
 * @author ads
 *
 */
/**
 * @author ads
 *
 */
/**
 * @author ads
 * 
 */
public class UpdateApkSearchUtils {

	private static int INSTALLED = 0;
	private static int UNINSTALLED = 1;
	private static int INSTALLED_UPDATE = 2;
	private Context context;

	public UpdateApkSearchUtils(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 检测手机是否安装本地apk,是否需要重新安装
	 * 
	 * @param file
	 * @return
	 */
	public int FindAllAPKFile(File file) {
		if (file.isFile()) {
			String name_s = file.getName();
			if (name_s.toLowerCase().endsWith(".apk")) {
				String apk_path = file.getAbsolutePath();
				PackageManager pm = context.getPackageManager();
				PackageInfo packageInfo = pm.getPackageArchiveInfo(apk_path,
						PackageManager.GET_ACTIVITIES);
				int versionNum = packageInfo.versionCode;
				String packageName = packageInfo.packageName;
				return doType(pm, packageName, versionNum);
			}
		} else {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (File file_str : files) {
					FindAllAPKFile(file_str);
				}
			}
		}
		return -1;
	}

	private int doType(PackageManager pm, String packageName, int versionCode) {
		List<PackageInfo> pakageinfos = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo pi : pakageinfos) {
			String pi_packageName = pi.packageName;
			int pi_versionCode = pi.versionCode;
			// 如果这个包名在系统已经安装过的应用中存在
			if (packageName.endsWith(pi_packageName)) {
				if (versionCode == pi_versionCode) {
					return INSTALLED;
				} else if (versionCode > pi_versionCode) {
					return INSTALLED_UPDATE;
				}
			}
		}
		return UNINSTALLED;
	}

	/**
	 * 判断本地apk文件 是不是网络上的最新版本
	 * 
	 * @param lastVersion
	 * @param lastPackageName
	 * @param file
	 * @return
	 */

	public boolean fileIsWebLastestVersion(int lastVersion,
			String lastPackageName, File file) {
		if (file.isFile()) {
			String name_s = file.getName();
			// 把name_s 里面所有字母换成小写,判断是否是以apk结尾
			if (name_s.toLowerCase().endsWith(".apk")) {
				String apk_path = file.getAbsolutePath();
				System.out.println("file.getAbsolutePath():"
						+ file.getAbsolutePath());
				PackageManager pm = context.getPackageManager();
				// 获取未安装 apk文件 packageInfo信息
				PackageInfo packageInfo = pm.getPackageArchiveInfo(apk_path,
						PackageManager.GET_ACTIVITIES);
				// 本地apk versionCode
				int versionNum = packageInfo.versionCode;
				// 本地apk 包名
				String packageName = packageInfo.packageName;

				if (packageName.endsWith(lastPackageName)
						&& lastVersion == versionNum) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 判断安装的apk是不是网络上的最新版本
	 * 
	 * @param lastVersion
	 * @param lastPackageName
	 * @return
	 * 
	 *         通过接收服务器传递过来的信息 最新的版本 和 包名 返回true代表本地版本是最新的 返回false代表需要下载服务器apk
	 */
	public boolean installedApkIsWebLastestVersion(int lastVersion,
			String lastPackageName) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			int version = info.versionCode;
			String packageName = info.packageName;
			if (lastPackageName.equals(packageName) && lastVersion <= version) {
				return true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 安装apk
	 * 
	 * @param file
	 */
	public void installApk(File file) {
		/*
		 * Intent intent = new Intent(); intent.setAction(Intent.ACTION_DELETE);
		 * intent.setData(Uri.parse("package:com.hongguang.love"));
		 * context.startActivity(intent);
		 */

		// 判断文件是否存在 file是否代表一个标准文件
		if (!file.exists() || !file.isFile()) {
			return;
		}
		Intent intent1 = new Intent(Intent.ACTION_VIEW);
		intent1.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent1);
	}
}
