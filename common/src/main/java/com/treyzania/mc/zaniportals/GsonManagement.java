package com.treyzania.mc.zaniportals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;
import com.treyzania.mc.zaniportals.gson.BlockPosAdapter;
import com.treyzania.mc.zaniportals.gson.Point3iAdapter;
import com.treyzania.mc.zaniportals.gson.PortalTargetAdapter;
import com.treyzania.mc.zaniportals.gson.PortalWorldAdapter;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;
import com.treyzania.mc.zaniportals.world.BlockPos;

public class GsonManagement {

	public static Gson getGson() {
		
		GsonBuilder gb = new GsonBuilder();
		
		// Config
		gb.enableComplexMapKeySerialization();
		gb.serializeNulls();
		gb.setPrettyPrinting(); // :)
		
		// Handlers
		gb.registerTypeAdapter(BlockPos.class, new BlockPosAdapter());
		gb.registerTypeAdapter(Point3i.class, new Point3iAdapter());
		gb.registerTypeAdapter(PortalTarget.class, new PortalTargetAdapter());
		gb.registerTypeAdapter(PortalWorld.class, new PortalWorldAdapter());
		
		return gb.create();
		
	}
	
}
