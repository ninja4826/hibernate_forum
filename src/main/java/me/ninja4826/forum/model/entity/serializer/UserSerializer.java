package me.ninja4826.forum.model.entity.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import me.ninja4826.forum.model.entity.UserEntity;

public class UserSerializer implements JsonSerializer<UserEntity> {

	@Override
	public JsonElement serialize(UserEntity src, Type typeOfSrc, JsonSerializationContext context) {
		return src.serialize();
	}

}
