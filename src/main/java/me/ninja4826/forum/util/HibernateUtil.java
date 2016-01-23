package me.ninja4826.forum.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import me.ninja4826.forum.gen.Category;
import me.ninja4826.forum.gen.CategoryAncestor;
import me.ninja4826.forum.gen.Post;
import me.ninja4826.forum.gen.PostAncestor;
import me.ninja4826.forum.gen.Topic;
import me.ninja4826.forum.gen.User;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
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
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory;
	}
	
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	public static <T> T transact(HibernateTransaction transaction, Class<T> cls) {
		Session s = open();
		try {
			return cls.cast(transaction.run(s));
		} catch (ClassCastException e) {
			e.printStackTrace();
			close();
		}
		close();
		return null;
	}
	
	public static Session open() {
		Session s = getCurrentSession();
		s.beginTransaction();
		return s;
	}
	
	public static void close() {
		Transaction tx = getCurrentSession().getTransaction();
		try {
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
	}
}
