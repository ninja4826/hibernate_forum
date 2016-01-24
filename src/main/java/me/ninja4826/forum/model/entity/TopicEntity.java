package me.ninja4826.forum.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.ninja4826.forum.gen.Post;
import me.ninja4826.forum.gen.Topic;
import me.ninja4826.forum.util.HibernateUtil;
import me.ninja4826.forum.util.JsonTransformer;

public class TopicEntity extends Topic implements EntityInterface<Topic> {
	
	public List<String> assocs = new ArrayList<>();
	public static String[] validAssocs = {"category", "user", "posts"};
	
	public TopicEntity(Topic t, boolean loadAssocs) {
		super(t.getId(),
				t.getCategory(),
				t.getUser(),
				t.getTitle(),
				t.getContent(),
				t.getCreatedAt(),
				t.getUpdatedAt());
		
		if (loadAssocs) assocs = Arrays.asList(validAssocs);
	}
	
	public TopicEntity(Topic t, String[] assocs) {
		super(t.getId(),
				t.getCategory(),
				t.getUser(),
				t.getTitle(),
				t.getContent(),
				t.getCreatedAt(),
				t.getUpdatedAt());
		this.assocs = Arrays.asList(assocs);
	}
	
	public TopicEntity(Topic t) {
		this(t, false);
	}

	@Override
	public void save() {
		HibernateUtil.transact((s) -> {
			s.save((Topic) this);
			return this;
		}, Topic.class);
	}

	@Override
	public void delete() {
		HibernateUtil.transact((s) -> {
			s.delete((Topic) this);
			return null;
		}, Object.class);
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		System.out.println("SERIALIZING TOPIC");
		System.out.println("ASSOCS");
		for (String s : this.assocs) {
			System.out.println(s);
		}
		try {
			JsonTransformer json = new JsonTransformer();
			
			obj.addProperty("title", this.getTitle());
			obj.addProperty("content", this.getContent());
			obj.addProperty("created_at", this.getCreatedAt().toString());
			obj.addProperty("updated_at", this.getUpdatedAt().toString());
			
			if (this.assocs.contains("category")) obj.addProperty("category", json.render(this.getCategory()));
			
			if (this.assocs.contains("user")) obj.addProperty("user", json.render(this.getUser()));
			
			if (this.assocs.contains("posts")) {
				Set<Post> posts = this.getPosts();
				ArrayList<PostEntity> pEntities = new ArrayList<>();
				for (Post p : posts) {
					pEntities.add(new PostEntity(p, false));
				}
				obj.addProperty("posts", json.render(pEntities));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}

}
