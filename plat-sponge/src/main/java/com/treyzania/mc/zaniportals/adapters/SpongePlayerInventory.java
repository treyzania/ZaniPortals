package com.treyzania.mc.zaniportals.adapters;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.item.inventory.property.SlotIndex;

public class SpongePlayerInventory implements PortalPlayerInventory {

	private PlayerInventory inventory;
	
	public SpongePlayerInventory(PlayerInventory inv) {
		this.inventory = inv;
	}
	
	@Override
	public void addItem(PortalItem item) {
		this.inventory.offer((ItemStack) item.getWrappedObject());
	}

	@Override
	public void setItem(PortalItem item, int slot) {

		Slot sl = null;
		
		if (slot >= 0 && slot <= 9) {
			sl = this.inventory.getHotbar().getSlot(new SlotIndex(slot)).get();
		} else if (slot >= 10) {
			sl = this.inventory.getMain().getSlot(new SlotIndex(slot - 10)).get();
		} else {
			// Idk.
		}
		
		sl.set((ItemStack) item.getWrappedObject());
		
	}

	@Override
	public PortalItem getItem(int slot) {
		
		Slot sl = null;
		
		if (slot >= 0 && slot <= 9) {
			sl = this.inventory.getHotbar().getSlot(new SlotIndex(slot)).get();
		} else if (slot >= 10) {
			sl = this.inventory.getMain().getSlot(new SlotIndex(slot - 10)).get();
		} else {
			return null;
		}
		
		return new SpongeItemStack(sl.peek().get());		
		
	}

	@Override
	public int getInventorySize() {
		return this.inventory.size();
	}

}
