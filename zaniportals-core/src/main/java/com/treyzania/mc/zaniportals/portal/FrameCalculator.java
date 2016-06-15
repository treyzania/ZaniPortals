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
	
	private Set<Integer> frameBlocks;
	private int maxPortalVolume;
	
	public FrameCalculator(int[] frameBlocks, int maxVolume) {
		
		// Can't use Arrays.asList here, sadly.
		this.frameBlocks = new TreeSet<Integer>();
		for (int i : frameBlocks) this.frameBlocks.add(i);
		
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
		this.recurse(frameCandidates, sign, this.frameBlocks, exploreFaces, this.maxPortalVolume);
		PortalBlock[] frameBlocks = world.getBlocks(frameCandidates.toArray(new Point3i[frameCandidates.size()]));
		
		// Find the air blocks touching the applicable faces of the frame blocks.
		Set<Point3i> airBlocks = new TreeSet<>();
		for (PortalBlock block : frameBlocks) {
			
			for (Face f : exploreFaces) {
				
				PortalBlock possibleAir = block.getBlockOnFace(f);
				
				// We don't need to check if the Set contains the point because Sets can't have duplicates.
				if (possibleAir.getId() == AIR_BLOCK_ID) airBlocks.add(possibleAir.getPoint3i());
				
			}
			
		}
		
		// Find an air block that we can recursively collect all air blocks from without going over out limit.
		Set<Integer> airId = new HashSet<>();
		airId.add(0); // We only want the one block ID of air.
		Set<Point3i> portalBlocks = null;
		for (Point3i airPos : airBlocks) {
			
			portalBlocks = new TreeSet<>(); // Reset each time.
			
			try {
				
				// Recursively collect all the air blocks in the volume.
				this.recurse(portalBlocks, world.getBlock(airPos), airId, exploreFaces, this.maxPortalVolume, this.frameBlocks);
				
			} catch (IllegalStateException e) {
				continue;
			}
			
			if (portalBlocks.size() >= this.maxPortalVolume) continue;
			
			// If we get to this point then it means we've found a valid set of blocks, and we don't need to reset.
			break;
			
		}
		
		// Install the block sets into the portal.
		portal.frameBlocks = portalBlocks.toArray(new Point3i[portalBlocks.size()]);
		portal.portalBlocks = portalBlocks.toArray(new Point3i[portalBlocks.size()]);
		
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
		
		visited.add(block.getPoint3i());
		
		boolean throwExceptions = (ignored == null) || (ignored.size() == 0);
		
		for (Face f : facesTravelable) {
			
			PortalBlock blockOnFace = block.getBlockOnFace(f);
			int otherId = block.getId();
			
			// We don't need to check if the Set contains the point because Sets can't have duplicates.
			if (types.contains(otherId)) {
				
				// Recursive from the block on that face, we won't be able to visit the current node because it's in the list.
				this.recurse(visited, blockOnFace, types, facesTravelable, max);
				
			} else if (throwExceptions && !ignored.contains(otherId)) {
				throw new IllegalStateException("Encountered block we should not be encountering!");
			}
			
			if (visited.size() >= max) return; // So we cannot recurse infinitely.
			
		}
		
	}
	
	public void recurse(Set<Point3i> visited, PortalBlock block, Set<Integer> types, Set<Face> facesTravelable, int max) {
		this.recurse(visited, block, types, facesTravelable, max, new HashSet<Integer>());
	}
	
}
