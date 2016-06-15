package com.treyzania.mc.zaniportals.portal;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.treyzania.mc.zaniportals.GsonManagement;
import com.treyzania.mc.zaniportals.Point3i;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;

public class PortalManager {

	private Set<Portal> portals;
	
	public PortalManager() {
		this.portals = new HashSet<>();
	}
	
	public boolean hasPortalWithName(String name) {
		
		for (Portal p : this.portals) {
			if (p.name.equals(name)) return true;
		}
		
		return false;
		
	}
	
	public boolean tryAddPortal(Portal portal) {
		return this.portals.add(portal);
	}
	
	public void removePortal(String name) {
		
		Iterator<Portal> iter = this.portals.iterator();
		
		while (iter.hasNext()) {
			
			Portal p = iter.next();
			if (p.name.equals(name)) iter.remove();
			
		}
		
	}
	
	public void removePortal(Portal portal) {
		this.portals.remove(portal);
	}
	
	public Portal getPortal(String name) {
		
		for (Portal p : this.portals) {
			if (p.name.equals(name)) return p;
		}
		
		return null;
		
	}
	
	public Portal findPortal(PortalLocation loc) {
		
		PortalWorld w = loc.getWorld();
		Point3i point = loc.getAsPoint3i();
		
		for (Portal p : this.portals) {
			
			if (!p.world.equals(w)) continue;
			
			Point3i[] pBlocks = p.getPortalLocations();
			Point3i[] fBlocks = p.getFrameLocations();
			
			for (Point3i pp : pBlocks) {
				if (pp.equals(point)) return p;
			}
			
			for (Point3i fp : fBlocks) {
				if (fp.equals(point)) return p;
			}
			
		}
		
		// Just give up.
		return null;
		
	}
	
	public Portal findPortal(PortalBlock block) {
		return this.findPortal(block.getLocation());
	}
	
	public void save(File f) {
		
		// Serialize the data itself.
		Gson gson = GsonManagement.getGson();
		String data = gson.toJson(this.portals);
		
		// Write it to the file.
		try {
			
			if (!f.exists()) {
				
				f.getParentFile().mkdirs();
				f.createNewFile();
				
			}
			
			FileWriter fw = new FileWriter(f);
			fw.write(data);
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void load(File f) {
		
		Gson gson = GsonManagement.getGson();
		JsonParser parser = new JsonParser();
		
		try {
			
			if (!f.exists()) return;
			
			FileReader fr = new FileReader(f);
			JsonArray list = parser.parse(fr).getAsJsonArray();
			
			for (JsonElement ele : list) {
				
				if (ele.isJsonObject()) {
					
					try {
						
						Portal p = gson.fromJson(ele, Portal.class);
						this.portals.add(p); // Put it in directly.
						
					} catch (Exception e) {
						System.out.println("Found malformed entry in Portal data file: " + ele.toString());
					}
					
				} else {
					System.out.println("Found invalid entry in Portal data file: " + ele.toString());
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("Something went wrong loading data! (" + e.getMessage() + ")");
		}
		
	}
	
}
