package me.ninja4826.forum.model.table.registry;

import java.util.Map;
import java.util.HashMap;

import me.ninja4826.forum.model.entity.Item;
import me.ninja4826.forum.model.table.Table;
import me.ninja4826.forum.model.table.TableInterface;

public class TableRegistry {
	
//	private static Map<String, TableInterface<? extends Item<?>>> tables = new HashMap<>();
//	
//	public static void addTable(TableInterface<? extends Item<?>> table) {
//		tables.put(table.getName(), table);
//	}
//	
//	public static TableInterface<? extends Item<?>> getTable(String name) {
//		return tables.get(name);
//	}
	
	private static Map<String, Table<? extends Item<?>>> tables = new HashMap<>();
	
	public static void addTable(Table<? extends Item<?>> table) {
		System.out.println("table.getName(): " + table.getName());
		System.out.println("table.getType().getSimpleName(): " + table.getType().getSimpleName());
		
		tables.put(table.getName(), table);
	}
	
	public static Table<? extends Item<?>> getTable(String name) {
		return tables.get(name);
	}
}
