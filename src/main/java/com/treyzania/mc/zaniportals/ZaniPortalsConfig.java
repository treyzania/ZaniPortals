package com.treyzania.mc.zaniportals;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class ZaniPortalsConfig {
	
	public List<Integer> signPlaceBlockIds;
	public List<Integer> portalStructureBlockIds;
	public int portalBlockId;
	
	protected ZaniPortalsConfig(FileConfiguration fc) {
		
		this.signPlaceBlockIds = fc.getIntegerList("PortalStructure.SignPlaceOnBlocks");
		this.portalStructureBlockIds = fc.getIntegerList("PortalStructure.PortalFrameBlocks");
		this.portalBlockId = fc.getInt("PortalStructure.PortalBlock");
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean isValidSignBlock(Material m) {
		return this.signPlaceBlockIds.contains(m.getId());
	}
	
	@SuppressWarnings("deprecation")
	public boolean isValidFrameBlock(Material m) {
		return this.portalStructureBlockIds.contains(m.getId());
	}
	
}
