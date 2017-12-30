package com.treyzania.mc.zaniportals;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;

import com.treyzania.mc.zaniportals.adapters.EventAcceptor;

public class SpongePortalEventAdapter {

	private EventAcceptor acceptor;
	
	private SpongePortalEventAdapter() {
		this.acceptor = new EventAcceptor();
	}
	
	@Listener
	public void onSignChange(ChangeSignEvent event) {
		
	}
	
	@Listener
	public void onMove(MoveEntityEvent event) {
		
	}

	@Listener
	public void onBreakBlock(ChangeBlockEvent.Break event) {
		
	}
	
	@Listener
	public void onPlaceBlock(ChangeBlockEvent.Place event) {
		
	}
	
}
