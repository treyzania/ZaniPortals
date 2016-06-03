package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.block.Sign;

public class BukkitSign implements PortalSign {

	private final Sign sign;
	
	public BukkitSign(Sign sign) {
		this.sign = sign;
	}
	
	@Override
	public void setLine(int line, String text) {
		this.sign.setLine(line, text);
	}

	@Override
	public String getLine(int line) {
		return this.sign.getLine(line);
	}
	
	@Override
	public Object getWrappedObject() {
		return this.sign;
	}
	
}
