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
	//left
	private Triangle lT;
	private Triangle lB;
	//right
	private Triangle rT;
	private Triangle rB;
	
	private boolean isActive = true;
	
	public CubeAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		
		int x = triangles.getIndexFromPos(xPos, yPos).x;
		int y = triangles.getIndexFromPos(xPos, yPos).y;
				
		if(!TriangleContainer.isTriangleLeft(x, y)){
			//Top
			tL = triangles.getTriangle(x-1, y);
			tR = triangles.getTriangle(x, y);
			//Left
			lT = triangles.getTriangle(x-1, y+1);
			lB = triangles.getTriangle(x-1, y+2);
			//Right
			rT = triangles.getTriangle(x, y+1);
			rB = triangles.getTriangle(x, y+2);
		}else{
			//Top
			tL = triangles.getTriangle(x, y);
			tR = triangles.getTriangle(x+1, y);
			//Left
			lT = triangles.getTriangle(x, y+1);
			lB = triangles.getTriangle(x, y+2);
			//Right
			rT = triangles.getTriangle(x+1, y+1);
			rB = triangles.getTriangle(x+1, y+2);
		}
		
		tL.addTopLayerEffect(0, new ColorSet(colorR, colorG, colorB));
		tR.addTopLayerEffect(0, new ColorSet(colorR, colorG, colorB));
		
		lT.addTopLayerEffect(0, new ColorSet(colorR-0.1f, colorG-0.1f, colorB-0.1f));
		lB.addTopLayerEffect(0, new ColorSet(colorR-0.1f, colorG-0.1f, colorB-0.1f));
		
		rT.addTopLayerEffect(0, new ColorSet(colorR-0.2f, colorG-0.2f, colorB-0.2f));
		rB.addTopLayerEffect(0, new ColorSet(colorR-0.2f, colorG-0.2f, colorB-0.2f));
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
	
	public void remove(){
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
