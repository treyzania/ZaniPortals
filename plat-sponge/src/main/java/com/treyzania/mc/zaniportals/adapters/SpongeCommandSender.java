package com.treyzania.mc.zaniportals.adapters;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

public class SpongeCommandSender implements PortalCommandSender {

	private CommandSource source;
	
	public SpongeCommandSender(CommandSource src) {
		this.source = src;
	}
	
	@Override
	public boolean isOp() {
		return this.source.hasPermission("zaniportals.fakeop");
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.source.hasPermission(permission);
	}

	@Override
	public boolean isPlayer() {
		return this.source instanceof Player;
	}

	@Override
	public PortalPlayer getAsPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getWrappedObject() {
		// TODO Auto-generated method stub
		return this.source;
	}

	@Override
	public void sendMessage(String message) {
		this.source.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
	}

}
