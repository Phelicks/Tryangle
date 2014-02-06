package com.januskopf.tryangle.triangles;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.triangles.animations.Animations;
import com.januskopf.tryangle.triangles.animations.SwipeAnimation;


public class TriangleContainer implements Serializable{
	
	private static final long serialVersionUID = -5993312098303853315L;
		
	private float xView = 0;
	private float yView = 0;
	private float length = 0;
	private float height = 0;
	
	private ArrayList<Animations> animations = new ArrayList<Animations>();
	private Triangle[][] triangles;
	
	public TriangleContainer(int xTriCount, int yTriCount){
		this.height = ((float)Math.sqrt(3)*(length/2));
		fillTriangleArray(xTriCount, yTriCount);
		this.resizeTriangles(0, 0, Display.getWidth(), Display.getHeight());
		this.checkBorder(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	//////////////////////////////
	//							//
	//			tick()			//
	//							//
	//////////////////////////////
	
	public void tick(){		
		if(Tryangle.isResized()){
			this.resizeTriangles(0, 0, Display.getWidth(), Display.getHeight());
		}
		
		this.runAnimations();
		
		for(int j = 0; j < triangles.length; j++){
			for(int i = 0; i < triangles[j].length; i++){
				triangles[j][i].tick();
			}
		}
	}
	
	//////////////////////////////
	//							//
	//			render()		//
	//							//
	//////////////////////////////
	
	public void render(){
		for(int y = 0; y < triangles.length; y++){
			for(int x = 0; x < triangles[y].length; x++){
				renderTriangle(x, y, triangles[y][x]);
			}
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void fillTriangleArray(int xNumber, int yNumber){
		triangles = new Triangle[yNumber][xNumber];
		
		//Triangle creation
		for(int y = 0; y < yNumber; y ++){
			for(int x = 0; x < xNumber; x ++){
				triangles[y][x] = new Triangle();
			}
		}
	}
	
	private void renderTriangle(int x, int y, Triangle t){
		float xPos = xView + x*height;
		float yPos = yView + y*length/2;
		float tHeight = height;
		if(isTriangleLeft(x, y)){
			tHeight = tHeight * -1;
			xPos = xPos + height;
		}
		
		GL11.glColor3f(t.getColorR(), t.getColorG(), t.getColorB());
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(xPos , yPos);//Top
			GL11.glVertex2f(xPos, yPos+length);//Bottom
			GL11.glVertex2f(xPos + tHeight, yPos+length/2);//Left/Right
		GL11.glEnd();
	}
	
	private void resizeTriangles(float x1, float y1, float x2, float y2){		
		if(triangles[0].length*height < x2-x1)
			this.setHeight((x2-x1)/triangles[0].length);	
		if(triangles.length*(length/2)-(length/2) < y2-y1)
			this.setLength((2*(y2-y1))/(triangles.length-1));
	}
	
	private void checkBorder(float x1, float y1, float x2, float y2){
		if(xView > x1)
			xView = x1;
		if(yView > y1-length/2)
			yView = y1-length/2;
		if(xView + triangles[0].length*height < x2)
			xView = x2 -(triangles[0].length*height);
		if(yView + triangles.length*(length/2) < y2)
			yView = y2 -(triangles.length*(length/2));
	}
	
	public static boolean isTriangleLeft(int x, int y){
		return ((x%2==0 && y%2==0) || (x%2!=0 && y%2!=0));
	}

	//////////////////////////////
	//							//
	//		field animations	//
	//							//
	//////////////////////////////

	public void addAnimation(Animations animation){
		animations.add(animation);
	}
	
	private void runAnimations() {
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_E)){
			animations.add(new SwipeAnimation(this));			
		}
		
		for(int i = 0; i < this.animations.size(); i++){
			if(animations.get(i).isRunning())
				animations.get(i).tick();
			else
				animations.remove(i);
		}
	}

	//////////////////////////////
	//							//
	//	getter()  &  setter()	//
	//							//
	//////////////////////////////
	
	public Triangle getExactTriangle(int xPos, int yPos){
		int x = (int)((xPos-xView) / height);
		int y = (int)((yPos-yView) / (length))*2;
		
		float oX = (((x+1)*height)-(xPos-xView))/height;
		float oY = (((y+1)*(length/2))-(yPos-yView))/(length/2);
		
		if(x % 2 != 0) oX = 1 - oX;
		
		int o = (int)(oX + Math.abs(oY));
		if(oY < 0) o *= -1;
		
		try {
			return this.triangles[y-o][x];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Point getIndexFromPos(int xPos, int yPos){
		int x = (int)((xPos-xView) / height);
		int y = (int)((yPos-yView) / (length))*2;
		
		float oX = (((x+1)*height)-(xPos-xView))/height;
		float oY = (((y+1)*(length/2))-(yPos-yView))/(length/2);
		
		if(x % 2 != 0) oX = 1 - oX;
		
		int o = (int)(oX + Math.abs(oY));
		if(oY < 0) o *= -1;
		
		return new Point(x, y-o);
	}
		
	public Triangle getTriangle(int x, int y){
		if((y >= 0 && y < triangles.length) && (x >= 0 && x < triangles[y].length)){
			return triangles[y][x];
		}
		else{
			return null;
		}
	}

	public int getxTriangles() {
		return triangles[0].length;
	}

	public int getyTriangles() {
		return triangles.length;
	}

	public float getLength() {
		return this.length;
	}
	
	public void setLength(float length){
		if(Math.round(triangles.length *(length/2)-(length/2)) >= Display.getHeight() &&
				Math.round(triangles[0].length*((float)Math.sqrt(3)*(length/2))) >= Display.getWidth()){
			this.length = length;
			this.height = ((float)Math.sqrt(3)*(length/2));
		}
		this.checkBorder(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	public void setHeight(float height){
		this.setLength((float)(2*height/Math.sqrt(3)));
	}

	//////////////////////////////
	//							//
	//	Field transformation	//
	//							//
	//////////////////////////////
	
	public void addHeight(float addHeight){
		this.setHeight(height+addHeight);		
	}
	
	public void addLength(float addLength){
		this.setLength(length+addLength);		
	}
	
	public void moveHorizontal(float move){
		xView += move;
		this.checkBorder(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	public void moveVertical(float move){
		yView += move;
		this.checkBorder(0, 0, Display.getWidth(), Display.getHeight());
	}
}