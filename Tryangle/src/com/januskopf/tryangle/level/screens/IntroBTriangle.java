package com.januskopf.tryangle.level.screens;

import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


public class IntroBTriangle
{
  private float startX;
  private float startY;
  private float sX;
  private float sY;
  private float length;
  private float height;
  private float speed;
  private float brightness = 0.0F;
  private float maxBrightness;
  private float flicker = 0.0F;
  private float mod;
  private boolean bright = true;
  private boolean isActive = true;
  private boolean toRigth = false;
  private Random random;

  public IntroBTriangle(float x, float y)
  {
    this.random = new Random();
    this.startX = x;
    this.startY = y;
    this.sX = x;
    this.sY = y;

    this.mod = (Math.abs(0.5F - this.random.nextFloat()) + 0.5F);
    this.length = (40.0F - this.mod * 20.0F);
    this.height = ((float)Math.sqrt(3.0D) * (this.length / 2.0F));
    this.speed = (0.06F - this.mod / 1000.0F);
    this.maxBrightness = (1.0F - this.mod / 5.0F);

    if (x > Display.getWidth()/2) this.toRigth = true; 
  }

  public void tick()
  {
    if (this.toRigth)
      this.sX += this.speed;
    else {
      this.sX -= this.speed;
    }
    if ((this.brightness < this.maxBrightness) && (this.bright) && (this.isActive)) {
      this.brightness += this.speed / 10.0F;
    }
    else if (this.brightness > 0.0F) {
      this.brightness -= this.speed / 10.0F;
      this.bright = false;
    }
    else {
      this.isActive = false;
    }
  }

  public void render() {
    this.flicker = 0.0F;
    if (this.random.nextInt(7) == 0) this.flicker = (0.1F - this.mod / 10.0F - this.random.nextFloat() / 2.0F / 10.0F);

    GL11.glColor3f(0.6F * this.brightness + this.flicker, 0.6F * this.brightness + this.flicker, 0.6F * this.brightness + this.flicker);
    GL11.glBegin(4);
    GL11.glVertex2f(this.sX, this.sY);
    GL11.glVertex2f(this.sX, this.sY + this.length);

    if (this.toRigth)
      GL11.glVertex2f(this.sX + this.height, this.sY + this.length / 2.0F);
    else
      GL11.glVertex2f(this.sX - this.height, this.sY + this.length / 2.0F);
    GL11.glEnd();
  }

  public float getStartX() {
    return this.startX;
  }

  public float getStartY() {
    return this.startY;
  }

  public void setFastEnd() {
    this.speed *= 6.0F;
  }

  public boolean isActive() {
    return this.isActive;
  }
}