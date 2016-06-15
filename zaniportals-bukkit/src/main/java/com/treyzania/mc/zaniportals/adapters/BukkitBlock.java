package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.treyzania.mc.zaniportals.world.Face;

public class BukkitBlock implements PortalBlock {
	
	protected final Block block;
	
	public BukkitBlock(Block block) {
		this.block = block;
	}
	
	@Override
	public PortalLocation getLocation() {
		return new BukkitLocation(this.block.getLocation());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setId(int id) {
		this.block.setTypeId(id);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getId() {
		return this.block.getTypeId();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setDamage(byte damage) {
		this.block.setData(damage);
	}
	
	@Override
	public byte getDamage() {
		return this.getDamage();
	}
	
	@Override
	public PortalSign getSignData() {
		return new BukkitSign(this.block);
	}
	
	@Override
	public PortalBlock getBlockOnFace(Face face) {
		
		Block b = null;
		
		switch (face) {
			
			case NORTH: {
				b = this.block.getRelative(BlockFace.NORTH);
				break;
			}
			
			case SOUTH: {
				b = this.block.getRelative(BlockFace.SOUTH);
				break;
			}
			
			case EAST: {
				b = this.block.getRelative(BlockFace.EAST);
				break;
			}
			
			case WEST: {
				b = this.block.getRelative(BlockFace.WEST);
				break;
			}
			
			case UP: {
				b = this.block.getRelative(BlockFace.UP);
				break;
			}
			
			case DOWN: {
				b = this.block.getRelative(BlockFace.DOWN);
				break;
			}
			
		}
		
		return b != null ? new BukkitBlock(b) : null;
		
	}
	
	@Override
	public Object getWrappedObject() {
		return this.block;
	}
	
}
