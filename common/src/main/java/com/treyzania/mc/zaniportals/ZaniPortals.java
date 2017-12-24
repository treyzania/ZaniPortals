package com.treyzania.mc.zaniportals;

import com.treyzania.mc.zaniportals.adapters.PortalConfig;
import com.treyzania.mc.zaniportals.adapters.PortalPlugin;
import com.treyzania.mc.zaniportals.adapters.ServerProvider;
import com.treyzania.mc.zaniportals.portal.FrameCalculator;
import com.treyzania.mc.zaniportals.portal.PortalManager;

public class ZaniPortals {

	public static ServerProvider server;
	public static PortalManager portals;
	public static PortalPlugin plugin;
	public static PortalConfig config;
	
	public static void savePortals() {
		portals.save(plugin.getPortalDataFile());
	}
	
	public static FrameCalculator getFrameCalculator() {
		return new FrameCalculator(config.getFrameBlockIds(), config.getMaxPortalSize());
	}
	
}
