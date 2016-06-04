package com.treyzania.mc.zaniportals.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.treyzania.mc.zaniportals.Point3i;

public interface PortalWorld extends Wrapper {

	public String getName();
	public File getFile();
	
	public PortalLocation createLocation(double x, double y, double z);
	
	public default PortalBlock[] getBlocks(Point3i[] locations) {
		
		List<PortalBlock> blocks = new ArrayList<>();
		
		for (Point3i loc : locations) {
			blocks.add(this.createLocation(loc.x, loc.y, loc.z).getBlock());
		}
		
		return blocks.toArray(new PortalBlock[blocks.size()]);
		
	}
	
}
