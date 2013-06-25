package com.januskopf.tryangle.sound;

import java.io.BufferedInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {

	public static final int SOUNDTRACK = 0;
	public static final String SOUNDTRACK_FILE = "Soundtrack.wav";
	
	public static final int DRAW_CUBE_1 = 1;
	public static final String DRAW_CUBE_FILE_1 = "DrawCube1.wav";
	
	public static final int DRAW_CUBE_2 = 2;
	public static final String DRAW_CUBE_FILE_2 = "DrawCube2.wav";
	
	public static final int DRAW_CUBE_3 = 3;
	public static final String DRAW_CUBE_FILE_3 = "DrawCube3.wav";
	
	public static final int CHANGE_COLOR = 4;
	public static final String CHANGE_COLOR_FILE = "ChangeColor.wav";
	
	public static final int SWOOSH = 5;
	public static final String SWOOSH_FILE = "Swoosh.wav";
	
	public static final int[] LOOPING = {SOUNDTRACK};
	public static final String[] WAV_FILES = {SOUNDTRACK_FILE,DRAW_CUBE_FILE_1,DRAW_CUBE_FILE_2,DRAW_CUBE_FILE_3,CHANGE_COLOR_FILE,SWOOSH_FILE};
	
	public static final int NUM_BUFFERS = WAV_FILES.length;
	public static final int NUM_SOURCES = WAV_FILES.length;
	
	private static Sound sound = new Sound();
	protected static IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
	protected static IntBuffer source = BufferUtils.createIntBuffer(NUM_BUFFERS);
	protected static FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3*NUM_BUFFERS).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	protected static FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3*NUM_BUFFERS).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	protected static FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	protected static FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	protected static FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();
	protected static boolean playing;
	private boolean tickPlay = false;
	private int tick = 0;
	private boolean[] startlist = new boolean[NUM_SOURCES];
	private int multi = 0;
	
	
	private Sound(){}
	
	public static Sound getInstance(){
		return sound;
	}
	

	  
	protected static int loadALData() {
		  	
	 	System.setProperty("org.lgjwl.util.Debug", "true");
		  
	  	System.out.println("LoadALData()");
		  
	    // Load wav data into a buffer.
	    AL10.alGenBuffers(buffer);

	    if(AL10.alGetError() != AL10.AL_NO_ERROR){
	    	System.out.println("AL10Error");
	    	return AL10.AL_FALSE;
	    }
		    
	    //Loads the wave file from your file system
	    for(int i = 0; i < NUM_SOURCES; i++){
		    java.io.FileInputStream fin = null;
		    try {
		      fin = new java.io.FileInputStream(WAV_FILES[i]);
		      if(fin != null){
		    	  System.out.println(WAV_FILES[i] + " loaded");
		      }
		    } 
		    catch (java.io.FileNotFoundException ex) {
		    	System.out.println("File " + WAV_FILES[i] + " not found");
		    	ex.printStackTrace();
			   	return AL10.AL_FALSE;
			}			 
			WaveData waveFile = WaveData.create(new BufferedInputStream(fin));			    
			try {
			   	fin.close();
			}
			catch (java.io.IOException ex) {
			}
			
			AL10.alBufferData(buffer.get(i),waveFile.format,waveFile.data,waveFile.samplerate);
			waveFile.dispose();
	    }
		// Bind the buffer with the source.
		AL10.alGenSources(source);
		if (AL10.alGetError() != AL10.AL_NO_ERROR){
			System.out.println("AL_FAlse(2)");
		    return AL10.AL_FALSE;
		}
		
		for(int i = 0; i < NUM_SOURCES; i++){
			System.out.println("Source " + i + " buffered");
			boolean isLoop = false;
			AL10.alSourcei(source.get(i), AL10.AL_BUFFER,   buffer.get(i) );
			AL10.alSourcef(source.get(i), AL10.AL_PITCH,    1.0f          );
			AL10.alSourcef(source.get(i), AL10.AL_GAIN,     1.0f          );
			AL10.alSource (source.get(i), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(i*3));
			AL10.alSource (source.get(i), AL10.AL_VELOCITY, (FloatBuffer) sourceVel.position(i*3));
			for(int h = 0; h < LOOPING.length; h++){
				if(LOOPING[h] == i){
					isLoop = true;
				}
			}
			if(isLoop == true){
				AL10.alSourcei(source.get(i), AL10.AL_LOOPING,  AL10.AL_TRUE);
			}
			else{
				AL10.alSourcei(source.get(i), AL10.AL_LOOPING,  AL10.AL_FALSE);
			}
			
		}
		// Do another error check and return.
		if (AL10.alGetError() == AL10.AL_NO_ERROR){
		  	System.out.println("AL_TRUE");
		   	return AL10.AL_TRUE;
		}
		System.out.println("AL_FALSE");
		return AL10.AL_FALSE;
	}
	  
	protected static void setListenerValues() {
		AL10.alListener(AL10.AL_POSITION,    listenerPos);
	    AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
	    AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}
	  
	protected static void killALData() {
	    AL10.alDeleteSources(source);
	    AL10.alDeleteBuffers(buffer);
	}
	  
	public static void initialize() {
		System.out.println("Initialize");
		// Initialize OpenAL and clear the error bit.
		try{
			AL.create();
		}
		catch (LWJGLException le) {
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
	  
	public static void start(int i){
		AL10.alSourcePlay(source.get(i));
	}
	
	public static void loop(int i){
		if(AL10.alGetSourcei(source.get(i), AL10.AL_SOURCE_STATE) != AL10.AL_PLAYING){
			AL10.alSourcePlay(source.get(i));
		}
	}
	
	public void startInBeat(int i){
		//System.out.println(i + "Start in Beat");
		tickPlay = true;
		startlist[i] = true;
	}
	
	public void startMulti(int i, int length){
		if(multi >= (i + length) || multi < i){
			multi = i;
		}
		System.out.println("multi: " + multi);
		start(i);
		multi++;
	}
		
	public void tick(){
		if(tick == 30){
			tick = 0;
			if(tickPlay == true){
				System.out.println(startlist.toString());
				for(int i = 0; i < startlist.length; i++){
					if(startlist[i]){
						start(i);
					}
				}
				tickPlay = false;
			startlist = new boolean[NUM_SOURCES];
			}
		}
		tick++;
	}
	
	public static void stop(int i){
		AL10.alSourceStop(source.get(i));
	}

	public static void end(){
		killALData();
		AL.destroy();
	}
}	