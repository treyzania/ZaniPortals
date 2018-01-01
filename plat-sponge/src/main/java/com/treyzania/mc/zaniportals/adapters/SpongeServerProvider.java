package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
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
		Sponge.getServer().getBroadcastChannel().send(this.causeRoot, TextSerializers.FORMATTING_CODE.deserialize(message));
	}

	@Override
	public void broadcast(String message, String permission) {
		
		Text text = TextSerializers.FORMATTING_CODE.deserialize(message);
		
		// Send it to the players.
		Sponge.getServer().getOnlinePlayers()
			.stream()
			.filter(p -> p.hasPermission(permission))
			.forEach(p -> p.sendMessage(text));
		
		// And the console.
		Sponge.getServer().getConsole().sendMessage(text);
		
		// FIXME The message doesn't include the plugin as a sender, so we should fix that.
		
	}

	@Override
	public void scheduleAsync(Runnable r, long tickDelay) {
		Sponge.getScheduler().createTaskBuilder()
			.async()
			.delayTicks(tickDelay)
			.execute(r)
			.submit(this.causeRoot);
	}

	@Override
	public void scheduleSync(Runnable r, long tickDelay) {
		Sponge.getScheduler().createTaskBuilder()
			.delayTicks(tickDelay)
			.execute(r)
			.submit(this.causeRoot);
	}

	@Override
	public PortalWorld getWorld(String name) {
		return Sponge.getServer().getWorld(name).map(w -> new SpongeWorld(w)).orElse(null);
	}

	@Override
	public PortalItem getItem(int id) {
		return new SpongeItemStack(ItemStack.builder().itemType(IDMap.getItemTypeById(id)).build());
	}

	@Override
	public PortalPlayer getPlayer(UUID uuid) {
		return Sponge.getServer().getPlayer(uuid).map(p -> new SpongePlayer(p)).orElse(null);
	}

	@Override
	public PortalPlayer getPlayer(String name) {
		return Sponge.getServer().getPlayer(name).map(p -> new SpongePlayer(p)).orElse(null);
	}

	@Override
	public String getUsername(UUID uuid) {
		return Sponge.getServer().getPlayer(uuid).map(p -> p.getName()).orElse(null);
	}

}
