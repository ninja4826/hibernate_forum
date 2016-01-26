package me.ninja4826.forum.model.entity;

import java.util.List;
import java.util.Set;

public interface ItemInterface<T extends Item<T>> {
	
	public String[] getAssocs();
	
	public boolean beforeUpdate();
	public boolean beforeInsert();
	public void save();
	public void afterUpdate();
	public void afterInsert();
	
	public void delete();
	public boolean validate();
//	public JsonElement serialize();
	
	public void load(String... assocs);
	public List<T> loadList(List<T> tList, String... assocs);
	public void set(String property, Object value);
}
