package com.treyzania.mc.zaniportals.portal;

import net.md_5.bungee.api.ChatColor;

public enum PortalPearlType {
	
	ACTIVATOR("" + ChatColor.WHITE + "Portal Charge"),
	LINK("" + ChatColor.WHITE + "Link Item");
	
	public String title;
	
	private PortalPearlType(String title) {
		this.title = title;
	}
	
	public static PortalPearlType getType(String typeLine) {
		
		for (PortalPearlType test : values()) {
			if (ChatColor.stripColor(typeLine).contains(ChatColor.stripColor(test.title))) return test;
		}
		
		return null;
		
	}
	
}
