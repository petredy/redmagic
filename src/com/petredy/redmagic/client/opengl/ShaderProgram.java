package com.petredy.redmagic.client.opengl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

import com.petredy.redmagic.client.utils.LoggerUtils;

public class ShaderProgram {

	private int id, type;
	private String path;
	
	public ShaderProgram(String path, int type){
		this.path = path;
		this.type = type;
	}
	
	public void load(){
		StringBuilder shaderSource = new StringBuilder();
		
		id = 0;
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while((line = reader.readLine()) != null){
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}catch(IOException e){
			System.err.println("Could not read shader file " + path + "!");
			e.printStackTrace();
		}
		
		id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, shaderSource);
		GL20.glCompileShader(id);
		
		// Logs shader compile errors
		if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == 0)
			LoggerUtils.getLogger(ShaderProgram.class).info(GL20.glGetShaderInfoLog(id, 1000));
		
	}
	
	public int getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public void dispose(){
		GL20.glDeleteShader(id);
	}
}
