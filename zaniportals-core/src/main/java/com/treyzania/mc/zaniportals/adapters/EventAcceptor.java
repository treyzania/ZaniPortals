package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.portal.Portal;
import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.PortalPearlType;
import com.treyzania.mc.zaniportals.world.Face;

import net.md_5.bungee.api.ChatColor;

public class EventAcceptor {
	
	public boolean onSignUpdate(PortalPlayer player, PortalSign sign, String[] lines) {
		
		return false;
		
	}
	
	public void onSignRightClick(PortalPlayer player, PortalSign sign, PortalItem hand, int slot) {
		
		if (!sign.isPortaly()) return;
		Portal portal = sign.getPortal();
		
		if (hand != null) {
			
			PortalPearlType type = hand.getPearlType();
			if (type == null) return;
			
			switch (type) {
				
				case LINK: {
					
					PortalHelper.interactWithPortal(player, portal, hand, slot);
					break;
					
				}
				
				case ACTIVATOR: {
					
					Portal created = PortalHelper.tryCreatePortal(player, sign);
					
					if (created != null) {
						
						// Remove the item.
						if (hand.getSize() == 1) {
							player.getInventory().setItem(ZaniPortals.server.getItem(0), slot); // Yucky hax to make this work.
						} else {
							hand.setSize(hand.getSize() - 1);
						}
						
						player.sendMessage(ChatColor.GREEN + "Created portal: " + created.name);
						
					} else {
						player.sendMessage(ChatColor.RED + "Failed to create portal");
					}
					
					break;
					
				}
				
				default:
					break;
				
			}
			
		} else {
			PortalHelper.interactWithPortal(player, portal, hand, slot);
		} 
		
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
