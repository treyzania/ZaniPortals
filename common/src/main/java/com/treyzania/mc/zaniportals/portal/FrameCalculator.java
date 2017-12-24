package com.treyzania.mc.zaniportals.portal;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.treyzania.mc.zaniportals.Point3i;
import com.treyzania.mc.zaniportals.adapters.PortalBlock;
import com.treyzania.mc.zaniportals.adapters.PortalSign;
import com.treyzania.mc.zaniportals.adapters.PortalWorld;
import com.treyzania.mc.zaniportals.world.Face;

public class FrameCalculator {

	private static final int AIR_BLOCK_ID = 0;
	
	private Set<Integer> frameTypes;
	private int maxPortalVolume;
	
	public FrameCalculator(int[] frameBlocks, int maxVolume) {
		
		// Can't use Arrays.asList here, sadly.
		this.frameTypes = new TreeSet<Integer>();
		for (int i : frameBlocks) this.frameTypes.add(i);
		
		this.maxPortalVolume = maxVolume;
		
	}
	
	public void populate(Portal portal) {
		
		PortalWorld world = portal.world;
		PortalSign sign = portal.getSignBlock().getBlock().getSignData();
		
		// Set up the things to be populated by the recursion.
		Set<Point3i> frameCandidates = new TreeSet<>();
		
		// Find the faces we can move through.
		Set<Face> exploreFaces = sign.getLockedAxis().getMovableFaces();
		
		// Populate the frame candidates
		this.recurse(frameCandidates, sign.getBlockAttachedOnto(), this.frameTypes, getFacesSet(), this.maxPortalVolume);
		PortalBlock[] frameBlockCandidates = world.getBlocks(frameCandidates.toArray(new Point3i[frameCandidates.size()]));
		
		// Find the air blocks touching the applicable faces of the frame blocks.
		Set<Point3i> airBlocks = new TreeSet<>();
		for (PortalBlock block : frameBlockCandidates) {
			
			for (Face f : exploreFaces) {
				
				PortalBlock possibleAir = block.getBlockOnFace(f);
				
				// We don't need to check if the Set contains the point because Sets can't have duplicates.
				if (possibleAir.getId() == AIR_BLOCK_ID) airBlocks.add(possibleAir.getPoint3i());
				
			}
			
		}
		
		// Find an air block that we can recursively collect all air blocks from without going over out limit.
		Set<Point3i> portalBlocks = new TreeSet<>();
		for (Point3i airPos : airBlocks) {
			
			Set<Point3i> portalBlockCandidates = new TreeSet<>();
			
			try {
				
				// Recursively collect all the air blocks in the volume.
				this.recurse(portalBlockCandidates, world.getBlock(airPos), getAirSet(), exploreFaces, this.maxPortalVolume + 1, this.frameTypes);
				
				// ( Plus one so we can be sure if we're over the limit. )
				
			} catch (IllegalStateException e) {
				continue;
			}
			
			if (portalBlockCandidates.size() > this.maxPortalVolume) continue;
			
			// Update if we are bigger than the one we have already.
			if (portalBlockCandidates.size() > portalBlocks.size()) portalBlocks = portalBlockCandidates;
			
		}
		
		// Now find the immediate frame blocks...
		Set<Point3i> frameBlocks = new TreeSet<>();
		for (Point3i portalBlock : portalBlocks) {
			
			PortalBlock block = world.getBlock(portalBlock);
			
			for (Face f : exploreFaces) {
				
				// Find the ID of the block on the face, and add it if applicable.
				PortalBlock possibleFrame = block.getBlockOnFace(f);
				if (this.frameTypes.contains(possibleFrame.getId()) && !portalBlocks.contains(possibleFrame.getPoint3i())) {
					
					// Sets can't have duplicates, so we're fine.
					frameBlocks.add(possibleFrame.getPoint3i());
					
				}
				
			}
			
		}
		
		// Then add the blocks of the sign and the block the sign is attached to.
		frameBlocks.add(sign.getPoint3i());
		frameBlocks.add(sign.getBlockAttachedOnto().getPoint3i());
		
		// Install the block sets into the portal.
		portal.setFrameLocations(frameBlocks);
		portal.setPortalLocations(portalBlocks);
		
	}
	
	/**
	 * Add all applicable blocks to the list of <code>visited</code> blocks.  When a block type is encountered
	 * that is not in the list of applicable types, an IllegalStateException will be thrown, unless it is in
	 * the <code>ignored</code> list.
	 * 
	 * 
	 * @param visited The list to add the blocks to..
	 * @param block The starting block.
	 * @param types The types to travel through.
	 * @param facesTravelable The faces we can travel through.
	 * @param max The max number of blocks that can be contained in the visited blocks list.
	 * @param ignored A list of blocks types, that will not throw an exception when encountered.  If empty, no exceptions will be thrown ever.
	 */
	public void recurse(Set<Point3i> visited, PortalBlock block, Set<Integer> types, Set<Face> facesTravelable, int max, Set<Integer> ignored) {
		
		if (visited.size() >= max) return; // So we cannot recurse infinitely.
		
		boolean newlyAdded = visited.add(block.getPoint3i());
		if (!newlyAdded) return;
		
		boolean throwExceptions = !((ignored == null) || (ignored.size() == 0));
		
		for (Face f : facesTravelable) {
			
			PortalBlock blockOnFace = block.getBlockOnFace(f);
			int otherId = blockOnFace.getId();
			
			if (types.contains(otherId)) {
				
				// Recursive from the block on that face, we won't be able to visit the current node because it's in the list.
				this.recurse(visited, blockOnFace, types, facesTravelable, max, ignored);
				
			} else if (throwExceptions && !ignored.contains(otherId)) {
				throw new IllegalStateException("Encountered block we should not be encountering!");
			}
			
		}
		
	}
	
	public void recurse(Set<Point3i> visited, PortalBlock block, Set<Integer> types, Set<Face> facesTravelable, int max) {
		this.recurse(visited, block, types, facesTravelable, max, new HashSet<Integer>());
	}
	
	private static Set<Integer> getAirSet() {
		
		Set<Integer> s = new HashSet<>();
		s.add(AIR_BLOCK_ID);
		
		return s;
		
	}
	
	private static Set<Face> getFacesSet() {
		
		Set<Face> faces = new HashSet<>();
		for (Face f : Face.values()) faces.add(f);
		
		return faces;
		
	}
	
}
