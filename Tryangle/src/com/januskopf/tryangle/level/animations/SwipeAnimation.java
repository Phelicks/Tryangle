package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.effects.ColorFlash;
import com.januskopf.tryangle.level.Level;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class SwipeAnimation extends Animations{

	private boolean isActive = true;

	private int xTriangles;
	private int yTriangles;
	
	private int rowAni = 0;

	private TriangleContainer triangles;
	
	public SwipeAnimation(TriangleContainer triangles) {
		this.triangles = triangles;
		this.xTriangles = Level.X_TRIANGLES;
		this.yTriangles = Level.Y_TRIANGLES;
	}

	@Override
	protected void startAnimation() {		
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < yTriangles; i++){		
			Triangle t = triangles.getBackgroundTriangle(rowAni, i);
			float c = 1.8f;
			t.addEffect(new ColorFlash(t.getColorR()*c, t.getColorG()*c, t.getColorB()*c, t, 20));
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
	protected boolean isActive() {
		return isActive;
	}

}
