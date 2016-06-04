package com.treyzania.mc.zaniportals.adapters;

public interface PortalLocation extends Wrapper {

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
	
	public PortalBlock getBlock();
	
	public default PortalLocation getLocationAtOffset(double x, double y, double z) {
		return this.getWorld().createLocation(this.getX() + x, this.getY() + y, this.getZ() + z);
	}
	
}
