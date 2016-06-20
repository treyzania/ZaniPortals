package com.treyzania.mc.zaniportals.portal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import com.treyzania.mc.zaniportals.Perms;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalItem;
import com.treyzania.mc.zaniportals.adapters.PortalLinkItem;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;
import com.treyzania.mc.zaniportals.adapters.PortalSign;
import com.treyzania.mc.zaniportals.portal.targets.AbsolutePortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.NamedPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.NotifyInvalidPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PlayerPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

import net.md_5.bungee.api.ChatColor;

public class PortalHelper {
	
	private static HashMap<Class<? extends PortalTarget>, String> portalTargetClasses = new HashMap<>();
	
	public static boolean isValidNewSignSyntax(PortalSign sign) {
		
		if (!ChatColor.stripColor(sign.getLine(0)).equals("[Portal]")) return false;
		return true;
		
	}
	
	public static boolean isValidPortalName(String name) {
		
		for (String s : Arrays.asList(" ", ",", "+", "=", ";", "\"", "'", "\\", "/", "`", "~", "?")) {
			if (name.contains(s)) return false;
		}
		
		return true;
		
	}
	
	public static String serializePortalTarget(PortalTarget target) {
		return String.format("%s:%s", portalTargetClasses.get(target.getClass()), target.getExpression());
	}
	
	public static PortalTarget deserializePortalTarget(String data) {
		
		String[] parts = data.split(":");
		
		Class<? extends PortalTarget> clazz = null;
		for (Class<? extends PortalTarget> c : portalTargetClasses.keySet()) if (portalTargetClasses.get(c).equals(parts[0])) clazz = c;
		
		if (clazz == AbsolutePortalTarget.class) {
			
			String[] locParts = parts[1].split(","); // (x,y,z) -> x,y,z -> `[x, y, z]`
			
			String worldName = locParts[0];
			int x = Integer.parseInt(locParts[1]);
			int y = Integer.parseInt(locParts[2]);
			int z = Integer.parseInt(locParts[3]);
			
			return new AbsolutePortalTarget(ZaniPortals.server.getWorld(worldName).createLocation(x, y, z));
			
		} else if (clazz == NamedPortalTarget.class) {
			return new NamedPortalTarget(parts[1]);
		} else if (clazz == NotifyInvalidPortalTarget.class) {
			return new NotifyInvalidPortalTarget();
		} else if (clazz == PlayerPortalTarget.class) {
			return new PlayerPortalTarget(UUID.fromString(parts[1]));
		}
		
		return null;
		
	}
	
	static {
		
		portalTargetClasses.put(AbsolutePortalTarget.class, "absolute");
		portalTargetClasses.put(NamedPortalTarget.class, "portal");
		portalTargetClasses.put(NotifyInvalidPortalTarget.class, "invalid");
		portalTargetClasses.put(PlayerPortalTarget.class, "player");
		
	}

	public static String makeTargetSerializationPretty(String data) {
		
		char initial = data.charAt(0);
		String rest = data.substring(1);
		
		return Character.toString(initial).toUpperCase() + rest.replaceFirst(":", ": ");
		
	}

	public static String makeTargetSerializationConforming(String pretty) {
		
		if (pretty == null || pretty.length() == 0) throw new IllegalArgumentException("Invalid pretty string!");
		
		char initial = pretty.charAt(0);
		String rest = pretty.substring(1);
		
		return Character.toString(initial).toLowerCase() + rest.replaceFirst(": ", ":");
		
	}
	
	public static boolean isPortalCompatible(PortalPlayer player, Portal portal, String permSelf, String permOther) {
		return player.hasPermission(permOther) || (player.isOwner(portal) && player.hasPermission(permSelf)) || player.isOp();
	}
	
