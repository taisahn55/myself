package com.hongguang.jaia_utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

public class FileCache {

	private static String CACHE_FILE_PATH = null;
	private static FileCache fileCache = null;
	private final static int CACHE_SIZE = 50 * 1024 * 1024;
	private static String FLAG = "cache:";
	private static String LARGE_FLAG = "large_cache:";
	private FileCache() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			CACHE_FILE_PATH = Environment.getExternalStorageDirectory()
					.getPath() + File.separator + "LoveCache";
		} else {
			CACHE_FILE_PATH = File.separator + "LoveCache";
		}
	}

	public static FileCache getInstance() {
		if (fileCache == null) {
			fileCache = new FileCache();
		}
		return fileCache;
	}

	public Bitmap getBitmap(String url, boolean isLarge) {
		File file = new File(getCacheFilePath(url, isLarge));
		if (file.exists()) {
			file.setLastModified(System.currentTimeMillis());
			return BitmapFactory.decodeFile(file.getPath());
		}
		return null;
	}

	public void putBitmap(String url, Bitmap bitmap, boolean b, boolean isLarge) {
		long bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
		if ((int) getCachedLarge() >= CACHE_SIZE
				|| bitmapSize >= getAvailableSpaceSize()) {
			if (b) {
				b = false;
				removeData();
				putBitmap(url, bitmap, b, isLarge);
			} else {
				return;
			}
		}
		File dir = new File(CACHE_FILE_PATH);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		File file = new File(getCacheFilePath(url, isLarge));
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			file.setLastModified(System.currentTimeMillis());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeData() {
		File dir = new File(CACHE_FILE_PATH);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		Arrays.sort(files, new ModifyDateUpdateComparator());
		int len = (int) (files.length * 0.4) + 1;
		for (int i = 0; i < len; i++) {
			files[i].delete();
		}
	}

	public long getCachedLarge() {
		File dir = new File(CACHE_FILE_PATH);
		File[] files = dir.listFiles();
		if (files == null) {
			return 0;
		}
		long large = 0;
		for (File f : files) {
			if (f.getName().contains("cache:")) {
				large += f.length();
			}
		}
		return large;
	}

	public long getAvailableSpaceSize() {
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		return (long) statFs.getAvailableBlocks()
				* (long) statFs.getBlockSize();
	}

	public String getShortName(String url, boolean isLarge) {
		int i = url.lastIndexOf(File.separator);
		if (i == -1) {
			return "";
		}
		String pathu = url.substring(0, i);
		int t = pathu.lastIndexOf(File.separator);
		String temp = null;
		if (t == -1) {
			temp = System.currentTimeMillis() + "";
		} else {
			temp = pathu.substring(t + 1);
		}
		if (isLarge) {
			return temp + url.substring(i + 1) + LARGE_FLAG;
		} else {
			return temp + url.substring(i + 1) + FLAG;
		}
	}

	public String getCacheFilePath(String url, Boolean isLarge) {
		return CACHE_FILE_PATH + File.separator + getShortName(url, isLarge);
	}

	private class ModifyDateUpdateComparator implements Comparator<File> {

		@Override
		public int compare(File file1, File file2) {
			if (file1.lastModified() > file2.lastModified()) {
				return 1;
			} else if (file1.lastModified() < file2.lastModified()) {
				return -1;
			} else {
				return 0;
			}
		}

	}
	public void clear() {
		File dir = new File(CACHE_FILE_PATH);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.getName().contains("cache:")) {
				f.delete();
			}
		}
		files = dir.listFiles();
		if (files == null || files.length == 0) {
			dir.delete();
		}
	}
	public void delete(String url, boolean isLarge) {
		File file = new File(getCacheFilePath(url, isLarge));
		if (file.exists()) {
			file.delete();
		}
	}
}
