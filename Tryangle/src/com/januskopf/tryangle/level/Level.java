package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.screens.IntroScreen;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class Level {
	
	public static final int X_TRIANGLES = 30;
	public static final int Y_TRIANGLES = 30;
	private float triangleLength = 50.0f;
	
	private float shield = 0;
	private int[] shieldX = {1,1,2,2,2,2,2,1,1,0,0,-1,-1,-1,-1,-1,0,0};	//X Positionen um den Cube herum beginnend oben rechts
	private int[] shieldY = {-2,-1,-1,0,1,2,3,3,4,4,3,3,2,1,0,-1,-1,-2};
	
	private float keyboardX = 0;
	private float keyboardY = 0;

	private IntroScreen intro;
	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private VerticeGrid verticeGrid;
	private TriangleContainer triangles;
	private CubeContainer cubes;
	
	public Level() {		
		intro = new IntroScreen();
		verticeGrid = new VerticeGrid(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		triangles = new TriangleContainer(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		cubes = new CubeContainer(triangles);

		animations.add(new FadeAnimation(triangles));
		animations.add(new RandomFlashing(triangles));
	}
		
	public void tick(){
		//intro.tick();
		triangles.tick();
		//animation.tick();
		cubes.tick();
		this.runAnimations();
		this.keyboardTriangle();
		this.drawShield();
	}

	public void render(){
		//intro.render();
		triangles.render();
		//verticeGrid.render();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////

	public static void addAnimation(Animations animation){
		animations.add(animation);
	}
	
	private void runAnimations() {
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			animations.add(new SwipeAnimation(triangles));			
		}
		
		for(int i = 0; i < animations.size(); i++){
			if(animations.get(i).isRunning())
				animations.get(i).tick();
			else
				animations.remove(i);
		}
	}
		
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
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN) && keyboardY < Y_TRIANGLES-1) {
        	keyboardY += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT) && keyboardX < X_TRIANGLES-1) {
        	keyboardX += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT) && keyboardX > 0) {
        	keyboardX -= 0.2;
        }
        
		triangles.getBackgroundTriangle((int)keyboardX, (int)keyboardY).setColor(0.27f ,0.57f ,0.80f);		
	}

}
