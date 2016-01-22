package me.ninja4826.forum.gen;
// Generated Jan 22, 2016 1:07:57 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Category generated by hbm2java
 */
public class Category implements java.io.Serializable {

	private int id;
	private Category category;
	private String name;
	private String description;
	private int hierarchyLevel;
	private Date createdAt;
	private Date updatedAt;
	private Set categories = new HashSet(0);
	private Set topics = new HashSet(0);
	private Set categoryAncestorsForCategoryId = new HashSet(0);
	private Set categoryAncestorsForAncestorId = new HashSet(0);

	public Category() {
	}

	public Category(int id, String name, String description, int hierarchyLevel, Date createdAt, Date updatedAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.hierarchyLevel = hierarchyLevel;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Category(int id, Category category, String name, String description, int hierarchyLevel, Date createdAt,
			Date updatedAt, Set categories, Set topics, Set categoryAncestorsForCategoryId,
			Set categoryAncestorsForAncestorId) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.description = description;
		this.hierarchyLevel = hierarchyLevel;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.categories = categories;
		this.topics = topics;
		this.categoryAncestorsForCategoryId = categoryAncestorsForCategoryId;
		this.categoryAncestorsForAncestorId = categoryAncestorsForAncestorId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHierarchyLevel() {
		return this.hierarchyLevel;
	}

	public void setHierarchyLevel(int hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
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

	public Set getCategories() {
		return this.categories;
	}

	public void setCategories(Set categories) {
		this.categories = categories;
	}

	public Set getTopics() {
		return this.topics;
	}

	public void setTopics(Set topics) {
		this.topics = topics;
	}

	public Set getCategoryAncestorsForCategoryId() {
		return this.categoryAncestorsForCategoryId;
	}

	public void setCategoryAncestorsForCategoryId(Set categoryAncestorsForCategoryId) {
		this.categoryAncestorsForCategoryId = categoryAncestorsForCategoryId;
	}

	public Set getCategoryAncestorsForAncestorId() {
		return this.categoryAncestorsForAncestorId;
	}

	public void setCategoryAncestorsForAncestorId(Set categoryAncestorsForAncestorId) {
		this.categoryAncestorsForAncestorId = categoryAncestorsForAncestorId;
	}

}
