package com.treyzania.mc.zaniportals.world;

public enum Face {

	NORTH(180F),
	SOUTH(0F),
	EAST(-90F),
	WEST(90F),
	UP(0F),
	DOWN(0F);
	
	public float angle;
	
	private Face(float angle) {
		this.angle = angle;
	}
	
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
