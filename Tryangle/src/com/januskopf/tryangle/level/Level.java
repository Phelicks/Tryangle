package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.triangles.Triangles;

public class Level {
	
	private int xTriNumber = 30;
	private int yTriNumber = 30;
	private float length = 50.0f;
	
	private int mouseX;
	private int mouseY;
	
	private float shield = 0;
	private int[] shieldX = {1,1,2,2,2,2,2,1,1,0,0,-1,-1,-1,-1,-1,0,0};	//X Positionen um den Cube herum beginnend oben rechts
	private int[] shieldY = {-2,-1,-1,0,1,2,3,3,4,4,3,3,2,1,0,-1,-1,-2};
	
	private float keyboardX = 10;
	private float keyboardY = 10;
	
	private float randomColorR;
	private float randomColorG = 0.21f;
	private float randomColorB = 0.62f;
	
	private Animation animation;
	private VerticeGrid verticeGrid;
	private Triangles triangles;
	private ArrayList<GridVertex> staticCubes = new ArrayList<GridVertex>();
		
	public Level() {
		verticeGrid = new VerticeGrid(xTriNumber, yTriNumber, length);
		triangles = new Triangles(xTriNumber, yTriNumber, length);
		animation = new Animation(triangles, xTriNumber, yTriNumber);
	}
		
	public void tick(){
		this.mousePos();
		this.cube();
		this.drawShield();
		this.keyboardTriangle();
		triangles.tick();
		animation.tick();
		
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_C)) {
			randomizeCubeColor();
		}
	}
	

	public void render(){
		triangles.render();
		verticeGrid.render();
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	private void mousePos(){
        this.mouseX = Mouse.getX();
        this.mouseY = Tryangle.HEIGHT - Mouse.getY();
	}	
	
	private void cube(){		
		for(int i=0; i < staticCubes.size(); i++){
			drawCube(staticCubes.get(i));
		}
				
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_E)) {
        	staticCubes.clear();
		}
		
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_R)) {
        	if(staticCubes.size()>0){
        		staticCubes.remove(staticCubes.size()-1);
        	}
        }
		if(Mouse.isClipMouseCoordinatesToWindow() == true){
			if (KeyboardListener.isKeyPressed(Keyboard.KEY_Y)) {
				triangles.getExactTriangle(mouseX,mouseY).setColor(0.09f, 0.31f, 0.72f);
			}else{
				GridVertex vertex = VerticeGrid.getClosestVertex(mouseX,mouseY);
				//drawCube(vertex);
				if(Mouse.isButtonDown(0) == true){
					//vertex.setCube();
					for(int i=0; i < staticCubes.size(); i++){
						if(staticCubes.get(i)==vertex){
							staticCubes.remove(i);
						}
					}
					staticCubes.add(vertex);
				}				
			}
		}
	}
	
	private void drawShield(){
		boolean shieldActivated = false;
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_S)) {
        	shieldActivated = true;
			shield += 0.2f;
        	if (shield > 17.2f) shield = 0;
        }
		
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_A)) {
        	shieldActivated = true;
        	shield -= 0.2f;
        	if (shield < 0) shield = 17;
        }
        
        if (shieldActivated){
        	GridVertex vertex = VerticeGrid.getClosestVertex(mouseX,mouseY);
        	triangles.getTriangle(vertex.getIndexX()-1 + shieldX[(int)shield], vertex.getIndexY()-2 + shieldY[(int)shield]).setColor(0.37f ,0.87f ,0.90f); //shield dreieck um cube
		}
	}
	
	public void keyboardTriangle(){
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_UP) && keyboardY > 0) {
        	keyboardY -= 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN) && keyboardY < yTriNumber-1) {
        	keyboardY += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT) && keyboardX < xTriNumber-1) {
        	keyboardX += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT) && keyboardX > 0) {
        	keyboardX -= 0.2;
        }
        
		triangles.getTriangle((int)keyboardX, (int)keyboardY).setColor(0.27f ,0.57f ,0.80f);		
	}
	
	public void randomizeCubeColor(){
    	randomColorR = (float)Math.random();
    	if(randomColorR>=0.2f){
    		randomColorR -= 0.2f;
    	}
    	randomColorG = (float)Math.random();
    	if(randomColorG>=0.2f){
    		randomColorG -= 0.2f;
    	}
    	randomColorB = (float)Math.random();
    	if(randomColorB>=0.2f){
    		randomColorB -= 0.2f;
    	}        	
    	//System.out.println("R:"+ randomColorR + " G:" + randomColorG + " B:" + randomColorB);	
	}
	
	public void drawCube(GridVertex vertex){
		Triangles t = triangles;
		t.getTriangle(vertex.getIndexX(),vertex.getIndexY()-1).setColor(randomColorR, randomColorG, randomColorB);
		t.getTriangle(vertex.getIndexX(),vertex.getIndexY()).setColor(randomColorR, randomColorG, randomColorB);
		t.getTriangle(vertex.getIndexX()-1,vertex.getIndexY()).setColor(randomColorR + 0.1f, randomColorG + 0.1f, randomColorB + 0.1f);
		t.getTriangle(vertex.getIndexX()-1,vertex.getIndexY()-1).setColor(randomColorR + 0.1f, randomColorG + 0.1f, randomColorB + 0.1f);
		t.getTriangle(vertex.getIndexX()-1,vertex.getIndexY()-2).setColor(randomColorR + 0.2f, randomColorG + 0.2f, randomColorB + 0.2f);
		t.getTriangle(vertex.getIndexX(),vertex.getIndexY()-2).setColor(randomColorR + 0.2f, randomColorG + 0.2f, randomColorB + 0.2f);
}

}
