package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.Perms;
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
		if (!(player.hasPermission(Perms.CREATE_PORTAL) || player.isOp())) {
			
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
		if (success) {
			
			ZaniPortals.getFrameCalculator().populate(portal);
			portal.fill(ZaniPortals.config.getPortalBlockId());
			
			ZaniPortals.savePortals();
			
		}
		
		player.sendMessage(
			success
			? "Created portal " + portal.name + " with " + portal.getFrameLocations().length + " frame blocks and " + portal.getPortalLocations().length + " portal blocks."
			: "Failed to create portal!"
		);
		
		return false;
		
	}
	
	public boolean onSignRightClick(PortalPlayer player, PortalSign sign, PortalItem hand) {
		
		if (!sign.isPortaly()) return false;
		Portal portal = sign.getPortal();
		
		PortalTarget target = new NamedPortalTarget(portal);
		
		if (player.isSneaking()) {
			
			// Sneaking lets us do configuration.
			if (hand == null) {
				
				// In this case we want to get the target data.
				PortalItem pearl = ZaniPortals.server.getItem(PortalItem.ENDER_PEARL_ID);
				PortalLinkItem pli = pearl.convertToLinkItem();
				pli.setTarget(target);
				
				// TODO Publicness checking.
				// TODO Permissions
				player.addItem(pli);
				player.sendMessage("Here's the link item! (" + target.getExpression() + ")");
				
			} else if (hand.isPortaly()) {
				
				// TODO Permissions
				player.sendMessage("Target set!");
				portal.setTarget(hand.convertToLinkItem().getTarget());
				
			}
			
		} else {
			
			player.sendMessage("Poof!");
			portal.enter(player);
			
		}
		
		return true;
		
	}
	
	public boolean onPlayerBreakBlock(PortalPlayer player, PortalBlock block) {
		
		if (!block.isSign()) return false;
		
		Portal portal = ZaniPortals.portals.findPortal(block);
		if (portal == null) return false; // Null check.
		
		// Clearer this way, even though it's much more space.
		boolean check = false;
		check |= (portal.owner.equals(player.getUniqueId()) && player.hasPermission(Perms.DESTROY_PORTAL));
		check |= player.hasPermission(Perms.DESTROY_ANY_PORTAL);
		check |= player.isOp();
		
		if (check) {
			
			ZaniPortals.portals.removePortal(portal);
			ZaniPortals.savePortals();
			player.sendMessage("Destroyed portal: " + portal.name);
			
		} else {
			return true; // Cancel the event.
		}
		
		return false;
		
	}
	
	public boolean onMove(PortalEntity ent) {
		
		Portal portal = ZaniPortals.portals.findPortalAtEntity(ent);
		if (portal != null) portal.enter(ent);
		
		return false;
		
	}
	
}
