package com.januskopf.tryangle.triangles.animations;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorSet;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;

public class TriangleAnimation extends Animations{
	
	//color
	private float colorR;
	private float colorG;
	private float colorB;
	
	//coordinates
	private int x;
	private int y;
	
	//top
	private Triangle triangle;
	private ColorSet triangleColor;
	
	
	private boolean isActive = true;
	
	private TriangleContainer triangles;
	
	public TriangleAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB){		
		this.triangles = triangles;
		
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		
		this.x = triangles.getIndexFromPos(xPos, yPos).x;
		this.y = triangles.getIndexFromPos(xPos, yPos).y;
		this.setTriangle(xPos, yPos);
//		System.out.println("Triangle Werte: x: " + x + "y: " + y);
	}
	
	private void setTriangle(int xPos, int yPos){
//		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;			

		triangle = triangles.getExactTriangle(xPos, yPos);

		if(triangle != null) triangleColor = new ColorSet(colorR, colorG, colorB);
		
		if(triangle != null)triangle.addTopLayerEffect(triangleColor, false);
			
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
		triangleColor.remove();
		
		
		if(triangle!=null)triangle.updateEffects();
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		y = triangles.getIndexFromPos(xPos, yPos).y;
		this.setTriangle(xPos, yPos);
	}
	
	
	public void changeColor(float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
	}
	
	
	public void remove(){
		isActive = false;
		triangleColor.remove();
		
		if(triangle!=null)triangle.updateEffects();
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
