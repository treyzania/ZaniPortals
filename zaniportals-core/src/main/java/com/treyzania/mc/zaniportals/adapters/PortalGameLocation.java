package com.treyzania.mc.zaniportals.adapters;

public interface PortalGameLocation {

	public PortalWorld getWorld();
	public double getX();
	public double getY();
	public double getZ();
	
	public default int getBlockX() {
		return (int) this.getX();
	}
	
	public default int getBlockY() {
		return (int) this.getY();
	}
	
	public default int getBlockZ() {
		return (int) this.getZ();
	}
	
	public void setBlock(PortalGameBlock block);
	public PortalGameBlock getBlock();
	
}
