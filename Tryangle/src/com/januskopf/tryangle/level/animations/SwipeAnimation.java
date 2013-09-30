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
		for(int i = 0; i < xTriangles; i++){		
			Triangle t = triangles.getTriangle(rowAni, i);
			t.addBottomLayerEffect(new ColorFlash(0.5f, 10));
		}		
		if(rowAni < yTriangles-1) 
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
