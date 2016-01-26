package me.ninja4826.forum.gen;
// Generated Jan 23, 2016 5:01:29 PM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Post generated by hbm2java
 */
/**
 * @deprecated
 * Use me.ninja4826.forum.model.entity.Post instead
 * @author huesk
 *
 */
@Deprecated
@Entity
@Table(name = "post", schema = "public")
public class Post implements java.io.Serializable {

	private int id;
	private Post parent;
	private Topic topic;
	private User user;
	private String content;
	private int hierarchyLevel = 1;
	private boolean savedBefore = false;
	private Date createdAt;
	private Date updatedAt;
	private Set<Post> children = new HashSet<Post>(0);
	private Set<PostAncestor> ancestors = new HashSet<PostAncestor>(0);
	private Set<PostAncestor> descendants = new HashSet<PostAncestor>(0);

	public Post() {
	}

	public Post(int id, Topic topic, User user, String content, int hierarchyLevel, boolean savedBefore, Date createdAt, Date updatedAt) {
		this.id = id;
		this.topic = topic;
		this.user = user;
		this.content = content;
		this.hierarchyLevel = hierarchyLevel;
		this.savedBefore = savedBefore;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Post(int id, Post parent, Topic topic, User user, String content, int hierarchyLevel, boolean savedBefore, Date createdAt,
			Date updatedAt, Set<Post> children, Set<PostAncestor> ancestors,
			Set<PostAncestor> descendants) {
		this.id = id;
		this.parent = parent;
		this.topic = topic;
		this.user = user;
		this.content = content;
		this.hierarchyLevel = hierarchyLevel;
		this.savedBefore = savedBefore;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.children = children;
		this.ancestors = ancestors;
		this.descendants = descendants;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public Post getParent() {
		return this.parent;
	}

	public void setParent(Post post) {
		this.parent = post;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_id", nullable = false)
	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "hierarchy_level", nullable = false)
	public int getHierarchyLevel() {
		return this.hierarchyLevel;
	}

	public void setHierarchyLevel(int hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}
	
	@Column(name = "saved_before", nullable = false)
	public boolean getSavedBefore() {
		return this.savedBefore;
	}
	
	public void setSavedBefore(boolean savedBefore) {
		this.savedBefore = savedBefore;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 29)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, length = 29)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<Post> getChildren() {
		return this.children;
	}

	public void setChildren(Set<Post> posts) {
		this.children = posts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "postByAncestorId")
	public Set<PostAncestor> getAncestors() {
		return this.ancestors;
	}

	public void setAncestors(Set<PostAncestor> postAncestorsForAncestorId) {
		this.ancestors = postAncestorsForAncestorId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "postByPostId")
	public Set<PostAncestor> getDescendants() {
		return this.descendants;
	}

	public void setDescendants(Set<PostAncestor> postAncestorsForPostId) {
		this.descendants = postAncestorsForPostId;
	}

}
