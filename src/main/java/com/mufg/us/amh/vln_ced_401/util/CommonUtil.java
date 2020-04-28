package com.mufg.us.amh.vln_ced_401.util;

import java.util.UUID;

import com.google.gson.Gson;



public class CommonUtil {

	public static String generateUniqueId() {
		return UUID.randomUUID().toString();
	}
	
	public static String toGson(Object user) {
		return new Gson().toJson(user);
	}

}
