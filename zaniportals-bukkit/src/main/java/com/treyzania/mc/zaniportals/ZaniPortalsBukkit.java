package com.treyzania.mc.zaniportals;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.treyzania.mc.zaniportals.adapters.BukkitCommandAdapter;
import com.treyzania.mc.zaniportals.adapters.BukkitServerProvider;
import com.treyzania.mc.zaniportals.adapters.PortalPlugin;
import com.treyzania.mc.zaniportals.cmd.AbstractPortalCommand;
import com.treyzania.mc.zaniportals.cmd.CommandGivePortalActivator;
import com.treyzania.mc.zaniportals.cmd.CommandListPortals;
import com.treyzania.mc.zaniportals.cmd.CommandPlayerLink;
import com.treyzania.mc.zaniportals.cmd.CommandSetPortalPublic;
import com.treyzania.mc.zaniportals.portal.PortalManager;

public class ZaniPortalsBukkit extends JavaPlugin implements PortalPlugin {

	public static ZaniPortalsBukkit INSTANCE;
	
	public ZaniPortalsConfig conf;
	
	@Override
	public void onEnable() {
		
		INSTANCE = this;
		
		// Set up the abstractination stuff.
		ZaniPortals.server = new BukkitServerProvider();
		ZaniPortals.plugin = this;
		this.loadConfig();
		
		// Set up the portal manager.
		PortalManager man = new PortalManager();
		man.load(this.getPortalDataFile());
		ZaniPortals.portals = man;
		
		// Basic Bukkit stuff.
		Bukkit.getPluginManager().registerEvents(new BukkitPortalEventAdapter(), this);
		this.registerCommand(new CommandListPortals("listportals"));
		this.registerCommand(new CommandGivePortalActivator("giveactivator"));
		this.registerCommand(new CommandPlayerLink("giveplayerlinkpearl"));
		this.registerCommand(new CommandSetPortalPublic("setportalaccess"));
		
	}
	
	@Override
	public void onDisable() {
		
		ZaniPortals.savePortals();
		
		// Clear the abstractination stuff.
		ZaniPortals.server = null;
		ZaniPortals.portals = null;
		ZaniPortals.plugin = null;
		ZaniPortals.config = null;
		
		INSTANCE = null;
		
	}
	
	private void loadConfig() {
		
		this.saveDefaultConfig();
		this.conf = new ZaniPortalsConfig(this.getConfig());
		ZaniPortals.config = this.conf;
		
	}
	
	private void registerCommand(AbstractPortalCommand command) {
		this.getCommand(command.getName()).setExecutor(new BukkitCommandAdapter(command));
	}
	
	@Override
	public File getPortalDataFile() {
		return new File(this.getDataFolder(), "portals.json");
	}
	
}
