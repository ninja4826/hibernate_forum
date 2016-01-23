package me.ninja4826.forum.util;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;

public class Util {
	
	private static Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	public static String jsonError(String message, Entry<String, String>... entries) {
		HashMap<String, String> payload = new HashMap<>();
		
		if (message != null) {
			payload.put("error", message);
		}
		
		for (Entry<String, String> entry : entries) {
			payload.put(entry.getKey(), entry.getValue());
		}
		return gson.toJson(payload);
	}
	
	public static String jsonError(String message) {
		return jsonError(message);
	}
	
	public static String jsonError(Entry<String, String>[] entries) {
		return jsonError(null, entries);
	}

}
