package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.effects.ColorFlash;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class WaterAnimation extends Animations{
	
	private boolean isActive = true;
	private int xPos;
	private int yPos;
	private int iRadius;
	private int radius;
	
	float colorR;
	float colorG;
	float colorB;
	
	private TriangleContainer triangles;
	
	public WaterAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB, int radius){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.xPos = xPos;
		this.yPos = yPos;
		this.triangles = triangles;
		this.iRadius = 10;
		this.radius = radius;
	}
	
	public WaterAnimation(TriangleContainer triangles, int xPos, int yPos) {
		this(triangles, xPos, yPos, 1.0f, 1.0f, 1.0f, 300);
		
	}

	@Override
	protected void startAnimation() {			
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < 360; i++){
			float a = (float)(Math.sin(i)*iRadius);
			float b = (float)(Math.cos(i)*iRadius);	
			
			try {
				Triangle t = triangles.getExactTriangle((int)(a+xPos), (int)(b+yPos));
				float d = 0.10f - (0.4f - (0.4f/iRadius));
				t.addEffect(new ColorFlash(colorR+d, colorG+d, colorB+d, t, 40));
			} catch (Exception e) {}
		}
		if(iRadius <= radius){
			iRadius += 4;
		}
		else{
			iRadius = 50;
			isActive = false;
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
