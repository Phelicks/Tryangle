package com.januskopf.tryangle.level;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.shapeContainer.IntroBTriangle;

public class LevelSelection {

	private boolean isLevelSelect;
	private boolean loadLevel;
	private int texture;
	
	private static Levels currentLevel;
	private Random random;
	private ArrayList<IntroBTriangle> bTriangles = new ArrayList<IntroBTriangle>();
	
	private Rectangle[] buttons;
	
	public LevelSelection(){
		currentLevel  = new Level1();
		random = new Random();
		texture = this.loadTexture("/introBackground.png");
		this.init();
	}
	
	private void init(){
		isLevelSelect = true;
		loadLevel = false;
		bTriangles.clear();
		bTActive = true;
		buttonBrightness = 1.0f;
		b = 1.0f;
		f = 0;
		
		buttons = new Rectangle[3];
		
		buttons[0] = new Rectangle(Tryangle.WIDTH/2 -120, Tryangle.HEIGHT/2+130, 240, 40);
		buttons[1] = new Rectangle(Tryangle.WIDTH/2 -120, Tryangle.HEIGHT/2+200, 240, 40);
		buttons[2] = new Rectangle(Tryangle.WIDTH/2 -120, Tryangle.HEIGHT/2+270, 240, 40);
	}
	
	public void tick(){
		if(isLevelSelect){
			if(KeyboardListener.isKeyPressed(Keyboard.KEY_ESCAPE));
			this.setActiveLevel();
			this.bigTriangleTick();
			this.backTriangles();
			if(random.nextInt(20) == 0 && bTriangles.size() < 20 && !loadLevel){
				int x = (random.nextInt(Tryangle.WIDTH)/40)*40;
				int y = (random.nextInt(Tryangle.HEIGHT)/40)*40;
				if(x > Tryangle.WIDTH/2+length/2+10 || x < Tryangle.WIDTH/2-length/2-10 || y > Tryangle.HEIGHT/2+10)
					bTriangles.add(new IntroBTriangle(x, y));
			}			
		}
		else{
			if(KeyboardListener.isKeyPressed(Keyboard.KEY_ESCAPE)){
				this.init();
			}
			currentLevel.tick();
		}
	}
	
