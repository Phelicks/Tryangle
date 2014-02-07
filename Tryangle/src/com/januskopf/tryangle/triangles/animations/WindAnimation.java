package com.januskopf.tryangle.triangles.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorFlash;

public class WindAnimation extends Animations{
	
	private boolean isActive = true;
	private int xPos;
	private int yPos;
	private float radius;
	private int iRadius = 0;
	
	private TriangleContainer triangles;
	
	public WindAnimation(TriangleContainer triangles, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.radius = 150;
		
		this.triangles = triangles;
	}

	@Override
	protected void startAnimation() {			
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < iRadius; i++){
			float a = (float)Math.sin(i)*iRadius;
			float b = (float)Math.cos(i)*iRadius;
			Triangle t = triangles.getExactTriangle((int)a+xPos, (int)b+yPos);
			if(t != null)t.addBottomLayerEffect(new ColorFlash(0.009f, 0.009f, 0.009f, 10+(iRadius/5)));
		}
		if(iRadius <= radius)
			iRadius += ((float)radius)/20f;
		else{
			iRadius = 00;
			isActive = false;
		}
	}

	@Override
	protected void endAnimation() {
		
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
