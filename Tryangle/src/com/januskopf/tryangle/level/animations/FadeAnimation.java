package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.effects.ColorTransition;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

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
		for(int i = 0; i < 3; i++){
			Triangle t = triangles.getBackgroundTriangle(rowAni, colAni);
			float c = (float)(1.0 - Math.random()/2.0);
			t.addEffect(new ColorTransition(0.0f*c, 0.69f*c, 0.39f*c, t, 100));
			
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
	protected boolean isActive() {
		return isActive;
	}

}
