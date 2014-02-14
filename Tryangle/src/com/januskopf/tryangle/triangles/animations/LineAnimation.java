package com.januskopf.tryangle.triangles.animations;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorSet;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;
import com.januskopf.tryangle.triangles.effects.TriangleColorSet;

public class LineAnimation extends Animations{
	
	//color
	private float colorR;
	private float colorG;
	private float colorB;
	
	//coordinates
	private int startX;
	private int startY;
	
	private int endX;
	private int endY;
	
	private int mouseX;
	private int mouseY;
	
	
	//top
	private Triangle startTriangle;
	private CubeColorSet startTriangleColor;
	
	private Triangle endTriangle;
	private CubeColorSet lineColor;
	
	private boolean newLine;
	
	//Last Directions
	private boolean upL;
	private boolean up;
	private boolean upR;
	
	private boolean downR;
	private boolean down;
	private boolean downL;
	
	private double angle;
	
	private boolean isActive = true;
	
	private TriangleContainer triangles;
	private ArrayList<Triangle> line = new ArrayList<Triangle>();
	
	public LineAnimation(TriangleContainer triangles, int xPos, int yPos, int startX, int startY, int lastDirection, float colorR, float colorG, float colorB){		
		this.triangles = triangles;
		
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		
		this.startX = startX;
		this.startY = startY;
		this.startTriangle = triangles.getTriangle(startX, startY);
		
		this.mouseX = triangles.getIndexFromPos(xPos, yPos).x;
		this.mouseY = triangles.getIndexFromPos(xPos, yPos).y;
		
		if(lastDirection == 0){
			newLine = true;
		}
		if(lastDirection == 1){
			upL = true;
		}
		if(lastDirection == 2){
			up = true;
		}
		if(lastDirection == 3){
			upR = true;
		}
		if(lastDirection == 4){
			downR = true;
		}
		if(lastDirection == 5){
			down = true;
		}
		if(lastDirection == 6){
			downL = true;
		}
		
		this.setLine(xPos, yPos);
//		System.out.println("Triangle Werte: x: " + x + "y: " + y);
	}
	
	private void setLine(int xPos, int yPos){
//		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;			

		endTriangle = triangles.getExactTriangle(xPos, yPos);
		
		if(!newLine){
			if(startTriangle.getCubeSide() == CubeColorSet.TOP_LEFT){
				lineColor = new CubeColorSet(endTriangle, colorR, colorG, colorB, CubeColorSet.LEFT_TOP);
			}
			else {
				lineColor = new CubeColorSet(endTriangle, colorR, colorG, colorB, CubeColorSet.TOP_LEFT);
			}
		}
		else {
			lineColor = new CubeColorSet(endTriangle, colorR, colorG, colorB, CubeColorSet.TOP_LEFT);
			startTriangle.addTopLayerEffect(lineColor, false);
			line.add(startTriangle);
		}
		
//		startTriangle.addTopLayerEffect(lineColor, false);
//		line.add(startTriangle);
		
		//LEFT TRIANGLE
		if(TriangleContainer.isTriangleLeft(startX, startY)){
			
			
			double xDif = xPos - (triangles.getTriangleVertices(startX, startY)[4] + (2*triangles.getHeight())/3); 
			double yDif = yPos - triangles.getTriangleVertices(startX, startY)[5]; 
			angle = Math.toDegrees(Math.atan2(yDif, xDif))+180;
			System.out.println(angle);
			
			
			//Line up-left
			if(angle >= 0 && angle < 60){
				
				float xTriCount = (triangles.getTriangleVertices(startX, startY)[4] - xPos) / (triangles.getHeight()/2);
				System.out.println(xTriCount);
				int difX = 0;
				int difY = 1;
				Triangle t = triangles.getTriangle(startX-difX, startY-difY);
				if(t != null) t.addTopLayerEffect(lineColor, false);
				if(t != null) line.add(t);
				setEnd(startX-difX, startY-difY);
				while(startX-difX > mouseX){
					difX++;
//					System.out.println(difX + "  " + difY);
					System.out.println("endX: " + mouseX + " endY: " + mouseY);
					Triangle t2 = triangles.getTriangle(startX-difX, startY-difY);
					setEnd(startX-difX, startY-difY);
//					System.out.println("lineX: " + (startX-difX) + " lineY: " + (startY-difY));
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX-difX, startY-difY);
					if((startX-difX == mouseX && startY-difY == mouseY)) break;// || (endX%2 != endY%2)){
						difY++;
						Triangle t3 = triangles.getTriangle(startX-difX, startY-difY);
						if(t3 != null) t3.addTopLayerEffect(lineColor, false);
						if(t3 != null) line.add(t3);
						setEnd(startX-difX, startY-difY);
					
				}
			}
			
			//Line up
			if(angle >= 60 && angle < 120){
				int difY = 0;
				while(startY-difY > mouseY){
					difY++;
//					System.out.println(difX + "  " + difY);
					Triangle t = triangles.getTriangle(startX, startY-difY);
					if(t != null) t.addTopLayerEffect(lineColor, false);
					if(t != null) line.add(t);
					setEnd(startX, startY-difY);
				}
			}
			
			//line up-right
			if(angle >= 120 && angle < 180){
				
				int difX = 0;
				int difY = 0;
				while(startX+difX < mouseX){
					difX++;
					System.out.println(difX + "  " + difY);
					Triangle t2 = triangles.getTriangle(startX+difX, startY-difY);
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX+difX, startY-difY);
					
					if(startX+difX == mouseX && startY-difY == mouseY) break;
					difY++;
					Triangle t3 = triangles.getTriangle(startX+difX, startY-difY);
					if(t3 != null) t3.addTopLayerEffect(lineColor, false);
					if(t3 != null) line.add(t3);
					setEnd(startX+difX, startY-difY);
				}
			}
			
