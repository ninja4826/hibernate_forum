package me.ninja4826.forum.model.entity;

import com.google.gson.JsonElement;

import me.ninja4826.forum.gen.Category;
import me.ninja4826.forum.util.HibernateUtil;

public class CategoryEntity extends Category implements EntityInterface<Category> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2476426175124893394L;
	
	public CategoryEntity() {
		super();
	}
	
	public CategoryEntity(Category c, boolean lazy) {
		super(c.getId(),
				c.getName(),
				c.getDescription(),
				c.getHierarchyLevel(),
				c.getSavedBefore(),
				c.getCreatedAt(),
				c.getUpdatedAt());
		if (lazy) {
			this.setParent(null);
			this.setChildren(null);
			this.setAncestors(null);
			this.setDescendants(null);
			this.setTopics(null);
		} else {
			this.setParent(c.getParent());
			this.setChildren(c.getChildren());
			this.setAncestors(c.getAncestors());
			this.setDescendants(c.getDescendants());
			this.setTopics(c.getTopics());
		}
	}
	
	public CategoryEntity(Category c) {
		this(c, true);
	}
	
	public void save() {
		HibernateUtil.transact((s) -> {
			s.save((Category) this);
			if (!this.getSavedBefore()) {
				if (this.getParent() != null) {
					this.setHierarchyLevel(this.getParent().getHierarchyLevel() + 1);
//					TODO: Create CategoryAncestors for parents recursively
				}
			}
			return this;
		}, CategoryEntity.class);
	}
	
	public void delete() {
		HibernateUtil.transact((s) -> {
			s.delete((Category) this);
			return null;
		}, Object.class);
	}
	
	public static CategoryEntity fromPOJO(Category c) {
		return new CategoryEntity(c);
	}

	@Override
	public boolean validate() {
		if (this.getName() == null) return false;
		return true;
	}

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		return null;
	}
}
