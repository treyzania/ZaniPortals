package com.treyzania.mc.zaniportals.world;

public enum Face {

	NORTH,
	SOUTH,
	EAST,
	WEST,
	UP,
	DOWN;
	
	public Face getOpposite() {
		
		for (Axis a : Axis.values()) {
			
			if (a.positive == this) return a.negative;
			if (a.negative == this) return a.positive;
			
		}
		
		return null;
		
	}
	
	public Axis getAxis() {
		
		for (Axis a : Axis.values()) if (a.isOnAxis(this)) return a;
		return null;
		
	}
	
}
