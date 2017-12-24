package com.treyzania.mc.zaniportals.adapters;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

/**
 * This class needs to be here since Sponge doesn't expose block IDs at all,
 * so we need to make block IDs totally fake in the context of the Sponge
 * version of ZaniPortals, which makes me somewhat sad, but that's okay as we'd
 * probably never be dealing with that many, and the IDs don't actually matter
 * that much.
 * 
 * @author treyzania
 */
public class IDMap {

	public static BlockType getTypeById(int id) {
		
		if (id == PortalSign.WALL_SIGN_ID) return BlockTypes.WALL_SIGN;
		return null; // FIXME Make this compute things.
		
	}
	
	public static int getIdByType(BlockType t) {
		
		if (t == BlockTypes.WALL_SIGN) return PortalSign.WALL_SIGN_ID;
		return 1;
		
	}
	
}
