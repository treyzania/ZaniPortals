package com.treyzania.mc.zaniportals;

import java.util.Map;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalSign;
import com.treyzania.mc.zaniportals.portal.Items;

/**
 * This class needs to be here since Sponge doesn't expose block IDs at all,
 * so we need to make block IDs totally fake in the context of the Sponge
 * version of ZaniPortals, which makes me somewhat sad, but that's okay as we'd
 * probably never be dealing with that many, and the IDs don't actually matter
 * that much.
 * 
 * And now later I'm realizing that it also needs to support item IDs as well.
 * 
 * @author treyzania
 */
public class IDMap {

	protected static final int STILL_WATER_ID = 9;
	
	public static BlockType getBlockTypeById(int id) {
		
		if (id == PortalSign.WALL_SIGN_ID) return BlockTypes.WALL_SIGN;
		if (id == STILL_WATER_ID) return BlockTypes.WATER;
		
		return getConfig().getBlockMap().get(id);
		
	}
	
	public static int getIdByBlockType(BlockType t) {
		
		if (t == BlockTypes.WALL_SIGN) return PortalSign.WALL_SIGN_ID;
		if (t == BlockTypes.WATER) return STILL_WATER_ID;
		
		// Ehhhh.  This is really slow and ugly but it doesn't matter that much.
		int id = -1;
		for (Map.Entry<Integer, BlockType> e : getConfig().getBlockMap().entrySet()) {
			if (e.getValue().equals(t.getId())) {
				id = e.getKey();
				break;
			}
		}
		
		return id;
		
	}

	public static ItemType getItemTypeById(int id) {
		
		if (id == Items.ENDER_PEARL_ID) return ItemTypes.ENDER_PEARL;
		return getConfig().getItemMap().get(id);
		
	}
	
	public static int getIdByItemType(ItemType t) {
		
		if (t == ItemTypes.ENDER_PEARL) return Items.ENDER_PEARL_ID;

		// See above.
		int id = -1;
		for (Map.Entry<Integer, BlockType> e : getConfig().getBlockMap().entrySet()) {
			if (e.getValue().equals(t.getId())) {
				id = e.getKey();
				break;
			}
		}
		
		return id;
		
	}
	
	private static ZaniPortalsSpongeConfig getConfig() {
		return (ZaniPortalsSpongeConfig) ZaniPortals.config; // please kill me
	}
	
}
