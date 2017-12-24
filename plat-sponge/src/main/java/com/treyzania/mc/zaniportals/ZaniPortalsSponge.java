package com.treyzania.mc.zaniportals;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

@Plugin(id = "zaniportals", name = "ZaniPortals", version = "0.0.1")
public class ZaniPortalsSponge {

	@Inject
	private Logger logger;
	
	@Listener
	public void onInitialization(GameInitializationEvent event) {
		this.logger.info("ZaniPortals initializing...");
	}
	
}
