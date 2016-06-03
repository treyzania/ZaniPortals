package com.treyzania.mc.zaniportals;

import org.bukkit.plugin.java.JavaPlugin;

import com.treyzania.mc.zaniportals.adapters.BukkitServerProvider;

public class ZaniPortalsBukkit extends JavaPlugin {

	public static ZaniPortalsBukkit INSTANCE;
	
	public ZaniPortalsConfig conf;
	
	@Override
	public void onEnable() {
		
		INSTANCE = this;
		ZaniPortals.server = new BukkitServerProvider();
		
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
