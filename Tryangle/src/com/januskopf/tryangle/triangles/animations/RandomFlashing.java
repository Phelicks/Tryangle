package com.januskopf.tryangle.triangles.animations;

import java.util.Random;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorTransition;

public class RandomFlashing extends Animations{	
	
	private boolean isActive = true;

	private int xTriangles;
	private int yTriangles;
	
	private Random random;
	private TriangleContainer triangles;
	
	public RandomFlashing(TriangleContainer triangles, int xTriangles, int yTriangles) {
		this.triangles = triangles;
		this.xTriangles = xTriangles;
		this.yTriangles = yTriangles;
		this.random = new Random();
	}

	@Override
	protected void startAnimation() {
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < 2; i++){
			Triangle t = triangles.getTriangle(random.nextInt(xTriangles), random.nextInt(yTriangles));
			float c = (float)(1.0 - Math.random()/2.0);
			t.addBackgroundEffect(new ColorTransition(triangles, triangles.getGroundColorR()*c, triangles.getGroundColorG()*c, triangles.getGroundColorB()*c, 150));
		}		
	}
	
	public void stopAnimation(){
		isActive = false;
	}

	@Override
	protected void endAnimation() {		
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