			//line down-right
			if(angle >= 180 && angle < 240){
				
				int difX = 0;
				int difY = 0;
				while(startX+difX < mouseX){
					difX++;
					System.out.println(difX + "  " + difY);
					Triangle t2 = triangles.getTriangle(startX+difX, startY+difY);
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX+difX, startY+difY);
					
					if(startX+difX == mouseX && startY+difY == mouseY) break;
					difY++;
					Triangle t3 = triangles.getTriangle(startX+difX, startY+difY);
					if(t3 != null) t3.addTopLayerEffect(lineColor, false);
					if(t3 != null) line.add(t3);
					setEnd(startX+difX, startY+difY);
				}
			}
			
			//Line down
			if(angle >= 240 && angle < 300){
				int difY = 0;
				while(startY+difY < mouseY){
					difY++;
//					System.out.println(difX + "  " + difY);
					Triangle t = triangles.getTriangle(startX, startY+difY);
					if(t != null) t.addTopLayerEffect(lineColor, false);
					if(t != null) line.add(t);
					setEnd(startX, startY+difY);
				}
			}
			
			//Line down-left
			if(angle >= 300 && angle < 360){
				
				float xTriCount = (triangles.getTriangleVertices(startX, startY)[4] - xPos) / (triangles.getHeight()/2);
				System.out.println(xTriCount);
				int difX = 0;
				int difY = 1;
				Triangle t = triangles.getTriangle(startX-difX, startY+difY);
				if(t != null) t.addTopLayerEffect(lineColor, false);
				if(t != null) line.add(t);
				setEnd(startX-difX, startY+difY);
				while(startX-difX > mouseX){
					difX++;
//					System.out.println(difX + "  " + difY);
					System.out.println("endX: " + mouseX + " endY: " + mouseY);
					Triangle t2 = triangles.getTriangle(startX-difX, startY+difY);
					System.out.println("lineX: " + (startX-difX) + " lineY: " + (startY+difY));
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX-difX, startY+difY);
					if((startX-difX == mouseX && startY+difY == mouseY)) break;// || (endX%2 != endY%2)){
						difY++;
						Triangle t3 = triangles.getTriangle(startX-difX, startY+difY);
						if(t3 != null) t3.addTopLayerEffect(lineColor, false);
						if(t3 != null) line.add(t3);
						setEnd(startX-difX, startY+difY);
					
				}
			}
			
		}
		
		
		//RIGHT TRIANGLE
		if(!TriangleContainer.isTriangleLeft(startX, startY)){
			
			
			double xDif = xPos - (triangles.getTriangleVertices(startX, startY)[4] - (2*triangles.getHeight())/3); 
			double yDif = yPos - triangles.getTriangleVertices(startX, startY)[5]; 
			angle = Math.toDegrees(Math.atan2(yDif, xDif))+180;
			System.out.println(angle);
			
			
			
			//line up-right
			if(angle >= 0 && angle < 60){
				
				int difX = 0;
				int difY = 0;
				while(startX-difX > mouseX){
					difX++;
					System.out.println(difX + "  " + difY);
					Triangle t2 = triangles.getTriangle(startX-difX, startY-difY);
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX-difX, startY-difY);
					if(startX-difX == mouseX && startY-difY == mouseY) break;
					difY++;
					Triangle t3 = triangles.getTriangle(startX-difX, startY-difY);
					if(t3 != null) t3.addTopLayerEffect(lineColor, false);
					if(t3 != null) line.add(t3);
					setEnd(startX-difX, startY-difY);
				}
			}
			
			//Line up
			if(angle >= 60 && angle < 120){
				int difY = 0;
				while(startY-difY > mouseY){
					difY++;
//					System.out.println(difX + "  " + difY);
					Triangle t = triangles.getTriangle(startX, startY-difY);
					if(t != null) t.addTopLayerEffect(lineColor, false);
					if(t != null) line.add(t);
					setEnd(startX, startY-difY);
				}
			}
			
			
			//Line up-right
			if(angle >= 120 && angle < 180){
				
				float xTriCount = (triangles.getTriangleVertices(startX, startY)[4] - xPos) / (triangles.getHeight()/2);
				System.out.println(xTriCount);
				int difX = 0;
				int difY = 1;
				Triangle t = triangles.getTriangle(startX+difX, startY-difY);
				if(t != null) t.addTopLayerEffect(lineColor, false);
				if(t != null) line.add(t);
				setEnd(startX+difX, startY-difY);
				while(startX+difX < mouseX){
					difX++;
//					System.out.println(difX + "  " + difY);
					System.out.println("endX: " + mouseX + " endY: " + mouseY);
					Triangle t2 = triangles.getTriangle(startX+difX, startY-difY);
					System.out.println("lineX: " + (startX+difX) + " lineY: " + (startY-difY));
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX+difX, startY-difY);
					if((startX+difX == mouseX && startY-difY == mouseY)) break;// || (endX%2 != endY%2)){
						difY++;
						Triangle t3 = triangles.getTriangle(startX+difX, startY-difY);
						if(t3 != null) t3.addTopLayerEffect(lineColor, false);
						if(t3 != null) line.add(t3);
						setEnd(startX+difX, startY-difY);
					
				}
			}
			
			
			//Line down-right
			if(angle >= 180 && angle < 240){
				
				float xTriCount = (triangles.getTriangleVertices(startX, startY)[4] - xPos) / (triangles.getHeight()/2);
				System.out.println(xTriCount);
				int difX = 0;
				int difY = 1;
				Triangle t = triangles.getTriangle(startX+difX, startY+difY);
				if(t != null) t.addTopLayerEffect(lineColor, false);
				if(t != null) line.add(t);
				setEnd(startX+difX, startY+difY);
				while(startX+difX < mouseX){
					difX++;
//					System.out.println(difX + "  " + difY);
					System.out.println("endX: " + mouseX + " endY: " + mouseY);
					Triangle t2 = triangles.getTriangle(startX+difX, startY+difY);
					System.out.println("lineX: " + (startX+difX) + " lineY: " + (startY+difY));
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX+difX, startY+difY);
					if((startX+difX == mouseX && startY+difY == mouseY)) break;// || (endX%2 != endY%2)){
						difY++;
						Triangle t3 = triangles.getTriangle(startX+difX, startY+difY);
						if(t3 != null) t3.addTopLayerEffect(lineColor, false);
						if(t3 != null) line.add(t3);
						setEnd(startX+difX, startY+difY);
					
				}
			}
			
			//Line down
			if(angle >= 240 && angle < 300){
				int difY = 0;
				while(startY+difY < mouseY){
					difY++;
//					System.out.println(difX + "  " + difY);
					Triangle t = triangles.getTriangle(startX, startY+difY);
					if(t != null) t.addTopLayerEffect(lineColor, false);
					if(t != null) line.add(t);
					setEnd(startX, startY+difY);
				}
			}
			
			//line down-right
			if(angle >= 300 && angle < 360){
				
				int difX = 0;
				int difY = 0;
				while(startX-difX > mouseX){
					difX++;
					System.out.println(difX + "  " + difY);
					Triangle t2 = triangles.getTriangle(startX-difX, startY+difY);
					if(t2 != null) t2.addTopLayerEffect(lineColor, false);
					if(t2 != null) line.add(t2);
					setEnd(startX-difX, startY+difY);
					if(startX-difX == mouseX && startY+difY == mouseY) break;
					difY++;
					Triangle t3 = triangles.getTriangle(startX-difX, startY+difY);
					if(t3 != null) t3.addTopLayerEffect(lineColor, false);
					if(t3 != null) line.add(t3);
					setEnd(startX-difX, startY+difY);
				}
			}
			
		}
		
		

