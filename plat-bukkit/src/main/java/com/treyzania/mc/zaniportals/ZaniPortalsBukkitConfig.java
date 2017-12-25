package com.treyzania.mc.zaniportals;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.treyzania.mc.zaniportals.adapters.PortalConfig;

public class ZaniPortalsBukkitConfig implements PortalConfig {
	
	public List<Integer> signPlaceBlockIds;
	public List<Integer> portalStructureBlockIds;
	public int portalBlockId;
	public int maxPortalBlocks;
	
	protected ZaniPortalsBukkitConfig(FileConfiguration fc) {
		
		this.signPlaceBlockIds = fc.getIntegerList("PortalStructure.SignPlaceOnBlocks");
		this.portalStructureBlockIds = fc.getIntegerList("PortalStructure.PortalFrameBlocks");
		this.portalBlockId = fc.getInt("PortalStructure.PortalBlock");
		this.maxPortalBlocks = fc.getInt("PortalStructure.MaxPortalBlocks");
		
	}
	
	@Override
	public int[] getFrameBlockIds() {
		
		// No easy way to get convert the list of Integers into an array of ints.
		int[] array = new int[portalStructureBlockIds.size()];
		for (int i = 0; i < portalStructureBlockIds.size(); i++) {
			array[i] = portalStructureBlockIds.get(i);
		}
		
		return array;
		
	}
	
	@Override
	public int getPortalBlockId() {
		return this.portalBlockId;
	}
	
	@Override
	public int getMaxPortalSize() {
		return this.maxPortalBlocks;
	}

	@Override
	public boolean isBlockImportant(int id) {
		
		// Very simple checks here.
		if (id == this.portalBlockId) return true;
		if (this.portalStructureBlockIds.contains(Integer.valueOf(id))) return true;
		if (this.signPlaceBlockIds.contains(Integer.valueOf(id))) return true;
		
		return false;
		
	}
	
}
