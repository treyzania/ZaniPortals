package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BukkitPlayerInventory implements PortalPlayerInventory {
	
	private PlayerInventory inventory;
	
	public BukkitPlayerInventory(PlayerInventory inv) {
		this.inventory = inv;
	}
	
	@Override
	public void addItem(PortalItem item) {
		this.inventory.addItem((ItemStack) item.getWrappedObject()); // Rather unsafe cast.
	}
	
	@Override
	public void setItem(PortalItem item, int slot) {
		this.inventory.setItem(slot, (ItemStack) item.getWrappedObject()); // Rather unsafe cast.
	}

	@Override
	public PortalItem getItem(int slot) {
		return new BukkitItem(this.inventory.getItem(slot));
	}

	@Override
	public int getInventorySize() {
		return this.inventory.getSize();
	}
	
}
