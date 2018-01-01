package com.treyzania.mc.zaniportals;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.treyzania.mc.zaniportals.adapters.PortalPlugin;
import com.treyzania.mc.zaniportals.adapters.SpongeCommandAdapter;
import com.treyzania.mc.zaniportals.adapters.SpongeServerProvider;
import com.treyzania.mc.zaniportals.cmd.AbstractPortalCommand;
import com.treyzania.mc.zaniportals.cmd.CommandGivePortalActivator;
import com.treyzania.mc.zaniportals.cmd.CommandListPortals;
import com.treyzania.mc.zaniportals.cmd.CommandPlayerLink;
import com.treyzania.mc.zaniportals.cmd.CommandReloadPortals;
import com.treyzania.mc.zaniportals.cmd.CommandSetPortalPublic;
import com.treyzania.mc.zaniportals.portal.PortalManager;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Plugin(id = "zaniportals", name = "ZaniPortals", version = "0.0.1")
public class ZaniPortalsSponge implements PortalPlugin {

	@Inject
	private GameRegistry gameRegistry;

	@Inject
	private CommandManager cmdManager;
	
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
		
		// Plug in a few event handlers and wrappers... (TODO more)
		ZaniPortals.server = new SpongeServerProvider(this);
		ZaniPortals.plugin = this;
		ZaniPortals.portals = new PortalManager();
		
		// First we just register the commands for the plugin.
		this.registerCommand(new CommandListPortals("listportals"));
		this.registerCommand(new CommandGivePortalActivator("giveactivator"));
		this.registerCommand(new CommandPlayerLink("giveplayerlinkpearl"));
		this.registerCommand(new CommandSetPortalPublic("setportalaccess"));
		this.registerCommand(new CommandReloadPortals("reloadportals"));
		
	}
	
	private void registerCommand(AbstractPortalCommand cmd) {
		this.cmdManager.register(this, new SpongeCommandAdapter(cmd), cmd.getName());
	}

	@Override
	public File getPortalDataFile() {
		return new File(Sponge.getGame().getSavesDirectory().toFile(), "portals.json");
	}
	
}
