package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.treyzania.mc.zaniportals.IDMap;

public class SpongeServerProvider implements ServerProvider {

	// TODO This needs more encapsulation of things.
	
	private Object causeRoot;
	
	public SpongeServerProvider(Object causeRoot) {
		this.causeRoot = causeRoot;
	}
	
	@Override
	public void broadcast(String message) {
		Sponge.getServer().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize(message));
	}

	@Override
	public void broadcast(String message, String permission) {
		Sponge.getServer().getOnlinePlayers()
			.stream()
			.filter(p -> p.hasPermission(permission))
			.forEach(p -> p.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message)));
	}

	@Override
	public void scheduleAsync(Runnable r, long tickDelay) {
		Sponge.getScheduler().createTaskBuilder()
			.async()
			.delayTicks(tickDelay)
			.execute(r);
	}

	@Override
	public void scheduleSync(Runnable r, long tickDelay) {
		Sponge.getScheduler().createTaskBuilder()
			.delayTicks(tickDelay)
			.execute(r);
	}

	@Override
	public PortalWorld getWorld(String name) {
		return new SpongeWorld(Sponge.getServer().getWorld(name).get()); // FIXME this should return null, instead
	}

	@Override
	public PortalItem getItem(int id) {
		return new SpongeItemStack(ItemStack.builder().itemType(IDMap.getItemTypeById(id)).build());
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
