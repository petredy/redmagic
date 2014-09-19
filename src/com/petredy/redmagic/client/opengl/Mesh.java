package com.petredy.redmagic.client.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.petredy.redmagic.client.utils.MatrixUtils;

public class Mesh {
	
	private float[] vertices, colors;
	private byte[] indicies;
	private Matrix4f matrix, translationMatrix, rotationMatrix, scaleMatrix;
	private FloatBuffer vertexBuffer, colorBuffer;
	private ByteBuffer indexBuffer;
	
	private int objectId, vertexId, colorId, indexId;
	
	public Mesh(float[] vertices, float[] colors, byte[] indicies){
		this.vertices = vertices;
		this.colors = colors;
		this.indicies = indicies;
		vertexId = GL15.glGenBuffers();
		colorId = GL15.glGenBuffers();
		indexId = GL15.glGenBuffers();
		
		matrix = new Matrix4f();
		matrix.setIdentity();
		translationMatrix = new Matrix4f();
		translationMatrix.setIdentity();
		rotationMatrix = new Matrix4f();
		rotationMatrix.setIdentity();
		scaleMatrix = new Matrix4f();
		scaleMatrix.setIdentity();
	}
	
	public void translate(Vector3f translation){
		translationMatrix.translate(translation);
	}
	
	public void rotate(float angle, Vector3f axis){
		Matrix4f.mul(rotationMatrix, MatrixUtils.rotate(angle, axis), rotationMatrix);
	}
	
	public FloatBuffer getBuffer(){
		calculateModelMatrix();
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix.store(buffer);
		buffer.flip();
		return buffer;
	}
	
	public void calculateModelMatrix(){
		Matrix4f.mul(translationMatrix, rotationMatrix, matrix);
		Matrix4f.mul(matrix, scaleMatrix, matrix);
	}
	
	public void createGLObject(){
		objectId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(objectId);
		
		// Generate Vertex Buffer
		setVertices(vertices);
		
		// Generate Color Buffer
		setColors(colors);
		
		// Generate Index Buffer
		setIndicies(indicies);
		
		GL30.glBindVertexArray(0);
	}
	
	public void setVertices(float[] vertices){
		vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertexBuffer.put(vertices);
		vertexBuffer.flip();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void setColors(float[] colors){
		colorBuffer = BufferUtils.createFloatBuffer(colors.length);
		colorBuffer.put(colors);
		colorBuffer.flip();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void setIndicies(byte[] indicies){
		indexBuffer = BufferUtils.createByteBuffer(indicies.length);
		indexBuffer.put(indicies);
		indexBuffer.flip();
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void disposeGLObject(){
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(indexId);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vertexId);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(colorId);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(objectId);
	}
	
	public int getIndexLength() {
		return indicies.length;
	}
	
	public int getObjectId() {
		return objectId;
	}
	
	public int getIndexId() {
		return indexId;
	}
}
