package me.ninja4826.forum.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

	private ObjectMapper mapper;
	private static JsonTransformer instance = null;
	
	private JsonTransformer() {
		this.mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate5Module());
	}
	
	public static JsonTransformer getInstance() {
		if (instance == null) instance = new JsonTransformer();
		return instance;
	}
	
	@Override
	public String render(Object model) throws Exception {
		return mapper.writeValueAsString(model);
	}
	
	public String render(Object model, boolean pretty) throws Exception {
		if (pretty) {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
		}
		return render(model);
	}
	
//	private Gson gson = (new GsonBuilder())
//			.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
////			.registerTypeAdapter(UserEntity.class, new EntitySerializer())
////			.registerTypeAdapter(CategoryEntity.class, new EntitySerializer())
////			.registerTypeAdapter(TopicEntity.class, new EntitySerializer())
////			.registerTypeAdapter(PostEntity.class, new EntitySerializer())
//			.create();
//
//	@Override
//	public String render(Object model) throws Exception {
//		return gson.toJson(model);
//	}

}
