package com.treyzania.mc.zaniportals.portal;

import com.treyzania.mc.zaniportals.Point3i;
import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public class Portal {

	public final PortalWorld world;
	public final String name;
	private PortalTarget target;
	
	private Point3i[] frameBlocks;
	private Point3i[] portalBlocks; 
	
	public Portal(PortalWorld world, String name, Point3i[] frame, Point3i[] portal) {
		
		this.world = world;
		this.name = name;
		
		this.frameBlocks = frame;
		this.portalBlocks = portal;
		
	}
	
	public Portal(PortalWorld world, String name) {
		this(world, name, new Point3i[0], new Point3i[0]);
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
	
	public void setTarget(PortalTarget target) {
		this.target = target;
	}
	
	public void enter(PortalEntity ent) {
		this.target.teleport(ent);
	}
	
}
