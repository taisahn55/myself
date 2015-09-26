package com.hongguang.jaia_utils;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import com.hongguang.jaia.R;
import com.hongguang.jaia.WelcomeActivity;
import com.hongguang.jaia_task.GetDataFromWeb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class GetAndStoreBitmap {

	private ExecutorService threadPool = WelcomeActivity.pool;
	private MyHandler handler = new MyHandler();
	private Context context;
	private HashMap<String, ImageView> imageMap = new HashMap<String, ImageView>();
	private HashMap<String, ProgressBar> barMap = new HashMap<String, ProgressBar>();

	public GetAndStoreBitmap(Context context) {
		super();
		this.context = context;
	}

	public void getBitmap(final int w, final int h, ImageView iv,
			final String url, final ProgressBar bar, final boolean isLarge,
			final boolean needHandle) {

		imageMap.put(url, iv);
		if (bar != null) {
			barMap.put(url, bar);
		}
		Bitmap bitmap = MemoryCache.getInstance().getBitmap(url);
		if (bitmap != null) {
			if (needHandle) {
				bitmap = handleBitmap(w, h, bitmap);
			}
			if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
				iv.setImageBitmap(bitmap);
				iv.setId(1);
				if (bar != null) {
					bar.setVisibility(View.INVISIBLE);
					barMap.remove(url);
				}
				imageMap.remove(url);
			}
		} else {
			bitmap = FileCache.getInstance().getBitmap(url, isLarge);
			if (bitmap != null) {
				MemoryCache.getInstance().putBitmap(url, bitmap);
				if (needHandle) {
					bitmap = handleBitmap(w, h, bitmap);
				}
				if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
					iv.setImageBitmap(bitmap);
					iv.setId(1);
					if (bar != null) {
						bar.setVisibility(View.INVISIBLE);
						barMap.remove(url);
					}
					imageMap.remove(url);
				}
			} else {

				threadPool.submit(new Runnable() {
					@Override
					public void run() {
						Message msg = handler.obtainMessage();
						byte[] data = (new GetDataFromWeb(context))
								.getData(url);
						if (data != null) {
							Bitmap bitmap = BitmapFactory.decodeByteArray(data,
									0, data.length);
							if (bitmap != null) {
								MemoryCache.getInstance()
										.putBitmap(url, bitmap);
								FileCache.getInstance().putBitmap(url, bitmap,
										true, isLarge);
								if (needHandle) {
									bitmap = handleBitmap(w, h, bitmap);
								}
								msg.what = 1;
								msg.obj = bitmap;
							}
						} else {
							msg.what = 0;
						}
						Bundle bundle = new Bundle();
						bundle.putString("url", url);
						// bundle.putBoolean("isLarge", isLarge);
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				});
			}
		}

	}

	public void getBitmap(ImageView iv, final String url,
			final ProgressBar bar, final boolean isLarge) {
		imageMap.put(url, iv);
		if (bar != null) {
			barMap.put(url, bar);
		}
		Bitmap bitmap = MemoryCache.getInstance().getBitmap(url);
		if (bitmap != null) {
			// System.out.println("从内存缓存中获取到图片");
			bitmap = checkBitmap(bitmap);
			if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
				iv.setImageBitmap(bitmap);
				iv.setId(1);
				if (bar != null) {
					bar.setVisibility(View.INVISIBLE);
					barMap.remove(url);
				}
				imageMap.remove(url);
			}
		} else {
			bitmap = FileCache.getInstance().getBitmap(url, isLarge);
			if (bitmap != null) {
				// System.out.println("从sd卡缓存中获取到图片");
				MemoryCache.getInstance().putBitmap(url, bitmap);
				bitmap = checkBitmap(bitmap);
				if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
					iv.setImageBitmap(bitmap);
					iv.setId(1);
					if (bar != null) {
						bar.setVisibility(View.INVISIBLE);
						barMap.remove(url);
					}
					imageMap.remove(url);
				}
			} else {

				threadPool.submit(new Runnable() {
					@Override
					public void run() {
						Message msg = handler.obtainMessage();
						// System.out.println("开始加载大图");
						byte[] data = (new GetDataFromWeb(context))
								.getData(url);
						if (data != null) {
							Bitmap bitmap = BitmapFactory.decodeByteArray(data,
									0, data.length);
							// System.out.println("大图加载成功");
							if (bitmap != null) {
								MemoryCache.getInstance()
										.putBitmap(url, bitmap);
								FileCache.getInstance().putBitmap(url, bitmap,
										true, isLarge);
								bitmap = checkBitmap(bitmap);
								msg.what = 1;
								msg.obj = bitmap;
							}
						} else {
							msg.what = 0;
						}
						Bundle bundle = new Bundle();
						bundle.putString("url", url);
						// bundle.putBoolean("isLarge", isLarge);
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				});
			}
		}

	}

	public void getBitmap(ImageView iv, final String url,
			final boolean isLogol, final boolean needChange,
			final boolean needCache) {
		imageMap.put(url, iv);
		if (!needCache) {
			threadPool.submit(new Runnable() {

				@Override
				public void run() {
					// System.out.println("logol的url为："+url);
					Message msg = handler.obtainMessage();
					// System.out.println("开始加载小图");
					byte[] data = (new GetDataFromWeb(context)).getData(url);
					if (data != null) {
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						if (bitmap != null) {
							// System.out.println("开始加载小图成功");
							if (!isLogol) {
								bitmap = checkBitmap(bitmap);
							}

							msg.obj = bitmap;
							msg.what = 1;
						}
					} else {
						msg.what = 0;
					}
					if (isLogol) {
						msg.arg1 = 100;
					}
					Bundle bundle = new Bundle();
					bundle.putString("url", url);
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
			});
		} else {
			// System.out.println("logol的url为："+url);
			Bitmap bitmap = MemoryCache.getInstance().getBitmap(url);
			if (bitmap != null) {
				// System.out.println("从内存缓存中或得图片");
				if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
					if (!isLogol) {
						bitmap = checkBitmap(bitmap);
					}

					iv.setImageBitmap(bitmap);
					iv.setId(1);
					imageMap.remove(url);
				}
			} else {
				bitmap = FileCache.getInstance().getBitmap(url, false);
				if (bitmap != null) {
					// System.out.println("从文件缓存中或得图片");
					MemoryCache.getInstance().putBitmap(url, bitmap);
					if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
						if (!isLogol) {
							bitmap = checkBitmap(bitmap);
						}

						iv.setImageBitmap(bitmap);
						iv.setId(1);
						imageMap.remove(url);
					}
				} else {

					threadPool.submit(new Runnable() {

						@Override
						public void run() {
							Message msg = handler.obtainMessage();
							byte[] data = (new GetDataFromWeb(context))
									.getData(url);
							if (data != null) {
								Bitmap bitmap = BitmapFactory.decodeByteArray(
										data, 0, data.length);
								if (bitmap != null) {
									// System.out.println("开启线程加载图片成功");
									MemoryCache.getInstance().putBitmap(url,
											bitmap);
									FileCache.getInstance().putBitmap(url,
											bitmap, true, false);
									if (!isLogol) {
										bitmap = checkBitmap(bitmap);
									}

									msg.obj = bitmap;
									msg.what = 1;
								}
							} else {
								// System.out.println("开启线程加载图片失败");
								msg.what = 0;
							}
							if (isLogol) {
								msg.arg1 = 100;
							}
							Bundle bundle = new Bundle();
							bundle.putString("url", url);
							bundle.putBoolean("isLarge", false);
							msg.setData(bundle);
							handler.sendMessage(msg);
						}
					});
				}
			}
		}

	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			String url = msg.getData().getString("url");
			// boolean isLarge = msg.getData().getBoolean("isLarge");
			ImageView iv = imageMap.get(url);
			ProgressBar bar = barMap.get(url);
			if (iv != null) {
				if (msg.what == 1) {
					Bitmap bitmap = (Bitmap) msg.obj;
					if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
						iv.setImageBitmap(bitmap);
						// System.out.println("图片设置成功");
					}
				} else if (msg.what == 0) {
					if (iv.getTag() != null && (iv.getTag() + "").equals(url)) {
						if (msg.arg1 == 100) {

							iv.setImageResource(R.drawable.load_failed);
						}
					}
				}
				imageMap.remove(url);
				iv.setId(1);
			}
			if (bar != null && (bar.getTag() + "").equals(url)) {
				bar.setVisibility(View.INVISIBLE);
				barMap.remove(url);
			}
		}

	}

	public void saveBitmap(String url, Bitmap bitmap, boolean isLarge) {
		MemoryCache.getInstance().putBitmap(url, bitmap);
		FileCache.getInstance().putBitmap(url, bitmap, true, isLarge);
	}

	public void deleteBitmap(String url, boolean isLarge) {
		MemoryCache.getInstance().delete(url);
		FileCache.getInstance().delete(url, isLarge);
	}

	public Bitmap get_Bitmap(final String url, boolean isLarge) {

		Bitmap bitmap = MemoryCache.getInstance().getBitmap(url);
		if (bitmap != null) {
			return bitmap;
		} else {
			bitmap = FileCache.getInstance().getBitmap(url, isLarge);
			if (bitmap != null) {
				return bitmap;
			}
		}
		return null;
	}

	public void refreshBitmap(final String url, final boolean isLarger) {
		threadPool.submit(new Runnable() {

			@Override
			public void run() {
				byte[] data = (new GetDataFromWeb(context)).getData(url);
				if (data != null) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					if (bitmap != null) {
						MemoryCache.getInstance().putBitmap(url, bitmap);
						FileCache.getInstance().putBitmap(url, bitmap, true,
								isLarger);
					}
				}
			}
		});
	}

	public Bitmap checkBitmap(Bitmap b) {
		DateSharedPreferences dsp = DateSharedPreferences.getInstance();
		int width = b.getWidth();
		int height = b.getHeight();
		float scale = 1f;
		if (width > Integer.valueOf(dsp.get(context, "screenW", "0"))
				|| height > Integer.valueOf(dsp.get(context, "screenH", "0"))) {
			scale = Math.min(
					(float) Integer.valueOf(dsp.get(context, "screenW", "0"))
							/ width,
					(float) Integer.valueOf(dsp.get(context, "screenH", "0"))
							/ height);
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			b = Bitmap.createBitmap(b, 0, 0, width, height, matrix, true);
		}
		return b;
	}

	public Bitmap handleBitmap(int w, int h, Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		/*
		 * System.out.println("公益视频图片宽为："+width+"   wwwwww=="+w);
		 * System.out.println("公益视频图片高为："+height+"   hhhhhh=="+h);
		 */
		float scaleHeight = (float) h / height;
		float scaleWidht = (float) w / width;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		// System.out.println("返回图片成功");
		return newBitmap;
	}
}
