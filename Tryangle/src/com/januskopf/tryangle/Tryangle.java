package com.januskopf.tryangle;

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
	private boolean running;
	private LevelSelection levelSelect;
	private KeyboardListener keyboard;
	private MouseListener mouse;
	public final Sound sound = Sound.getInstance();
	
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
					
		sound.initialize();
		sound.start(sound.SOUNDTRACK);
		
		this.levelSelect = new LevelSelection();
		this.keyboard = new KeyboardListener();
		this.mouse = new MouseListener();
		
		while(running){
			if(Display.isCloseRequested()){ 
				sound.end();
				stop();
				System.out.println("closing...");
			}
			
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
		sound.tick();
	}
 
	public void render(){
		levelSelect.render();
	}
}
