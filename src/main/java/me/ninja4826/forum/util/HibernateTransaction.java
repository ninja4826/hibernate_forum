package me.ninja4826.forum.util;

import org.hibernate.Session;

public interface HibernateTransaction {
	public Object run(Session s);
}
