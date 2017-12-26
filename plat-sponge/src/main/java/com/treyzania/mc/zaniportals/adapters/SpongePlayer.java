package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class SpongePlayer implements PortalPlayer {

	private Player player;
	
	@Override
	public boolean isOp() {
		return this.player.hasPermission("zaniportals.fakeop");
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.player.hasPermission(permission);
	}

	@Override
	public UUID getUniqueId() {
		return this.player.getUniqueId();
	}

	@Override
	public String getName() {
		return this.player.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setLocation(PortalLocation loc) {
		this.player.setLocation((Location<World>) loc.getWrappedObject()); // Not great, but it works.
	}

	@Override
	public PortalLocation getLocation() {
		return new SpongeLocation(this.player.getLocation());
	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public void setPitch(float angle) {
		Vector3d rot = this.player.getRotation();
		this.player.setLocationAndRotation(this.player.getLocation(), new Vector3d(angle, rot.getY(), rot.getZ()));
	}

	@Override
	public void setYaw(float angle) {
		Vector3d rot = this.player.getRotation();
		this.player.setLocationAndRotation(this.player.getLocation(), new Vector3d(angle, rot.getY(), rot.getZ()));
	}

	@Override
	public PortalPlayer getAsPlayer() {
		return this;
	}

	@Override
	public Object getWrappedObject() {
		return this.player;
	}

	@Override
	public void sendMessage(String message) {
		this.player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
	}

	@Override
	public String getDisplayName() {
		return TextSerializers.FORMATTING_CODE.serialize(this.player.getDisplayNameData().displayName().get());
	}

	@Override
	public void addItem(PortalItem item) {
		this.getInventory().addItem(item);
	}

	@Override
	public boolean isSneaking() {
		// TODO Auto-generated method stub
		return false; // OH GOD THIS IS GOING TO BE SO MUCH WORK TO IMPLEMENT.
	}

	@Override
	public PortalPlayerInventory getInventory() {
		return new SpongePlayerInventory((PlayerInventory) this.player.getInventory());
	}

}
