package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class SpongeEntity implements PortalEntity {

	private Entity entity;
	
	public SpongeEntity(Entity ent) {
		this.entity = ent;
	}
	
	@Override
	public UUID getUniqueId() {
		return this.entity.getUniqueId();
	}

	@Override
	public String getName() {
		return this.entity.get(Keys.DISPLAY_NAME).map(t -> TextSerializers.FORMATTING_CODE.serialize(t)).orElse(this.entity.getType().getName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setLocation(PortalLocation loc) {
		this.entity.setLocation((Location<World>) loc.getWrappedObject());
	}

	@Override
	public PortalLocation getLocation() {
		return new SpongeLocation(this.entity.getLocation());
	}

	@Override
	public boolean isPlayer() {
		return this.entity instanceof Player;
	}

	@Override
	public void setPitch(float angle) {
		Vector3d rot = this.entity.getRotation();
		this.entity.setLocationAndRotation(this.entity.getLocation(), new Vector3d(angle, rot.getY(), rot.getZ()));
	}

	@Override
	public void setYaw(float angle) {
		Vector3d rot = this.entity.getRotation();
		this.entity.setLocationAndRotation(this.entity.getLocation(), new Vector3d(angle, rot.getY(), rot.getZ()));
	}

	@Override
	public PortalPlayer getAsPlayer() {
		return new SpongePlayer((Player) this.entity);
	}

	@Override
	public Object getWrappedObject() {
		return this.entity;
	}

}