//		if(endTriangle != null) lineColor = new CubeColorSet(endTriangle, colorR, colorG, colorB, TriangleColorSet.BRIGHT);
//		
//		if(endTriangle != null) endTriangle.addTopLayerEffect(lineColor, false);
			
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
		lineColor.remove();
		
		
		if(endTriangle!=null)endTriangle.updateEffects();
		
		mouseX = triangles.getIndexFromPos(xPos, yPos).x;
		mouseY = triangles.getIndexFromPos(xPos, yPos).y;
		this.setLine(xPos, yPos);
	}
	
	
	public void changeColor(float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
	}
	
	
	public void remove(){
		isActive = false;
		lineColor.remove();
//		startTriangleColor.remove();
		
		for(int i = line.size()-1; i >= 0; i--){
			if(line.get(i) != null) line.get(i).updateEffects();
		}
		startTriangle.updateEffects();
		endTriangle.updateEffects();
		
//		if(endTriangle!=null)endTriangle.updateEffects();
	}
	

	@Override
	public boolean isActive() {
		return isActive;
	}
	
	public int getStartX(){
		return startX;
	}
	
	public int getStartY(){
		return startY;
	}
	
	public int getEndX(){
		return endX;
	}
	
	public int getEndY(){
		return endY;
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
	
	private void setEnd(int x, int y){
		this.endX = x;
		this.endY = y;
	}
}
