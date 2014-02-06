package com.januskopf.tryangle.triangles.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorTransition;

public class BackgroundChangeAnimation extends Animations{

	private boolean isActive;
	
	private TriangleContainer triangles;

	private float colorR;
	private float colorG;
	private float colorB;
	
	public BackgroundChangeAnimation(TriangleContainer triangles) {
		this.isActive = true;		
		this.triangles = triangles;
	}

	@Override
	protected void startAnimation() {
		
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
	
	public void updateBackground(){
		colorR = triangles.getGroundColorR();
		colorG = triangles.getGroundColorG();
		colorB = triangles.getGroundColorB();
		
		for (int i = 0; i < triangles.getxTriangles(); i++) {
			for (int j = 0; j < triangles.getyTriangles(); j++) {
				Triangle t = triangles.getTriangle(i, j);
				float c = (float)(1.0 - Math.random()/2.0);
				t.overrideBackgroundEffect(new ColorTransition(triangles, colorR*c, colorG*c, colorB*c, 2000));
			}
		}
	}	
	

}
