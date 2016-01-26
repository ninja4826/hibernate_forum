package me.ninja4826.forum.model.table;

import me.ninja4826.forum.model.entity.Item;

public class DefaultTable<T extends Item<T>> extends Table<T> {
	public DefaultTable(Class<T> cls) {
		super(cls);
	}
}
