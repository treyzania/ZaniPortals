package com.treyzania.mc.zaniportals.portal;

import net.md_5.bungee.api.ChatColor;

public enum PortalPearlType {
	
	ACTIVATOR("Portal Charge"),
	LINK("Link Item");
	
	public String title;
	
	private PortalPearlType(String title) {
		this.title = ChatColor.WHITE + title;
	}
	
	public static PortalPearlType getType(String typeLine) {
		
		for (PortalPearlType test : values()) {
			if (ChatColor.stripColor(typeLine).contains(ChatColor.stripColor(test.title))) return test;
		}
		
		return null;
		
	}
	
}
