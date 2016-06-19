package com.treyzania.mc.zaniportals.adapters;

public interface PortalCommandSender extends PortalPermissible, PossiblyPlayer, Wrapper {

	public void sendMessage(String message);
	
}
