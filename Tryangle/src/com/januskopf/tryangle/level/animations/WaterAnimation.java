package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorFlash;

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
			
			Triangle t = triangles.getExactTriangle((int)(a+xPos), (int)(b+yPos));
			if(t != null){
				float d = 0.01f - (0.04f - (0.04f/iRadius));
				t.addBottomLayerEffect(new ColorFlash(colorR+d, colorG+d, colorB+d, 40));
			}
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
	public boolean isActive() {
		return isActive;
	}

}
