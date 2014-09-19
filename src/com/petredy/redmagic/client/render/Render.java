package com.petredy.redmagic.client.render;

import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.petredy.redmagic.client.Client;
import com.petredy.redmagic.client.opengl.Camera;
import com.petredy.redmagic.client.opengl.Mesh;
import com.petredy.redmagic.client.opengl.Shader;
import com.petredy.redmagic.client.utils.LoggerUtils;

public class Render {

	private byte[] indicies;
	
	private Logger logger = LoggerUtils.getLogger(Render.class);
	
	private Camera camera;
	
	private Mesh mesh, otherMesh;
	private RenderMesh meshRenderer, otherMeshRenderer;
	
	public Render(){
		
		Client.getInstance().getResourceManager().registerShader("shader.main", "assets/shaders/vertecies/vertex.glsl", "assets/shaders/fragments/fragment.glsl");
		
		float[] vertices = {
				-0.5f, 0.5f, 0f, 1f,   // Left top         ID: 0
				-0.5f, -0.5f, 0f, 1f,  // Left bottom      ID: 1
				0.5f, -0.5f, 0f, 1f,   // Right bottom     ID: 2
				0.5f, 0.5f, 0f, 1f  // Right left       ID: 3
		};
		
		float[] colors = {
			1f, 0f, 0f, 1f,
			0f, 1f, 0f, 1f,
			0f, 0f, 1f, 1f,
			1f, 1f, 1f, 1f
		};
		
		indicies = new byte[]{
			0, 1, 2,
			2, 3, 0
		};
		
		mesh = new Mesh(vertices, colors, indicies);
		otherMesh = new Mesh(new float[]{
			5.0f, 0.0f, 5.0f, 1.0f,
			5.0f, 0.0f, -5.0f, 1.0f,
			-5.0f, 0.0f, -5.0f, 1.0f,
			-5.0f, 0.0f, 5.0f, 1.0f,
		}, new float[]{
			0.5f, 0.5f, 0.5f, 1f,
			0.5f, 0.5f, 0.5f, 1f,
			0.5f, 0.5f, 0.5f, 1f,
			0.5f, 0.5f, 0.5f, 1f
		}, indicies);
		mesh.createGLObject();
		otherMesh.createGLObject();
		
		mesh.translate(new Vector3f(0.0f, 1.0f, 0.0f));
		//mesh.rotate(45f, new Vector3f(0.0f, 0.0f, 1.0f));
		
		otherMesh.translate(new Vector3f(0.0f, -1.0f, 0.0f));
		
		otherMesh.rotate(45f, new Vector3f(1.0f, 0.0f, 0.0f));
		
		meshRenderer = new RenderMesh(mesh);
		otherMeshRenderer = new RenderMesh(otherMesh);
		
		camera = new Camera();
		//camera.lookAt(new Vector3f(0.0f, 2.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f));
		camera.setFieldOfView(90, Display.getWidth() / Display.getHeight(), 0.1f, 100.0f);
		
		camera.translate(new Vector3f(0, 2.0f, 10.0f));
		//camera.yaw(45);
		
	}
	
	public void render(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		Shader shader = Client.getInstance().getResourceManager().getShader("shader.main");
		shader.begin();
		
		
		
		camera.bind(shader);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		// Wire-Frame - Mode
		//GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
		
		meshRenderer.begin(shader);
		meshRenderer.render();
		meshRenderer.end();
		
		
		otherMeshRenderer.begin(shader);
		otherMeshRenderer.render();
		otherMeshRenderer.end();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			camera.translate(new Vector3f(-1.0f, 0.0f, 0.0f));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			camera.translate(new Vector3f(1.0f, 0.0f, 0.0f));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			camera.translate(new Vector3f(0.0f, 0.0f, -1.0f));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			camera.translate(new Vector3f(0.0f, 0.0f, 1.0f));
		}
		
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		shader.end();
	}
	
	public void dispose(){
		mesh.disposeGLObject();
		otherMesh.disposeGLObject();
		
		Client.getInstance().getResourceManager().getShader("shader.main").dispose();
	}
	
}
