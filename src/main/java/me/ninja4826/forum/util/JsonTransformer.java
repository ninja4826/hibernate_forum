package me.ninja4826.forum.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.ninja4826.forum.model.entity.*;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {
	
	private Gson gson = (new GsonBuilder())
			.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
			.registerTypeAdapter(UserEntity.class, new EntitySerializer())
			.registerTypeAdapter(CategoryEntity.class, new EntitySerializer())
			.registerTypeAdapter(TopicEntity.class, new EntitySerializer())
			.registerTypeAdapter(PostEntity.class, new EntitySerializer())
			.create();

	@Override
	public String render(Object model) throws Exception {
		return gson.toJson(model);
	}

}
