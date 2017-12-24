package com.treyzania.mc.zaniportals.cmd;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalCommandSender;
import com.treyzania.mc.zaniportals.portal.Portal;

import net.md_5.bungee.api.ChatColor;

public class CommandSetPortalPublic extends StandardPortalCommand {

	public CommandSetPortalPublic(String name) {
		super(name);
	}

	@Override
	public boolean execute(PortalCommandSender sender, String[] args) {
		
		if (args.length != 2) return false;
		
		String portalName = args[0];
		String state = args[1];
		
		Portal portal = ZaniPortals.portals.getPortal(portalName);
		if (portal == null) {
			
			sender.sendMessage(ChatColor.RED + "That portal doesn't seem to exist!  Check capitalization?");
			return true;
			
		}
		
		boolean isOwner = false;
		if (sender.isPlayer()) isOwner = sender.getAsPlayer().isOwner(portal);
		
		boolean canExecute = sender.hasPermission(Perms.SET_PORTAL_ACCESS_ANY) || (isOwner && sender.hasPermission(Perms.SET_PORTAL_ACCESS)) || sender.isOp();
		if (!canExecute) {
			
			sender.sendMessage(ChatColor.RED + "You cannot use this command on this portal!");
			return true;
			
		}
		
		// Very simple actual execution.
		if (state.equals("self")) {
			portal.isPublicLink = false;
		} else if (state.equals("all")) {
			portal.isPublicLink = true;
		} else {
			return false;
		}
		
		// Now just update and save things.
		portal.updateSign();
		ZaniPortals.savePortals();
		return true;
		
	}
	
}
