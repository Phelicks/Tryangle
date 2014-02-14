package com.januskopf.tryangle.triangles;

import org.lwjgl.opengl.GL11;

public class TriangleRenderer {
	
	public static void renderTriangle(Triangle t, int x, int y, float xView, float yView, float height, float length){
		float xPos = xView + x*height;
		float yPos = yView + y*length/2;
		float tHeight = height;
		if(TriangleContainer.isTriangleLeft(x, y)){
			tHeight = tHeight * -1;
			xPos = xPos + height;
		}
		
		if(t.getColorR() != 0 || t.getColorG() != 0 || t.getColorB() != 0){
			GL11.glColor3f(t.getColorR(), t.getColorG(), t.getColorB());
			GL11.glBegin(GL11.GL_TRIANGLES);
				GL11.glVertex2f(xPos , yPos);//Top
				GL11.glVertex2f(xPos, yPos+length);//Bottom
				GL11.glVertex2f(xPos + tHeight, yPos+length/2);//Left/Right
			GL11.glEnd();
		}
	}

}
