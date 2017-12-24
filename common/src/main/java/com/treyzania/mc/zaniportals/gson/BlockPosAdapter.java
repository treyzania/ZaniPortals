package com.treyzania.mc.zaniportals.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;
import com.treyzania.mc.zaniportals.world.BlockPos;

public class BlockPosAdapter extends Adapter<BlockPos> {
	
	@Override
	public JsonElement serialize(BlockPos object, Type type, JsonSerializationContext ctx) {
		return new JsonPrimitive(String.format("%s:%s,%s,%s", object.world.getName(), object.getX(), object.getY(), object.getZ()));
	}
	
	@Override
	public BlockPos deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		
		String[] parts = element.getAsString().split(":");
		
		// Split everything up
		String worldName = parts[0];
		String[] axisValues = parts[1].split(",");
		
		// Create the actual BlockPos
		PortalWorld world = ZaniPortals.server.getWorld(worldName);
		return new BlockPos(world, Integer.parseInt(axisValues[0]), Integer.parseInt(axisValues[1]), Integer.parseInt(axisValues[2]));
		
	}
	
}
