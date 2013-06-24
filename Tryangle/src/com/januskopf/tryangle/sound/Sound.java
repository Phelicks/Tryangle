package com.januskopf.tryangle.sound;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {
	
	
	
	  IntBuffer buffer = BufferUtils.createIntBuffer(1);

	  /** Sources are points emitting sound. */
	  IntBuffer source = BufferUtils.createIntBuffer(1);

	  /** Position of the source sound. */
	  FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	  /** Velocity of the source sound. */
	  FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	  /** Position of the listener. */
	  FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	  /** Velocity of the listener. */
	  FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

	  /** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
	  FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();

	  
	  int loadALData() {
		  	
		  	System.setProperty("org.lgjwl.util.Debug", "true");
		  
		  	System.out.println("LoadALData()");
		  
		    // Load wav data into a buffer.
		    AL10.alGenBuffers(buffer);

		    if(AL10.alGetError() != AL10.AL_NO_ERROR){
		    	System.out.println("AL10Error");
		    	return AL10.AL_FALSE;
		    }
		    
		    //Loads the wave file from your file system
		    java.io.FileInputStream fin = null;
		    try {
		      fin = new java.io.FileInputStream("res/Soundtrack.wav");
		      if(fin != null){
		    	  System.out.println("File Loaded");
		      }
		      
		    } 
		    catch (java.io.FileNotFoundException ex) {
		      
		    	ex.printStackTrace();
		    	return AL10.AL_FALSE;
		    }
		    
		    WaveData waveFile = WaveData.create(new BufferedInputStream(fin));
		    
		    
		    
		    try {
		    	fin.close();
		    }
		    catch (java.io.IOException ex) {
		    }
			
		    
		    //Loads the wave file from this class's package in your classpath
		    //WaveData waveFile = WaveData.create("bass20.wav");
		    AL10.alBufferData(buffer.get(0),waveFile.format,waveFile.data,waveFile.samplerate);
		    
		    waveFile.dispose();

		    // Bind the buffer with the source.
		    AL10.alGenSources(source);

		    if (AL10.alGetError() != AL10.AL_NO_ERROR){
		    	System.out.println("AL_FAlse(2)");
		    	return AL10.AL_FALSE;
		    }
		    AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffer.get(0) );
		    AL10.alSourcef(source.get(0), AL10.AL_PITCH,    1.0f          );
		    AL10.alSourcef(source.get(0), AL10.AL_GAIN,     1.0f          );
		    AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos     );
		    AL10.alSource (source.get(0), AL10.AL_VELOCITY, sourceVel     );

		    // Do another error check and return.
		    if (AL10.alGetError() == AL10.AL_NO_ERROR){
		    	System.out.println("AL_TRUE");
		    	return AL10.AL_TRUE;
		    }
		    System.out.println("AL_FALSE");
		    return AL10.AL_FALSE;
		  }
	  
	  void setListenerValues() {
		    AL10.alListener(AL10.AL_POSITION,    listenerPos);
		    AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
		    AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
		  }
	  
	  void killALData() {
		    AL10.alDeleteSources(source);
		    AL10.alDeleteBuffers(buffer);
		  }
	  
	  public void initialize() {
		  	System.out.println("Initialize");
		    // Initialize OpenAL and clear the error bit.
		    try{
		      AL.create();
		    } catch (LWJGLException le) {
		      le.printStackTrace();
		      return;
		    }
		    AL10.alGetError();
		    
		    // Load the wav data.
		    if(loadALData() == AL10.AL_FALSE) {
		      System.out.println("Error loading data.");
		      return;
		    }
		    
		    
		    setListenerValues();
		    System.out.println("OpenAL initialized");
	  }
	  
	  public void start(){
	  	AL10.alSourcePlay(source.get(0));
	  }
	  
	  public void stop(){
		  AL10.alSourceStop(source.get(0));
	  }
	  
	  public void end(){
		    killALData();
		    AL.destroy();
		 }
}	
