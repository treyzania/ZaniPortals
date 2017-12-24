package com.treyzania.mc.zaniportals.cmd;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalCommandSender;
import com.treyzania.mc.zaniportals.portal.PortalManager;

import net.md_5.bungee.api.ChatColor;

public class CommandReloadPortals extends StandardPortalCommand {

	public CommandReloadPortals(String name) {
		super(name);
	}

	@Override
	public boolean execute(PortalCommandSender sender, String[] args) {
		
		if (!sender.hasPermission(Perms.RELOAD_PORTALS) && !sender.isOp()) {
			
			sender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
			return true;
			
		}
		
		// Very simple.
		sender.sendMessage(ChatColor.GREEN + "Reloading portals...");
		ZaniPortals.portals = new PortalManager();
		ZaniPortals.portals.load(ZaniPortals.plugin.getPortalDataFile());
		sender.sendMessage(ChatColor.GREEN + "Done!");
		
		return true;
		
	}

}
