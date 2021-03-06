package me.ninja4826.forum.model.entity;
// Generated Jan 24, 2016 12:27:19 PM by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import me.ninja4826.forum.util.HibernateUtil;

/**
 * User generated by hbm2java
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "user", schema = "public", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "username") })
public class User extends Item<User> implements java.io.Serializable {

	public static enum Role {
		BANNED,
		USER,
		MOD,
		ADMIN
	}

	public static String[] validAssocs = {"topics", "posts"};

//	private int id;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "salt", nullable = false)
	private String salt;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Enumerated
	@Column(name = "role", nullable = false)
	private Role role;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	private Set<Topic> topics = new HashSet<Topic>(0);

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	private Set<Post> posts = new HashSet<Post>(0);

	public User() {
		super(User.class);
	}
	
	public User(int id, String username, String email, String salt, String passwordHash, Role role, Date createdAt,
			Date updatedAt) {
		super(User.class, id, createdAt, updatedAt);
		this.username = username;
		this.email = email;
		this.salt = salt;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public User(int id, String username, String email, String password, String salt, String passwordHash, Role role,
			Date createdAt, Date updatedAt, Set<Topic> topics, Set<Post> posts) {
		super(User.class, id, createdAt, updatedAt);
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.passwordHash = passwordHash;
		this.role = role;
		this.topics = topics;
		this.posts = posts;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean hasRole(Role r) {
		return (this.getRole().ordinal() >= r.ordinal());
	}
	
	public boolean hasRole(String str) {
		return this.hasRole(Role.valueOf(str.toUpperCase()));
	}

	public Set<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public Set<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	

	public void updatePassword(String pass) {
		this.salt = BCrypt.gensalt();
		this.passwordHash = BCrypt.hashpw(pass, this.salt);
	}
	
	public void updatePassword() {
		this.updatePassword(this.getPassword());
	}

	public boolean verifyPassword(String pass) {
		return BCrypt.checkpw(pass, this.passwordHash);
	}
	
	@Override
	public String[] getAssocs() {
		return validAssocs;
	}

	@Override
	public boolean beforeUpdate() {
		System.out.println("Registry works!");
		if (this.getPassword() != null && this.getPasswordHash() == null) this.updatePassword();
		if (this.getRole() == null) this.role = Role.USER;
		return true;
	}
	
	@Override
	public boolean beforeInsert() {
		return this.beforeUpdate();
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
	public void load(String... assocs) {
		if (assocs.length == 0) {
			this.load(validAssocs);
		}
		HibernateUtil.transact((s) -> {
			for (String str : assocs) {
				switch (str) {
				case "topics":
					this.topics = this.getTopics();
					break;
				case "posts":
					this.posts = this.getPosts();
					break;
				}
			}
			return null;
		}, User.class);
	}

	@Override
	public void set(String property, Object value) {
		switch (property) {
		case "username":
			this.setUsername((String) value);
			break;
		case "password":
			this.setPassword((String) value);
			break;
		case "role":
			this.setRole((Role) value);
		}
	}
}
