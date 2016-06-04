package com.treyzania.mc.zaniportals.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;

public class PortalWorldAdapter extends Adapter<PortalWorld> {

	@Override
	public JsonElement serialize(PortalWorld object, Type type, JsonSerializationContext ctx) {
		return new JsonPrimitive(object.getName());
	}

	@Override
	public PortalWorld deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		return ZaniPortals.server.getWorld(element.getAsString());
	}

}
