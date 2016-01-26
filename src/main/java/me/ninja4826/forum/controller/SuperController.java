package me.ninja4826.forum.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import me.ninja4826.forum.util.JsonTransformer;
import me.ninja4826.forum.util.Util;
import spark.Route;
import spark.Spark;

public class SuperController {

	protected final HashMap<String, Route> gets = new HashMap<>();
	protected final HashMap<String, Route> posts = new HashMap<>();
	protected final HashMap<String, Route> puts = new HashMap<>();
	protected final HashMap<String, Route> deletes = new HashMap<>();
	protected final Gson gson = new Gson();
	
	protected static final SuperController instance = new SuperController();
	
	protected SuperController() {}

	public void get(String path, Route route) {
		gets.put(path, route);
	}
	public void post(String path, Route route) {
		posts.put(path, route);
	}
	public void put(String path, Route route) {
		puts.put(path, route);
	}
	public void delete(String path, Route route) {
		deletes.put(path, route);
	}
	
	@SuppressWarnings("unchecked")
	public void halt(int code, String message) {
		Spark.halt(code, jsonError(message));
	}
	
	public void halt(int code, String message, Entry<String, String>[] entries) {
		Spark.halt(code, jsonError(message, entries));
	}
	
	public void halt(String message, Entry<String, String>[] entries) {
		Spark.halt(jsonError(message, entries));
	}
	
	public void halt(int code, Entry<String, String>[] entries) {
		Spark.halt(code, jsonError(entries));
	}
	
	public HashMap<String, HashMap<String, Route>> getRoutes() {
		HashMap<String, HashMap<String, Route>> routes = new HashMap<>();
		routes.put("gets", this.gets);
		routes.put("posts", this.posts);
		routes.put("puts", this.puts);
		routes.put("deletes", this.deletes);
		
		return routes;
	}
	
	public static void setupEndpoints() {
		for (Entry<String, Route> r : instance.gets.entrySet()) {
			Spark.get(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
		for (Map.Entry<String, Route> r : instance.posts.entrySet()) {
			Spark.post(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
		for (Map.Entry<String, Route> r : instance.puts.entrySet()) {
			Spark.put(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
		for (Map.Entry<String, Route> r : instance.deletes.entrySet()) {
			Spark.delete(r.getKey(), "application/json", r.getValue(), JsonTransformer.getInstance());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String jsonError(String message, Entry<String, String>... entries) {
		return Util.jsonError(message, entries);
	}
	
	public static String jsonError(Entry<String, String>[] entries) {
		return Util.jsonError(entries);
	}
	
	public static void addController(SuperController controller) {
		HashMap<String, HashMap<String, Route>> routes = controller.getRoutes();

		for (Entry<String, Route> r : routes.get("gets").entrySet()) {
			instance.get(r.getKey(), r.getValue());
		}
		for (Entry<String, Route> r : routes.get("posts").entrySet()) {
			instance.post(r.getKey(), r.getValue());
		}
		for (Entry<String, Route> r : routes.get("puts").entrySet()) {
			instance.put(r.getKey(), r.getValue());
		}
		for (Entry<String, Route> r : routes.get("deletes").entrySet()) {
			instance.delete(r.getKey(), r.getValue());
		}
	}
}




































