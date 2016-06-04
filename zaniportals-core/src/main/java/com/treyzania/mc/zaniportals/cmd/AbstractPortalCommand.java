package com.treyzania.mc.zaniportals.cmd;

import com.treyzania.mc.zaniportals.adapters.PortalCommandSender;

public abstract class AbstractPortalCommand {

	public abstract String getName();
	public abstract boolean execute(PortalCommandSender sender, String[] args);
	
}
