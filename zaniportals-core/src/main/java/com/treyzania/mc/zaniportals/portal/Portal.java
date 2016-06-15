package com.treyzania.mc.zaniportals.portal;

import java.util.UUID;

import com.treyzania.mc.zaniportals.Point3i;
import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public class Portal {

	public final PortalWorld world;
	public final String name;
	public final UUID owner;
	private PortalTarget target;
	
	private Point3i signBlock;
	private Point3i[] frameBlocks;
	private Point3i[] portalBlocks; 
	
	public Portal(PortalWorld world, UUID owner, String name, Point3i[] frame, Point3i[] portal) {
		
		this.world = world;
		this.owner = owner;
		this.name = name;
		
		this.frameBlocks = frame;
		this.portalBlocks = portal;
		
	}
	
	public Portal(PortalWorld world, UUID owner, String name) {
		this(world, owner, name, new Point3i[0], new Point3i[0]);
	}
	
	public boolean isInPortal(PortalLocation loc) {
		
		if (!loc.getWorld().getName().equals(this.world.getName())) return false;
		
		for (Point3i p : this.portalBlocks) {
			if (p.x == loc.getBlockX() && p.y == loc.getY() && p.z == loc.getZ()) return true;
		}
		
		return false;
		
	}
	
	public void setSignBlock(Point3i p3i) {
		this.signBlock = p3i;
	}
	
	public PortalLocation getSignBlock() {
		return this.world.createLocation(this.signBlock);
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
	
	public PortalTarget getTarget() {
		return this.target;
	}
	
	public void enter(PortalEntity ent) {
		this.target.teleport(ent);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
}
