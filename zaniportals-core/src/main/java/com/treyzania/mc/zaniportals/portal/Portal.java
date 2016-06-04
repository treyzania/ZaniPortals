package com.treyzania.mc.zaniportals.portal;

import com.treyzania.mc.zaniportals.Point3i;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;

public class Portal {

	public final PortalWorld world;
	
	private Point3i[] frameBlocks;
	private Point3i[] portalBlocks; 
	
	public Portal(PortalWorld world, PortalTarget target, Point3i[] frame, Point3i[] portal) {
		
		this.world = world;
		
		this.frameBlocks = frame;
		this.portalBlocks = portal;
		
	}
	
	public Portal(PortalWorld world, PortalTarget target) {
		this(world, target, new Point3i[0], new Point3i[0]);
	}
	
	public boolean isInPortal(PortalLocation loc) {
		
		if (!loc.getWorld().getName().equals(this.world.getName())) return false;
		
		for (Point3i p : this.portalBlocks) {
			if (p.x == loc.getBlockX() && p.y == loc.getY() && p.z == loc.getZ()) return true;
		}
		
		return false;
		
	}
	
	public Point3i[] getFrameLocations() {
		return this.frameBlocks;
	}
	
	public Point3i[] getPortalLocations() {
		return this.frameBlocks;
	}
	
	
	
}
