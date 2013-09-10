package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorTransition;

public class FadeAnimation extends Animations{
	
	private boolean isActive = true;
	
	private int xTriangles;
	private int yTriangles;
	
	private int rowAni = 0;
	private int colAni = 0;

	private TriangleContainer triangles;
	
	public FadeAnimation(TriangleContainer triangles, int xTriangles, int yTriangles) {
		this.triangles = triangles;
		this.xTriangles = xTriangles;
		this.yTriangles = yTriangles;
	}

	@Override
	protected void startAnimation() {		
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < 10; i++){
			Triangle t = triangles.getBackgroundTriangle(rowAni, colAni);
			float c = (float)(1.0 - Math.random()/2.0);
			if(t != null)t.addBackgroundEffect(new ColorTransition(0.0f*c, 0.69f*c, 0.39f*c, 100));
			
			if(rowAni < xTriangles-1)
				rowAni++;
			else{
				rowAni = 0;
				
				if(colAni < yTriangles-1){
					colAni++;
				}
				else{
					isActive = false;
				}
			}			
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
