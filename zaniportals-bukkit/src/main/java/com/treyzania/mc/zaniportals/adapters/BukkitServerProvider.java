package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.Bukkit;

import com.treyzania.mc.zaniportals.ZaniPortalsBukkit;

public class BukkitServerProvider implements ServerProvider {
	
	@Override
	public void broadcast(String message) {
		Bukkit.broadcastMessage(message);
	}

	@Override
	public void broadcast(String message, String permission) {
		Bukkit.broadcast(message, permission);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void scheduleAsync(Runnable r, long tickDelay) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(ZaniPortalsBukkit.INSTANCE, r, tickDelay);
	}

	@Override
	public void scheduleSync(Runnable r, long tickDelay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ZaniPortalsBukkit.INSTANCE, r, tickDelay);
	}

	@Override
	public PortalWorld getWorld(String name) {
		return new BukkitWorld(Bukkit.getWorld(name));
	}

}
