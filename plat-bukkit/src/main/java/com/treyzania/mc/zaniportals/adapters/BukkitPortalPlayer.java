package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BukkitPortalPlayer extends BukkitEntity implements PortalPlayer, PortalCommandSender {
	
	private final Player player;
	
	public BukkitPortalPlayer(Player player) {
		
		super(player);
		
		this.player = player;
		
	}
	
	@Override
	public String getDisplayName() {
		return this.player.getDisplayName();
	}
	
	@Override
	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}
	
	@Override
	public boolean isOp() {
		return this.player.isOp();
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.player.hasPermission(permission);
	}

	@Override
	public void addItem(PortalItem item) {
		this.player.getInventory().addItem((ItemStack) item.getWrappedObject());
	}

	@Override
	public boolean isSneaking() {
		return this.player.isSneaking();
	}

	@Override
	public PortalPlayerInventory getInventory() {
		return new BukkitPlayerInventory(this.player.getInventory());
	}
	
}
