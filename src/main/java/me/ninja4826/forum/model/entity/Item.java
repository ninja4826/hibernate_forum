package me.ninja4826.forum.model.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.ninja4826.forum.util.HibernateUtil;

@JsonIgnoreProperties({"assocs"})
@SuppressWarnings("rawtypes")
@MappedSuperclass
public abstract class Item<T extends Item<T>> implements ItemInterface<T> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 29, columnDefinition="TIMESTAMP DEFAULT NOW()")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, length = 29, columnDefinition="TIMESTAMP DEFAULT NOW()")
	private Date updatedAt;
	
	@PreUpdate
	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}

	@Transient
	private Class<T> type;
	
	public int getID() {
		return this.id;
	}
	
	public Date getCreatedAt() {
		return this.createdAt;
	}
	
	public Date getUpdatedAt() {
		return this.updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	public Item(Class<T> type) {
		this.type = type;
	}
	
	public Item(Class<T> type, int id, Date createdAt, Date updatedAt) {
		this.type = type;
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save() {
		HibernateUtil.transact((s) -> {
			if (this.validate()) {
				s.save((T) this);
			} else {
				return null;
			}
			return (T) this;
		}, this.type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete() {
		HibernateUtil.transact((s) -> {
			s.delete((T) this);
			return null;
		}, this.type);
	}
	
	@Override
	public boolean beforeUpdate() {
		return true;
	}
	
	@Override
	public boolean beforeInsert() {
		return true;
	}
	
	@Override
	public void afterUpdate() {}
	
	@Override
	public void afterInsert() {}
	
	@Override
	public List<T> loadList(List<T> tList, String... assocs) {
		List<T> newList = new ArrayList<>();
		for (T t : tList) {
			t.load(assocs);
			newList.add(t);
		}
		return newList;
	}
}












