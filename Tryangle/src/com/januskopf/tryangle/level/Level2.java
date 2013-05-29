package com.januskopf.tryangle.level;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.Levels;
import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.effects.ColorFlash;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class Level2 extends Levels{
	
	public static final int X_TRIANGLES = 50;
	public static final int Y_TRIANGLES = 50;
	private float triangleLength = (float)Tryangle.HEIGHT/((float)Y_TRIANGLES-1)*2f;

	private VerticeGrid verticeGrid;
	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private TriangleContainer triangles;
	private CubeContainer cubes;
	private Random random;
	private ArrayList<int[]> firePos = new ArrayList<int[]>();
	
	public Level2() {
		random = new Random();
		verticeGrid = new VerticeGrid(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		triangles = new TriangleContainer(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		cubes = new CubeContainer(triangles);
	}
		
	public void tick(){
		triangles.tick();
		this.runAnimations();
		this.mouseTriangle();

		int x = MouseListener.getMouseX(); 		
		int y = MouseListener.getMouseY(); 
		//this.fireAnimation(x, y);
		this.waterAnimation();
		
		if(MouseListener.isButtonClicked(0)){
			int pos[] = {x, y};
			firePos.add(pos);
		}
		for(int i = 0; i < firePos.size(); i++){
			int pos[] = firePos.get(i);
			this.fireAnimation(pos[0], pos[1]);			
		}
		
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			firePos.clear();
		}
		
		//this.circle();
	}

	public void render(){
		triangles.render();
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	private void mouseTriangle() {
		int x = MouseListener.getMouseX(); 		
		int y = MouseListener.getMouseY(); 
//		
//		try {
//			Triangle t = triangles.getExactTriangle(x, y);
//			t.addEffect(new ColorFlash(1.0f, 0, 0, t, 30));
//		} catch (Exception e) {}
	}

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
	
	private void fireAnimation(int x, int y){		
		if(random.nextInt(5) == 0)animations.add(new FireAnimation(triangles, x, y, random.nextInt(30)));
		if(random.nextInt(20) == 0)animations.add(new FireAnimation(triangles, x, y, 50 + random.nextInt(50)));		
	}
	public void waterAnimation(){
		if (MouseListener.isButtonPressed(1)) {
			firePos.clear();
			int x = MouseListener.getMouseX();
			int y = MouseListener.getMouseY();
			Animations animation = new WaterAnimation(triangles, x, y, 0f, (float)Math.abs(0.5f-(float)Math.random()), 0.75f, 1000, 50);
			this.animations.add(animation);
		}

	}
	
	private void circle(){
		int xPos = MouseListener.getMouseX(); 		
		int yPos = MouseListener.getMouseY();
		
		
		for (int iRadius = 10; iRadius < 150; iRadius += 20) {
			for (int i = 0; i < 45; i++) {
				float a = (float) Math.sin(i) * iRadius;
				float b = (float) Math.cos(i) * iRadius;
				try {
					Triangle t = triangles.getExactTriangle((int) a + xPos,
							(int) b + yPos);
					float d = 0.10f - (0.04f - (0.04f / iRadius));
					t.addEffect(new ColorFlash(t.getColorR() + d, t.getColorG()
							+ d, t.getColorB() + d, t, 40));
				} catch (Exception e) {
				}
			}
		}
		
	}

}
