package me.ninja4826.forum.gen;
// Generated Jan 22, 2016 1:07:57 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private int id;
	private String username;
	private String email;
	private String password;
	private String salt;
	private String passwordHash;
	private String role;
	private Date createdAt;
	private Date updatedAt;
	private Set topics = new HashSet(0);
	private Set posts = new HashSet(0);

	public User() {
	}

	public User(int id, String username, String email, String salt, String passwordHash, String role, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.salt = salt;
		this.passwordHash = passwordHash;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public User(int id, String username, String email, String password, String salt, String passwordHash, String role,
			Date createdAt, Date updatedAt, Set topics, Set posts) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.passwordHash = passwordHash;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.topics = topics;
		this.posts = posts;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set getTopics() {
		return this.topics;
	}

	public void setTopics(Set topics) {
		this.topics = topics;
	}

	public Set getPosts() {
		return this.posts;
	}

	public void setPosts(Set posts) {
		this.posts = posts;
	}

}
