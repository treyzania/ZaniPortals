package com.treyzania.mc.zaniportals.cmd;

public abstract class StandardPortalCommand extends AbstractPortalCommand {

	private final String name;
	
	public StandardPortalCommand(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
}
