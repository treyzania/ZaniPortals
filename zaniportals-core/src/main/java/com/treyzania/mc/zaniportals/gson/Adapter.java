package com.treyzania.mc.zaniportals.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public abstract class Adapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

	@Override
	public abstract JsonElement serialize(T object, Type type, JsonSerializationContext ctx);

	@Override
	public abstract T deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException;
	
}
