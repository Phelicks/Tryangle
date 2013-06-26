package com.januskopf.tryangle;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.LevelSelection;
import com.januskopf.tryangle.sound.Sound;
 
public class Tryangle implements Runnable{
	
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final static int WIDTH = (int)screenSize.getWidth();
	public final static int HEIGHT = (int)screenSize.getHeight();
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
	
	public void initDisplay(){
		try{			
			DisplayMode displayMode = null;
	        DisplayMode[] modes = Display.getAvailableDisplayModes();

	         for (int i = 0; i < modes.length; i++){
             if (modes[i].getWidth() == Tryangle.WIDTH
	             && modes[i].getHeight() == Tryangle.HEIGHT
	             && modes[i].isFullscreenCapable()) {
                    displayMode = modes[i];
                    break;
               }
	        }
	        System.out.println("\nMode: " + displayMode.toString());
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
		
		try {
			Display.setFullscreen(true);
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}
		
		sound.initialize();
		sound.start(sound.SOUNDTRACK);
		sound.setVolume(sound.SOUNDTRACK,0.8f);
		
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
}
