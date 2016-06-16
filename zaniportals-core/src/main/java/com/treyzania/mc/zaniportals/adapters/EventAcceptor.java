package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.portal.Portal;
import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.targets.NamedPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;
import com.treyzania.mc.zaniportals.world.Face;

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
				
				if (!PortalHelper.isPortalCompatible(player, portal, Perms.GET_OWN_PORTAL_REFERENCE, Perms.GET_OTHER_PORTAL_REFERENCE)) return true;
				
				// In this case we want to get the target data.
				PortalItem pearl = ZaniPortals.server.getItem(PortalItem.ENDER_PEARL_ID);
				PortalLinkItem pli = pearl.convertToLinkItem();
				pli.setTarget(target);
				
				// TODO Publicness checking.
				player.addItem(pli);
				player.sendMessage("Here's the link item! (" + target.getExpression() + ")");
				
			} else if (hand.isPortaly()) {
				
				if (!PortalHelper.isPortalCompatible(player, portal, Perms.SET_OWN_PORTAL_TARGET, Perms.SET_OTHER_PORTAL_TARGET)) return true;
				player.sendMessage("Target set!");
				portal.setTarget(hand.convertToLinkItem().getTarget());
				
			}
			
		} else {
			
			if (!PortalHelper.isPortalCompatible(player, portal, Perms.USE_OWN_PORTAL, Perms.USE_OTHER_PORTAL)) return true;
			player.sendMessage("Poof!");
			portal.enter(player);
			
		}
		
		return true;
		
	}
	
	public boolean onPlayerBreakBlock(PortalPlayer player, PortalBlock block) {
		
		// First check to see if it's important, if it isn't just break out of here.  It's more efficient than to go and look up the block.
		if (!(ZaniPortals.config.isBlockImportant(block.getId()) || block.isSign())) return false;
		
		// Now we check for the more important things.
		Portal portal = ZaniPortals.portals.findPortal(block.getLocation(), false, true);
		if (portal == null) return false; // Null check.
		
		// Clearer this way, even though it's much more space.
		boolean check = false;
		check |= player.isOwner(portal) && player.hasPermission(Perms.DESTROY_OWN_PORTAL);
		check |= !player.isOwner(portal) && player.hasPermission(Perms.DESTROY_OTHER_PORTAL);
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
	
	public boolean onNaturalDestroy(PortalBlock block) {
		
		// Cancels it if there is a portal there.
		return ZaniPortals.portals.findPortal_anyBlock(block) != null;
		
	}
	
	public boolean onMove(PortalEntity ent) {
		
		// TODO FIXME Doing this straight off the bat will get rather inefficient with many portals.
		Portal portal = ZaniPortals.portals.findPortalAtEntity(ent);
		if (portal == null) return false;
		
		if (ent instanceof PortalPlayer) {
			
			PortalPlayer player = (PortalPlayer) ent;
			
			if (!PortalHelper.isPortalCompatible(player, portal, Perms.USE_OWN_PORTAL, Perms.USE_OTHER_PORTAL)) {
				
				player.sendMessage("You do not have permission to use this portal!");
				return true;
				
			}
			
			portal.enter(player);
			
		} else {
			portal.enter(ent);
		}
		
		return false;
		
	}
	
	public boolean onFlow(PortalBlock from, PortalBlock to) {
		
		// Search to see if any nearby blocks are portals.
		PortalBlock test = null;
		for (Face f : Face.values()) {
			
			// Check for sources.
			test = from.getBlockOnFace(f);
			if (ZaniPortals.portals.findPortal_anyBlock(test) != null) return true;
			
			// Check for destinations.  (This step might not be necessary.)
			test = to.getBlockOnFace(f);
			if (ZaniPortals.portals.findPortal_anyBlock(test) != null) return true;
			
		}
		
		return false;
		
	}
	
	public boolean onPlayerPlaceBlock(PortalPlayer player, PortalBlock block) {
		return ZaniPortals.portals.findPortal(block.getLocation(), true, false) != null;
	}
	
}
