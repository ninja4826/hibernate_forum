package me.ninja4826.forum.model.table;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.metadata.ClassMetadata;

import me.ninja4826.forum.model.entity.Item;
import me.ninja4826.forum.model.query.QueryExpression;
import me.ninja4826.forum.util.HibernateUtil;
import me.ninja4826.forum.util.JsonTransformer;
import me.ninja4826.forum.util.Trans;

public abstract class Table<T extends Item<T>> implements TableInterface<T> {
	
//	public String[] eagerJoins = new String[]{};
	
	private Class<T> type;
	private ClassMetadata classMetadata;
	private List<String> fields;
	private Trans<T> trans = new Trans<>();
	
	public Table(Class<T> cls) {
		this.type = cls;
		this.classMetadata = HibernateUtil.getSessionFactory().getClassMetadata(this.type);
		this.fields = Arrays.asList(this.classMetadata.getPropertyNames());
	}
	
	public Class<T> getType() {
		return this.type;
	}
	
	@Override
	public String getName() {
		return this.type.getSimpleName();
	}
	
	@Override
	public T create(T t) {
		if (t.beforeInsert()) {
			t.save();
			t.afterInsert();
			return t;
		}
		return null;
	}

	@Override
	public T create(Map<String, Object> tObj) throws InstantiationException, IllegalAccessException {
		T obj = type.newInstance();
		for (Map.Entry<String, Object> entry : tObj.entrySet()) {
			if (this.fields.contains(entry.getKey())) {
				obj.set(entry.getKey(), entry.getValue());
			}
		}
		return this.create(obj);
	}

	@Override
	public T update(int id, Map<String, Object> tObj) {
		T t = trans.transact((s) -> {
			T item = s.byId(this.type).load(id);
			for (Map.Entry<String, Object> entry : tObj.entrySet()) {
				if (this.fields.contains(entry.getKey())) {
					item.set(entry.getKey(), entry.getValue());
				}
			}
			return item;
		});
		if (t.beforeUpdate()) {
			t.save();
			t.afterUpdate();
			return t;
		}
		return null;
	}

	@Override
	public T getByID(int id) {
		return trans.transact((s) -> {
			return s.byId(this.type).getReference(id);
		});
	}

	@Override
	public T getByID(int id, String... assocs) {
		T t = getByID(id);
		t.load(assocs);
		return t;
	}

	@Override
	public T getByID(int id, boolean load) {
		T t = getByID(id);
		if (load) t.load();
		return t;
	}

	@Override
	public T getOneBy(List<QueryExpression> queries, String... assocs) {
		T t = trans.transact((s) -> {
			Criteria c = s.createCriteria(this.type);
			for (QueryExpression q : queries) {
				c.add(q.run());
			}
			return c.uniqueResult();
		});
		t.load(assocs);
		return t;
	}

	@Override
	public T getOneBy(List<QueryExpression> queries, boolean load) {
		T t = getOneBy(queries);
		if (load) {
			t.load();
		} else {
//			t.load(eagerJoins);
		}
		return t;
	}

	@Override
	public T getOneBy(QueryExpression query, String... assocs) {
		return getOneBy(Arrays.asList(new QueryExpression[]{query}), assocs);
	}

	@Override
	public T getOneBy(QueryExpression query, boolean load) {
		T t = getOneBy(query);
		if (load) {
			t.load();
		} else {
//			t.load(eagerJoins);
		}
		return t;
	}

	@Override
	public T getOneBy(String property, QueryExpression.Operator operator, String value, String... assocs) {
		return getOneBy(new QueryExpression(property, operator, value), assocs);
	}

	@Override
	public T getOneBy(String property, QueryExpression.Operator operator, String value, boolean load) {
		T t = getOneBy(new QueryExpression(property, operator, value));
		if (load) {
			t.load();
		} else {
//			t.load(eagerJoins);
		}
		return t;
	}

	@Override
	public T getOneBy(String property, String value, String... assocs) {
		return getOneBy(property, QueryExpression.Operator.EQ, value, assocs);
	}

	@Override
	public T getOneBy(String property, String value, boolean load) {
		return getOneBy(property, QueryExpression.Operator.EQ, value, load);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getBy(List<QueryExpression> queries, String... assocs) {
		return trans.transactList((s) -> {
			Criteria c = s.createCriteria(this.type);
			for (QueryExpression q : queries) {
				c.add(q.run());
			}
			
			for (String assoc : assocs) {
				c.setFetchMode(assoc, FetchMode.JOIN);
			}
			return c.list();
		});
	}

	@Override
	public List<T> getBy(List<QueryExpression> queries, boolean load) throws InstantiationException, IllegalAccessException {
		String[] assocs = new String[]{};
		if (load) {
			assocs = this.type.newInstance().getAssocs();
		} else {
//			assocs = eagerJoins;
		}
		return getBy(queries, assocs);
	}

	@Override
	public List<T> getBy(QueryExpression query, String... assocs) {
		return getBy(Arrays.asList(new QueryExpression[]{query}), assocs);
	}

	@Override
	public List<T> getBy(QueryExpression query, boolean load) throws InstantiationException, IllegalAccessException {
		return getBy(Arrays.asList(new QueryExpression[]{query}), load);
	}

	@Override
	public List<T> getBy(String property, QueryExpression.Operator operator, String value, String... assocs) {
		return getBy(new QueryExpression(property, operator, value), assocs);
	}

	@Override
	public List<T> getBy(String property, QueryExpression.Operator operator, String value, boolean load) throws InstantiationException, IllegalAccessException {
		String[] assocs = new String[]{};
		if (load) {
			assocs = this.type.newInstance().getAssocs();
		} else {
//			assocs = eagerJoins;
		}
		return getBy(new QueryExpression(property, operator, value), assocs);
	}

	@Override
	public List<T> getBy(String property, String value, String... assocs) {
		return getBy(new QueryExpression(property, QueryExpression.Operator.EQ, value), assocs);
	}

	@Override
	public List<T> getBy(String property, String value, boolean load) throws InstantiationException, IllegalAccessException {
		String[] assocs = new String[]{};
		if (load) {
			assocs = this.type.newInstance().getAssocs();
		} else {
//			assocs = eagerJoins;
		}
		return getBy(new QueryExpression(property, QueryExpression.Operator.EQ, value), assocs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String... assocs) {
		return trans.transactList((s) -> {
			Criteria c = s.createCriteria(this.type);
			for (String assoc : assocs) {
				c.setFetchMode(assoc, FetchMode.JOIN);
			}
			List<T> tList = c.list();
			try {
				System.out.println(JsonTransformer.getInstance().render(tList, true));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (T t : tList) {
				System.out.println("ID: " + t.getID());
			}
			return c.list();
		});
	}

	@Override
	public List<T> getAll(boolean load) throws InstantiationException, IllegalAccessException {
		String[] assocs = new String[]{};
		if (load) {
			assocs = this.type.newInstance().getAssocs();
		}
		return getAll(assocs);
	}
}
