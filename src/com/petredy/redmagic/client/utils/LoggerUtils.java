package com.petredy.redmagic.client.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtils {

	private static Handler fileHandler;
	
	private static int[] params = new int[]{
		GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS,
		GL13.GL_MAX_CUBE_MAP_TEXTURE_SIZE,
		GL20.GL_MAX_DRAW_BUFFERS,
		GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS,
		GL20.GL_MAX_TEXTURE_IMAGE_UNITS,
		GL11.GL_MAX_TEXTURE_SIZE,
		GL20.GL_MAX_VARYING_FLOATS,
		GL20.GL_MAX_VERTEX_ATTRIBS,
		GL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS,
		GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS,
		GL11.GL_MAX_VIEWPORT_DIMS,
		GL11.GL_STEREO
	};
	
	private static String[] names = new String[]{
		"GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS",
		"GL_MAX_CUBE_MAP_TEXTURE_SIZE",
		"GL_MAX_DRAW_BUFFERS",
		"GL_MAX_FRAGMENT_UNIFORM_COMPONENTS",
		"GL_MAX_TEXTURE_IMAGE_UNITS",
		"GL_MAX_TEXTURE_SIZE",
		"GL_MAX_VARYING_FLOATS",
		"GL_MAX_VERTEX_ATTRIBS",
		"GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS",
		"GL_MAX_VERTEX_UNIFORM_COMPONENTS",
		"GL_MAX_VIEWPORT_DIMS",
		"GL_STEREO"
	};
	
	public static void init(){
		try {
			fileHandler = new FileHandler("log.txt");
			fileHandler.setLevel(Level.FINEST);
			fileHandler.setFormatter(new SimpleFormatter());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logSystemInfo(Class cls){
		Logger log = getLogger(cls);
		String text = "Redmagic log file!\n";
		text += "System information:\n";
		text += "GL Context params:\n";
		text += "GL_RENDERER: " + GL11.glGetString(GL11.GL_RENDERER) + "\n";
		text += "GL_VERSION: " + GL11.glGetString(GL11.GL_VERSION) + "\n";
		for(int i = 0; i < params.length; i++){
			text += names[i] + ": " + GL11.glGetInteger(params[i]) + "\n";
		}
		log.info(text);
	}
	
	public static Logger getLogger(Class cls){
		Logger log = Logger.getLogger(cls.getName());
		log.setLevel(Level.FINEST);
		log.addHandler(fileHandler);
		return log;
	}
	
}
