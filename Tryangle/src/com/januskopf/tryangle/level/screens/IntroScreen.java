package com.januskopf.tryangle.level.screens;

import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


public class IntroScreen {
	private Random random;

	public IntroScreen() {
		random = new Random();
			
	}
	
	public void tick(){
		
		
	}
	
	public void render(){
		if (random.nextInt(30) == 0) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			GL11.glColor3f(0.8f, 0.8f, 0.8f);
			GL11.glBegin(GL11.GL_POINTS);
			for (int i = 0; i < Display.getHeight(); i++) {
				for (int j = 0; j < Display.getWidth(); j++) {
					int r = random.nextInt(Math.abs((Math.abs(300 - i)-300)) + Math.abs((Math.abs(400 - j))-400) + 20);
					if (r < 10)
						GL11.glVertex2f(j, i);
				}
			}
			GL11.glEnd();
			//
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_POINTS);
			for (int i = 0; i < Display.getHeight(); i++) {
				for (int j = 0; j < Display.getWidth(); j++) {
					int r = random.nextInt(Math.abs((Math.abs(300 - i)-300)) + Math.abs((Math.abs(400 - j))-400) + 20);
					if (r < 10)
						GL11.glVertex2f(j, i);
				}
			}
			GL11.glEnd();
			//
			GL11.glColor3f(0.3f, 0.3f, 0.3f);
			GL11.glBegin(GL11.GL_POINTS);
			for (int i = 0; i < Display.getHeight(); i++) {
				for (int j = 0; j < Display.getWidth(); j++) {
					int r = random.nextInt(Math.abs((Math.abs(300 - i)-300)) + Math.abs((Math.abs(400 - j))-400) + 20);
					if (r < 10)
						GL11.glVertex2f(j, i);
				}
			}
			GL11.glEnd();
			//
		}	
	}

}
