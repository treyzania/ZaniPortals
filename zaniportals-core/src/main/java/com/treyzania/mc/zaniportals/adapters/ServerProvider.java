package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

public interface ServerProvider {

	public void broadcast(String message);
	public void broadcast(String message, String permission);
	
	public void scheduleAsync(Runnable r, long tickDelay);
	public void scheduleSync(Runnable r, long tickDelay);
	
	public default void scheduleSync(Runnable r) {
		this.scheduleSync(r, 0);
	}
	
	public default void scheduleAsync(Runnable r) {
		this.scheduleAsync(r, 0);
	}
	
	public PortalWorld getWorld(String name);
	
	public PortalItem getItem(int id);
	public PortalPlayer getPlayer(UUID uuid);
	
}
