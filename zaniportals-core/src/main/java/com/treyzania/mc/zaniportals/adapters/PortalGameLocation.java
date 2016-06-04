package com.treyzania.mc.zaniportals.adapters;

public interface PortalGameLocation extends Wrapper {

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
	
	public PortalGameBlock getBlock();
	
	public default PortalGameLocation getLocationAtOffset(double x, double y, double z) {
		return this.getWorld().createLocation(this.getX() + x, this.getY() + y, this.getZ() + z);
	}
	
}
