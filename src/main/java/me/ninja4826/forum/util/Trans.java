package me.ninja4826.forum.util;

public class Trans<T> {
	private Class<T> tClass;
	public Trans() {}
	
	public T transact(HibernateTransaction transaction) {
		return HibernateUtil.transact(transaction, this.tClass);
	}
}
