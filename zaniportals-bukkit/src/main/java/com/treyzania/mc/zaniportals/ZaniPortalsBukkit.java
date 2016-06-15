package com.treyzania.mc.zaniportals;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.treyzania.mc.zaniportals.adapters.BukkitCommandAdapter;
import com.treyzania.mc.zaniportals.adapters.BukkitServerProvider;
import com.treyzania.mc.zaniportals.cmd.AbstractPortalCommand;
import com.treyzania.mc.zaniportals.cmd.CommandListPortals;
import com.treyzania.mc.zaniportals.portal.PortalManager;

public class ZaniPortalsBukkit extends JavaPlugin {

	public static ZaniPortalsBukkit INSTANCE;
	
	public ZaniPortalsConfig conf;
	
	@Override
	public void onEnable() {
		
		INSTANCE = this;
		ZaniPortals.server = new BukkitServerProvider();
		
		this.loadConfig();
		
		// Basic Bukkit stuff.
		Bukkit.getPluginManager().registerEvents(new BukkitPortalEventAdapter(), this);
		this.registerCommand(new CommandListPortals("listportals"));
		
		// Set up the portal manager.
		PortalManager man = new PortalManager();
		man.load(this.getPortalDataFile());
		ZaniPortals.portals = man;
		
	}
	
	@Override
	public void onDisable() {
		
		ZaniPortals.portals.save(this.getPortalDataFile());
		ZaniPortals.portals = null;
		
		INSTANCE = null;
		
	}
	
	private void loadConfig() {
		
		this.saveDefaultConfig();
		this.conf = new ZaniPortalsConfig(this.getConfig());
		
	}
	
	private void registerCommand(AbstractPortalCommand command) {
		this.getCommand(command.getName()).setExecutor(new BukkitCommandAdapter(command));
	}
	
	private File getPortalDataFile() {
		return new File(this.getDataFolder(), "portals.json");
	}
	
}
