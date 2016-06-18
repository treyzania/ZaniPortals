package com.treyzania.mc.zaniportals.adapters;

import java.util.List;

import com.treyzania.mc.zaniportals.portal.PortalPearlType;

public interface PortalItem extends Wrapper {

	public static int ENDER_PEARL_ID = 368;
	public static String PORTAL_PEARL_NAME = "Portal Pearl";
	
	public void setId(int id);
	public int getId();
	
	public void setDamage(short damage);
	public short getDamage();
	
	public void setSize(int size);
	public int getSize();
	
	public void setName(String name);
	public String getName();
	
	public List<String> getLore();
	public void setLore(List<String> lore);
	
	public void setGlowing(boolean mode);
	public boolean isGlowing();
	
	public default void makePortalyItem() {
		
		if (this.isPortaly()) return;
		if (!this.canBePortaly()) throw new NullPointerException("Wrong type of item!");
		
		this.setName(PORTAL_PEARL_NAME);
		this.setGlowing(true);
		
	}
	
	public default boolean canBePortaly() {
		return this.getId() == ENDER_PEARL_ID;
	}
	
	public default boolean isPortaly() {
		
		if (!this.canBePortaly()) return false;
		if (!this.isGlowing()) return false;
		if (!this.getName().equals(PORTAL_PEARL_NAME)) return false; 
		
		return true;
		
	}
	
	public default PortalPearlType getPortalType() {
		
		if (!this.isPortaly()) return null;
		
		for (PortalPearlType type : PortalPearlType.values()) {
			if (this.getLore().get(0).equals(type.title)) return type;
		}
		
		return null;
		
	}
	
	public PortalLinkItem convertToLinkItem();
	
}
