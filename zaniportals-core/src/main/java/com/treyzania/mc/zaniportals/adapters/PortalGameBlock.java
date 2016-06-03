package com.treyzania.mc.zaniportals.adapters;

public interface PortalGameBlock {

	public void setId(int id);
	public int getId();
	
	public void setDamage(byte damage);
	public byte getDamage();
	
	public PortalSign getSignData();
	public default boolean isSign() {
		return this.getId() == PortalSign.WALL_SIGN_ID;
	}
	
}
