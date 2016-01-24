package me.ninja4826.forum.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonElement;

import me.ninja4826.forum.gen.Post;
import me.ninja4826.forum.gen.Topic;
import me.ninja4826.forum.gen.User;

public class PostEntity extends Post implements EntityInterface<Post> {
	
	public List<String> assocs = new ArrayList<>();
	public static String[] validAssocs = {
			"topic",
			"ancestors",
			"user",
			"descendants",
			"children",
			"parent"
	};
	
	public PostEntity() {
		super();
	}
	
	public PostEntity(Post p, boolean lazy) {
		this.setId(p.getId());
		this.setContent(p.getContent());
		this.setHierarchyLevel(p.getHierarchyLevel());
		this.setSavedBefore(p.getSavedBefore());
		this.setCreatedAt(p.getCreatedAt());
		this.setUpdatedAt(p.getUpdatedAt());
		if (lazy) {
			this.setTopic(null);
			this.setAncestors(null);
			this.setUser(null);
			this.setDescendants(null);
			this.setChildren(null);
			this.setParent(null);
		} else {
			this.setTopic(p.getTopic());
			this.setUser(p.getUser());
			this.setAncestors(p.getAncestors());
			this.setDescendants(p.getDescendants());
			this.setChildren(p.getChildren());
			this.setParent(p.getParent());
		}
	}
	
	public PostEntity(Post p) {
		this(p, true);
	}
	
	public PostEntity(int id, Topic topic, User user, String content, int hierarchyLevel, boolean savedBefore, Date createdAt, Date updatedAt, boolean lazy) {
		super(id,
				topic,
				user,
				content,
				hierarchyLevel,
				savedBefore,
				createdAt,
				updatedAt);
		if (lazy) {
			this.setTopic(null);
			this.setAncestors(null);
			this.setUser(null);
			this.setDescendants(null);
			this.setChildren(null);
			this.setParent(null);
		}
	}

	@Override
	public void save() {
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
