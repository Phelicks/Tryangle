package com.januskopf.tryangle;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.screens.LevelSelection;
import com.januskopf.tryangle.sound.Sound;
 
public class Tryangle implements Runnable{
	
	public final static boolean FULLSCREEN = false;
	public final static int FPS = 60;
	private int width = 1280; 
	private int height = 720;
	private static boolean running;
	private LevelSelection levelSelect;
	private KeyboardListener keyboard;
	private MouseListener mouse;
	public final Sound sound = Sound.getInstance();
	
	private int frame = 0;
	private int fps = 0;
	private long fpsCounter = System.currentTimeMillis();
	
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
		Sound.end();
	}
		
	public void initOpenGL(){
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		this.resize();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND); 
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		System.out.println("You are running OpenGL version ["+GL11.glGetString(GL11.GL_VERSION)+"].");
	}
	
	public void initSound(){
		Sound.initialize();
		Sound.start(Sound.SOUNDTRACK);
		Sound.setVolume(Sound.SOUNDTRACK,0.8f);		
	}
	
	public void run(){
		this.setDisplayMode(width, height, FULLSCREEN);
		this.initOpenGL();
		this.initSound();
		
		this.levelSelect = new LevelSelection();
		this.keyboard = new KeyboardListener();
		this.mouse = new MouseListener();
		
		while(running){
			Display.update();
			
			if(Display.wasResized()){
				this.resize();
			}
			
			if(Display.isDirty()){
				System.out.println("Ohoh");
			}
			
			if(Display.isCloseRequested()){ 
				stop();
				System.out.println("closing...");
			}
//			this.frameCounter();
			
//			long countStart = System.currentTimeMillis();
			this.render();
//			long countRender = System.currentTimeMillis();
			this.tick();
//			long countTick = System.currentTimeMillis();
//			
//			long countAll = countRender-countStart;
//			
//			long tickPer;
//			try {
//				tickPer = (countTick-countStart)*100/countAll*100;
//				tickPer /= 100;
//			} catch (java.lang.ArithmeticException e1) {
//				tickPer = 0;
//			}
//			
//			long renderPer;
//			try {
//				renderPer = (countRender-countTick)*100/countAll*100;
//				renderPer /= 100;
//			} catch (java.lang.ArithmeticException e1) {
//				renderPer = 0;
//			}
//			
//			Display.setTitle("Tryangle - Memory: " + (Runtime.getRuntime().totalMemory()/1024/1024) + "MB" + " - FPS: " + fps + " Frametime: " + (countAll) +"ms"+ " - tick: " + tickPer + "% render: " + renderPer + "%");

			//Fullscreen?
			if(KeyboardListener.isKeyClicked(Keyboard.KEY_F11)){
				try {
					boolean isFull = Display.isFullscreen();
					Display.setFullscreen(!isFull);
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
			
			Display.sync(FPS);
		}
		System.out.println("END");
	}
	
	public void tick(){		
//		keyboard.tick();	
		mouse.tick();
		levelSelect.tick();
		sound.tick();
	}
 
	public void render(){
		levelSelect.render();

		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_POINTS);
			GL11.glVertex2f(MouseListener.getMouseX(), MouseListener.getMouseY());
		GL11.glEnd();
	}
	

	private void resize() {
		System.out.println(Display.getWidth());
		System.out.println(Display.getHeight());
		
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
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
			PixelFormat p = new PixelFormat().withSamples(4);
			Display.setFullscreen(fullscreen);
			
			Display.create(p);
				
	    } catch (LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}

	
	private void frameCounter(){
		this.frame++;
		if((this.fpsCounter+1000) <= System.currentTimeMillis()){
			this.fps = this.frame;
			this.frame = 0;
			this.fpsCounter = System.currentTimeMillis(); 
		}
	}
}
