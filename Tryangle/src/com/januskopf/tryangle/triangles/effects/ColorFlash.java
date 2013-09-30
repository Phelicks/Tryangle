package com.januskopf.tryangle.triangles.effects;


public class ColorFlash extends Effect{
	
	private float stepColorR;
	private float stepColorG;
	private float stepColorB;
	
	private float setColorR;
	private float setColorG;
	private float setColorB;
	
	private int tickCount;
	private int ticks;
	
	public  ColorFlash(float flashColorR, float flashColorG, float flashColorB, int ticks){
		super();
		
		this.ticks = ticks;
		tickCount = ticks;
		
		this.stepColorR = flashColorR / ticks*2;
		this.stepColorG = flashColorG / ticks*2;
		this.stepColorB = flashColorB / ticks*2;
	}
	
	public ColorFlash(float brightness, int ticks) {
		this(brightness, brightness, brightness, ticks);		
	}
	
	public ColorFlash(int ticks) {
		this(1.0f, 1.0f, 1.0f, ticks);		
	}

	@Override
	protected void startEffect() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void runEffect() {

		if(ticks > tickCount/2){			
			this.setColorR += stepColorR;
			this.setColorG += stepColorG;
			this.setColorB += stepColorB;			
		}
		else{
			this.setColorR -= stepColorR;
			this.setColorG -= stepColorG;
			this.setColorB -= stepColorB;			
		}		
		ticks--;
	}

	@Override
	protected void endEffect() {
		
	}

	@Override
	protected boolean isActive() {
		if(ticks <= 0)
			return false;
		else
			return true;
	}

	@Override
	public float getColorR() {
		return setColorR;
	}

	@Override
	public float getColorG() {
		return setColorG;
	}

	@Override
	public float getColorB() {
		return setColorB;
	}
}
