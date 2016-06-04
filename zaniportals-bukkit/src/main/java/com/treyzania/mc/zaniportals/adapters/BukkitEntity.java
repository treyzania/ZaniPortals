package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class BukkitEntity implements PortalEntity {

	private Entity entity;
	
	public BukkitEntity(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public UUID getUniqueId() {
		return this.entity.getUniqueId();
	}
	
	@Override
	public String getName() {
		return this.entity.getName();
	}
	
	@Override
	public void setLocation(PortalGameLocation loc) {
		this.entity.teleport((Location) loc.getWrappedObject());
	}

	@Override
	public PortalGameLocation getLocation() {
		return new BukkitLocation(this.entity.getLocation());
	}

	@Override
	public Object getWrappedObject() {
		return this.entity;
	}

	@Override
	public boolean isPlayer() {
		return this.entity instanceof Player;
	}

	@Override
	public PortalPlayer getAsPlayer() {
		return new BukkitPortalPlayer((Player) this.entity);
	}

}
