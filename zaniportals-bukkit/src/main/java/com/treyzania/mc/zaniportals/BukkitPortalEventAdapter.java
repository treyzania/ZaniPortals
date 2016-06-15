package com.treyzania.mc.zaniportals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
		
		PortalPlayer pp = new BukkitPortalPlayer(event.getPlayer());
		PortalItem heldItem = (event.getItem() != null ? new BukkitItem(event.getItem()) : null);
		
		Block block = event.getClickedBlock();
		Material mat = (block != null) ? block.getType() : null;
		PortalBlock pb = new BukkitBlock(block);
		
		switch (event.getAction()) {
			
			// Just forward it right through.
			case RIGHT_CLICK_BLOCK: {
				
				if (mat == Material.WALL_SIGN || mat == Material.SIGN_POST) {
					event.setCancelled(this.acceptor.onSignRightClick(pp, pb.getSignData(), heldItem));
				}
				
				break;
				
			}
			
			default: {
				
				if (heldItem != null && heldItem.isPortaly()) event.setCancelled(true);
				
			}
			
		}
		/*
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			
			//PortalPlayer pp = new BukkitPortalPlayer(event.getPlayer());
			PortalItem loc = new BukkitItem(event.getPlayer().getInventory().getItemInMainHand());
			
			if (loc.isPortaly()) {
				
				
				
			} else {
				System.out.println("not portaly");
			}
			
			PortalLinkItem pli = loc.convertToLinkItem();
			pli.getTarget().teleport(pp);
			
		} else {
			
			
			
		}
		*/
	}
	
}
