package com.januskopf.tryangle.level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.alt.CubeContainer;
import com.januskopf.tryangle.alt.CubeSetter;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.triangles.TriangleContainer;

public class Level1 extends Levels{
	
	private int yTriangles = 40;
	private float triangleLength = (float)Tryangle.HEIGHT/((float)yTriangles-2)*2.1f;
	private int xTriangles = (int)((float)Tryangle.WIDTH /((float)Math.sqrt(3)*(triangleLength/2)));

	private TriangleContainer triangles;
	private CubeContainer cubes;
	private CubeSetter cubeSetter;

	private Animations fadeAnimation;
	private Animations flashAnimation;
	
//	private Sound sound = Sound.getInstance();
	
	public Level1() {
		triangles = new TriangleContainer(xTriangles, yTriangles, triangleLength);
		cubes = new CubeContainer(triangles);
		cubeSetter = new CubeSetter(cubes, triangles);
		
		fadeAnimation = new FadeAnimation(triangles, xTriangles, yTriangles);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);
		
		triangles.addAnimation(fadeAnimation);
		
	}
		
	public void tick(){
		triangles.tick();
		//cubeSetter.tick();
		cubes.tick();
		if(fadeAnimation != null && !fadeAnimation.isActive() ){
			triangles.addAnimation(flashAnimation);
			fadeAnimation = null;
		}
	}
		

	public void render(){
		GL11.glColor3f(0, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(Display.getWidth(), 0);
			GL11.glVertex2f(Display.getWidth(), Display.getHeight());
			GL11.glVertex2f(0, Display.getHeight());
		GL11.glEnd();
		triangles.render();
	}
}	
