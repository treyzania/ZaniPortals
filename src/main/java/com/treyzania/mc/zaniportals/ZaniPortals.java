package com.treyzania.mc.zaniportals;

import org.bukkit.plugin.java.JavaPlugin;

public class ZaniPortals extends JavaPlugin {

	public static ZaniPortals INSTANCE;
	
	public ZaniPortalsConfig conf;
	
	@Override
	public void onEnable() {
		
		INSTANCE = this;
		
		this.loadConfig();
		
	}
	
	@Override
	public void onDisable() {
		
		INSTANCE = null;
		
	}
	
	private void loadConfig() {
		
		this.saveDefaultConfig();
		this.conf = new ZaniPortalsConfig(this.getConfig());
		
	}
	
}
