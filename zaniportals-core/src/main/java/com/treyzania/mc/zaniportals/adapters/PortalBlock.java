package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.Point3i;
import com.treyzania.mc.zaniportals.world.Face;

public interface PortalBlock extends Wrapper {

	public PortalLocation getLocation();
	public default PortalWorld getWorld() {
		return this.getLocation().getWorld();
	}
	
	public default Point3i getPoint3i() {
		return this.getLocation().getAsPoint3i();
	}
	
	public void setId(int id);
	public int getId();
	
	public void setDamage(byte damage);
	public byte getDamage();
	
	public PortalSign getSignData();
	public default boolean isSign() {
		return this.getId() == PortalSign.WALL_SIGN_ID;
	}
	
	public PortalBlock getBlockOnFace(Face face);
	
}
