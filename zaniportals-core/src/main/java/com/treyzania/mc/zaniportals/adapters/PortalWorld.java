package com.treyzania.mc.zaniportals.adapters;

import java.io.File;

public interface PortalWorld extends Wrapper {

	public String getName();
	public File getFile();
	
	public PortalGameLocation createLocation(double x, double y, double z);
	
}
