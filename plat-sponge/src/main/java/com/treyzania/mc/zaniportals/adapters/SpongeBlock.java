package com.treyzania.mc.zaniportals.adapters;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.treyzania.mc.zaniportals.IDMap;
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.world.Face;

public class SpongeBlock implements PortalBlock {

	protected Location<World> loc;
	
	private int id;
	private byte damage = 0;
	
	public SpongeBlock(Location<World> loc) {
		this.loc = loc;
		this.id = IDMap.getIdByBlockType(this.loc.getBlockType());
	}
	
	@Override
	public Object getWrappedObject() {
		return this.loc;
	}

	@Override
	public PortalLocation getLocation() {
		return new SpongeLocation(this.loc);
	}

	@Override
	public void setId(int id) {
		this.setId_noUpdate(id);
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId_noUpdate(int id) {
		this.id = id;
		this.loc.setBlockType(IDMap.getBlockTypeById(id), BlockChangeFlag.NONE, Cause.source(ZaniPortals.plugin).build());
	}

	@Override
	public void setDamage(byte damage) {
		this.damage = damage;
	}

	@Override
	public byte getDamage() {
		return this.damage;
	}

	@Override
	public PortalSign getSignData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PortalBlock getBlockOnFace(Face face) {
		// TODO Auto-generated method stub
		return null;
	}

}
