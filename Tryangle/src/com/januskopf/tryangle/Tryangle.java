package com.januskopf.tryangle;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.Level1;
import com.januskopf.tryangle.level.Level2;
import com.januskopf.tryangle.level.Level3;
import com.januskopf.tryangle.level.Levels;
 
public class Tryangle implements Runnable{
	
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	public final static int FPS = 60;
	private boolean running;
	
	private static Levels currentLevel;
	private KeyboardListener keyboard;
	private MouseListener mouse;
	
		
	public static void main(String[] args)	{
		Tryangle t = new Tryangle();
		t.start();
	}
	
	public void start()	{
		this.running = true;
		new Thread(this).start();
	}
	
	public void stop()	{
		this.running = false;
	}
	
	public void initDisplay(){
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setResizable(false);
			PixelFormat p = new PixelFormat().withSamples(4);
			Display.create(p);
		}
		catch (LWJGLException e){
			e.printStackTrace();
		}
	}
	
	public void initOpenGL(){
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, -1.0, 1.0);
		System.out.println("You are running OpenGL version ["+GL11.glGetString(GL11.GL_VERSION)+"].");
	}
	
	public void run(){
		this.initDisplay();
		this.initOpenGL();

		this.keyboard = new KeyboardListener();
		this.mouse = new MouseListener();
		Tryangle.currentLevel  = new Level1();
		
		while(running){
			if(Display.isCloseRequested()) stop();
			
			this.tick();
			this.render();
			Display.sync(FPS);
			Display.update();
		}
	}
	
	public void tick(){		
		keyboard.tick();	
		mouse.tick();
		this.setActiveLevel();
		currentLevel.tick();
	}
 
	public void render(){		
		currentLevel.render();	
	}
	
	private void setActiveLevel(){
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_1))
			currentLevel = new Level1();
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_2))
			currentLevel = new Level2();
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_3))
			currentLevel = new Level3();
	}
	
	public static Levels getCurrentLevel(){
		return Tryangle.currentLevel;
	}
}
