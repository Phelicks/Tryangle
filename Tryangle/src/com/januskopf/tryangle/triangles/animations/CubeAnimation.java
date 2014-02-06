package com.januskopf.tryangle.triangles.animations;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorSet;

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
		
		this.x = triangles.getIndexFromPos(xPos, yPos).x;
		this.y = triangles.getIndexFromPos(xPos, yPos).y;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		this.setCube(x, y);
//		System.out.println("x: " + x + "y: " + y);
	}
	
	private void setCube(int x, int y){
		if(!TriangleContainer.isTriangleLeft(x, y)){
//			System.out.println("is right");
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
//			System.out.println("is left");
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
		
		//top
		tLColor = new ColorSet(colorR, colorG, colorB);
		tRColor = new ColorSet(colorR, colorG, colorB);
		//left
		lTColor = new ColorSet(colorR-0.1f, colorG-0.1f, colorB-0.1f);
		lBColor = new ColorSet(colorR-0.1f, colorG-0.1f, colorB-0.1f);
		//right
		rTColor = new ColorSet(colorR-0.2f, colorG-0.2f, colorB-0.2f);
		rBColor = new ColorSet(colorR-0.2f, colorG-0.2f, colorB-0.2f);
		
		if((tL != null && tL.getColorR() == colorR-0.1f) && (tR != null && tR.getColorR() == colorR-0.2f) || (KeyboardListener.isKeyPressed(Keyboard.KEY_B))){
			if(tL != null)tL.addTopLayerEffect(tLColor, true);
			if(tR != null)tR.addTopLayerEffect(tRColor, true);			
		}
		else{
			if(tL != null)tL.addTopLayerEffect(tLColor, false);
			if(tR != null)tR.addTopLayerEffect(tRColor, false);			
		}
		
		if((lT != null && lT.getColorR() == colorR) && (lB != null && lB.getColorR() == colorR-0.2f) || (KeyboardListener.isKeyPressed(Keyboard.KEY_B))){
			if(lT != null)lT.addTopLayerEffect(lTColor, true);
			if(lB != null)lB.addTopLayerEffect(lBColor, true);
		}else{			
			if(lT != null)lT.addTopLayerEffect(lTColor, false);
			if(lB != null)lB.addTopLayerEffect(lBColor, false);
		}

		if((rT != null && rT.getColorR() == colorR) && (rB != null && rB.getColorR() == colorR-0.1f) || (KeyboardListener.isKeyPressed(Keyboard.KEY_B))){
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
	
	public void moveTo(int xPos, int yPos){
		tLColor.remove();
		tRColor.remove();		
		lTColor.remove();
		lBColor.remove();		
		rTColor.remove();
		rBColor.remove();
		
		if(tL!=null)tL.updateEffects();
		if(tR!=null)tR.updateEffects();
		if(lT!=null)lT.updateEffects();
		if(lB!=null)lB.updateEffects();
		if(rT!=null)rT.updateEffects();
		if(rB!=null)rB.updateEffects();
		
		int x = triangles.getIndexFromPos(xPos, yPos).x;
		int y = triangles.getIndexFromPos(xPos, yPos).y;		
		this.setCube(x, y);
	}
	
	
	public void changeColor(float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
	}
	
	
	public void remove(){
		isActive = false;
		tLColor.remove();
		tRColor.remove();		
		lTColor.remove();
		lBColor.remove();		
		rTColor.remove();
		rBColor.remove();
		
		if(tL!=null)tL.updateEffects();
		if(tR!=null)tR.updateEffects();
		if(lT!=null)lT.updateEffects();
		if(lB!=null)lB.updateEffects();
		if(rT!=null)rT.updateEffects();
		if(rB!=null)rB.updateEffects();
	}
	
	public boolean delete(int xPos, int yPos){
		int x = triangles.getIndexFromPos(xPos, yPos).x;
		int y = triangles.getIndexFromPos(xPos, yPos).y;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		if(this.x == x && this.y == y){
			remove();
			return true;
		}
		return false;
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
}
