package com.treyzania.mc.zaniportals;

import org.bukkit.plugin.java.JavaPlugin;


public class ZaniPortals extends JavaPlugin {

	public static ZaniPortals INSTANCE;
	
	@Override
	public void onEnable() {
		
		INSTANCE = this;
		
	}
	
	@Override
	public void onDisable() {
		
		INSTANCE = null;
		
	}
	
}
