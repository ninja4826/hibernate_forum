package me.ninja4826.forum.model.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import me.ninja4826.forum.gen.User;
import me.ninja4826.forum.model.entity.UserEntity;
import me.ninja4826.forum.util.HibernateUtil;
import me.ninja4826.forum.util.Trans;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.NaturalIdLoadAccess;

public class UserTable {
	
	private static Trans<UserEntity> trans = new Trans<>();
	
	public static boolean validate(UserEntity u) {
		String pattern =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (!Pattern.compile(pattern).matcher(u.getEmail()).matches()) return false;
		
		if (u.getUsername() == null) return false;
		
		return true;
	}
	
	public static UserEntity create(UserEntity u) {
		
		if (!validate(u) || u.getPassword() == null) return null;
		
		u.updatePassword(u.getPassword());
		u.setPassword(null);
		
		u.save();
		
		return u;
	}
	
	public static UserEntity create(String uname, String email, String pass, String role) {
		UserEntity u = new UserEntity();
		u.setUsername(uname);
		u.setEmail(email);
		u.setPassword(pass);
		
		return create(u);
	}
	
	public static UserEntity create(String uname, String email, String pass) {
		return create(uname, email, pass, "user");
	}
	
	public static UserEntity update(int id, HashMap<String, String> uObj) {
		
		UserEntity user = trans.transact((s) -> {
			UserEntity u = new UserEntity(s.byId(User.class).load(id));
			
			for (Map.Entry<String, String> entry : uObj.entrySet()) {
				switch (entry.getKey())
				{
				case "username":
					u.setUsername(entry.getValue());
					break;
				case "password":
					u.updatePassword(entry.getValue());
					break;
				}
			}
			u.save();
			return u;
		});
		return user;
	}
	
	public static UserEntity getByID(int id, boolean lazy) {
		UserEntity u = trans.transact((s) -> {
			UserEntity user;
			if (lazy) {
				String hql = "select u from User u where u.id = :id";
				user = new UserEntity((User) s.createQuery(hql).setParameter("id", id).uniqueResult());
			} else {
				user = new UserEntity((User) s.byId(User.class).load(id));
			}
			return user;
		});
		return u;
	}
	
	public static UserEntity getByID(int id) {
		return getByID(id, true);
	}
	
	public static UserEntity getJoins(User u) {
		return getJoins(u.getId());
	}
	
	public static UserEntity getJoins(int id) {
		return getByID(id, false);
	}
	
	public static UserEntity getBy(Map<String, Object> fields, boolean lazy) {
		UserEntity user = trans.transact((s) -> {
			UserEntity u;
			NaturalIdLoadAccess<User> load = s.byNaturalId(User.class);
			if (fields.isEmpty()) return null;
			for (Entry<String, Object> entry : fields.entrySet()) {
				load = load.using(entry.getKey(), entry.getValue());
			}
			if (lazy) {
				u = new UserEntity(load.getReference());
			} else {
				u = new UserEntity(load.load());
			}
			return u;
		});
		return user;
	}
	
	public static UserEntity getBy(Map<String, Object> fields) {
		return getBy(fields, true);
	}
	
	public static UserEntity getBy(String field, Object val, boolean lazy) {
		HashMap<String, Object> map = new HashMap<>();
		map.put(field, val);
		return getBy(map, lazy);
	}
	
	public static UserEntity getBy(String field, Object val) {
		return getBy(field, val, true);
	}
	
	@SuppressWarnings("unchecked")
	public static List<UserEntity> getAll(boolean lazy) {
		ArrayList<UserEntity> entities = HibernateUtil.transact((s) -> {
			Criteria c = s.createCriteria(User.class);
			if (!lazy) {
				c = c.setFetchMode("topics", FetchMode.SELECT)
						.setFetchMode("posts", FetchMode.SELECT);
			}
			List<User> users = (List<User>) c.list();
			List<UserEntity> userEntities = new ArrayList<UserEntity>();
			for (User u : users) {
				userEntities.add(new UserEntity(u));
			}
			return userEntities;
		}, ArrayList.class);
		return entities;
	}
	
	public static List<UserEntity> getAll() {
		return getAll(true);
	}
}
