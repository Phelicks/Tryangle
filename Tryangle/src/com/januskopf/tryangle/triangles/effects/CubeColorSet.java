package com.januskopf.tryangle.triangles.effects;

import com.januskopf.tryangle.triangles.Triangle;

public class CubeColorSet extends Effect{

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
	
	private int ticks = 18;
	private int tickCount;
	
	private byte side;
	
	boolean isActive = true;
	private boolean remove;
	
	public static final byte TOP_LEFT     = 0;
	public static final byte TOP_RIGHT    = 1;
	public static final byte RIGHT_TOP    = 2;
	public static final byte RIGHT_BOTTOM = 3;
	public static final byte LEFT_BOTTOM  = 4;
	public static final byte LEFT_TOP 	  = 5;
	
	public CubeColorSet(Triangle triangle, float r, float g, float b, byte side){
		float br = 0;
		if(side == 2 || side == 3) br = 0.2f;
		if(side == 4 || side == 5) br = 0.1f;
		this.newColorR = r - br;
		this.newColorG = g - br;
		this.newColorB = b - br;
		this.side = side;
		
		this.startColorR = triangle.getColorR();
		this.startColorG = triangle.getColorG();
		this.startColorB = triangle.getColorB();
		
		this.curColorR = startColorR;
		this.curColorG = startColorG;
		this.curColorB = startColorB;
		
		this.stepColorR = (newColorR - this.startColorR) / ticks;
		this.stepColorG = (newColorG - this.startColorG) / ticks;
		this.stepColorB = (newColorB - this.startColorB) / ticks;
		
		tickCount = ticks;
	}

	@Override
	protected void startEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runEffect() {
		if(ticks > 0 && !remove){			
			this.curColorR += stepColorR;
			this.curColorG += stepColorG;
			this.curColorB += stepColorB;
			ticks--;
			
		}
		else if(remove && ticks <= tickCount){	
			this.curColorR -= stepColorR;
			this.curColorG -= stepColorG;
			this.curColorB -= stepColorB;
			ticks++;
		}
		else if(remove){
			isActive = false;
		}
	}

	@Override
	protected void endEffect() {
		
	}
	
	public void remove(){
		remove = true;
	}

	@Override
	protected boolean isActive() {
		return !remove;//isActive;
	}

	@Override
	public float getColorR() {
		return curColorR;
	}

	@Override
	public float getColorG() {
		return curColorG;
	}

	@Override
	public float getColorB() {
		return curColorB;
	}
	
	public byte getSide() {
		return side;
	}

}
