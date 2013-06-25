package com.januskopf.tryangle.level;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeSetter;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;
import com.januskopf.tryangle.sound.*;

public class Level1 extends Levels{
	
	private int yTriangles = 40;
	private float triangleLength = (float)Tryangle.HEIGHT/((float)yTriangles-2)*2.1f;
	private int xTriangles = (int)((float)Tryangle.WIDTH /((float)Math.sqrt(3)*(triangleLength/2)))+2;
	
	private float shield = 0;
	private int[] shieldX = {1,1,2,2,2,2,2,1,1,0,0,-1,-1,-1,-1,-1,0,0};	//X Positionen um den Cube herum beginnend oben rechts
	private int[] shieldY = {-2,-1,-1,0,1,2,3,3,4,4,3,3,2,1,0,-1,-1,-2};
	
	private float keyboardX = 0;
	private float keyboardY = 0;

	private VerticeContainer verticeContainer;
	private TriangleContainer triangles;
	private CubeContainer cubes;
	private CubeSetter cubeSetter;
	
	private Sound sound = Sound.getInstance();
	
	public Level1() {
		verticeContainer = new VerticeContainer(xTriangles, yTriangles, triangleLength);
		triangles = new TriangleContainer(verticeContainer, xTriangles, yTriangles, triangleLength);
		cubes = new CubeContainer(verticeContainer, triangles);
		cubeSetter = new CubeSetter(cubes, triangles, verticeContainer);
		
		triangles.addAnimation(new FadeAnimation(triangles, xTriangles, yTriangles));
		triangles.addAnimation(new RandomFlashing(triangles, xTriangles, yTriangles));
		
	}
		
	public void tick(){
		triangles.tick();
		//animation.tick();
		cubeSetter.tick();
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
        	sound.startInBeat(sound.SWOOSH);
        }
		
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_A)) {
        	shieldActivated = true;
        	shield -= 0.2f;
        	if (shield < 0) shield = 17;
        }
        
        if (shieldActivated){
        	GridVertex vertex = verticeContainer.getClosestVertex(mouseX,mouseY);
        	triangles.getTriangle(vertex.getIndexX()-1 + shieldX[(int)shield], vertex.getIndexY()-2 + shieldY[(int)shield]).setColor(0.37f ,0.87f ,0.90f); //shield dreieck um cube
		}
	}
	
	public void keyboardTriangle(){
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_UP) && keyboardY > 0) {
        	keyboardY -= 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN) && keyboardY < yTriangles-1) {
        	keyboardY += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT) && keyboardX < xTriangles-1) {
        	keyboardX += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT) && keyboardX > 0) {
        	keyboardX -= 0.2;
        }
        
		triangles.getBackgroundTriangle((int)keyboardX, (int)keyboardY).setColor(0.27f ,0.57f ,0.80f);		
	}

}	
