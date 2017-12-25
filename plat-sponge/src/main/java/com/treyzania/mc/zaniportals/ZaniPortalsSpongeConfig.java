package com.treyzania.mc.zaniportals;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;

import com.treyzania.mc.zaniportals.adapters.PortalConfig;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class ZaniPortalsSpongeConfig implements PortalConfig {
	
	@Setting(value = "portal.frameIds")
	private int[] frameIds = { 49, 112 };
	
	@Setting(value = "portal.signBlockIds", comment = "The IDs allowed for the block that the sign goes on.")
	private int[] signBlockIds = { 49 };
	
	@Setting(value = "portal.maxSize")
	private int maxSize = 50;
	
	@Setting(value = "idmap.blocks")
	private Map<Integer, String> blockIds;
	
	@Setting(value = "idmap.items")
	private Map<Integer, String> itemIds;
	
	private transient GameRegistry gameReg;
	
	protected void setGR(GameRegistry gr) {
		this.gameReg = gr;
	}
	
	@Override
	public int[] getFrameBlockIds() {
		return this.frameIds;
	}

	@Override
	public int getPortalBlockId() {
		return IDMap.STILL_WATER_ID;
	}

	@Override
	public int getMaxPortalSize() {
		return this.maxSize;
	}

	@Override
	public boolean isBlockImportant(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<Integer, BlockType> getBlockMap() {
		
		Map<Integer, BlockType> m = new HashMap<>();
		
		for (Map.Entry<Integer, String> e : this.blockIds.entrySet()) {
			m.put(e.getKey(), this.gameReg.getType(BlockType.class, e.getValue()).get());
		}
		
		return m;
		
	}
	
	public Map<Integer, ItemType> getItemMap() {

		Map<Integer, ItemType> m = new HashMap<>();
		
		for (Map.Entry<Integer, String> e : this.blockIds.entrySet()) {
			m.put(e.getKey(), this.gameReg.getType(ItemType.class, e.getValue()).get());
		}
		
		return m;
		
	}
	
}
