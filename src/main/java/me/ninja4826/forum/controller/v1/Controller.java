package me.ninja4826.forum.controller.v1;

import me.ninja4826.forum.controller.SuperController;
import spark.Route;

public class Controller extends SuperController {
	
	public final String API_CONTEXT = "/api/v1";

	public void get(String path, Route route) {
		super.get(API_CONTEXT + path, route);
	}
	public void post(String path, Route route) {
		super.post(API_CONTEXT + path, route);
	}
	public void put(String path, Route route) {
		super.put(API_CONTEXT + path, route);
	}
	public void delete(String path, Route route) {
		super.delete(API_CONTEXT + path, route);
	}
}
