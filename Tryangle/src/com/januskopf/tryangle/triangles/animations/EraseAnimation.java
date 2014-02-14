package com.januskopf.tryangle.triangles.animations;

import java.util.Random;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorFlash;

public class EraseAnimation extends Animations{
		
	private int xPos;
	private int yPos;
	
	private int xPos2;
	private int yPos2;
	
	private int xPos3;
	private int yPos3;
	
	private int xPos4;
	private int yPos4;
	
	private int startDuration;
	private int duration;
	private boolean isActive = true;
	
	float cR;
	float cG;
	float cB;
	
	int ticks;
	int randY;
	int randX;
	int randDif;
	
	private TriangleContainer triangles;
	private Random random;
	private Triangle lastTriangle;
	private Triangle lastTriangle2;
	private Triangle lastTriangle3;
	private Triangle lastTriangle4;
	private float tLength;
	private float tHeight;
	
	public EraseAnimation(TriangleContainer triangles, int xPos, int yPos, float cR, float cG, float cB, int duration) {
		random = new Random();
		this.triangles = triangles;
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.xPos2 = xPos;
		this.yPos2 = yPos+10;
		
		this.xPos3 = xPos;
		this.yPos3 = yPos;
		
		this.xPos4 = xPos;
		this.yPos4 = yPos+10;
		
		this.startDuration = duration;
		this.duration = duration;
		
		this.cR = cR;
		this.cG = cG;
		this.cB = cB;

		this.tLength = triangles.getLength();
		this.tHeight = triangles.getHeight();
		
		this.ticks = 7;
		this.randY = (int)tLength/7;
		this.randX = (int)tHeight/5;
		this.randDif = 2;		
	}

	@Override
	protected void startAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runAnimation() {

		tLength = triangles.getLength();
		tHeight = triangles.getHeight();
				
		float p = 0.0f;
		
		Triangle t = triangles.getExactTriangle(xPos, yPos);
		if(t != null && t != lastTriangle)t.addBottomLayerEffect(new ColorFlash(cR-p, cG-p, cB-p, ticks));
		lastTriangle = t;
		
		Triangle t2 = triangles.getExactTriangle(xPos2, yPos2);
		if(t2 != null && t2 != lastTriangle2)t2.addBottomLayerEffect(new ColorFlash(cR-p, cG-p, cB-p, ticks));
		lastTriangle2 = t2;
		
		Triangle t3 = triangles.getExactTriangle(xPos3, yPos3);
		if(t3 != null && t3 != lastTriangle3)t3.addBottomLayerEffect(new ColorFlash(cR-p, cG-p, cB-p, ticks));
		lastTriangle3 = t3;
		
		Triangle t4 = triangles.getExactTriangle(xPos4, yPos4);
		if(t4 != null && t4 != lastTriangle4)t4.addBottomLayerEffect(new ColorFlash(cR-p, cG-p, cB-p, ticks));
		lastTriangle4 = t4;
		
		yPos -= tLength/30 +random.nextInt(randY);
		xPos += tHeight/25 +random.nextInt(randX);
		
		yPos2 += tLength/10 + random.nextInt(randY);
		xPos2 += tHeight/25 + random.nextInt(randX);
		
		yPos3 -= tLength/30 + random.nextInt(randY);
		xPos3 -= tHeight/25 +random.nextInt(randX);
		
		yPos4 += tLength/10 +random.nextInt(randY);
		xPos4 -= tHeight/25 +random.nextInt(randX);
		
		duration--;
		if(duration < 1 || yPos < 0)
			isActive = false;
	}

	@Override
	protected void endAnimation() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
