package com.januskopf.tryangle;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.LevelSelection;
import com.januskopf.tryangle.sound.Sound;
 
public class Tryangle implements Runnable{
	
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	public final static int FPS = 60;
	private static boolean running;
	
	private LevelSelection levelSelect;
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
	
	public static void stop()	{
		Tryangle.running = false;
	}
	
	public void initDisplay(){
		try{			
			DisplayMode displayMode = null;
	        DisplayMode[] modes = Display.getAvailableDisplayModes();

	         for (int i = 0; i < modes.length; i++){
             if (modes[i].getWidth() == Tryangle.WIDTH
	             && modes[i].getHeight() == Tryangle.HEIGHT
	             && modes[i].isFullscreenCapable()) {
                    displayMode = modes[i];
               }
	        }
			Display.setDisplayMode(displayMode);
			Display.setResizable(false);
			Display.setTitle("Tryangle");
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
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND); 
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("You are running OpenGL version ["+GL11.glGetString(GL11.GL_VERSION)+"].");
	}
	
	public void run(){
		this.initDisplay();
		this.initOpenGL();
		
		Sound sound = new Sound();
		sound.initialize();
		sound.start();
		
		this.levelSelect = new LevelSelection();
		this.keyboard = new KeyboardListener();
		this.mouse = new MouseListener();
		
		while(running){
			if(Display.isCloseRequested()) stop();
			
			this.tick();
			this.render();
			if(KeyboardListener.isKeyClicked(Keyboard.KEY_F11)){
				try {
					boolean isFull = Display.isFullscreen();
					Display.setFullscreen(!isFull);
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
			Display.sync(FPS);
			Display.update();
		}
	}
	
	public void tick(){		
		keyboard.tick();	
		mouse.tick();
		levelSelect.tick();
	}
 
	public void render(){
		levelSelect.render();
	}
}
