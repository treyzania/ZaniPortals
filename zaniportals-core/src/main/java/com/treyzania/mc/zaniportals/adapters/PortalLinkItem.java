package com.treyzania.mc.zaniportals.adapters;

import java.util.List;

import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.PortalPearlType;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

import net.md_5.bungee.api.ChatColor;

public interface PortalLinkItem extends PortalItem {

	public default void setTarget(PortalTarget target) {
		
		String data = PortalHelper.serializePortalTarget(target);
		String pretty = PortalHelper.makeTargetSerializationPretty(data);
		
		this.resetPearlType(PortalPearlType.LINK);;
		this.appendLore(ChatColor.BLUE + pretty);
		
	}
	
	public default PortalTarget getTarget() {
		
		List<String> lore = this.getLore();
		
		// Validate
		if (!lore.get(0).equals(PortalPearlType.LINK.title)) throw new NullPointerException("Invalid portal link item!"); 
		
		String data = PortalHelper.makeTargetSerializationConforming(ChatColor.stripColor(lore.get(1)));
		
		// Actually deserialize
		return PortalHelper.deserializePortalTarget(data);
		
	}
	
}
