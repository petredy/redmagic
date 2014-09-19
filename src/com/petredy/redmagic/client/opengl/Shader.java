package com.petredy.redmagic.client.opengl;

import org.lwjgl.opengl.GL20;

public class Shader {
	
	private String name;
	private int id, viewId, projectionId, modelId;
	private ShaderProgram vertexShader, fragmentShader;
	
	public Shader(String name, String vPath, String fPath){
		this.name = name;
		vertexShader = new ShaderProgram(vPath, GL20.GL_VERTEX_SHADER);
		fragmentShader = new ShaderProgram(fPath, GL20.GL_FRAGMENT_SHADER);
	}
	
	public void load(){
		vertexShader.load();
		fragmentShader.load();
		
		id = GL20.glCreateProgram();
		GL20.glAttachShader(id, vertexShader.getId());
		GL20.glAttachShader(id, fragmentShader.getId());
		
		GL20.glBindAttribLocation(id, 0, "in_Position");
		GL20.glBindAttribLocation(id, 1, "in_Color");
		
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
		
		viewId = GL20.glGetUniformLocation(id, "view");
		projectionId = GL20.glGetUniformLocation(id, "projection");
		modelId = GL20.glGetUniformLocation(id, "model");
	}
	
	public int getViewId() {
		return viewId;
	}
	
	public int getProjectionId() {
		return projectionId;
	}
	
	public int getModelId() {
		return modelId;
	}
	
	public void begin(){
		GL20.glUseProgram(id);
	}
	
	public void end(){
		GL20.glUseProgram(0);
	}
	
	public void dispose(){
		GL20.glUseProgram(0);
		GL20.glDetachShader(id, vertexShader.getId());
		GL20.glDetachShader(id, fragmentShader.getId());
		
		vertexShader.dispose();
		fragmentShader.dispose();
		GL20.glDeleteProgram(id);
	}
}
