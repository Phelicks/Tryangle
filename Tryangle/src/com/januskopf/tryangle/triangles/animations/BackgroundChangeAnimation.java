package com.januskopf.tryangle.triangles.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorTransition;

public class BackgroundChangeAnimation extends Animations{

	private boolean isActive;
	
	private TriangleContainer triangles;

	private float oldR;
	private float oldG;
	private float oldB;

	private float colorR;
	private float colorG;
	private float colorB;

	private boolean animated;
	
	public BackgroundChangeAnimation(TriangleContainer triangles, float red, float green, float blue, boolean animated) {
		this.isActive = true;		
		this.animated = animated;
		this.triangles = triangles;
		
		oldR = triangles.getGroundColorR();
		oldG = triangles.getGroundColorG();
		oldB = triangles.getGroundColorB();
		
		colorR = red;
		colorG = green;
		colorB = blue;
	}

	@Override
	protected void startAnimation() {
		if(animated){
			for (int i = 0; i < triangles.getxTriangles(); i++) {
				for (int j = 0; j < triangles.getyTriangles(); j++) {
					Triangle t = triangles.getTriangle(i, j);
	
					float r = oldR / t.getColorR();
					float g = oldG / t.getColorG();
					float b = oldB / t.getColorB();
					
					t.overrideBackgroundEffect(new ColorTransition(triangles, colorR*r, colorG*g, colorB*b, 1000));
				}
			}
		}
	}

	@Override
	protected void runAnimation() {
		
	}

	@Override
	protected void endAnimation() {
		
	}

	@Override
	public boolean isActive() {
		return isActive;
	}
	
	public float getColorR() {
		return colorR;
	}

	public float getColorG() {
		return colorG;
	}

	public float getColorB() {
		return colorB;
	}
}
