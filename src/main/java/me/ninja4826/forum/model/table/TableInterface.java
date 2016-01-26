package me.ninja4826.forum.model.table;

import java.util.Map;

import java.util.List;

import me.ninja4826.forum.model.entity.Item;
import me.ninja4826.forum.model.query.CriteriaExpression;
import me.ninja4826.forum.model.query.QueryExpression;

public interface TableInterface<T extends Item<T>> {
	
	public String getName();
	
	public T create(T t);
	public T create(Map<String, Object> tObj) throws InstantiationException, IllegalAccessException;
	
	public T update(int id, Map<String, Object> tObj);

	public T getByID(int id);
	public T getByID(int id, String... assocs);
	public T getByID(int id, boolean load);
	
	public T getOneBy(List<QueryExpression> queries, String... assocs);
	public T getOneBy(List<QueryExpression> queries, boolean load);
	public T getOneBy(QueryExpression query, String... assocs);
	public T getOneBy(QueryExpression query, boolean load);
	public T getOneBy(String property, QueryExpression.Operator operator, String value, String... assocs);
	public T getOneBy(String property, QueryExpression.Operator operator, String value, boolean load);
	public T getOneBy(String property, String value, String... assocs);
	public T getOneBy(String property, String value, boolean load);
	
	public List<T> getBy(List<QueryExpression> queries, String... assocs);
	public List<T> getBy(List<QueryExpression> queries, boolean load) throws InstantiationException, IllegalAccessException;
	public List<T> getBy(QueryExpression query, String... assocs);
	public List<T> getBy(QueryExpression query, boolean load) throws InstantiationException, IllegalAccessException;
	public List<T> getBy(String property, QueryExpression.Operator operator, String value, String... assocs);
	public List<T> getBy(String property, QueryExpression.Operator operator, String value, boolean load) throws InstantiationException, IllegalAccessException;
	public List<T> getBy(String property, String value, String... assocs);
	public List<T> getBy(String property, String value, boolean load) throws InstantiationException, IllegalAccessException;
	
	public List<T> getAll(String... assocs);
	public List<T> getAll(boolean load) throws InstantiationException, IllegalAccessException;

	
}
