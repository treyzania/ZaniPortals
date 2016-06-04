package com.treyzania.mc.zaniportals;

import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;

public class BlockPos {

	public PortalWorld world;
	public Point3i point;
	
	public BlockPos(PortalWorld w, Point3i p) {
		
		this.world = w;
		this.point = p;
		
	}
	
	public BlockPos(PortalWorld w, int x, int y, int z) {
		this(w, new Point3i(x, y, z));
	}
	
	public int getX() {
		return this.point.x;
	}
	
	public int getY() {
		return this.point.y;
	}
	
	public int getZ() {
		return this.point.z;
	}
	
	public PortalLocation getAsLocation() {
		return this.world.createLocation(this.getX(), this.getY(), this.getZ());
	}
	
}
