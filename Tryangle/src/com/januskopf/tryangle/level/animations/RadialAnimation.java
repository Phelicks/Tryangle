package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.effects.ColorFlash;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class RadialAnimation extends Animations{
	
	private boolean isActive = true;
	private int xPos;
	private int yPos;
	private float radius;
	private int iRadius = 50;
	
	private TriangleContainer triangles;
	
	public RadialAnimation(TriangleContainer triangles, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.radius = triangles.getLength()*5;
		
		this.triangles = triangles;
	}

	@Override
	protected void startAnimation() {			
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < iRadius; i++){
			float a = (float)Math.sin(i)*iRadius;
			float b = (float)Math.cos(i)*iRadius;			
			try {
				Triangle t = triangles.getExactTriangle((int)a+xPos, (int)b+yPos);
				float d = 0.10f - (0.04f - (0.04f/iRadius));
				t.addEffect(new ColorFlash(t.getColorR()+d, t.getColorG()+d, t.getColorB()+d, t, 40));
			} catch (Exception e) {
				
			}
		}
		if(iRadius <= radius)
			iRadius += ((float)radius)/50f;
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
