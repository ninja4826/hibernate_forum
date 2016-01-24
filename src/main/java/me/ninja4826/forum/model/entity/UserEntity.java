package me.ninja4826.forum.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.JsonElement;

import me.ninja4826.forum.gen.Post;
import me.ninja4826.forum.gen.Topic;
import me.ninja4826.forum.gen.User;
import me.ninja4826.forum.util.HibernateUtil;
import me.ninja4826.forum.util.JsonTransformer;

import com.google.gson.JsonObject;

public class UserEntity extends User implements EntityInterface<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2061694309203575642L;
	
	public List<String> assocs = new ArrayList<>();
	public static String[] validAssocs = {"topics", "posts"};
	
	private List<TopicEntity> topicEntities = new ArrayList<>();

	public UserEntity() {
		super();
	}
	
	public UserEntity(int id, String username, String email, String password, String salt, String passwordHash, String role,
			Date createdAt, Date updatedAt, Set<Topic> topics, Set<Post> posts, boolean loadAssocs) {
		super(id,
				username,
				email,
				password,
				salt,
				passwordHash,
				role,
				createdAt,
				updatedAt,
				topics,
				posts);
		if (loadAssocs) assocs = Arrays.asList(validAssocs);
	}
	
	public UserEntity(int id, String username, String email, String password, String salt, String passwordHash, String role,
			Date createdAt, Date updatedAt, Set<Topic> topics, Set<Post> posts, String[] assocs) {
		super(id,
				username,
				email,
				password,
				salt,
				passwordHash,
				role,
				createdAt,
				updatedAt,
				topics,
				posts);
		this.assocs = Arrays.asList(assocs);
	}
	
	public UserEntity(User u, boolean loadAssocs) {
		super(u.getId(),
				u.getUsername(),
				u.getEmail(),
				u.getSalt(),
				u.getPasswordHash(),
				u.getRole(),
				u.getCreatedAt(),
				u.getUpdatedAt());
		if (loadAssocs) assocs = Arrays.asList(validAssocs);
	}
	
	public UserEntity(User u, String[] assocs) {
		super(u.getId(),
				u.getUsername(),
				u.getEmail(),
				u.getSalt(),
				u.getPasswordHash(),
				u.getRole(),
				u.getCreatedAt(),
				u.getUpdatedAt());
		this.assocs = Arrays.asList(assocs);
	}
	
	public UserEntity(User u) {
		this(u, false);
	}

	public void updatePassword(String pass) {
		this.setSalt(BCrypt.gensalt());
		this.setPasswordHash(BCrypt.hashpw(pass, this.getSalt()));
	}
	
	public boolean verifyPassword(String pass) {
		return BCrypt.checkpw(pass, this.getPasswordHash());
	}
	
	@Override
	public void save() {
		HibernateUtil.transact((s) -> {
			s.save((User) this);
			return this;
		}, UserEntity.class);
	}
	
	@Override
	public void delete() {
		HibernateUtil.transact((s) -> {
			s.delete((User) this);
			return null;
		}, Object.class);
	}
	
	public static UserEntity fromPOJO(User u) {
		return new UserEntity(u);
	}
	
	public List<TopicEntity> getTopicEntities() {
		return this.topicEntities;
	}
	
	public void setTopicEntities(List<TopicEntity> topics) {
		this.topicEntities = topics;
	}

	@Override
	public boolean validate() {
		String pattern = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (!Pattern.compile(pattern).matcher(this.getEmail()).matches()) return false;
		
		if (this.getUsername() == null) return false;
		if (this.getPassword() == null && this.getPasswordHash() == null) return false;
		
		return true;
	}

	@Override
	public JsonElement serialize() {
		
		
//		TODO: Figure out why the associated models are not loaded.
		
		
		
		JsonObject obj = new JsonObject();
		System.out.println("SERIALIZING USER");
		System.out.println("USER ASSOCS");
		for (String s : this.assocs) {
			System.out.println(s);
		}
		try {
			JsonTransformer json = new JsonTransformer();
			
			obj.addProperty("username", this.getUsername());
			obj.addProperty("email", this.getEmail());
			obj.addProperty("password", this.getPassword());
			obj.addProperty("salt", this.getSalt());
			obj.addProperty("password_hash", this.getPasswordHash());
			obj.addProperty("role", this.getRole());
			obj.addProperty("created_at", this.getCreatedAt().toString());
			obj.addProperty("updated_at", this.getUpdatedAt().toString());
			
			if (this.assocs.contains("topics")) {
				Set<Topic> topics = this.getTopics();
				System.out.println("NUMBER OF ASSOCIATED TOPICS");
				System.out.println(Integer.toString(topics.size()));
				ArrayList<TopicEntity> tEntities = new ArrayList<>();
				for (Topic t : topics) {
					System.out.println(t.getTitle());
					tEntities.add(new TopicEntity(t, false));
				}
				obj.addProperty("topics", json.render(tEntities));
			}
			
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
