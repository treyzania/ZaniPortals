package com.treyzania.mc.zaniportals.adapters;

public interface PortalConfig {
	
	public int[] getFrameBlockIds();
	public int getPortalBlockId();
	
	public int getMaxPortalSize();
	
	public boolean isBlockImportant(int id);
	
}
