package com.petredy.redmagic.client.utils.resource;

import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;

import com.petredy.redmagic.client.opengl.Shader;

public class ResourceManager {

	private HashMap<String, TextureResource> textures = new HashMap<String, TextureResource>();
	private HashMap<String, Shader> shaders = new HashMap<String, Shader>();
	public ResourceManager(){
		
	}
	
	public void registerTexture(String name, String path){
		textures.put(name, new TextureResource(name, path));
	}
	
	public void registerShader(String name, String vPath, String fPath){
		shaders.put(name, new Shader(name, vPath, fPath));
	}
	
	public void loadTextures(){
		for(TextureResource res: textures.values()){
			res.load();
		}
	}
	
	public Texture getTexture(String name){
		TextureResource res = textures.get(name);
		if(res != null)return res.getTexture();
		return null;
	}
	
	public void loadShaders(){
		for(Shader shader: shaders.values()){
			shader.load();
		}
	}
	
	public Shader getShader(String name){
		return shaders.get(name);
	}
	
}