	public void render(){
		if(isLevelSelect){
			drawBackgroung();
			
			for (int i = 0; i < bTriangles.size(); i++) {
				bTriangles.get(i).render();
			}
			
			this.drawButtons();
			if (bTActive)this.bigTriangleRender();
		}
		else
			currentLevel.render();		
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	

	private void drawBackgroung() {				
		int picX = 100;
		int picY = 100;
				
		int xTimes = Tryangle.WIDTH / picX;
		int yTimes = Tryangle.HEIGHT/ picY;
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture);
		GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			
			GL11.glTexCoord2f(xTimes, 0);
			GL11.glVertex2f(Tryangle.WIDTH, 0);
			
			GL11.glTexCoord2f(xTimes, yTimes);
			GL11.glVertex2f(Tryangle.WIDTH, Tryangle.HEIGHT);
			
			GL11.glTexCoord2f(0, yTimes);
			GL11.glVertex2f(0, Tryangle.HEIGHT);
			
		GL11.glEnd();	
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	private float b = 1.0f; //brightness
	private float f = 0f; //flicker
	private boolean bTActive = true;
	
	private void bigTriangleRender(){	
		
		if(loadLevel && b > -0.5){
			b -= 0.02;
			if(random.nextInt(2) == 0) f = (0.1f - (random.nextFloat()/2f)/10f)*4;
		}
		else if(loadLevel){
			bTActive = false;
			isLevelSelect = false;
		}
		
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glColor3f(0.8f*b+f, 0.8f*b+f, 0.8f*b+f);
				GL11.glVertex2f(Tryangle.WIDTH/2, Tryangle.HEIGHT/2 + y);
			GL11.glColor3f(0.95f*b+f, 0.95f*b+f, 0.95f*b+f);
				GL11.glVertex2f(Tryangle.WIDTH/2 + length/2, Tryangle.HEIGHT/2 - height + y);
			GL11.glColor3f(0.95f*b+f, 0.95f*b+f, 0.95f*b+f);
				GL11.glVertex2f(Tryangle.WIDTH/2 - length/2, Tryangle.HEIGHT/2 - height + y);
		GL11.glEnd();

	}	
	
	float buttonBrightness;
	
	private void drawButtons(){		
		float t = 0.25f * buttonBrightness;
		float bb = 0.3f * buttonBrightness;
		
		if(loadLevel){
			buttonBrightness -= 0.1;
		}
		
		for (int i = 0; i < buttons.length; i++) {
			float x = (float)buttons[i].getX();	
			float y = (float)buttons[i].getY();	
			float l = (float)buttons[i].getWidth();		
			float h = (float)buttons[i].getHeight();		
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glColor3f(t, t, t);
					GL11.glVertex2f(x, y);
				GL11.glColor3f(t, t, t);
					GL11.glVertex2f(x+l, y);
				GL11.glColor3f(bb, bb, bb);
					GL11.glVertex2f(x+l, y+h);
				GL11.glColor3f(bb, bb, bb);
					GL11.glVertex2f(x, y+h);
			GL11.glEnd();
		}		
	}
	
	private void backTriangles(){
		for(int i=0; i < bTriangles.size(); i++){
			IntroBTriangle t = bTriangles.get(i);
			if(t.isActive())
				t.tick();
			else{
				bTriangles.remove(i);
				t = null;
			}
		}
	}
	
	private float y = 0;
	private float speed;
	private float length;
	private float height;
	private boolean up = false;
	
	private void bigTriangleTick(){
		length = 350.0f;
		height = ((float)Math.sqrt(3)*(length/2));
		
		if(y > 18 || y < 2){
			speed = 0.06f;
		}else{
			speed = 0.1f;			
		}
		
		if(y < 20 && !up){
			y += speed;
		}else if(up && y > 0){
			y -= speed;
		}
		else if(up){
			up = false;
		}
		else{
			up = true;
		}
	}
	
	private int loadTexture(String path){
		int tex;
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(Tryangle.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
		
		ByteBuffer b = BufferUtils.createByteBuffer((width * height) *3);
		tex = GL11.glGenTextures();
		
		for(int i = 0; i < pixels.length; i++){
			byte rr = (byte)((pixels[i] >> 16) & 0xFF);
			byte gg = (byte)((pixels[i] >> 8) & 0xFF);
			byte bb = (byte)((pixels[i]) & 0xFF);
			
			b.put(rr);
			b.put(gg);
			b.put(bb);
		}
		
		b.flip();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, b);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		return tex;
	}
	
	private void setActiveLevel(){
		if(!loadLevel && MouseListener.isButtonClicked(0)){
			int x = MouseListener.getMouseX();
			int y = MouseListener.getMouseY();

			if(buttons[0].contains(x, y)){
				for(int i=0; i < bTriangles.size(); i++){
					bTriangles.get(i).setFastEnd();
				}
				currentLevel = new Level1();
				loadLevel = true;
			}
			else if(buttons[1].contains(x, y)){
				for(int i=0; i < bTriangles.size(); i++){
					bTriangles.get(i).setFastEnd();
				}
				currentLevel = new Level2();
				loadLevel = true;				
			}
			else if(buttons[2].contains(x, y)){
				try {
					currentLevel = new Level3();
					loadLevel = true;
					for(int i=0; i < bTriangles.size(); i++){
						bTriangles.get(i).setFastEnd();
					}
				}catch (Exception e){
					System.out.println("Konnte Level 3 nicht laden.");
				}			
			}
		}
	}
	
	
	public static Levels getCurrentLevel(){
		return currentLevel;
	}
	
	public void goToLevelSelect(){
		this.isLevelSelect = true;
	}
	
}