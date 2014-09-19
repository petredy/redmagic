package com.petredy.redmagic.client;

import org.lwjgl.LWJGLException;

import com.petredy.redmagic.client.render.Render;
import com.petredy.redmagic.client.utils.LoggerUtils;
import com.petredy.redmagic.client.utils.Window;
import com.petredy.redmagic.client.utils.resource.ResourceManager;

public class Client {

	private static Client instance;
	
	private Window window;
	private ResourceManager resourceManager;
	
	private long lastFrameTime;
	private float deltaTime;
	
	public Client(){
		resourceManager = new ResourceManager();
	}
	
	
	public void start() throws LWJGLException{
		LoggerUtils.init();
		
		window = new Window();
		window.setTitle("redmagic");
		window.open(800, 600, false);
		LoggerUtils.logSystemInfo(Client.class);
		
		
		window.initGL3D();
		Render renderer = new Render();
		
		resourceManager.loadTextures();
		resourceManager.loadShaders();
		
		lastFrameTime = System.currentTimeMillis();
		while(!window.isCloseRequested()){
			long time = System.currentTimeMillis();
			deltaTime = (time - lastFrameTime) / 1000f;
			lastFrameTime = time;
			
			renderer.render();
			
			window.update();
		}
		
		renderer.dispose();
		
		window.dispose();
	}
	
	public ResourceManager getResourceManager(){
		return resourceManager;
	}
	
	public static void main(String[] args) throws LWJGLException{
		Client.instance = new Client();
		Client.instance.start();
	}
	
	public static Client getInstance(){
		return instance;
	}
	
}
