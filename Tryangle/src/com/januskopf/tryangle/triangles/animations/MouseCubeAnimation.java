package com.januskopf.tryangle.triangles.animations;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;

public class MouseCubeAnimation extends Animations{
	
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
	
	private float[] tLVertices;
	//left
	private Triangle lT;
	private Triangle lB;
	private CubeColorSet lTColor;
	private CubeColorSet lBColor;
	
	private float[] lBVertices;
	//right
	private Triangle rT;
	private Triangle rB;
	private CubeColorSet rTColor;
	private CubeColorSet rBColor;
	
	private float[] rTVertices;
	
	private boolean isActive = true;
	
	private TriangleContainer triangles;
	
	public MouseCubeAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB){		
		this.triangles = triangles;
		
		this.colorR = colorR+0.1f;
		this.colorG = colorG+0.1f;
		this.colorB = colorB+0.1f;
		
		this.x = triangles.getIndexFromPos(xPos, yPos).x;
		this.y = triangles.getIndexFromPos(xPos, yPos).y;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		this.setCube(x, y);
//		System.out.println("Cube Werte: x: " + x + "y: " + y);
	}
	
	private void setCube(int x, int y){		
		//Top
		tL = triangles.getTriangle(x, y);
		tR = triangles.getTriangle(x+1, y);
		tLVertices = triangles.getTriangleVertices(x, y);
		//Left
		lT = triangles.getTriangle(x, y+1);
		lB = triangles.getTriangle(x, y+2);
		lBVertices = triangles.getTriangleVertices(x, y+2);
		//Right
		rT = triangles.getTriangle(x+1, y+1);
		rB = triangles.getTriangle(x+1, y+2);
		rTVertices = triangles.getTriangleVertices(x+1, y+1);
		
//		//top
//		tLColor = new CubeColorSet(colorR, colorG, colorB, CubeColorSet.TOP_LEFT);
//		tRColor = new CubeColorSet(colorR, colorG, colorB, CubeColorSet.TOP_RIGHT);
//		//left
//		lTColor = new CubeColorSet(colorR, colorG, colorB, CubeColorSet.LEFT_TOP);
//		lBColor = new CubeColorSet(colorR, colorG, colorB, CubeColorSet.LEFT_BOTTOM);
//		//right
//		rTColor = new CubeColorSet(colorR, colorG, colorB, CubeColorSet.RIGHT_TOP);
//		rBColor = new CubeColorSet(colorR, colorG, colorB, CubeColorSet.RIGHT_BOTTOM);
		
		
		
//		//if cube beneath
//		if((tL != null && tL.getCubeSide() == CubeColorSet.LEFT_BOTTOM) && (tR != null && tR.getCubeSide() == CubeColorSet.RIGHT_BOTTOM) 
//				|| (KeyboardListener.isKeyPressed(Keyboard.KEY_B))){
//			if(tL != null)tL.addTopLayerEffect(tLColor, true);
//			if(tR != null)tR.addTopLayerEffect(tRColor, true);			
//		}
//		else{
//			if(tL != null)tL.addTopLayerEffect(tLColor, false);
//			if(tR != null)tR.addTopLayerEffect(tRColor, false);			
//		}
//		
//		//if cube top right beneath
//		if((lT != null && lT.getCubeSide() == CubeColorSet.TOP_RIGHT) && (lB != null && lB.getCubeSide() == CubeColorSet.RIGHT_TOP) 
//				|| (KeyboardListener.isKeyPressed(Keyboard.KEY_B))){
//			if(lT != null)lT.addTopLayerEffect(lTColor, true);
//			if(lB != null)lB.addTopLayerEffect(lBColor, true);
//		}else{			
//			if(lT != null)lT.addTopLayerEffect(lTColor, false);
//			if(lB != null)lB.addTopLayerEffect(lBColor, false);
//		}
//		
//		//if cube top left behind
//		if((rT != null && rT.getCubeSide() == CubeColorSet.TOP_LEFT) && (rB != null && rB.getCubeSide() == CubeColorSet.LEFT_TOP) 
//				|| (KeyboardListener.isKeyPressed(Keyboard.KEY_B))){
//			if(rT != null)rT.addTopLayerEffect(rTColor, true);
//			if(rB != null)rB.addTopLayerEffect(rBColor, true);			
//		}else{			
//			if(rT != null)rT.addTopLayerEffect(rTColor, false);
//			if(rB != null)rB.addTopLayerEffect(rBColor, false);
//		}
	}
	
	@Override
	protected void startAnimation() {
		runAnimation();
		
	}

	@Override
	protected void runAnimation() {
		
		
	}

	@Override
	protected void endAnimation() {
		// TODO Auto-generated method stub
		
	}
	
	public void render(){
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(tLVertices[0], tLVertices[1]);
			GL11.glVertex2f(tLVertices[4], tLVertices[5]);	
		GL11.glEnd();
		
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(tLVertices[4], tLVertices[5]);
			GL11.glVertex2f(lBVertices[4], lBVertices[5]);	
		GL11.glEnd();
		
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(lBVertices[4], lBVertices[5]);
			GL11.glVertex2f(lBVertices[2], lBVertices[3]);	
		GL11.glEnd();
		
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(lBVertices[2], lBVertices[3]);
			GL11.glVertex2f(rTVertices[2], rTVertices[3]);	
		GL11.glEnd();
		
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(rTVertices[2], rTVertices[3]);
			GL11.glVertex2f(rTVertices[0], rTVertices[1]);	
		GL11.glEnd();
		
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(rTVertices[0], rTVertices[1]);
			GL11.glVertex2f(tLVertices[0], tLVertices[1]);	
		GL11.glEnd();
		
		//Lines to center
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(tLVertices[4], tLVertices[5]);
			GL11.glVertex2f(tLVertices[2], tLVertices[3]);	
		GL11.glEnd();
		
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(lBVertices[2], lBVertices[3]);
			GL11.glVertex2f(tLVertices[2], tLVertices[3]);	
		GL11.glEnd();
		
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(rTVertices[0], rTVertices[1]);
			GL11.glVertex2f(tLVertices[2], tLVertices[3]);	
		GL11.glEnd();
	}
	
	public void moveTo(int xPos, int yPos){
//		tLColor.remove();
//		tRColor.remove();		
//		lTColor.remove();
//		lBColor.remove();		
//		rTColor.remove();
//		rBColor.remove();
//		
//		if(tL!=null)tL.updateEffects();
//		if(tR!=null)tR.updateEffects();
//		if(lT!=null)lT.updateEffects();
//		if(lB!=null)lB.updateEffects();
//		if(rT!=null)rT.updateEffects();
//		if(rB!=null)rB.updateEffects();
		
		int x = triangles.getIndexFromPos(xPos, yPos).x;
		int y = triangles.getIndexFromPos(xPos, yPos).y;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		this.setCube(x, y);
	}
	
	
	public void changeColor(float colorR, float colorG, float colorB){
		this.colorR = colorR+0.1f;
		this.colorG = colorG+0.1f;
		this.colorB = colorB+0.1f;
	}
	
	
	public void remove(){
		isActive = false;
//		tLColor.remove();
//		tRColor.remove();		
//		lTColor.remove();
//		lBColor.remove();		
//		rTColor.remove();
//		rBColor.remove();
//		
//		if(tL!=null)tL.updateEffects();
//		if(tR!=null)tR.updateEffects();
//		if(lT!=null)lT.updateEffects();
//		if(lB!=null)lB.updateEffects();
//		if(rT!=null)rT.updateEffects();
//		if(rB!=null)rB.updateEffects();
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
