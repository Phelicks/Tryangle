package com.januskopf.tryangle;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.Level;
 
public class Tryangle implements Runnable{
	
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	public final static int FPS = 60;
	private boolean running;
	
	private Level level;
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
		this.level = new Level();
		
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
		level.tick();		
	}
 
	public void render(){		
		level.render();		
	}
}