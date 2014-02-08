package com.januskopf.tryangle.triangles.animations;

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
	private float[] tLVertices;
	//left
	private Triangle lT;
	private Triangle lB;	
	private float[] lBVertices;
	//right
	private Triangle rT;
	private Triangle rB;	
	private float[] rTVertices;
	
	private boolean isActive = true;
	
	private TriangleContainer triangles;
	private boolean topLeft;
	private boolean topRight;
	private boolean leftTop;
	private boolean leftBottom;
	private boolean rightBottom;
	private boolean rightTop;
	private boolean centerBottom;
	private boolean centerLeft;
	private boolean centerRight;
	
	public MouseCubeAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB){		
		this.triangles = triangles;
		
		this.colorR = colorR+0.1f;
		this.colorG = colorG+0.1f;
		this.colorB = colorB+0.1f;
		
		this.x = triangles.getIndexFromPos(xPos, yPos).x;
		this.y = triangles.getIndexFromPos(xPos, yPos).y;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		this.setCube(x, y);
	}
	
	private void setCube(int x, int y){
		if(!TriangleContainer.isTriangleLeft(x, y)){
			//Top
			tL = triangles.getTriangle(x-1, y);
			tR = triangles.getTriangle(x, y);
			tLVertices = triangles.getTriangleVertices(x-1, y);
			//Left
			lT = triangles.getTriangle(x-1, y+1);
			lB = triangles.getTriangle(x-1, y+2);
			lBVertices = triangles.getTriangleVertices(x-1, y+2);
			//Right
			rT = triangles.getTriangle(x, y+1);
			rB = triangles.getTriangle(x, y+2);
			rTVertices = triangles.getTriangleVertices(x, y+1);
		}else{
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
		}
		
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_B)){
			topLeft		= tL!=null && tL.getCubeSide() == -1;
			topRight	= tR!=null && tR.getCubeSide() == -1;
			rightTop	= rT!=null && rT.getCubeSide() == -1;
			rightBottom	= rB!=null && rB.getCubeSide() == -1;
			leftTop		= lT!=null && lT.getCubeSide() == -1;
			leftBottom	= lB!=null && lB.getCubeSide() == -1;
			
			centerBottom  	= (leftBottom || rightBottom);
			centerLeft  	= (leftTop    || topLeft);
			centerRight 	= (rightTop   || topRight);	
		}else{
			if(tL != null && tR != null) {
				boolean top = (tL.getCubeSide() != CubeColorSet.LEFT_BOTTOM) || (tR.getCubeSide() != CubeColorSet.RIGHT_BOTTOM);	
				topLeft  = top;
				topRight = top;				
			}
			if(lT != null && lB != null){
				boolean left  = (lT.getCubeSide() != CubeColorSet.TOP_RIGHT)   || (lB.getCubeSide() != CubeColorSet.RIGHT_TOP);
				leftTop    = left;
				leftBottom = left;
			}
			if(rT != null && rB != null){
				boolean right = (rT.getCubeSide() != CubeColorSet.TOP_LEFT)    || (rB.getCubeSide() != CubeColorSet.LEFT_TOP);
				rightTop    = right;
				rightBottom = right;
			}
			
			centerBottom   = true;
			centerLeft  = true;
			centerRight = true;			
		}		
	}
	
	public void render() {
		
		if(topLeft){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(tLVertices[0], tLVertices[1]);
				GL11.glVertex2f(tLVertices[4], tLVertices[5]);	
			GL11.glEnd();
		}
		
		if(topRight){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(rTVertices[0], rTVertices[1]);
				GL11.glVertex2f(tLVertices[0], tLVertices[1]);	
			GL11.glEnd();
		}
		
		if(leftTop){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(tLVertices[4], tLVertices[5]);
				GL11.glVertex2f(lBVertices[4], lBVertices[5]);	
			GL11.glEnd();
		}
		
		if(leftBottom){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(lBVertices[4], lBVertices[5]);
				GL11.glVertex2f(lBVertices[2], lBVertices[3]);	
			GL11.glEnd();
		}
		
		if(rightBottom){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(lBVertices[2], lBVertices[3]);
				GL11.glVertex2f(rTVertices[2], rTVertices[3]);	
			GL11.glEnd();
		}
		
		if(rightTop){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(rTVertices[2], rTVertices[3]);
				GL11.glVertex2f(rTVertices[0], rTVertices[1]);	
			GL11.glEnd();
		}
		
		//Lines to center
		if(centerLeft){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(tLVertices[4], tLVertices[5]);
				GL11.glVertex2f(tLVertices[2], tLVertices[3]);	
			GL11.glEnd();
		}
		
		if(centerBottom){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(lBVertices[2], lBVertices[3]);
				GL11.glVertex2f(tLVertices[2], tLVertices[3]);	
			GL11.glEnd();
		}
		
		if(centerRight){
			GL11.glColor3f(colorR, colorG, colorB);
			GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2f(rTVertices[0], rTVertices[1]);
				GL11.glVertex2f(tLVertices[2], tLVertices[3]);	
			GL11.glEnd();
		}
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
	}
	
	public void moveTo(int xPos, int yPos){		
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
