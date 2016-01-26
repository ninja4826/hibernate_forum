package me.ninja4826.forum.model.table;

import java.util.List;

import org.hibernate.Criteria;

import me.ninja4826.forum.model.entity.User;
import me.ninja4826.forum.model.query.CriteriaExpression;
import me.ninja4826.forum.util.Trans;

public class UserTable extends Table<User> {
	
	private Trans<User> trans = new Trans<>();

	public UserTable() {
		super(User.class);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<User> getAll(String... assocs) {
//		return trans.transactList((s) -> {
//			Criteria c = s.createCriteria(User.class);
//			
//			c.set
//			
//			return c.list();
//		});
//	}
	
	@SuppressWarnings("unchecked")
	public List<User> find(CriteriaExpression<User> ce) {
		return trans.transactList((s) -> {
			Criteria c = s.createCriteria(User.class);
			c = ce.run(c);
			return c.list();
		});
	}
}
