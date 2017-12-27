package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.text.serializer.TextSerializers;
import com.flowpowered.math.vector.Vector3d;

public class SpongePlayer extends SpongeEntity implements PortalPlayer {

	private Player player;
	
	public SpongePlayer(Player p) {
		
		super(p);
		
		// Ah, I'm lazy so we store another reference.
		this.player = p;
		
	}
	
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
