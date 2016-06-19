package com.treyzania.mc.zaniportals.portal;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalItem;

import net.md_5.bungee.api.ChatColor;

public class Items {

	public static final int ENDER_PEARL_ID = 368;
	public static final String PORTAL_PEARL_NAME = "" + ChatColor.AQUA + "Portal Pearl";
	
	public static boolean isPortalPearlName(String name) {
		return name.contains("Portal Pearl");
	}
	
	public static PortalItem getActivatorItem(int size) {
		
		PortalItem item = ZaniPortals.server.getItem(ENDER_PEARL_ID);
		
		item.setSize(size);
		item.resetPearlType(PortalPearlType.ACTIVATOR);
		
		return item;
		
	}
	
}
