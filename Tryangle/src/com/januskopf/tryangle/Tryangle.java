package com.januskopf.tryangle;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.screens.LevelSelection;
import com.januskopf.tryangle.sound.Sound;
 
public class Tryangle implements Runnable{
	
	public final static int WIDTH = 1280; 
	public final static int HEIGHT = 720;
	public final static boolean FULLSCREEN = false;
	public final static int FPS = 60;
	private static boolean running;
	private LevelSelection levelSelect;
	private KeyboardListener keyboard;
	private MouseListener mouse;
	public final Sound sound = Sound.getInstance();
	
	public static void main(String[] args)	{
		Tryangle t = new Tryangle();
		t.start();
	}
	
	public void start()	{
		Tryangle.running = true;
		new Thread(this).start();
	}
	
	public static void stop()	{
		Tryangle.running = false;
	}
		
	public void initOpenGL(){
		GL11.glDisable(GL11.GL_DEPTH_TEST);
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
		this.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
		this.initOpenGL();
				
		Sound.initialize();
		Sound.start(Sound.SOUNDTRACK);
		Sound.setVolume(Sound.SOUNDTRACK,0.8f);
		
		this.levelSelect = new LevelSelection();
		this.keyboard = new KeyboardListener();
		this.mouse = new MouseListener();
		
		while(running){
			if(Display.isCloseRequested()){ 
				Sound.end();
				stop();
				System.out.println("closing...");
			}
			if(Display.wasResized())this.resize();
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
		System.out.println("END");
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
	

	private void resize() {
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen) {

	    try {
	        DisplayMode targetDisplayMode = null;
			
		if (fullscreen) {
		    DisplayMode[] modes = Display.getAvailableDisplayModes();
		    int freq = 0;
					
		    for (int i=0;i<modes.length;i++) {
		        DisplayMode current = modes[i];
						
			if ((current.getWidth() == width) && (current.getHeight() == height)) {
			    if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
			        if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
				    targetDisplayMode = current;
				    freq = targetDisplayMode.getFrequency();
	                        }
	                    }

			    // if we've found a match for bpp and frequence against the 
			    // original display mode then it's probably best to go for this one
			    // since it's most likely compatible with the monitor
			    if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
	                        (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
	                            targetDisplayMode = current;
	                            System.out.println(targetDisplayMode);
	                            break;
	                    }
	                }
	            }
	        } else {
	            targetDisplayMode = new DisplayMode(width,height);
	        }

	        if (targetDisplayMode == null) {
	            System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
	            return;
	        }

	        Display.setDisplayMode(targetDisplayMode);
	        Display.setVSyncEnabled(true);
			Display.setResizable(true);
			Display.setTitle("Tryangle");
			PixelFormat p = new PixelFormat().withSamples(8);
			Display.setFullscreen(fullscreen);
			
			Display.create(p);
				
	    } catch (LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}
}
