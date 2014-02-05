package com.januskopf.tryangle.triangles.animations;

import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.effects.ColorFlash;

public class WaterAnimation extends Animations{
	
	private boolean isActive = true;
	private int xPos;
	private int yPos;
	private float iRadius;
	private int radius;
	
	float colorR;
	float colorG;
	float colorB;
	
	private TriangleContainer triangles;
	
	public WaterAnimation(TriangleContainer triangles, int xPos, int yPos, float colorR, float colorG, float colorB, int radius){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.xPos = xPos;
		this.yPos = yPos;
		this.triangles = triangles;
		this.iRadius = 1;
		this.radius = radius;
	}
	
	public WaterAnimation(TriangleContainer triangles, int xPos, int yPos) {
		this(triangles, xPos, yPos, 1.0f, 1.0f, 1.0f, 300);		
	}

	@Override
	protected void startAnimation() {			
	}

	@Override
	protected void runAnimation() {
//		for(int i = 0; i < 360; i++){
//			float a = (float)(Math.sin(i)*iRadius);
//			float b = (float)(Math.cos(i)*iRadius);	
//			
//			Triangle t = triangles.getExactTriangle((int)(a+xPos), (int)(b+yPos));
//			if(t != null){
//				float d = 0.01f - (0.04f - (0.04f/iRadius));
//				t.addBottomLayerEffect(new ColorFlash(colorR+d, colorG+d, colorB+d, 40));
//			}
//		}
		
		int x0 = triangles.getIndexFromPos(xPos, yPos).x;
		int y0 = triangles.getIndexFromPos(xPos, yPos).y;
//		int dx = (int) -iRadius;
//		int dy = (int) -iRadius;
		int x = (int) iRadius;
		int y = 0;
		float xDif = iRadius;
		
//		while(y < x){
//			
//			/*
//			GL11.glColor3f(1, 0, 0);
//			GL11.glBegin(GL11.GL_POINTS);
//				GL11.glVertex2f(xPos + x , yPos + y);
//				GL11.glVertex2f(xPos - x , yPos + y);
//				GL11.glVertex2f(xPos + x , yPos - y);
//				GL11.glVertex2f(xPos - x , yPos - y);
//				
//				GL11.glVertex2f(xPos + y , yPos + x);
//				GL11.glVertex2f(xPos - y , yPos + x);
//				GL11.glVertex2f(xPos + y , yPos - x);
//				GL11.glVertex2f(xPos - y , yPos - x);
//			GL11.glEnd();
//			*/
//			Triangle[] triangle = new Triangle[8];
//			triangle[0]  = triangles.getTriangle(x0 + x, y0 + y);
//			triangle[1]  = triangles.getTriangle(x0 + x, y0 - y);
//			triangle[2]  = triangles.getTriangle(x0 - x, y0 + y);
//			triangle[3]  = triangles.getTriangle(x0 - x, y0 - y);
//			
//			triangle[4] = triangles.getTriangle(x0 + y, y0 + x);
//			triangle[5] = triangles.getTriangle(x0 + y, y0 - x);
//			triangle[6] = triangles.getTriangle(x0 - y, y0 + x);
//			triangle[7] = triangles.getTriangle(x0 - y, y0 - x);
//			
//			float c = 0.01f - (0.04f - (0.04f/iRadius));
//			for(Triangle t : triangle){				
//				if(t != null){
//					t.addBottomLayerEffect(new ColorFlash(colorR+c, colorG+c, colorB+c, 40));
//				}
//			}
//			
//			dx = dx + 2*y + 1;
//			y = y + 1;
//			if(dx > 0){
//				dx = dx - 2 * x + 2;
//				xDif = xDif - 0.866f;
//				x = Math.round(xDif);
//				//x = x - 1;
//			}
//		}
		
		
		float aDif = iRadius*0.6f;
		int a = Math.round(aDif);
		int b = (int)iRadius;
		int dx = 0;
		int dy = a;
		float a2 = a*a; 
		float b2 = b*b;
		float err = b2-(2*b-1)*a2;
		float e2;
		
		
		while(dy >= 0){
			
			
//			GL11.glColor3f(1, 0, 0);
//			GL11.glBegin(GL11.GL_POINTS);
//				GL11.glVertex2f(xPos + dx , yPos + dy);
//				GL11.glVertex2f(xPos - dx , yPos + dy);
//				GL11.glVertex2f(xPos + dx , yPos - dy);
//				GL11.glVertex2f(xPos - dx , yPos - dy);
//				
//			GL11.glEnd();
			
			Triangle[] triangle = new Triangle[6];
			triangle[0]  = triangles.getTriangle(x0 + dx+1, y0 + dy+1);
			triangle[1]  = triangles.getTriangle(x0 + dx+1, y0 - dy);
			triangle[2]  = triangles.getTriangle(x0 - dx, y0 + dy+1);
			triangle[3]  = triangles.getTriangle(x0 - dx, y0 - dy);
			
			
			
			e2 = 2*err;
			if (e2 < (2*dx+1)*b2){
				dx++;
				err += (2*dx+1)*b2;
			}
			if (e2 > -(2*dy+1)*a2){
				dy--;
				err -= (2*dy-1)*a2;
			}
//			while (dx++ < a){
//				triangle[4] = triangles.getTriangle(x0 + dx, y0);
//				triangle[5] = triangles.getTriangle(x0 - dx, y0);
//			}
			
			float c = 0.08f - (0.04f - (0.04f/iRadius));
			for(Triangle t : triangle){				
				if(t != null){
					t.addBottomLayerEffect(new ColorFlash(colorR+c, colorG+c, colorB+c, 40));
				}
			}
			
		}
		
		if(iRadius <= radius){
			iRadius += 0.3f;
		}
		else{
			iRadius = 50;
			isActive = false;
		}
	}

	@Override
	protected void endAnimation() {
		
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
