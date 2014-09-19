package com.petredy.redmagic.client.utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class MatrixUtils {

	public static Matrix4f rotate(float angle, Vector3f rotation){
		float rad = (float) (angle * (Math.PI / 360)); // multiplied by 2 for the vector/angle to quaternion
		Quaternion quad = createQuaternion(angle, rotation);
		quad.normalise();
		return convert(quad);
	}
	
	public static Quaternion createQuaternion(float angle, Vector3f axis){
		return new Quaternion((float) Math.sin(Math.toRadians(angle)) * axis.x, (float) Math.sin(Math.toRadians(angle)) * axis.y, (float) Math.sin(Math.toRadians(angle)) * axis.z, (float) Math.cos(Math.toRadians(angle)));
	}
	
	public static Matrix4f convert(Quaternion quad){
		Matrix4f mat = new Matrix4f();
		mat.m00 = (float) (1 - 2 * Math.pow(quad.y, 2) - 2 * Math.pow(quad.z, 2));
		mat.m10 = (float) (2 * quad.x * quad.y - 2 * quad.w * quad.z);
		mat.m20 = (float) (2 * quad.x * quad.z + 2 * quad.w * quad.y);
		mat.m30 = 0;
		
		mat.m01 = (float) (2 * quad.x * quad.y + 2 * quad.w * quad.z);
		mat.m11 = (float) (1 - 2 * Math.pow(quad.x, 2) - 2 * Math.pow(quad.z, 2));
		mat.m21 = (float) (2 * quad.y * quad.z - 2 * quad.w * quad.x);
		mat.m31 = 0;
		
		mat.m02 = (float) (2 * quad.x * quad.z - 2 * quad.w * quad.y);
		mat.m12 = (float) (2 * quad.y * quad.z + 2 * quad.w * quad.x);
		mat.m22 = (float) (1 - 2 * Math.pow(quad.x, 2) - 2 * Math.pow(quad.y, 2));
		mat.m32 = 0;
		
		mat.m03 = 0;
		mat.m13 = 0;
		mat.m23 = 0;
		mat.m33 = 1;
		
		return mat;
	}
	
}
