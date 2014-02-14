package com.januskopf.tryangle.triangles.animations;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;

public class CubeAnimation extends Animations{
	
	//color
	private float colorR;
	private float colorG;
	private float colorB;
	
	//coordinates
	private int x;
	private int y;
	
	//top
	private Triangle tL;
	private Triangle tR;
	private CubeColorSet tLColor;
	private CubeColorSet tRColor;
	//left
	private Triangle lT;
	private Triangle lB;
	private CubeColorSet lTColor;
	private CubeColorSet lBColor;
	//right
	private Triangle rT;
	private Triangle rB;
	private CubeColorSet rTColor;
	private CubeColorSet rBColor;
	
	private boolean isActive = true;
	
	private TriangleContainer triangles;

	public CubeAnimation(TriangleContainer triangles, int x, int y, float colorR, float colorG, float colorB, boolean setBehinde){		
		this.triangles = triangles;
		
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;

		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		this.x = x;
		this.y = y;
		this.setCube(x, y, setBehinde);
	}
	
	private void setCube(int x, int y, boolean setBehinde){
		//Top
		tL = triangles.getTriangle(x, y);
		tR = triangles.getTriangle(x+1, y);
		//Left
		lT = triangles.getTriangle(x, y+1);
		lB = triangles.getTriangle(x, y+2);
		//Right
		rT = triangles.getTriangle(x+1, y+1);
		rB = triangles.getTriangle(x+1, y+2);
		
		//top
		if(tL != null) tLColor = new CubeColorSet(tL, colorR, colorG, colorB, CubeColorSet.TOP_LEFT);
		if(tR != null) tRColor = new CubeColorSet(tR, colorR, colorG, colorB, CubeColorSet.TOP_RIGHT);
		//left
		if(lT != null) lTColor = new CubeColorSet(lT, colorR, colorG, colorB, CubeColorSet.LEFT_TOP);
		if(lB != null) lBColor = new CubeColorSet(lB, colorR, colorG, colorB, CubeColorSet.LEFT_BOTTOM);
		//right
		if(rT != null) rTColor = new CubeColorSet(rT, colorR, colorG, colorB, CubeColorSet.RIGHT_TOP);
		if(rB != null) rBColor = new CubeColorSet(rB, colorR, colorG, colorB, CubeColorSet.RIGHT_BOTTOM);
		
		if((tL != null && tL.getCubeSide() == CubeColorSet.LEFT_BOTTOM) && (tR != null && tR.getCubeSide() == CubeColorSet.RIGHT_BOTTOM) 
				|| setBehinde){
			if(tL != null)tL.addTopLayerEffect(tLColor, true);
			if(tR != null)tR.addTopLayerEffect(tRColor, true);			
		}
		else{
			if(tL != null)tL.addTopLayerEffect(tLColor, false);
			if(tR != null)tR.addTopLayerEffect(tRColor, false);			
		}
		
		if((lT != null && lT.getCubeSide() == CubeColorSet.TOP_RIGHT) && (lB != null && lB.getCubeSide() == CubeColorSet.RIGHT_TOP) 
				|| setBehinde){
			if(lT != null)lT.addTopLayerEffect(lTColor, true);
			if(lB != null)lB.addTopLayerEffect(lBColor, true);
		}else{			
			if(lT != null)lT.addTopLayerEffect(lTColor, false);
			if(lB != null)lB.addTopLayerEffect(lBColor, false);
		}

		if((rT != null && rT.getCubeSide() == CubeColorSet.TOP_LEFT) && (rB != null && rB.getCubeSide() == CubeColorSet.LEFT_TOP) 
				|| setBehinde){
			if(rT != null)rT.addTopLayerEffect(rTColor, true);
			if(rB != null)rB.addTopLayerEffect(rBColor, true);			
		}else{			
			if(rT != null)rT.addTopLayerEffect(rTColor, false);
			if(rB != null)rB.addTopLayerEffect(rBColor, false);
		}
	}
	
	@Override
	protected void startAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endAnimation() {
		// TODO Auto-generated method stub
		
	}
	
	public void changeColor(float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
	}
	
	
	public void remove(){
		isActive = false;
		if(tLColor!=null)tLColor.remove();
		if(tRColor!=null)tRColor.remove();		
		if(lTColor!=null)lTColor.remove();
		if(lBColor!=null)lBColor.remove();		
		if(rTColor!=null)rTColor.remove();
		if(rBColor!=null)rBColor.remove();
		
		if(tL!=null)tL.updateEffects();
		if(tR!=null)tR.updateEffects();
		if(lT!=null)lT.updateEffects();
		if(lB!=null)lB.updateEffects();
		if(rT!=null)rT.updateEffects();
		if(rB!=null)rB.updateEffects();
	}
	

	@Override
	public boolean isActive() {
		return isActive;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public float getColorR(){
		return colorR;
	}
	
	public float getColorG(){
		return colorG;
	}
	
	public float getColorB(){
		return colorB;
	}
}
