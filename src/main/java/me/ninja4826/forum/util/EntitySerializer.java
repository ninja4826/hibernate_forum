package me.ninja4826.forum.util;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import me.ninja4826.forum.model.entity.EntityInterface;

@SuppressWarnings("rawtypes")
public class EntitySerializer implements JsonSerializer<EntityInterface> {

	@Override
	public JsonElement serialize(EntityInterface src, Type typeOfSrc, JsonSerializationContext context) {
		return src.serialize();
	}

}
