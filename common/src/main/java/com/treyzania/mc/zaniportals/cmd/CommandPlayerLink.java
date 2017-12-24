package com.treyzania.mc.zaniportals.cmd;

import java.util.UUID;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalCommandSender;
import com.treyzania.mc.zaniportals.adapters.PortalPlayerInventory;
import com.treyzania.mc.zaniportals.portal.Items;
import com.treyzania.mc.zaniportals.portal.targets.PlayerPortalTarget;

import net.md_5.bungee.api.ChatColor;

public class CommandPlayerLink extends StandardPortalCommand {

	public CommandPlayerLink(String name) {
		super(name);
	}

	@Override
	public boolean execute(PortalCommandSender sender, String[] args) {
		
		if (!(sender.isOp() || sender.hasPermission(Perms.GIVE_PLAYER_LINK))) {
			
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
			return true;
			
		}
		
		if (!sender.isPlayer()) {
			
			sender.sendMessage("You must be a player to run this command!");
			return true;
			
		}
		
		if (args.length != 1 && args.length != 2) return false;
		
		String player = args[0];
		int num = 1;
		
		try {
			num = Math.min(Integer.parseInt(args[1]), 64);
		} catch (Exception e) {}
		
		UUID id = null;
		
		try {
			id = UUID.fromString(player);
		} catch (Exception e) {
			
			try {
				id = ZaniPortals.server.getPlayer(player).getUniqueId();
			} catch (Exception e2) {
				
				sender.sendMessage(ChatColor.RED + "That player isn't online right now.  Did you try using their UUID?");
				return true;
				
			}
			
		}
		
		// Put it together and give it to the player.
		PlayerPortalTarget target = new PlayerPortalTarget(id);
		PortalPlayerInventory inv = sender.getAsPlayer().getInventory();
		inv.addItem(Items.getLinkItem(target, num));
		
		return true;
		
	}

}
