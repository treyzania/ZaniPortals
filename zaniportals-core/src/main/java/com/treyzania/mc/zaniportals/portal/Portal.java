package com.treyzania.mc.zaniportals.portal;

import java.util.Set;
import java.util.UUID;

import com.treyzania.mc.zaniportals.Point3i;
import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalSign;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;
import com.treyzania.mc.zaniportals.portal.targets.NotifyInvalidPortalTarget;
import com.treyzania.mc.zaniportals.portal.targets.PortalTarget;
import com.treyzania.mc.zaniportals.world.Axis;
import com.treyzania.mc.zaniportals.world.Face;

import net.md_5.bungee.api.ChatColor;

public class Portal {
	
	private static final float LANDING_DROP_OFFSET_FACTOR = 1F;
	
	public final PortalWorld world;
	public final String name;
	public final UUID owner;
	private PortalTarget target = new NotifyInvalidPortalTarget(); // Default.
	public transient long lastUpdate; // Very quick fix because of some odd behavior on Bukkit's end.
	
	private Point3i signBlock;
	protected Point3i[] frameBlocks;
	protected Point3i[] portalBlocks;
	
	public Portal(PortalWorld world, UUID owner, String name, Point3i[] frame, Point3i[] portal) {
		
		this.world = world;
		this.owner = owner;
		this.name = name;
		
		this.frameBlocks = frame;
		this.portalBlocks = portal;
		
	}
	
	public Portal(PortalWorld world, UUID owner, String name) {
		this(world, owner, name, new Point3i[0], new Point3i[0]);
	}
	
	public boolean isInPortal(PortalLocation loc) {
		
		if (!loc.getWorld().getName().equals(this.world.getName())) return false;
		
		for (Point3i p : this.portalBlocks) {
			if (p.x == loc.getBlockX() && p.y == loc.getY() && p.z == loc.getZ()) return true;
		}
		
		return false;
		
	}
	
	public void setSignBlock(Point3i p3i) {
		this.signBlock = p3i;
	}
	
	public PortalLocation getSignBlock() {
		return this.world.createLocation(this.signBlock);
	}
	
	public void setFrameLocations(Set<Point3i> points) {
		this.frameBlocks = points.toArray(new Point3i[points.size()]);
	}
	
	public Point3i[] getFrameLocations() {
		return this.frameBlocks;
	}
	
	public void setPortalLocations(Set<Point3i> points) {
		this.portalBlocks = points.toArray(new Point3i[points.size()]);
	}
	
	public Point3i[] getPortalLocations() {
		return this.portalBlocks;
	}
	
	public void setTarget(PortalTarget target) {
		
		this.target = target;
		this.updateTargetLine();
		
	}
	
	public void updateTargetLine() {
		
		PortalSign sign = this.getSignBlock().getBlock().getSignData();
		if (sign != null && target != null) sign.setLine(3, ChatColor.ITALIC + target.getName());
		
	}
	
	public PortalTarget getTarget() {
		return this.target;
	}
	
	public void enter(PortalEntity ent) {
		
		// This is the only place that we can be sure that the block is loaded for us to update it, sadly.
		this.updateTargetLine();
		
		// Now we actually teleport.
		this.target.teleport(ent);
		
	}
	
	/**
	 * Sets all blocks in the volume of the portal to that block.
	 * 
	 * @param id The ID of the block.
	 */
	public void fill(int id) {
		
		for (Point3i p : this.portalBlocks) {
			this.world.getBlock(p).setId_noUpdate(id);
		}
		
	}
	
	public PortalLocation getLandingLocation() {
		
		float sumX = 0;
		float sumY = 0;
		float sumZ = 0;
		
		for (Point3i point : this.getPortalLocations()) {
			
			sumX += point.x;
			sumY += point.y;
			sumZ += point.z;
			
		}
		
		float numLocs = this.getPortalLocations().length;
		PortalLocation averageLocation = this.world.createLocation(sumX / numLocs, sumY / numLocs, sumZ / numLocs);
		float offset = (float) (LANDING_DROP_OFFSET_FACTOR * Math.cbrt(numLocs));
		
		Face direction = this.getSignBlock().getBlock().getSignData().getFace().getOpposite();
		float dirSign = (float) direction.getAxis().getSign(direction);
		
		float xFac = Axis.X.isOnAxis(direction) ? 1 : 0;
		float zFac = Axis.Z.isOnAxis(direction) ? 1 : 0;
		
		return averageLocation.getLocationAtOffset(dirSign * xFac * offset, 0, dirSign * zFac * offset);
		
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
}
