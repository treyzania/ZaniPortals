package com.treyzania.mc.zaniportals.adapters;

public interface PortalBlock extends Wrapper {

	public PortalLocation getLocation();
	public default PortalWorld getWorld() {
		return this.getLocation().getWorld();
	}
	
	public void setId(int id);
	public int getId();
	
	public void setDamage(byte damage);
	public byte getDamage();
	
	public PortalSign getSignData();
	public default boolean isSign() {
		return this.getId() == PortalSign.WALL_SIGN_ID;
	}
	
}