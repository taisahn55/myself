package com.hongguang.jaia_utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Environment;

import com.hongguang.jaia.R;

@SuppressLint("NewApi")
public class BitmapLruCache {
	private static BitmapLruCache bitLru = null;
	private static MemoryCache mc = null;
	public static int bmaxSize = 0;
	public static int density = 100;
	public static int size = 0;

	public static BitmapLruCache getInstance(int lrusiz) {
		if (bitLru == null) {
			bitLru = new BitmapLruCache();
			mc = MemoryCache.getInstance();
		}
		return bitLru;
	}

	public void addBitmapLru(String n, Bitmap b) {
		mc.putBitmap(n, b);
	}

	/**
	 * 明缓存读取
	 * 
	 * @param n
	 * @return
	 */
	public Bitmap getBitmap(String n) {
		Bitmap bit = null;
		if (mc.getBitmap(n) != null) {
			bit = mc.getBitmap(n);
		}
		return bit;
	}

	public void delete(String n) {
		mc.delete(n);
	}

	private String getFile() {
		String url = Environment.getExternalStorageDirectory() + "" + "/Love"
				+ "/";
		return url;
	}

	/**
	 * 路径读取缩略图
	 * 
	 * @param n
	 * @param path
	 * @param context
	 * @return
	 */
	public Bitmap getUrlbitmap(String n, String path, Context context) {

		Bitmap bit = null;
		String name = n.substring(n.lastIndexOf("/") + 1, n.length());
		try {
			bit = myBitmapFactory(getFile() + name, 150, 150);
			System.out.println("本地加载图片" + bit);
			int degree = getBitmapDegree(path);
			if (degree > 0) {
				bit = rotateBitmapByDegree(bit, degree);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (bit == null) {
			// bit = defaultBitmap(context);
		} else {
			addBitmapLru(name, bit);
		}
		return bit;
	}

	public Bitmap getUrlbitmaps(String n, String path, Context context) {
		Bitmap bit = null;
		try {
			bit = myBitmapFactory(path, 150, 150);
			System.out.println("本地加载图片" + bit);
			int degree = getBitmapDegree(path);
			if (degree > 0) {
				bit = rotateBitmapByDegree(bit, degree);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (bit == null) {
			// bit = defaultBitmap(context);
		} else {
			addBitmapLru(n, bit);
		}
		return bit;
	}

	/**
	 * 路径读取大图
	 * 
	 * @param resId
	 * @param path
	 * @param context
	 * @param state
	 * @return
	 */
	public Bitmap getOutBitmap(String resId, String path, Context context,
			String state) {
		Bitmap bmp = null;
		try {
			bmp = imageZoom(path);
			int degree = getBitmapDegree(path);
			if (degree > 0) {
				bmp = rotateBitmapByDegree(bmp, degree);
			}
			if (bmp != null && state != null) {
				addBitmapLru(resId, bmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmp;
	}

	/**
	 * 压缩按照文件大小
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private Bitmap imageZoom(String path) throws Exception {
		Bitmap bitmap = myBitmapFactory(path, 720, 1280);
		// int totalSize = bitmap.getWidth() * bitmap.getHeight();
		// if (totalSize > 720 * 1280) {
		// bitmap = zoomImage(bitmap,
		// bitmap.getWidth() / Math.sqrt(totalSize / 1000000d),
		// bitmap.getHeight() / Math.sqrt(totalSize / 1000000d));
		// }
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, density, baos);
		byte[] b = baos.toByteArray();
		int maxSize = 150 * 1024;
		while (b.length > maxSize) {
			density -= 4;
			baos.reset();
			if (density <= 0) {
				break;
			}
			bitmap.compress(CompressFormat.JPEG, density, baos);
			b = baos.toByteArray();
		}
		bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		return bitmap;
	}

	/**
	 * 压缩按照图片宽高比
	 * 
	 * @param path
	 * @param ww
	 * @param hh
	 * @return
	 * @throws Exception
	 */
	public Bitmap myBitmapFactory(String path, float ww, float hh)// 图大小
			throws Exception {
		Bitmap bitmap = null;
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		int be = 1;
		if (w >= h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(path, newOpts);
		return bitmap;
	}

	private Bitmap defaultBitmap(Context context) {
		InputStream inputStream = context.getResources().openRawResource(
				R.drawable.morentu);
		BitmapDrawable drawable = new BitmapDrawable(inputStream);
		return drawable.getBitmap();
	}

	private int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90 :
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180 :
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270 :
					degree = 270;
					break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree)
			throws Exception {
		Bitmap returnBm = null;
		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}

}
