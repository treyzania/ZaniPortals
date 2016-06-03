package com.treyzania.mc.zaniportals;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.treyzania.mc.zaniportals.portal.PortalHelper;


public class SignPlaceListener implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		
		Block block = event.getBlock();
		
		// Validation.
		if (!PortalHelper.isValidNewSignSyntax((Sign) block.getState())) return; // Do nothing.
		if (!PortalHelper.isValidSignBlock(block)) return; // Do nothing.
		
		// TODO Create this algorithm and logic.
		
	}
	
}
