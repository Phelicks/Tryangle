package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorFlash;

public class SwipeAnimation extends Animations{

	private boolean isActive = true;

	private int xTriangles;
	private int yTriangles;
	
	private int rowAni = 0;

	private TriangleContainer triangles;
	
	public SwipeAnimation(TriangleContainer triangles) {
		this.triangles = triangles;
		this.xTriangles = triangles.getxTriangles();
		this.yTriangles = triangles.getyTriangles();
	}

	@Override
	protected void startAnimation() {		
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < yTriangles; i++){		
			Triangle t = triangles.getBackgroundTriangle(rowAni, i);
			float c = 1.8f;
			if(t != null)t.addTopLayerEffect(new ColorFlash(c, c, c, 20));//War mal TriangleFarbe + c
		}		
		if(rowAni < xTriangles-1) 
			rowAni++;
		else{
			isActive = false;
			rowAni = 0;
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
