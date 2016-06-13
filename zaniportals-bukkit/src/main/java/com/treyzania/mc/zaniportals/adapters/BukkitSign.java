package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class BukkitSign extends BukkitBlock implements PortalSign {

	public BukkitSign(Block block) {
		super(block);
	}
	
	@Override
	public void setLine(int line, String text) {
		
		Sign s = (Sign) this.block.getState();
		
		s.setLine(line, text);
		s.update();
		
	}

	@Override
	public String getLine(int line) {
		return ((Sign) this.block.getState()).getLine(line);
	}
	
}
