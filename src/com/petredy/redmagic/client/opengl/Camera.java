package com.petredy.redmagic.client.opengl;

import java.nio.FloatBuffer;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.petredy.redmagic.client.utils.LoggerUtils;
import com.petredy.redmagic.client.utils.MatrixUtils;

public class Camera {

	private Matrix4f viewMatrix;
	private Matrix4f projectionMatrix;
	
	private Vector3f position = new Vector3f();
	
	private Quaternion orientation = new Quaternion(0.0f, 0f, 0.0f, 1.0f);
	
	private Logger logger = LoggerUtils.getLogger(Camera.class);
	
	public Camera(){
		viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
	}
	
	public void lookAt(Vector3f position, Vector3f targetPosition, Vector3f upVector){
		this.position = position;
		
		viewMatrix.setIdentity();
		
		Vector3f deltaVector = new Vector3f();
		Vector3f.sub(targetPosition, position, deltaVector);
		deltaVector.normalise();
		
	    upVector.normalise();
	    
	    Vector3f rightVector = new Vector3f();
	    Vector3f.cross(deltaVector, upVector, rightVector);
	    rightVector.normalise();
	    
	    Vector3f.cross(rightVector, deltaVector, upVector);

	    viewMatrix.m00 = rightVector.x;
	    viewMatrix.m01 = rightVector.y;
	    viewMatrix.m02 = rightVector.z;
	    
	    viewMatrix.m10 = upVector.x;
	    viewMatrix.m11 = upVector.y;
	    viewMatrix.m12 = upVector.z;
	    
	    viewMatrix.m20 = -deltaVector.x;
	    viewMatrix.m21 = -deltaVector.y;
	    viewMatrix.m22 = -deltaVector.z;
		
		translate(position);
		
		/*
		String text = "delta:" + deltaVector.toString() + "\n";
		text += "right:" + rightVector.toString() + "\n";
		text += "up:" + upVector.toString() + "\n";
		text += "pos:" + position.negate(null).toString() + "\n";
		text += viewMatrix.toString();
		logger.info(text);
		*/
	}
	
	public void calculateViewMatrix(){
		viewMatrix.setIdentity();
		Matrix4f.mul(viewMatrix, MatrixUtils.convert(orientation), viewMatrix);
		viewMatrix.translate(position);
		
	}
	
	public void roll(float angle){
		viewMatrix.rotate((float)Math.toRadians(angle), new Vector3f(0.0f, 0.0f, 1.0f));
	}

	
	public void setFieldOfView(float angle, float aspectRatio, float near, float far){
		projectionMatrix = new Matrix4f();
		projectionMatrix.setIdentity();
	
		float range = (float) (Math.tan(angle * 0.5d) * near);
		
		projectionMatrix.m00 = (2 * near) / (range * aspectRatio +  range * aspectRatio);
		projectionMatrix.m11 = near / range;
		projectionMatrix.m22 = -(far + near) / (far - near);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -(2 * far * near) / (far - near);
		projectionMatrix.m33 = 0;
		
		//logger.info(projectionMatrix.toString());
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public void bind(Shader shader){
		FloatBuffer viewBuffer = BufferUtils.createFloatBuffer(16);
		
		calculateViewMatrix();
		
		viewMatrix.store(viewBuffer);
		viewBuffer.flip();
		FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
		projectionMatrix.store(projectionBuffer);
		projectionBuffer.flip();
		
		GL20.glUniformMatrix4(shader.getViewId(), false, viewBuffer);
		GL20.glUniformMatrix4(shader.getProjectionId(), false, projectionBuffer);
	}
	
	public void translate(Vector3f position){
		Vector3f.add(this.position, position.negate(null), this.position);
	}
	
	public Vector3f getPosition(){
		logger.info(viewMatrix.toString());
		return position;
	}
}
