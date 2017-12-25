package com.treyzania.mc.zaniportals;

import java.io.IOException;

import org.slf4j.Logger;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Plugin(id = "zaniportals", name = "ZaniPortals", version = "0.0.1")
public class ZaniPortalsSponge {

	@Inject
	private GameRegistry gameRegistry;
	
	@Inject
	private Logger logger;
	
	@Inject
	private GuiceObjectMapperFactory omFactory;
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;
	
	@Listener
	public void onPreInitialization(GamePreInitializationEvent event) {
		
		ZaniPortalsSpongeConfig config;
		
		try {
			
			CommentedConfigurationNode node = this.configLoader.load(ConfigurationOptions.defaults().setObjectMapperFactory(this.omFactory));
			config = node.getValue(TypeToken.of(ZaniPortalsSpongeConfig.class));
			
		} catch (IOException | ObjectMappingException e) {
			throw new IllegalStateException("Failed to load configuration!", e);
		}
		
		// global variables reee
		ZaniPortals.config = config;
		
		// This is fucking horrible.
		config.setGR(this.gameRegistry);
		
	}
	
	@Listener
	public void onInitialization(GameInitializationEvent event) {
		
		this.logger.info("ZaniPortals initializing...");
		
		
		
		
	}
	
}
