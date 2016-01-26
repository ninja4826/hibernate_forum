package me.ninja4826.forum.util;

import java.util.List;

import org.hibernate.Session;

public interface HibernateTransaction {
	public Object run(Session s);
	public List<Object> runList(Session s);
}
