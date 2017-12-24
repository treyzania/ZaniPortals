package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;

import com.treyzania.mc.zaniportals.world.Axis;
import com.treyzania.mc.zaniportals.world.Face;

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

	@Override
	public Axis getLockedAxis() {
		return this.getFace().getAxis();
	}
	
	@Override
	public Face getFace() {
		
		MaterialData data = this.block.getState().getData();
		
		if (data instanceof Attachable) {
			
			Attachable a = (Attachable) data;
			
			switch (a.getAttachedFace()) {					
				
				case NORTH: {
					return Face.NORTH;
				}
				
				case SOUTH: {
					return Face.SOUTH;
				}
				
				case EAST: {
					return Face.EAST;
				}
				
				case WEST: {
					return Face.WEST;
				}
				
				default:
					break;
				
			}
			
		}
		
		return null; 
		
	}
	
	@Override
	public PortalBlock getBlockAttachedOnto() {
		
		MaterialData data = this.block.getState().getData();
		
		if (data instanceof Attachable) {
			
			Attachable a = (Attachable) data;
			return new BukkitBlock(this.block.getRelative(a.getAttachedFace()));
			
		}
		
		return null;
		
	}
	
}
