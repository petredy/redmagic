package com.petredy.redmagic.client.utils.resource;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class TextureResource {

	private String name;
	private String path;
	private Texture texture;
	
	public TextureResource(String name, String path){
		setName(name);
		setPath(path);
	}
	
	public void load(){
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Texture getTexture() {
		return texture;
	}
}
