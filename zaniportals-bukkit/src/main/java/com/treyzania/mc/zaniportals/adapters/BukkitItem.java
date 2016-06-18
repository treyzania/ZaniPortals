package com.treyzania.mc.zaniportals.adapters;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BukkitItem implements PortalItem {
	
	private ItemStack item;
	
	public BukkitItem(ItemStack item) {
		this.item = item;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setId(int id) {
		this.item.setTypeId(id);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getId() {
		return this.item.getTypeId();
	}
	
	@Override
	public void setDamage(short damage) {
		this.item.setDurability(damage);
	}
	
	@Override
	public short getDamage() {
		return this.item.getDurability();
	}
	
	@Override
	public void setSize(int size) {
		this.item.setAmount(size);
	}
	
	@Override
	public int getSize() {
		return this.item.getAmount();
	}
	
	@Override
	public void setName(String name) {
		
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(name);
		this.item.setItemMeta(im);
		
	}
	
	@Override
	public String getName() {
		
		ItemMeta im = this.item.getItemMeta();
		return (im.getDisplayName() != null && !im.getDisplayName().isEmpty()) ? im.getDisplayName() : this.item.getType().name().replace("_", " ").toLowerCase();
		
	}
	
	@Override
	public List<String> getLore() {
		return this.item.getItemMeta().getLore();
	}
	
	@Override
	public void setLore(List<String> lore) {
		
		ItemMeta im = this.item.getItemMeta();
		im.setLore(lore);
		this.item.setItemMeta(im);
		
	}
	
	@Override
	public void setGlowing(boolean mode) {
		
		ItemMeta im = this.item.getItemMeta();
		
		if (mode) {
			
			// Enable
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			this.item.setItemMeta(im);
			if (!im.hasEnchant(Enchantment.MENDING)) this.item.addUnsafeEnchantment(Enchantment.MENDING, 1); // Just something that won't actually do anything.
			
		} else {
			
			// Disable
			im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
			for (Enchantment e : im.getEnchants().keySet()) im.removeEnchant(e);
			this.item.setItemMeta(im);
			
		}
		
	}
	
	@Override
	public boolean isGlowing() {
		
		ItemMeta im = this.item.getItemMeta();
		return im.hasItemFlag(ItemFlag.HIDE_ENCHANTS) && im.hasEnchant(Enchantment.MENDING);
		
	}
	
	@Override
	public PortalLinkItem convertToLinkItem() {
		
		this.makePortalyItem();
		return new BukkitLinkItem(this.item);
		
	}

	@Override
	public Object getWrappedObject() {
		return this.item;
	}
	
}
