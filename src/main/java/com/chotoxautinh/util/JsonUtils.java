/**
 * @author chotoxautinh
 *
 * Apr 11, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.util;

import com.google.gson.Gson;

public class JsonUtils {

	private static final Gson MAPPER = new Gson();

	public static <T> T fromJson(String json, Class<T> clazz) {
		return MAPPER.fromJson(json, clazz);
	}
	
	public static String toJson(Object obj) {
		return MAPPER.toJson(obj);
	}
}
