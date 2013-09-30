package com.januskopf.tryangle.level.animations;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorSet;

public class CubeAnimation extends Animations{
	
	//color
	private float colorR;
	private float colorG;
	private float colorB;
	
	//top
	private Triangle tL;
	private Triangle tR;
	private ColorSet tLColor;
	private ColorSet tRColor;
	//left
	private Triangle lT;
	private Triangle lB;
	private ColorSet lTColor;
	private ColorSet lBColor;
	//right
	private Triangle rT;
	private Triangle rB;
	private ColorSet rTColor;
	private ColorSet rBColor;
	
	private boolean isActive = true;
	
	private TriangleContainer triangles;
	
	public CubeAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB){
		
		
		this.triangles = triangles;
		
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		
		int x = triangles.getIndexFromPos(xPos, yPos).x;
		int y = triangles.getIndexFromPos(xPos, yPos).y;
		
		this.setCube(x, y);
	}
	
	private void setCube(int x, int y){
		if(!TriangleContainer.isTriangleLeft(x, y)){
			//Top
			tL = triangles.getTriangle(x-1, y);
			tR = triangles.getTriangle(x, y);
			//Left
			lT = triangles.getTriangle(x, y+1);
			lB = triangles.getTriangle(x, y+2);
			//Right
			rT = triangles.getTriangle(x-1, y+1);
			rB = triangles.getTriangle(x-1, y+2);
		}else{
			//Top
			tL = triangles.getTriangle(x, y);
			tR = triangles.getTriangle(x+1, y);
			//Left
			lT = triangles.getTriangle(x+1, y+1);
			lB = triangles.getTriangle(x+1, y+2);
			//Right
			rT = triangles.getTriangle(x, y+1);
			rB = triangles.getTriangle(x, y+2);
		}
		
		//top
		tLColor = new ColorSet(colorR, colorG, colorB);
		tRColor = new ColorSet(colorR, colorG, colorB);
		//left
		lTColor = new ColorSet(colorR-0.1f, colorG-0.1f, colorB-0.1f);
		lBColor = new ColorSet(colorR-0.1f, colorG-0.1f, colorB-0.1f);
		//right
		rTColor = new ColorSet(colorR-0.2f, colorG-0.2f, colorB-0.2f);
		rBColor = new ColorSet(colorR-0.2f, colorG-0.2f, colorB-0.2f);
		
		if(tL != null)tL.addTopLayerEffect(0, tLColor);
		if(tR != null)tR.addTopLayerEffect(0, tRColor);
		
		if(lT != null)lT.addTopLayerEffect(0, lTColor);
		if(lB != null)lB.addTopLayerEffect(0, lBColor);
		
		if(rT != null)rT.addTopLayerEffect(0, rTColor);
		if(rB != null)rB.addTopLayerEffect(0, rBColor);
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
	
	public void moveTo(int xPos, int yPos){
		tLColor.remove();
		tRColor.remove();
		
		lTColor.remove();
		lBColor.remove();
		
		rTColor.remove();
		rBColor.remove();
		
		int x = triangles.getIndexFromPos(xPos, yPos).x;
		int y = triangles.getIndexFromPos(xPos, yPos).y;
		
		this.setCube(x, y);
	}
	
	public void remove(){
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}
}
