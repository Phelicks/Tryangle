package com.januskopf.tryangle;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.canvas.Canvas;
import com.januskopf.tryangle.input.*;
 
public class Tryangle implements Runnable{
	
	public final int WIDTH = 800;
	public final int HEIGHT = 600;
	public final int FPS = 60;
	
	private boolean running;
	
	private Canvas canvas;
	private KeyboardListener keyboard;
	
		
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
		this.canvas = new Canvas();
		
		while(running){
			if(Display.isCloseRequested()) stop();
			
			this.tick();
			this.render();
			Display.sync(FPS);
			Display.update();
		}
	}
	

	public void render(){
		
		canvas.render();
		
	}
	
	public void tick(){
		
		keyboard.tick();
		canvas.tick();
		
	}
 
}