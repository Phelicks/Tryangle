package com.januskopf.tryangle.level.animations;

import java.util.Random;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorFlash;

public class FireAnimation extends Animations{
		
	private int xPos;
	private int yPos;
	private int startDuration;
	private int duration;
	private boolean isActive = true;
	
	private TriangleContainer triangles;
	private Random random;
	
	public FireAnimation(TriangleContainer triangles, int xPos, int yPos, int duration) {
		random = new Random();
		this.triangles = triangles;
		this.xPos = xPos;
		this.yPos = yPos;
		this.startDuration = duration;
		this.duration = duration;
		
	}

	@Override
	protected void startAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runAnimation() {
		float p = ((float)startDuration/(float)duration)/8f;
		try {
			Triangle t = triangles.getExactTriangle(xPos, yPos);
			t.addBottomLayerEffect(new ColorFlash(1.0f-p, Math.abs(random.nextFloat()-0.5f)-p, 0f, 50));
		} catch (Exception e) {}
		yPos -= random.nextInt(13);
		xPos += -20 + random.nextInt(41);
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
