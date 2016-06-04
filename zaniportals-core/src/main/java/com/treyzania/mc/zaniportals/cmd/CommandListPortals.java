package com.treyzania.mc.zaniportals.cmd;

import com.treyzania.mc.zaniportals.adapters.PortalCommandSender;

public class CommandListPortals extends StandardPortalCommand {
	
	public CommandListPortals(String name) {
		super(name);
	}
	
	@Override
	public boolean execute(PortalCommandSender sender, String[] args) {
		
		// TODO
		sender.sendMessage("NYI");
		
		return true;
		
	}

}
