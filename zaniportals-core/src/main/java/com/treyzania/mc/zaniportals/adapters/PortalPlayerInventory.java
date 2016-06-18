package com.treyzania.mc.zaniportals.adapters;

public interface PortalPlayerInventory {
	
	public void addItem(PortalItem item);
	
	public void setItem(PortalItem item, int slot);
	public PortalItem getItem(int slot);
	
	public int getInventorySize();
	
}
