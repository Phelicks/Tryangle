package com.januskopf.tryangle.level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeSetter;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class Level1 extends Levels{
	
	private int yTriangles = 40;
	private float triangleLength = (float)Tryangle.HEIGHT/((float)yTriangles-2)*2.1f;
	private int xTriangles = (int)((float)Tryangle.WIDTH /((float)Math.sqrt(3)*(triangleLength/2)));

	private VerticeContainer verticeContainer;
	private TriangleContainer triangles;
	private CubeContainer cubes;
	private CubeSetter cubeSetter;
	
//	private Sound sound = Sound.getInstance();
	
	public Level1() {
		verticeContainer = new VerticeContainer(xTriangles, yTriangles, triangleLength);
		triangles = new TriangleContainer(xTriangles, yTriangles, triangleLength);
		cubes = new CubeContainer(verticeContainer, triangles);
		cubeSetter = new CubeSetter(cubes, triangles, verticeContainer);
		
		triangles.addAnimation(new FadeAnimation(triangles, xTriangles, yTriangles));
		//triangles.addAnimation(new RandomFlashing(triangles, xTriangles, yTriangles));
		
	}
		
	public void tick(){
		triangles.tick();
		//cubeSetter.tick();
		cubes.tick();

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
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_F8))verticeContainer.render();
	}
}	
