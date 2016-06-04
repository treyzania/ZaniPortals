package com.treyzania.mc.zaniportals;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.treyzania.mc.zaniportals.adapters.BukkitBlock;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.portal.PortalHelper;

public class SignPlaceListener implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		
		PortalBlock block = new BukkitBlock(event.getBlock());
		
		// Validation.
		if (!PortalHelper.isValidNewSignSyntax(block.getSignData())) return; // Do nothing.
		if (!PortalHelper.isValidSignBlock(block)) return; // Do nothing.
		
		// TODO Create this algorithm and logic.
		
	}
	
}