	public static Portal tryCreatePortal(PortalPlayer player, PortalSign sign) {
		
		// Validation.
		if (!PortalHelper.isValidNewSignSyntax(sign)) return null; // Do nothing.
		if (!(player.hasPermission(Perms.CREATE_PORTAL) || player.isOp())) {
			
			player.sendMessage("You don't have permission to do that!");
			return null;
			
		}
		
		String name = ChatColor.stripColor(sign.getLine(1));
		
		if (ZaniPortals.portals.hasPortalWithName(name)) {
			
			player.sendMessage("A portal with that name already exists!");
			return null; // Cancel sign update.
			
		}
		
		if (!PortalHelper.isValidPortalName(name)) {
			
			player.sendMessage("Invalid portal name!");
			return null;
			
		}
		
		// Create the actual portal.
		Portal portal = new Portal(player.getWorld(), player.getUniqueId(), name);
		portal.setSignBlock(sign.getLocation().getAsPoint3i());
		
		if (ZaniPortals.portals.tryAddPortal(portal)) {
			
			ZaniPortals.getFrameCalculator().populate(portal);
			portal.fill(ZaniPortals.config.getPortalBlockId());
			
			if (portal.getPortalLocations().length > 0) {
				ZaniPortals.savePortals();
			} else {
				ZaniPortals.portals.removePortal(portal);
			}
			
			// Everything can be handled by the Portal object itself.
			portal.updateSign();
			
		} else {
			return null;
		}
		
		return portal;
		
	}
	
	public static void interactWithPortal(PortalPlayer player, Portal portal, PortalItem hand, int slot) {
		
		if (portal == null) return;
		
		// Sneaking lets us do configuration.
		if (player.isSneaking()) {
			
			if (portal.lastUpdate + PORTAL_UPDATE_DELAY >= System.currentTimeMillis()) return;
			portal.lastUpdate = System.currentTimeMillis();
			
			// Very yucky mess to check if this is the same portal thing.
			boolean isPortalLinkSameAsThisPortal = false;
			if (hand != null && hand.isPortaly()) {
				
				PortalLinkItem pli = hand.convertToLinkItem();
				PortalTarget pt = pli.getTarget();
				
				isPortalLinkSameAsThisPortal = pt instanceof NamedPortalTarget && ((NamedPortalTarget) pt).getName().equals(portal.name);
				
			}
			
			// Simplified logic here.
			if (hand == null || isPortalLinkSameAsThisPortal) {
				PortalHelper.givePortalLinkItem(player, portal);
			} else {
				PortalHelper.setPortalTarget(portal, player, hand, slot);
			}
			
		} else {
			
			if (!PortalHelper.isPortalCompatible(player, portal, Perms.USE_OWN_PORTAL, Perms.USE_OTHER_PORTAL)) return;
			player.sendMessage("Poof!");
			portal.enter(player);
			
		}
		
	}

	public static void givePortalLinkItem(PortalPlayer player, Portal portal) {
		
		if (!isPortalCompatible(player, portal, Perms.GET_OWN_PORTAL_REFERENCE, Perms.GET_OTHER_PORTAL_REFERENCE)) return;
		
		PortalTarget target = new NamedPortalTarget(portal);
		
		// In this case we want to get the target data.
		PortalItem pearl = ZaniPortals.server.getItem(Items.ENDER_PEARL_ID);
		PortalLinkItem pli = pearl.convertToLinkItem();
		pli.setTarget(target);
		
		// TODO Publicness checking.
		player.addItem(pli);
		player.sendMessage("Here's the link item! (" + target.getExpression() + ")");
		
	}

	public static void setPortalTarget(Portal portal, PortalPlayer player, PortalItem hand, int slot) {
		
		if (!isPortalCompatible(player, portal, Perms.SET_OWN_PORTAL_TARGET, Perms.SET_OTHER_PORTAL_TARGET)) return;
		
		PortalTarget target = hand.convertToLinkItem().getTarget();
		
		if (!target.getName().equals(portal.name)) {
			
			player.sendMessage("Target set!");
			portal.setTarget(target);
			
		} else {
			
			// This shouldn't normally happen.
			player.sendMessage("You can't set a portal to target itself!");
			
		}
		
		// Remove the item.
		if (hand.getSize() == 1) {
			player.getInventory().setItem(ZaniPortals.server.getItem(0), slot); // Yucky hax to make this work.
		} else {
			hand.setSize(hand.getSize() - 1);
		}
		
	}

	private static final long PORTAL_UPDATE_DELAY = 250L;
	
}
