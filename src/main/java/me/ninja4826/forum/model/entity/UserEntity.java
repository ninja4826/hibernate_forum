package me.ninja4826.forum.model.entity;

import org.mindrot.jbcrypt.BCrypt;

import me.ninja4826.forum.gen.User;
import me.ninja4826.forum.util.HibernateUtil;

public class UserEntity extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2061694309203575642L;

	public UserEntity() {
		super();
	}
	
	public UserEntity(User u, boolean lazy) {
		super(u.getId(),
				u.getUsername(),
				u.getEmail(),
				u.getSalt(),
				u.getPasswordHash(),
				u.getRole(),
				u.getCreatedAt(),
				u.getUpdatedAt());
		if (!lazy) {
			this.setPassword(u.getPassword());
			this.setTopics(u.getTopics());
			this.setPosts(u.getPosts());
		}
	}
	
	public UserEntity(User u) {
		this(u, true);
	}

	public void updatePassword(String pass) {
		this.setSalt(BCrypt.gensalt());
		this.setPasswordHash(BCrypt.hashpw(pass, this.getSalt()));
	}
	
	public boolean verifyPassword(String pass) {
		return BCrypt.checkpw(pass, this.getPasswordHash());
	}
	
	public void save() {
		HibernateUtil.transact((s) -> {
			s.save((User) this);
			return this;
		}, UserEntity.class);
	}
	
	public void delete() {
		HibernateUtil.transact((s) -> {
			s.delete((User) this);
			return null;
		}, Object.class);
	}
	
	public static UserEntity fromPOJO(User u) {
		return new UserEntity(u);
	}
}
