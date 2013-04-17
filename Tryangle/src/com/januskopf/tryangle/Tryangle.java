package com.januskopf.tryangle;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.entity.*;
 
public class Tryangle implements Runnable{
	
	public final int WIDTH = 800;
	public final int HEIGHT = 600;
	public final int FPS = 60;
	
	private boolean running;
	private final int xTriCount = 20;
	private final int yTriCount = 26;
	
	private Triangle[][] triangleArray = new Triangle[yTriCount][xTriCount];
	
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
			//PixelFormat p = new PixelFormat().withSamples(8);
			Display.create();
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
		this.createTriArray();
		while(running){
			if(Display.isCloseRequested()) stop();
			
			this.tick();
			this.render();
			Display.sync(FPS);
			Display.update();
		}
	}
	
		
	private void createTriArray() {
		
		float xPos = 0.0f;
		float yPos = -50.0f; //-50
		float l = 50.0f; //50
		float h = ((float)Math.sqrt((l*l) - ((l/2)*(l/2))));
		
		float x = xPos;
		float y = yPos;
		
		for(int j = 0; j < yTriCount; j++){
			for(int i = 0; i < xTriCount; i++){
				
				if(i % 2 == 0)
					triangleArray[j][i] = new Triangle(x, y, l, 0.85f, 0.48f, 0.13f);
				else
					triangleArray[j][i] = new Triangle(x + h, y, l, 0.71f, 0.20f, 0.13f, true);
				
				x = x + h;
			}
			if(j%2 == 0)
				x = xPos;
			else
				x = xPos -h;
			y = y + l/2;
		}
		
	}

	public void render(){
		for(int j = 0; j < yTriCount; j++){
			for(int i = 0; i < xTriCount; i++){
				triangleArray[j][i].render();
			}
		}
	}
	
	public void tick(){
		
	}
 
}