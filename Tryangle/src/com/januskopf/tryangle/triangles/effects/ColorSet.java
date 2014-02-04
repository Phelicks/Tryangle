package com.januskopf.tryangle.triangles.effects;

public class ColorSet extends Effect{
	
	private float colorR;
	private float colorG;
	private float colorB;
	
	boolean isActive = true;
	
	public ColorSet(float r, float g, float b){
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
	}

	@Override
	protected void startEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endEffect() {
		// TODO Auto-generated method stub
		
	}
	
	public void remove(){
		isActive = false;		
	}

	@Override
	protected boolean isActive() {
		return isActive;
	}
	
	@Override
	public boolean isRunning() {
		return isActive;
	}

	@Override
	public float getColorR() {
		return colorR;
	}

	@Override
	public float getColorG() {
		return colorG;
	}

	@Override
	public float getColorB() {
		return colorB;
	}

}
