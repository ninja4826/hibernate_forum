package me.ninja4826.forum.util;

import java.util.List;

import me.ninja4826.forum.model.entity.Item;
import me.ninja4826.forum.util.HibernateUtil.Transact;
import me.ninja4826.forum.util.HibernateUtil.TransactList;

public class Trans<T extends Item<T>> {
	private Class<T> tClass;
	public Trans() {}
	
	public T transact(Transact<T> transaction) {
		return HibernateUtil.transact(transaction, this.tClass);
	}
	
	public List<T> transactList(TransactList<T> transaction) {
		return HibernateUtil.transactList(transaction, this.tClass);
	}
}
