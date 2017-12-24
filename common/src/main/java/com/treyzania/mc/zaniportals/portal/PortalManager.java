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
import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalSign;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;

public class PortalManager implements Iterable<Portal> {

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
		
		portal.fill(0);
		this.portals.remove(portal);
		
	}
	
	public Portal getPortal(String name) {
		
		for (Portal p : this.portals) {
			if (p.name.equals(name)) return p;
		}
		
		return null;
		
	}
	
	/*
	 * UGHH TERRIBLE SIGNATURE.
	 */
	public Portal findPortal(PortalLocation loc, boolean volume, boolean frame) {
		
		int id = loc.getBlock().getId();
		if (id != PortalSign.WALL_SIGN_ID && !ZaniPortals.config.isBlockImportant(id)) return null;
		
		PortalWorld w = loc.getWorld();
		Point3i point = loc.getAsPoint3i();
		
		for (Portal p : this.portals) {
			
			if (!p.world.isSame(w)) continue;
			
			if (volume) for (Point3i pp : p.getPortalLocations()) {
				if (pp.equals(point)) return p;
			}
			
			if (frame) for (Point3i fp : p.getFrameLocations()) {
				if (fp.equals(point)) return p;
			}
			
		}
		
		// Just give up.
		return null;
		
	}
	
	public Portal findPortal(PortalBlock block, boolean volume, boolean frame) {
		return this.findPortal(block.getLocation(), volume, frame);
	}
	
	public Portal findPortal_anyBlock(PortalBlock block) {
		return this.findPortal(block, true, true);
	}
	
	public Portal findPortalAtEntity(PortalEntity ent) {
		return this.findPortal(ent.getLocation(), true, false);
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

	@Override
	public Iterator<Portal> iterator() {
		return this.portals.iterator();
	}
	
}
