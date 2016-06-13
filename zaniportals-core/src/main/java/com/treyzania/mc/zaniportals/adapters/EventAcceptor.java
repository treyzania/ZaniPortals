package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.portal.PortalHelper;

public class EventAcceptor {

	public boolean onSignUpdate(PortalPlayer player, PortalSign sign) {
		
		// Validation.
		if (!PortalHelper.isValidNewSignSyntax(sign)) return false; // Do nothing.
		if (!PortalHelper.isValidSignBlock(sign)) return false; // Do nothing.
		
		// TODO Create this algorithm and logic.
		
		return false;
		
	}
	
	public boolean onEnderPearlUse(PortalPlayer player, PortalItem item) {
		return item.isPortaly();
	}
	
	public boolean onSignRightClick(PortalPlayer player, PortalSign sign, PortalLinkItem hand) {
		
		sign.copyData(hand.getTarget());
		
		return true;
		
	}
	
}
