package com.januskopf.tryangle.triangles.effects;


public class ColorFlash extends BackgroundEffect{

	private float startColorR;
	private float startColorG;
	private float startColorB;
	
	private float stepColorR;
	private float stepColorG;
	private float stepColorB;
	
	private float setColorR;
	private float setColorG;
	private float setColorB;
	
	private float flashColorR;
	private float flashColorG;
	private float flashColorB;
	
	private int tickCount;
	private int ticks;
	
	public  ColorFlash(float flashColorR, float flashColorG, float flashColorB, int ticks){
		super();
		
		this.ticks = ticks;
		tickCount = ticks;
		
		this.flashColorR = flashColorR;
		this.flashColorG = flashColorG;
		this.flashColorB = flashColorB;
		
		this.startColorR = super.getBackgroundR();
		this.startColorG = super.getBackgroundG();
		this.startColorB = super.getBackgroundB();
		
		this.setColorR = super.getBackgroundR();
		this.setColorG = super.getBackgroundG();
		this.setColorB = super.getBackgroundB();
		
		this.stepColorR = (flashColorR - this.startColorR) / ticks*2;
		this.stepColorG = (flashColorG - this.startColorG) / ticks*2;
		this.stepColorB = (flashColorB - this.startColorB) / ticks*2;
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
	public void stop() {
		endEffect();
		ticks = 0;
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

	@Override
	protected void newBackgroundListener() {
		
		this.startColorR = super.getBackgroundR();
		this.startColorG = super.getBackgroundG();
		this.startColorB = super.getBackgroundB();
		
		this.setColorR = super.getBackgroundR();
		this.setColorG = super.getBackgroundG();
		this.setColorB = super.getBackgroundB();
		
		this.stepColorR = (flashColorR - this.startColorR) / ticks*2;
		this.stepColorG = (flashColorG - this.startColorG) / ticks*2;
		this.stepColorB = (flashColorB - this.startColorB) / ticks*2;
		
	}

}
