package com.treyzania.mc.zaniportals.adapters;

import java.util.Arrays;
import java.util.List;

import com.treyzania.mc.zaniportals.GsonManagement;
import com.treyzania.mc.zaniportals.gson.PortalTargetAdapter;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;

public interface PortalLinkItem extends PortalItem {

	public static String PORTAL_LINK_IDENTIFIER = "Portal Link";
	
	public default void setTarget(PortalTarget target) {
		
		String data = GsonManagement.getGson().toJson(target);
		String pretty = PortalTargetAdapter.makeSerializationPretty(data);
		
		this.setLore(Arrays.asList(PORTAL_LINK_IDENTIFIER, pretty));
		
	}
	
	public default PortalTarget getTarget() {
		
		List<String> lore = this.getLore();
		
		// Validate
		if (!lore.get(0).equals(PORTAL_LINK_IDENTIFIER)) throw new NullPointerException("Invalid portal link item!"); 
		
		String pretty = lore.get(1);
		String data = PortalTargetAdapter.makeSerializationConforming(pretty);
		
		// Actually deserialize
		PortalTarget target = GsonManagement.getGson().fromJson(data, PortalTarget.class);
		return target;
		
	}
	
}
