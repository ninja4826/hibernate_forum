package me.ninja4826.forum.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import me.ninja4826.forum.model.entity.Category;
import me.ninja4826.forum.model.entity.CategoryAncestor;
import me.ninja4826.forum.model.entity.Item;
import me.ninja4826.forum.model.entity.Post;
import me.ninja4826.forum.model.entity.PostAncestor;
import me.ninja4826.forum.model.entity.Topic;
import me.ninja4826.forum.model.entity.User;
import me.ninja4826.forum.model.table.DefaultTable;
import me.ninja4826.forum.model.table.UserTable;
import me.ninja4826.forum.model.table.registry.TableRegistry;

public class HibernateUtil {
	
	public static interface Transact<T> {
		public Object run(Session s);
	}
	public static interface TransactList<T> {
		public List<T> run(Session s);
	}
	
	private static SessionFactory sessionFactory;
	private static boolean booted = false;
	
	private static SessionFactory buildSessionFactory() {
//		BootstrapServiceRegistry bootstrapRegistry = new BootstrapServiceRegistryBuilder()
//				.applyIntegrator(new EventIntegrator())
//				.build();
		
//		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder(bootstrapRegistry)
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure()
				.build();
		try {
			
			Metadata metadata = new MetadataSources(registry)
					.addAnnotatedClass(Category.class)
					.addAnnotatedClass(CategoryAncestor.class)
					.addAnnotatedClass(Post.class)
					.addAnnotatedClass(PostAncestor.class)
					.addAnnotatedClass(Topic.class)
					.addAnnotatedClass(User.class)
					
					.getMetadataBuilder()
					.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
					.build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();
			
			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			ex.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static void boot() {
		System.out.println("Called boot.");
		if (!booted && sessionFactory == null) {
			List<User> users = new ArrayList<>();;
			try {
				sessionFactory = buildSessionFactory();
				booted = true;
				TableRegistry.addTable(new UserTable());
				TableRegistry.addTable(new DefaultTable<Topic>(Topic.class));
				System.out.println("Getting all users");
				users = (List<User>)(List<?>) TableRegistry.getTable("User").getAll(true);
			} catch (NullPointerException | InstantiationException | IllegalAccessException e) {
				System.out.println(users.size());
				System.out.println("uuuugh");
				e.printStackTrace();
			}
			
		}
	}
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory;
	}
	
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	public static <T extends Item<T>> T transact(Transact<T> transaction, Class<T> cls) {
		Session s = open();
		try {
			T t = cls.cast(transaction.run(s));
			close();
			return t;
		} catch (ClassCastException e) {
			e.printStackTrace();
			close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Item<T>> List<T> transactList(TransactList<T> transaction, Class<T> cls) {
		Session s = open();
		List<T> objs = new ArrayList<>();
		List<User> users = new ArrayList<>();
		try {
			List<T> trans = transaction.run(s);
//			for (T t : trans) {
////				For testing's sake.
//				users.add((User) t);
////				objs.add(cls.cast(t));
//			}
			objs = (List<T>)(List<?>) trans;
			close();
			return objs;
		} catch (ClassCastException | NullPointerException e) {
			System.out.println("ugh");
			System.out.println(objs.size());
			System.out.println(users.size());
			e.printStackTrace();
			close();
		}
		return null;
	}
	
	public static Session open(boolean transaction) {
		Session s = getCurrentSession();
		if (transaction) {
			s.beginTransaction();
		}
		return s;
	}
	
	public static Session open() {
		return open(true);
	}
	
	public static void close() {
		Transaction tx = getCurrentSession().getTransaction();
		try {
			tx.commit();
		} catch (Exception e) {
			System.err.println("Uh oh!");
			e.printStackTrace();
			tx.rollback();
		}
	}
}
