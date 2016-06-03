package com.treyzania.mc.zaniportals.adapters;

import java.util.List;

public interface PortalSign {

	public static int WALL_SIGN_ID = 68;
	
	public void setText(List<String> text);
	public List<String> getText();
	
	public default void setLine(int line, String text) {
		
		List<String> lines = this.getText();
		
		// Ensure that we have enough lines to work with here.
		while (lines.size() <= line) lines.add("");
		lines.set(line, text);
		
		this.setText(lines);
		
		
	}
	
	public default String getLine(int line) {
		return this.getText().get(line);
	}
	
	static void validate(List<String> test) {
		
		if (test.size() > 4) throw new IllegalArgumentException("Array of invalid size.");
		
	}
	
}
