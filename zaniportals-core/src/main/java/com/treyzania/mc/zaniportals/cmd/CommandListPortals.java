package com.treyzania.mc.zaniportals.cmd;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalCommandSender;
import com.treyzania.mc.zaniportals.portal.Portal;

import net.md_5.bungee.api.ChatColor;

public class CommandListPortals extends StandardPortalCommand {
	
	public CommandListPortals(String name) {
		super(name);
	}
	
	@Override
	public boolean execute(PortalCommandSender sender, String[] args) {
		
		sender.sendMessage(ChatColor.GREEN + "Portals:");
		for (Portal p : ZaniPortals.portals) {
			
			if (p.isSecret && !(sender.hasPermission(Perms.LIST_SECRET_PORTALS) || sender.isOp()) ) continue;
			
			// Configure the parts.
			boolean pub = p.isPublicLink;
			ChatColor col = pub ? ChatColor.DARK_GREEN : ChatColor.BLUE;
			String explanation = "(" + (pub ? "public" : "private") + ")";
			String secret = p.isSecret ? " " + ChatColor.RED + "[secret]" : "";
			
			sender.sendMessage(ChatColor.YELLOW + " - " + p.name + " " + col + explanation + secret);
			
		}
		
		return true;
		
	}

}
