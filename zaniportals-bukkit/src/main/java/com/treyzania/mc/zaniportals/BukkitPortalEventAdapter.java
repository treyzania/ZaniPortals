package com.treyzania.mc.zaniportals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.treyzania.mc.zaniportals.adapters.BukkitBlock;
import com.treyzania.mc.zaniportals.adapters.BukkitItem;
import com.treyzania.mc.zaniportals.adapters.BukkitPortalPlayer;
import com.treyzania.mc.zaniportals.adapters.BukkitSign;
import com.treyzania.mc.zaniportals.adapters.EventAcceptor;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.adapters.PortalItem;
import com.treyzania.mc.zaniportals.adapters.PortalLinkItem;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;
import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.targets.AbsolutePortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public class BukkitPortalEventAdapter implements Listener {

	private final EventAcceptor acceptor;
	
	public BukkitPortalEventAdapter() {
		this.acceptor = new EventAcceptor();
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		
		PortalPlayer pp = new BukkitPortalPlayer(event.getPlayer());
		
		Block b = event.getBlock(); 
		
		event.setCancelled(this.acceptor.onSignUpdate(pp, new BukkitSign(b)));
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		PortalPlayer pp = new BukkitPortalPlayer(event.getPlayer());
		PortalItem heldItem = (event.getItem() != null ? new BukkitItem(event.getItem()) : null);
		
		Block block = event.getClickedBlock();
		Material mat = (block != null) ? block.getType() : null;
		PortalBlock pb = new BukkitBlock(block);
		
		switch (event.getAction()) {
			
			case LEFT_CLICK_AIR: {
				break;
			}
			
			case LEFT_CLICK_BLOCK: {
				
				if (event.getItem() != null) break;
				
				Block b = event.getClickedBlock();
				PortalBlock bb = new BukkitBlock(b);
				PortalTarget target = new AbsolutePortalTarget(bb.getLocation().getLocationAtOffset(0, 1, 0));
				
				ItemStack is = new ItemStack(PortalItem.ENDER_PEARL_ID);
				
				PortalItem pearl = new BukkitItem(is);
				PortalLinkItem pli = pearl.convertToLinkItem();
				pli.setTarget(target);
				
				event.getPlayer().getInventory().addItem(is);
				System.out.println(PortalHelper.serializePortalTarget(pli.getTarget()));
				
				event.setCancelled(true);
				
				break;
				
			}
			
			// Just forward it right through.
			case RIGHT_CLICK_AIR: {
				
				break;
				
			}
			
			// Just forward it right through.
			case RIGHT_CLICK_BLOCK: {
				
				if (mat == Material.WALL_SIGN || mat == Material.SIGN_POST) {
					
					boolean signPortaly = pb.isSign() && pb.getSignData().isPortaly();
					
					if (heldItem == null && signPortaly) {
						
						PortalTarget target = pb.getSignData().getTarget();
						target.teleport(pp);
						
					} else if (heldItem.isPortaly() && pb.getSignData().isPortaly()) {
						
						// Pass it through and let the acceptor handle it.
						event.setCancelled(this.acceptor.onSignRightClick(pp, pb.getSignData(), heldItem.convertToLinkItem()));
						
					}
					
				}
				
				break;
				
			}
			
			default:
				break;
			
			
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
