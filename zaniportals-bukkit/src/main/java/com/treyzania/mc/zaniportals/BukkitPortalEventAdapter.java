package com.treyzania.mc.zaniportals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.treyzania.mc.zaniportals.adapters.BukkitBlock;
import com.treyzania.mc.zaniportals.adapters.BukkitItem;
import com.treyzania.mc.zaniportals.adapters.BukkitPortalPlayer;
import com.treyzania.mc.zaniportals.adapters.BukkitSign;
import com.treyzania.mc.zaniportals.adapters.EventAcceptor;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.adapters.PortalItem;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;

public class BukkitPortalEventAdapter implements Listener {

	private final EventAcceptor acceptor;
	
	public BukkitPortalEventAdapter() {
		this.acceptor = new EventAcceptor();
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		
		PortalPlayer pp = new BukkitPortalPlayer(event.getPlayer());
		
		Block b = event.getBlock(); 
		
		event.setCancelled(this.acceptor.onSignUpdate(pp, new BukkitSign(b), event.getLines()));
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		Player p = event.getPlayer();
		PortalPlayer pp = new BukkitPortalPlayer(p);
		PortalItem heldItem = (event.getItem() != null ? new BukkitItem(event.getItem()) : null);
		
		Block block = event.getClickedBlock();
		Material mat = (block != null) ? block.getType() : null;
		PortalBlock pb = new BukkitBlock(block);
		
		if (heldItem != null && !heldItem.isPortaly()) return;
		
		switch (event.getAction()) {
			
			// Just forward it right through.
			case RIGHT_CLICK_BLOCK: {
				
				if (mat == Material.WALL_SIGN) {
					
					// Finally, we can just pass everything through here.
					this.acceptor.onSignRightClick(pp, pb.getSignData(), heldItem, p.getInventory().getHeldItemSlot());
					
					
				}
				
				event.setCancelled(true);
				break;
				
			}
			
			case RIGHT_CLICK_AIR: {
				
				event.setCancelled(true);
				break;
				
			}
			
			case LEFT_CLICK_BLOCK:
			case LEFT_CLICK_AIR:
			default: {
				
				event.setCancelled(false);
				break; // Nothing to do here.
				
			}
			
		}
		
		
		
	}
	
	@EventHandler
	public void onDestroy(BlockBreakEvent event) {
		
		if (event.getPlayer() == null) return;
		
		// Pull out the properties.
		PortalPlayer player = new BukkitPortalPlayer(event.getPlayer());
		PortalBlock block = new BukkitBlock(event.getBlock());
		
		event.setCancelled(this.acceptor.onPlayerBreakBlock(player, block));
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		// Very simple, just wrap the player and pass the event.
		PortalPlayer pp = new BukkitPortalPlayer(event.getPlayer());
		event.setCancelled(this.acceptor.onMove(pp));
		
	}
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		this.acceptor.onNaturalDestroy(new BukkitBlock(event.getBlock()));
	}
	
	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event) {
		
		boolean cancel = false;
		
		PortalBlock block = new BukkitBlock(event.getBlock());
		PortalBlock source = new BukkitBlock(event.getSource());
		
		// Won't always destroy it.  Better safe than sorry.
		cancel |= this.acceptor.onFlow(source, block);
		
		event.setCancelled(cancel);
		
	}
	
	@EventHandler
	public void onBlockChange(BlockFromToEvent event) {
		
		PortalBlock from = new BukkitBlock(event.getBlock());
		PortalBlock to = new BukkitBlock(event.getToBlock());
		
		event.setCancelled(this.acceptor.onFlow(to, from));
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(this.acceptor.onPlayerPlaceBlock(new BukkitPortalPlayer(event.getPlayer()), new BukkitBlock(event.getBlock())));
	}
	
}
