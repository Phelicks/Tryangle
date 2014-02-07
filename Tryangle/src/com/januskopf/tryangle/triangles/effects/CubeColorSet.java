package com.januskopf.tryangle.triangles.effects;

public class CubeColorSet extends Effect{
	
	private float colorR;
	private float colorG;
	private float colorB;
	private byte side;
	
	boolean isActive = true;
	
	public static final byte TOP_LEFT     = 0;
	public static final byte TOP_RIGHT    = 1;
	public static final byte RIGHT_TOP    = 2;
	public static final byte RIGHT_BOTTOM = 3;
	public static final byte LEFT_BOTTOM  = 4;
	public static final byte LEFT_TOP 	  = 5;
	
	public CubeColorSet(float r, float g, float b, byte side){
		float br = 0;
		if(side == 2 || side == 3) br = 0.2f;
		if(side == 4 || side == 5) br = 0.1f;
		this.colorR = r - br;
		this.colorG = g - br;
		this.colorB = b - br;
		this.side = side;
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
	
	public byte getSide() {
		return side;
	}

}
