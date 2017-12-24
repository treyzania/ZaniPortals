package com.treyzania.mc.zaniportals.cmd;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalCommandSender;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;
import com.treyzania.mc.zaniportals.adapters.PortalPlayerInventory;
import com.treyzania.mc.zaniportals.portal.Items;

import net.md_5.bungee.api.ChatColor;

public class CommandGivePortalActivator extends StandardPortalCommand {

	public CommandGivePortalActivator(String name) {
		super(name);
	}

	@Override
	public boolean execute(PortalCommandSender sender, String[] args) {
		
		if (!(sender.isOp() || sender.hasPermission(Perms.GIVE_ACTIVATOR))) {
			
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
			return true;
			
		}
		
		if (args.length != 1 && args.length != 2) return false;
		
		String target = args[0];
		int num = 1;
		
		try {
			num = Math.min(Integer.parseInt(args[1]), 64);
		} catch (Exception e) {}
		
		PortalPlayer rec = ZaniPortals.server.getPlayer(target);
		PortalPlayerInventory inv = rec.getInventory();
		
		inv.addItem(Items.getActivatorItem(num));
		
		return true;
		
	}

}
