package com.januskopf.tryangle.level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.input.KeyboardListener;

public class LevelSelection {

	private boolean isLevelSelect = true;
	private static Levels currentLevel;
	
	public LevelSelection(){
		currentLevel  = new Level1();
	}
	
	public void tick(){
		if(isLevelSelect){
			if(KeyboardListener.isKeyPressed(Keyboard.KEY_ESCAPE));
			this.setActiveLevel();
		}
		else{
			if(KeyboardListener.isKeyPressed(Keyboard.KEY_ESCAPE))isLevelSelect=true;
			currentLevel.tick();
		}
	}
	
	public void render(){
		if(isLevelSelect)
			drawLevel();
		else
			currentLevel.render();		
	}
	
	private void drawLevel() {
		float length = 350.0f;
		float height = ((float)Math.sqrt(3)*(length/2));
		
		GL11.glColor3f(0.0f, 0.0f, 0.0f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(Tryangle.WIDTH, 0);
			GL11.glVertex2f(Tryangle.WIDTH, Tryangle.HEIGHT);
			GL11.glVertex2f(0, Tryangle.HEIGHT);
		GL11.glEnd();	
			
			
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(0.78f, 0.78f, 0.78f);
			GL11.glVertex2f(Tryangle.WIDTH/2, Tryangle.HEIGHT/2);
			GL11.glColor3f(0.95f, 0.95f, 0.95f);
			GL11.glVertex2f(Tryangle.WIDTH/2 + length/2, Tryangle.HEIGHT/2 - height);
			GL11.glColor3f(0.95f, 0.95f, 0.95f);
			GL11.glVertex2f(Tryangle.WIDTH/2 - length/2, Tryangle.HEIGHT/2 - height);
		GL11.glEnd();	
	}

	private void setActiveLevel(){
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_1)){
			currentLevel = new Level1();
			isLevelSelect = false;
		}
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_2)){
			currentLevel = new Level2();
		isLevelSelect = false;
	}
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_3)){
			try {
				currentLevel = new Level3();
				isLevelSelect = false;
			}catch (Exception e){
				System.out.println("Konnte Level 3 nicht laden.");
			}
		}
	}
	
	public static Levels getCurrentLevel(){
		return currentLevel;
	}
	
	public void goToLevelSelect(){
		this.isLevelSelect = true;
	}
	
}
