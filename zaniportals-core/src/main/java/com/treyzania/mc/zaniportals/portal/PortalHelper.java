package com.treyzania.mc.zaniportals.portal;

import java.util.HashMap;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.adapters.PortalSign;
import com.treyzania.mc.zaniportals.portal.targets.AbsolutePortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.NamedPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.NotifyInvalidPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public class PortalHelper {
	
	private static HashMap<Class<? extends PortalTarget>, String> portalTargetClasses = new HashMap<>();
	
	public static boolean isValidSignBlock(PortalBlock b) {
		return false; // TODO Create this algorithm.
	}
	
	public static boolean isValidNewSignSyntax(PortalSign sign) {
		return false; // TODO Create this algorithm.
	}
	
	public static String serializePortalTarget(PortalTarget target) {
		return String.format("%s:%s", portalTargetClasses.get(target.getClass()), target.getName());
	}
	
	public static PortalTarget deserializePortalTarget(String data) {
		
		String[] parts = data.split(":");
		
		Class<? extends PortalTarget> clazz = null;
		for (Class<? extends PortalTarget> c : portalTargetClasses.keySet()) if (portalTargetClasses.get(c).equals(parts[0])) clazz = c;
		
		if (clazz == AbsolutePortalTarget.class) {
			
			String[] locParts = parts[1].substring(1, parts[1].length() - 1).split(","); // (x,y,z) -> x,y,z -> `[x, y, z]`
			String worldName = locParts[0];
			int x = Integer.parseInt(locParts[1]);
			int y = Integer.parseInt(locParts[2]);
			int z = Integer.parseInt(locParts[3]);
			
			return new AbsolutePortalTarget(ZaniPortals.server.getWorld(worldName).createLocation(x, y, z));
			
		} else if (clazz == NamedPortalTarget.class) {
			return new NamedPortalTarget(parts[1]);
		} else if (clazz == NotifyInvalidPortalTarget.class) {
			return new NotifyInvalidPortalTarget();
		}
		
		return null;
		
	}
	
	static {
		
		portalTargetClasses.put(AbsolutePortalTarget.class, "absolute");
		portalTargetClasses.put(NamedPortalTarget.class, "portal");
		portalTargetClasses.put(NotifyInvalidPortalTarget.class, "invalid");
		
	}

	public static String makeTargetSerializationPretty(String data) {
		
		char initial = data.charAt(0);
		String rest = data.substring(1);
		
		return Character.toString(initial).toUpperCase() + rest.replaceFirst(":", ": ");
		
	}

	public static String makeTargetSerializationConforming(String pretty) {
		
		char initial = pretty.charAt(0);
		String rest = pretty.substring(1);
		
		return Character.toString(initial).toLowerCase() + rest.replaceFirst(": ", ":");
		
	}
	
}
