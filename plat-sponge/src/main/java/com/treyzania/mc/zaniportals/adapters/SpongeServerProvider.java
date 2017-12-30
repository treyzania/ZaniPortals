package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.serializer.TextSerializers;

public class SpongeServerProvider implements ServerProvider {

	// TODO This needs more encapsulation of things.
	
	@Override
	public void broadcast(String message) {
		Sponge.getServer().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize(message));
	}

	@Override
	public void broadcast(String message, String permission) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scheduleAsync(Runnable r, long tickDelay) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scheduleSync(Runnable r, long tickDelay) {
		// TODO Auto-generated method stub

	}

	@Override
	public PortalWorld getWorld(String name) {
		return new SpongeWorld(Sponge.getServer().getWorld(name).get()); // FIXME this should return null, instead
	}

	@Override
	public PortalItem getItem(int id) {
		return null;
	}

	@Override
	public PortalPlayer getPlayer(UUID uuid) {
		return new SpongePlayer(Sponge.getServer().getPlayer(uuid).get());
	}

	@Override
	public PortalPlayer getPlayer(String name) {
		return new SpongePlayer(Sponge.getServer().getPlayer(name).get());
	}

	@Override
	public String getUsername(UUID uuid) {
		return Sponge.getServer().getPlayer(uuid).map(p -> p.getName()).orElse(null);
	}

}
