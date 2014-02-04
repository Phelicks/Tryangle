package com.januskopf.tryangle.triangles.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorTransition;

public class FadeAnimation extends Animations{
	
	private boolean isActive = true;
	
	private int xTriangles;
	private int yTriangles;
	
	private float colorR;
	private float colorG;
	private float colorB;
	
	int ticks;
	
	private int rowAni = 0;
	private int colAni = 0;
	
	boolean randomise;

	private TriangleContainer triangles;
	
	public FadeAnimation(TriangleContainer triangles, int ticks, boolean randomise) {
		this.triangles = triangles;
		this.xTriangles = triangles.getxTriangles();
		this.yTriangles = triangles.getyTriangles();
		
		this.colorR = triangles.getBackgroundRed();
		this.colorG = triangles.getBackgroundGreen();
		this.colorB = triangles.getBackgroundBlue();
		
		this.ticks = ticks;
		
		this.randomise = randomise;
	}

	@Override
	protected void startAnimation() {		
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < 10; i++){
			Triangle t = triangles.getTriangle(rowAni, colAni);
			
			float c;
			
			if(randomise)
				c = (float)(1.0 - Math.random()/2.0);
			else
				c = 1;
			
			t.addBackgroundEffect(new ColorTransition(colorR*c, colorG*c, colorB*c, ticks));
			
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
