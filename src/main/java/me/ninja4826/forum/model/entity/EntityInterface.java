package me.ninja4826.forum.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

public interface EntityInterface<T> {
	
	public List<String> assocs = new ArrayList<>();
	
	public void save();
	public void delete();
	public boolean validate();
	public JsonElement serialize();
}
