package me.ninja4826.forum.model.table;

import me.ninja4826.forum.model.entity.CategoryEntity;
import me.ninja4826.forum.util.Trans;

public class CategoryTable {
	
	private static Trans<CategoryEntity> trans = new Trans<>();
	
	public static boolean validate(CategoryEntity c) {
		if (c.getName() == null) return false;
		return true;
	}

	public static CategoryEntity create(CategoryEntity c) {
		if (!c.validate()) return null;
		c.save();
		return c;
	}
	
	public static CategoryEntity create(String name, String description, CategoryEntity parent) {
		CategoryEntity c = new CategoryEntity();
		c.setName(name);
		c.setDescription(description);
		c.setParent(parent);
		
		return create(c);
	}
}
