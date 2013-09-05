package com.januskopf.tryangle.level.shapeContainer;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.animations.Animations;
import com.januskopf.tryangle.level.animations.SwipeAnimation;

import java.io.Serializable;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


public class TriangleContainer implements Serializable{
	
	private static final long serialVersionUID = -5993312098303853315L;
	private float xView = 0;
	private float yView = 0;
	private float length;
	private float height;
	
	private ArrayList<Animations> animations = new ArrayList<Animations>();
	private Triangle[][] background;
	private Triangle[][] foreground;
	
	public TriangleContainer(int xTriCount, int yTriCount, float length){
		this.length = length;
		this.height = ((float)Math.sqrt(3)*(length/2));
		fillTriangleArray(xTriCount, yTriCount);
	}
	
	public void fillTriangleArray(int xNumber, int yNumber){
		background = new Triangle[yNumber][xNumber];
		foreground = new Triangle[yNumber][xNumber];
		
		//Triangle creation
		for(int y = 0; y < yNumber; y ++){
			for(int x = 0; x < xNumber; x ++){
				background[y][x] = new Triangle();
			}
		}
	}
	
	public void tick(){
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_SUBTRACT))this.setLength(length-0.1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_ADD))this.setLength(length+0.1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_UP))yView -= 1f;
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN))yView += 1f;
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT))xView -= 1f;
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT))xView += 1f;
		
		runAnimations();
		for(int j = 0; j < background.length; j++){
			for(int i = 0; i < background[j].length; i++){
				if(foreground[j][i] != null)foreground[j][i].tick();
				background[j][i].tick();
			}
		}
	}
	
	public void render(){
		for(int y = 0; y < background.length; y++){
			for(int x = 0; x < background[y].length; x++){
				renderTriangle(x, y, background[y][x]);
			}
		}
	}
	
	private void renderTriangle(int x, int y, Triangle t){
		float xPos = xView + x*height;
		float yPos = yView + y*length/2;
		float tHeight = height;
		if((x%2==0 && y%2==0) || (x%2!=0 && y%2!=0)){
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void addAnimation(Animations animation){
		animations.add(animation);
	}
	
	private void runAnimations() {
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			animations.add(new SwipeAnimation(this));			
		}
		
		for(int i = 0; i < this.animations.size(); i++){
			if(animations.get(i).isRunning())
				animations.get(i).tick();
			else
				animations.remove(i);
		}
	}
	
//	public void setForegroundTriangle(Triangle triangle){
//		int x = triangle.getVertex(0).getIndexX();
//		
//		if(triangle.isLeft() && x > 0)
//			x -= 1;
//			
//		int y = triangle.getVertex(0).getIndexY();
//		try {
//			foreground[y][x] = triangle;
//			triangle.setForeground(true);
//		} catch (ArrayIndexOutOfBoundsException e) {
//		}
//	}
	
	public void removeForegroundTriangle(int x, int y, boolean isLeft){
		if(isLeft && x > 0)
			x -= 1;
		try {
			foreground[y][x] = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isForegroundTriangle(int x, int y){
		if(foreground[y][x] == null)
			return false;
		else
			return true;
	}
	
	public Triangle getExactTriangle(int mouseX, int mouseY){
		return this.background[0][0];
	}
	
//	public Triangle getExactTriangle(int mouseX, int mouseY){
//		
//		GridVertex vertex = verticeContainer.getClosestVertex(mouseX, mouseY);
//		mouseY = Tryangle.HEIGHT - mouseY;
//		
//		float vX = vertex.getxPos();
//		float vY = vertex.getyPos();
//		
//		float deltaX = mouseX-vX;
//		float deltaY = mouseY-vY;
//		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
//
//		if(angle>-30 && angle<=30){
//			return getTriangle(vertex.getIndexX(),vertex.getIndexY()-1);
//		}
//		else if(angle>30 && angle<=90){
//			return getTriangle(vertex.getIndexX(),vertex.getIndexY());
//		}
//		else if(angle>90 && angle<=150){
//			return getTriangle(vertex.getIndexX()-1,vertex.getIndexY());
//		}
//		else if((angle>150 && angle<=180)||(angle>-180 && angle<=-150)){
//			return getTriangle(vertex.getIndexX()-1,vertex.getIndexY()-1);
//		}
//		else if(angle>-150 && angle<=-90){
//			return getTriangle(vertex.getIndexX()-1,vertex.getIndexY()-2);
//		}
//		else{
//			return getTriangle(vertex.getIndexX(),vertex.getIndexY()-2);
//		}
//	}
	
	public Triangle getBackgroundTriangle(int x, int y){
		if((x >= 0 && x < background.length) && (y >= 0 && y < background[y].length)){
			return background[y][x];
		}
		else{
			return null;
		}
	}
	
	public Triangle getTriangle(int x, int y){
		if((x >= 0 && x < background.length)&&(y >= 0 && y < background[x].length)){
			if(foreground[y][x] == null)
				return background[y][x];
			else
				return foreground[y][x];
		}
		else{
			return null;
		}
	}

	public int getxTriangles() {
		return background[0].length;
	}

	public int getyTriangles() {
		return background.length;
	}
	
	public void setLength(float length){
		this.length = length;
		this.height = ((float)Math.sqrt(3)*(length/2));
	}
}