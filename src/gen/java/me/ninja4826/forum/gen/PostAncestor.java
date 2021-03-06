package me.ninja4826.forum.gen;
// Generated Jan 23, 2016 5:01:29 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PostAncestor generated by hbm2java
 */
/**
 * @deprecated
 * @author huesk
 *
 */
@Deprecated
@Entity
@Table(name = "post_ancestor", schema = "public")
public class PostAncestor implements java.io.Serializable {

	private int id;
	private Post postByAncestorId;
	private Post postByPostId;

	public PostAncestor() {
	}

	public PostAncestor(int id, Post postByAncestorId, Post postByPostId) {
		this.id = id;
		this.postByAncestorId = postByAncestorId;
		this.postByPostId = postByPostId;
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
	@JoinColumn(name = "ancestor_id", nullable = false)
	public Post getPostByAncestorId() {
		return this.postByAncestorId;
	}

	public void setPostByAncestorId(Post postByAncestorId) {
		this.postByAncestorId = postByAncestorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	public Post getPostByPostId() {
		return this.postByPostId;
	}

	public void setPostByPostId(Post postByPostId) {
		this.postByPostId = postByPostId;
	}

}
