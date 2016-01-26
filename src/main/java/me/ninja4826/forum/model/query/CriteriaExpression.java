package me.ninja4826.forum.model.query;

import org.hibernate.Criteria;
import org.hibernate.Session;

public interface CriteriaExpression<T> {
	public Criteria run(Criteria c);
}
