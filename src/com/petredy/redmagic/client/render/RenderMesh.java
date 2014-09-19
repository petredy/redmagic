package com.petredy.redmagic.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.petredy.redmagic.client.opengl.Mesh;
import com.petredy.redmagic.client.opengl.Shader;

public class RenderMesh {

	private Mesh mesh;
	
	public RenderMesh(Mesh mesh){
		this.mesh = mesh;
	}
	
	public void begin(Shader shader){
		GL20.glUniformMatrix4(shader.getModelId(), false, mesh.getBuffer());
		
		GL30.glBindVertexArray(mesh.getObjectId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIndexId());
	}
	
	public void render(){
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexLength(), GL11.GL_UNSIGNED_BYTE, 0);
	}
	
	public void end(){
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	
	
}
