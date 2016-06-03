package com.treyzania.mc.zaniportals.adapters;

public interface PortalSign extends Wrapper {

	public static int WALL_SIGN_ID = 68;
	
	public void setLine(int line, String text);
	public String getLine(int line);
	
}
