package com.treyzania.mc.zaniportals.adapters;

public interface PortalCommandSender {

	public void sendMessage(String message);
	
	public boolean isOp();
	public boolean hasPermission(String permission);
	
}
