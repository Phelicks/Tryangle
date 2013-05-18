package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.entity.Cube;
import com.januskopf.tryangle.entity.effects.Effects;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.Animation;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.triangles.Cubes;
import com.januskopf.tryangle.level.triangles.Triangles;

public class Level {
	
	private int xTriNumber = 30;
	private int yTriNumber = 30;
	private float length = 50.0f;
	
	private float shield = 0;
	private int[] shieldX = {1,1,2,2,2,2,2,1,1,0,0,-1,-1,-1,-1,-1,0,0};	//X Positionen um den Cube herum beginnend oben rechts
	private int[] shieldY = {-2,-1,-1,0,1,2,3,3,4,4,3,3,2,1,0,-1,-1,-2};
	
	private float keyboardX = 0;
	private float keyboardY = 0;
	
	private Animation animation;
	private VerticeGrid verticeGrid;
	private Triangles triangles;
	private Cubes cubes;
	
	public Level() {
		verticeGrid = new VerticeGrid(xTriNumber, yTriNumber, length);
		triangles = new Triangles(xTriNumber, yTriNumber, length);
		animation = new Animation(triangles, xTriNumber, yTriNumber);
		cubes = new Cubes(triangles, animation);
	}
		
	public void tick(){
		triangles.tick();
		animation.tick();
		cubes.tick();
		this.keyboardTriangle();
		this.drawShield();
	}
	

	public void render(){
		triangles.render();
		//verticeGrid.render();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
		
	private void drawShield(){
		int mouseX = MouseListener.getMouseX();
		int mouseY = MouseListener.getMouseY();
		
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
        
		triangles.getBackgroundTriangle((int)keyboardX, (int)keyboardY).setColor(0.27f ,0.57f ,0.80f);		
	}

}
