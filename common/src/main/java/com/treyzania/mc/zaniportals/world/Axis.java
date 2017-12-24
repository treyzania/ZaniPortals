package com.treyzania.mc.zaniportals.world;

import java.util.HashSet;
import java.util.Set;

public enum Axis {

	X(Face.EAST, Face.WEST),
	Y(Face.UP, Face.DOWN),
	Z(Face.SOUTH, Face.NORTH);
	
	public Face positive, negative;
	
	private Axis(Face pos, Face neg) {
		
		this.positive = pos;
		this.negative = neg;
		
	}
	
	public int getSign(Face face) {
		
		if (this.positive == face) return 1;
		if (this.negative == face) return -1;
		
		return 0;
		
	}
	
	public boolean isOnAxis(Face face) {
		return this.getSign(face) != 0;
	}
	
	public Set<Face> getMovableFaces() {
		
		Set<Face> faces = new HashSet<>(4);
		
		for (Face f : Face.values()) {
			if (!this.isOnAxis(f)) faces.add(f);
		}
		
		return faces;
		
	}
	
}
