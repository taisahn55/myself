package com.hongguang.jaia_utils;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class MemoryCache {

	private final static int MAP_CACHE_SIZE = 15;
	private LruCache<String, Bitmap> lruCache = null;
	private LinkedHashMap<String, SoftReference<Bitmap>> merCache = null;
	private static MemoryCache mc = null;

	private MemoryCache() {
		int lruCacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 4);
		merCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
				MAP_CACHE_SIZE, 0.75f, true) {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(
					Entry<String, SoftReference<Bitmap>> eldest) {

				if (eldest != null) {
					return true;
				}
				return false;
			}

		};

		lruCache = new LruCache<String, Bitmap>(lruCacheSize) {

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				if (oldValue != null) {
					merCache.put(key, new SoftReference<Bitmap>(oldValue));
				}
			}

			@Override
			protected int sizeOf(String key, Bitmap value) {
				return (int) (value.getRowBytes() * value.getHeight() / 1024);
			}

		};
	}

	public static MemoryCache getInstance() {
		if (mc == null) {
			mc = new MemoryCache();
		}
		return mc;
	}

	public synchronized void putBitmap(String url, Bitmap bitmap) {
		if (bitmap != null) {
			lruCache.put(url, bitmap);
		}
	}

	public synchronized Bitmap getBitmap(String url) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		Bitmap bitmap = lruCache.get(url);
		if (bitmap != null) {
			lruCache.remove(url);
			lruCache.put(url, bitmap);
			return bitmap;
		}
		SoftReference<Bitmap> sr = merCache.get(url);
		if (sr != null) {
			bitmap = sr.get();
			if (bitmap != null) {
				merCache.remove(url);
				lruCache.put(url, bitmap);
				return bitmap;
			} else {
				sr = null;
				merCache.remove(url);
			}
		}
		return bitmap;
	}

	public void clear() {
		merCache.clear();
	}

	public void delete(String url) {
		lruCache.remove(url);
		merCache.remove(url);
	}
}
