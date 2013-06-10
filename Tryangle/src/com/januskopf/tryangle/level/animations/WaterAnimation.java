package com.januskopf.tryangle.level.animations;

import org.lwjgl.opengl.GL11;

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
	
	public WaterAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB, int radius, int iRadius){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.xPos = xPos;
		this.yPos = yPos;
		this.triangles = triangles;
		this.iRadius = iRadius;
		this.radius = radius;
	}
	
	public WaterAnimation(TriangleContainer triangles, int xPos, int yPos) {
		this(triangles, xPos, yPos, 1.0f, 1.0f, 1.0f, 300, 50);
		
	}

	@Override
	protected void startAnimation() {			
	}

	@Override
	protected void runAnimation() {
		for(int i = 0; i < 360; i++){
			float a = (float)Math.sin(i*2)*iRadius;
			float b = (float)Math.cos(i*2)*iRadius;	
			
			GL11.glColor3f(0.5f, 0.5f, 0.6f);
			GL11.glPointSize(1.5f);
			GL11.glBegin(GL11.GL_POINTS);
				GL11.glVertex2i((int)a+xPos, (int)b+yPos);
			GL11.glEnd();
			
			try {
				Triangle t = triangles.getExactTriangle((int)a+xPos, (int)b+yPos);
				float d = 0.10f - (0.04f - (0.04f/iRadius));
				t.addEffect(new ColorFlash(colorR+d, colorG+d, colorB+d, t, 40));
			} catch (Exception e) {
				
			}
		}
		if(iRadius <= radius)
			iRadius += 5;
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
