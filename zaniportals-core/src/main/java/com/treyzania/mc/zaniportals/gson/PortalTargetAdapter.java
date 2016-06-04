package com.treyzania.mc.zaniportals.gson;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.portal.targets.AbsolutePortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.NamedPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.NotifyInvalidPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public class PortalTargetAdapter extends Adapter<PortalTarget> {

	private static HashMap<Class<? extends PortalTarget>, String> classNames = new HashMap<>();
	
	@Override
	public JsonElement serialize(PortalTarget object, Type type, JsonSerializationContext ctx) {
		
		return new JsonPrimitive(String.format("%s:%s", classNames.get(object.getClass()), object.getName()));
		
	}

	@Override
	public PortalTarget deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		
		String[] parts = element.getAsString().split(":");
		
		Class<? extends PortalTarget> clazz = null;
		for (Class<? extends PortalTarget> c : classNames.keySet()) if (classNames.get(c).equals(parts[0])) clazz = c;
		
		if (clazz == AbsolutePortalTarget.class) {
			
			String[] locParts = parts[1].substring(1, parts[1].length() - 1).split(","); // (x,y,z) -> x,y,z -> `[x, y, z]`
			String worldName = locParts[0];
			int x = Integer.parseInt(locParts[1]);
			int y = Integer.parseInt(locParts[2]);
			int z = Integer.parseInt(locParts[3]);
			
			return new AbsolutePortalTarget(ZaniPortals.server.getWorld(worldName).createLocation(x, y, z));
			
		} else if (clazz == NamedPortalTarget.class) {
			return new NamedPortalTarget(parts[1]);
		} else if (clazz == NotifyInvalidPortalTarget.class) {
			return new NotifyInvalidPortalTarget();
		}
		
		return null;
		
	}
	
	public static String makeSerializationPretty(String data) {
		
		char initial = data.charAt(0);
		String rest = data.substring(1);
		
		return Character.toString(initial).toUpperCase() + rest.replaceFirst(":", ": ");
		
	}
	
	public static String makeSerializationConforming(String pretty) {
		
		char initial = pretty.charAt(0);
		String rest = pretty.substring(1);
		
		return Character.toString(initial).toLowerCase() + rest.replaceFirst(": ", ":");
		
	}
	
	static {
		
		classNames.put(AbsolutePortalTarget.class, "absolute");
		classNames.put(NamedPortalTarget.class, "portal");
		classNames.put(NotifyInvalidPortalTarget.class, "invalid");
		
	}
	
}
