package com.treyzania.mc.zaniportals.adapters;

import java.util.List;
import java.util.stream.Collectors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.treyzania.mc.zaniportals.IDMap;

public class SpongeItemStack implements PortalItem {

	protected ItemStack stack;
	
	public SpongeItemStack(ItemStack is) {
		this.stack = is;
	}
	
	@Override
	public Object getWrappedObject() {
		return this.stack;
	}

	@Override
	public void setId(int id) {
		this.stack.copyFrom(ItemStack.builder().from(this.stack).itemType(IDMap.getItemTypeById(id)).build());
	}

	@Override
	public int getId() {
		return IDMap.getIdByItemType(this.stack.getItem());
	}

	@Override
	public void setDamage(short damage) {
		this.stack.transform(Keys.ITEM_DURABILITY, _v -> Integer.valueOf(damage));
	}

	@Override
	public short getDamage() {
		return (short) this.stack.get(Keys.ITEM_DURABILITY).get().intValue();
	}

	@Override
	public void setSize(int size) {
		this.stack.copyFrom(ItemStack.builder().from(this.stack).quantity(size).build());
	}

	@Override
	public int getSize() {
		return this.stack.getQuantity();
	}

	@Override
	public void setName(String name) {
		this.stack.transform(Keys.DISPLAY_NAME, _v -> TextSerializers.FORMATTING_CODE.deserialize(name));
	}

	@Override
	public String getName() {
		return this.stack.getItem().getName();
	}

	@Override
	public List<String> getLore() {
		return this.stack.get(Keys.ITEM_LORE).get().stream()
			.map(l -> TextSerializers.FORMATTING_CODE.serialize(l))
			.collect(Collectors.toList());
	}

	@Override
	public void setLore(List<String> lore) {
		
		List<Text> textLore = lore.stream()
			.map(l -> TextSerializers.FORMATTING_CODE
			.deserialize(l))
			.collect(Collectors.toList());
		
		this.stack.transform(Keys.ITEM_LORE, _v -> textLore);
		
	}

	@Override
	public void setGlowing(boolean mode) {
		this.stack.transform(Keys.GLOWING, _v -> Boolean.valueOf(mode));
	}

	@Override
	public boolean isGlowing() {
		return this.stack.get(Keys.GLOWING).get().booleanValue();
	}

	@Override
	public PortalLinkItem convertToLinkItem() {
		return new SpongeLinkItem(this.stack);
	}

}
