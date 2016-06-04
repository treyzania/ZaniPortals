package com.treyzania.mc.zaniportals.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.treyzania.mc.zaniportals.Point3i;

public class Point3iAdapter extends Adapter<Point3i> {

	@Override
	public JsonElement serialize(Point3i object, Type type, JsonSerializationContext ctx) {
		return new JsonPrimitive(String.format("%s,%s,%s", object.x, object.y, object.z));
	}
	
	@Override
	public Point3i deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		
		String[] parts = element.getAsString().split(",");
		return new Point3i(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
		
	}
	
}
