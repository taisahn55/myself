package com.hongguang.jaia_utils;

import com.google.gson.Gson;

public class Constant {
	private static Gson mGson = null;

	public static Gson getGson() {
		if (mGson == null) {
			mGson = new Gson();
		}
		return mGson;
	}

}
