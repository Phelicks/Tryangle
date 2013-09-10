package com.januskopf.tryangle.triangles.effects;


public class ColorTransition extends BackgroundEffect{

	private float startColorR;
	private float startColorG;
	private float startColorB;

	private float curColorR;
	private float curColorG;
	private float curColorB;
	
	private float stepColorR;
	private float stepColorG;
	private float stepColorB;
	
	private float newColorR;
	private float newColorG;
	private float newColorB;

	private int ticks;
	
	
	public ColorTransition(float newColorR, float newColorG, float newColorB, int ticks) {
		super();
		
		this.ticks = ticks;
		
		this.startColorR = super.getBackgroundR();
		this.startColorG = super.getBackgroundG();
		this.startColorB = super.getBackgroundB();
		
		this.curColorR = startColorR;
		this.curColorG = startColorG;
		this.curColorB = startColorB;
		
		this.stepColorR = (newColorR - this.startColorR) / ticks;
		this.stepColorG = (newColorG - this.startColorG) / ticks;
		this.stepColorB = (newColorB - this.startColorB) / ticks;

		this.newColorR = newColorR;
		this.newColorG = newColorG;
		this.newColorB = newColorB;
	}

	@Override
	protected void startEffect() {
	}

	@Override
	protected void runEffect() {
		this.curColorR += stepColorR;
		this.curColorG += stepColorG;
		this.curColorB += stepColorB;
		ticks--;
	}

	@Override
	protected void endEffect() {
		this.curColorR  = newColorR;
		this.curColorG  = newColorG;
		this.curColorB  = newColorB;
	}

	@Override
	protected void newBackgroundListener() {

		this.startColorR = super.getBackgroundR();
		this.startColorG = super.getBackgroundG();
		this.startColorB = super.getBackgroundB();

		this.curColorR = startColorR;
		this.curColorG = startColorG;
		this.curColorB = startColorB;
		
		this.stepColorR = (newColorR - this.startColorR) / ticks;
		this.stepColorG = (newColorG - this.startColorG) / ticks;
		this.stepColorB = (newColorB - this.startColorB) / ticks;
	}

	@Override
	protected boolean isActive() {
		if(ticks <= 0)
			return false;
		else
			return true;
	}

	@Override
	public void stop() {
		endEffect();
		ticks = 0;
	}

	@Override
	public float getColorR() {
		return this.curColorR;
	}

	@Override
	public float getColorG() {
		return this.curColorG;
	}

	@Override
	public float getColorB() {
		return this.curColorB;
	}

}
