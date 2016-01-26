package me.ninja4826.forum.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import me.ninja4826.forum.util.JsonTransformer;
import spark.Route;
import spark.Spark;

public class ControllerInterface {

	protected static final HashMap<String, Route> gets = new HashMap<>();
	protected static final HashMap<String, Route> posts = new HashMap<>();
	protected static final HashMap<String, Route> puts = new HashMap<>();
	protected static final HashMap<String, Route> deletes = new HashMap<>();
	protected static String API_CONTEXT;
	protected static final Gson gson = new Gson();

	public static void get(String path, Route route) {
		gets.put(path, route);
	}
	public static void post(String path, Route route) {
		posts.put(path, route);
	}
	public static void put(String path, Route route) {
		puts.put(path, route);
	}
	public static void delete(String path, Route route) {
		deletes.put(path, route);
	}
	
	public static void setupEndpoints() {
		for (Entry<String, Route> r : gets.entrySet()) {
			Spark.get(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
		for (Map.Entry<String, Route> r : posts.entrySet()) {
			Spark.post(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
		for (Map.Entry<String, Route> r : puts.entrySet()) {
			Spark.put(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
		for (Map.Entry<String, Route> r : deletes.entrySet()) {
			Spark.delete(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
	}
	
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
}
