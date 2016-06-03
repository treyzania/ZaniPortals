package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.block.Block;

public class BukkitBlock implements PortalGameBlock {

	private final Block block;
	
	public BukkitBlock(Block block) {
		this.block = block;
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
		return null;
	}

	@Override
	public Object getWrappedObject() {
		return this.block;
	}

}
