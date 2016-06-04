package com.treyzania.mc.zaniportals.portal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
	
}
