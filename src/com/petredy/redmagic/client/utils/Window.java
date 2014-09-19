package com.petredy.redmagic.client.utils;

import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

	private Logger logger = LoggerUtils.getLogger(Window.class);
	
	public Window(){
		
	}
	
	public void initGL2D(){
		
	}
	
	public void initGL3D(){
		
		
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		// Enablse Face culling
		// The backside, which is drawn clockwise will not be drawn
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glFrontFace(GL11.GL_CCW);
		
	}
	
	public void setTitle(String title){
		Display.setTitle(title);
	}
	public void open(int width, int height, boolean fullscreen) throws LWJGLException{
		setDisplayMode(width, height, fullscreen);
		Display.create();
	}
	
	private void setDisplayMode(int width, int height, boolean fullscreen) {
		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) &&
				(Display.getDisplayMode().getHeight() == height) &&
				(Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {

			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i=0;i<modes.length;i++) {
					DisplayMode current = modes[i];
					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}


						// if we've found a match for bpp and frequence against the		
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
								(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}

			if (targetDisplayMode == null) {
				logger.warning("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			//Display.setFullscreen(fullscreen);


		} catch (LWJGLException e) {
			logger.warning("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
		}

	}
	
	public boolean isCloseRequested(){
		return Display.isCloseRequested();
	}
	
	
	public void update(){
		Display.update();
		Display.sync(60);
	}
	
	public void dispose(){
		Display.destroy();
		System.exit(0);
	}
}
