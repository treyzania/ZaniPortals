package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.portal.Portal;
import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.targets.NamedPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public class EventAcceptor {

	public boolean onSignUpdate(PortalPlayer player, PortalSign sign, String[] lines) {
		
		// Validation.
		if (!PortalHelper.isValidNewSignSyntax(lines)) return false; // Do nothing.
		if (!PortalHelper.isValidSignBlock(sign)) return false; // Do nothing.
		if (!(player.hasPermission("zaniportals.portal.create") || player.isOp())) {
			
			player.sendMessage("You don't have permission to do that!");
			return false;
			
		}
		
		String name = lines[1];
		
		if (ZaniPortals.portals.hasPortalWithName(name)) {
			
			player.sendMessage("A portal with that name already exists!");
			return true; // Cancel sign update.
			
		}
		
		// Create the actual portal.
		Portal portal = new Portal(player.getWorld(), player.getUniqueId(), name);
		portal.setSignBlock(sign.getLocation().getAsPoint3i());
		
		boolean success = ZaniPortals.portals.tryAddPortal(portal);
		
		player.sendMessage(success ? "Created portal: " + portal.name : "Failed to create portal!");
		
		return false;
		
	}
	
	public boolean onEnderPearlUse(PortalPlayer player, PortalItem item) {
		return item.isPortaly();
	}
	
	public boolean onSignRightClick(PortalPlayer player, PortalSign sign, PortalItem hand) {
		
		if (!sign.isPortaly()) return false;
		
		Portal portal = ZaniPortals.portals.getPortal(sign.getLine(1));
		PortalTarget target = new NamedPortalTarget(portal);
		
		if (player.isSneaking()) {
			
			// Sneaking lets us do configuration.
			if (hand == null) {
				
				// In this case we want to get the target data.
				PortalItem pearl = ZaniPortals.server.getItem(PortalItem.ENDER_PEARL_ID);
				PortalLinkItem pli = pearl.convertToLinkItem();
				pli.setTarget(target);
				
				player.addItem(pli);
				player.sendMessage("Here's the link item! (" + target.getExpression() + ")");
				
			} else if (hand.isPortaly()) {
				
				player.sendMessage("Target set!");
				portal.setTarget(hand.convertToLinkItem().getTarget());
				
			}
			
		} else {
			
			player.sendMessage("Poof!");
			portal.enter(player);
			
		}
		
		return true;
		
	}
	
}
