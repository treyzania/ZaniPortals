package com.treyzania.mc.zaniportals;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;

import com.treyzania.mc.zaniportals.adapters.EventAcceptor;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;
import com.treyzania.mc.zaniportals.adapters.SpongeBlock;
import com.treyzania.mc.zaniportals.adapters.SpongePlayer;

public class SpongePortalEventAdapter {

	private EventAcceptor acceptor;
	
	private SpongePortalEventAdapter() {
		this.acceptor = new EventAcceptor();
	}
	
	@Listener
	public void onSignChange(ChangeSignEvent event) {
		// TODO I don't actually think we need this.
	}
	
	@Listener
	public void onMove(MoveEntityEvent event) {
		
		if (event.getTargetEntity() instanceof Player) {
			
			Player p = (Player) event.getTargetEntity();
			if (this.acceptor.onMove(new SpongePlayer(p))) event.setCancelled(true);
			
		}
		
	}

	@Listener
	public void onBreakBlock(ChangeBlockEvent.Break event) {
		
		Optional<Player> op = event.getCause().first(Player.class);
		
		if (op.isPresent()) {
			
			PortalPlayer pp = new SpongePlayer(op.get());
			
			boolean cancel = false;
			for (Transaction<BlockSnapshot> tx : event.getTransactions()) {
				PortalBlock pb = new SpongeBlock(tx.getFinal().getLocation().get());
				if (this.acceptor.onPlayerBreakBlock(pp, pb)) cancel |= true;
			}
			
			if (cancel) event.setCancelled(true);
			
		}
		
	}
	
	@Listener
	public void onPlaceBlock(ChangeBlockEvent.Place event) {
		
		Optional<Player> op = event.getCause().first(Player.class);
		
		if (op.isPresent()) {
			
			PortalPlayer pp = new SpongePlayer(op.get());
			
			// Try each of the block snapshots.
			boolean cancel = false;
			for (Transaction<BlockSnapshot> tx : event.getTransactions()) {
				PortalBlock pb = new SpongeBlock(tx.getFinal().getLocation().get());
				if (this.acceptor.onPlayerPlaceBlock(pp, pb)) cancel |= true;
			}
			
			if (cancel) event.setCancelled(true);
			
		}
		
	}
	
}
