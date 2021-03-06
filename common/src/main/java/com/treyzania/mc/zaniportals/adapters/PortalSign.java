package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.portal.Portal;
import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;
import com.treyzania.mc.zaniportals.world.Axis;
import com.treyzania.mc.zaniportals.world.Face;

import net.md_5.bungee.api.ChatColor;

public interface PortalSign extends PortalBlock, Wrapper {

	public static int WALL_SIGN_ID = 68;
	public static int PORTAL_DATA_LINE = 1;
	
	public void setLine(int line, String text);
	public String getLine(int line);
	
	/**
	 * @return The axis that this portal is placed on.
	 */
	public Axis getLockedAxis();
	
	public default boolean isPortaly() {
		
		if (!PortalHelper.isValidNewSignSyntax(this)) return false;
		
		return true;
		
	}
	
	public default void copyData(PortalTarget target) {
		
		String data = PortalHelper.serializePortalTarget(target);
		
		this.setLine(PORTAL_DATA_LINE, data);
		
	}
	
	public default Portal getPortal() {
		return ZaniPortals.portals.getPortal(ChatColor.stripColor(this.getLine(PORTAL_DATA_LINE)));
	}
	
	public default PortalTarget getTarget() {
		return this.getPortal().getTarget();
	}
	
	public Face getFace();
	public PortalBlock getBlockAttachedOnto();
	
}
