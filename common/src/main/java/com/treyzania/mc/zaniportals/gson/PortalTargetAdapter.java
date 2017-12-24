package com.treyzania.mc.zaniportals.gson;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public class PortalTargetAdapter extends Adapter<PortalTarget> {
	
	@Override
	public JsonElement serialize(PortalTarget object, Type type, JsonSerializationContext ctx) {
		return new JsonPrimitive(PortalHelper.serializePortalTarget(object));
	}

	@Override
	public PortalTarget deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		return PortalHelper.deserializePortalTarget(element.getAsString());
	}
	
}
