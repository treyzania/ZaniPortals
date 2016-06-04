package com.treyzania.mc.zaniportals.adapters;

import java.util.Arrays;
import java.util.List;

import com.treyzania.mc.zaniportals.portal.PortalHelper;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public interface PortalLinkItem extends PortalItem {

	public static String PORTAL_LINK_IDENTIFIER = "Portal Link";
	
	public default void setTarget(PortalTarget target) {
		
		String data = PortalHelper.serializePortalTarget(target);
		String pretty = PortalHelper.makeTargetSerializationPretty(data);
		
		this.setLore(Arrays.asList(PORTAL_LINK_IDENTIFIER, pretty));
		
	}
	
	public default PortalTarget getTarget() {
		
		List<String> lore = this.getLore();
		
		// Validate
		if (!lore.get(0).equals(PORTAL_LINK_IDENTIFIER)) throw new NullPointerException("Invalid portal link item!"); 
		
		String data = PortalHelper.makeTargetSerializationConforming(lore.get(1));
		
		// Actually deserialize
		return PortalHelper.deserializePortalTarget(data);
		
	}
	
}
