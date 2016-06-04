package com.treyzania.mc.zaniportals;

import org.bukkit.plugin.java.JavaPlugin;

import com.treyzania.mc.zaniportals.adapters.BukkitCommandAdapter;
import com.treyzania.mc.zaniportals.adapters.BukkitServerProvider;
import com.treyzania.mc.zaniportals.cmd.AbstractPortalCommand;
import com.treyzania.mc.zaniportals.cmd.CommandListPortals;

public class ZaniPortalsBukkit extends JavaPlugin {

	public static ZaniPortalsBukkit INSTANCE;
	
	public ZaniPortalsConfig conf;
	
	@Override
	public void onEnable() {
		
		INSTANCE = this;
		ZaniPortals.server = new BukkitServerProvider();
		
		this.loadConfig();
		
		this.registerCommand(new CommandListPortals("listportals"));
		
	}
	
	@Override
	public void onDisable() {
		
		INSTANCE = null;
		
	}
	
	private void loadConfig() {
		
		this.saveDefaultConfig();
		this.conf = new ZaniPortalsConfig(this.getConfig());
		
	}
	
	private void registerCommand(AbstractPortalCommand command) {
		this.getCommand(command.getName()).setExecutor(new BukkitCommandAdapter(command));
	}
	
}
