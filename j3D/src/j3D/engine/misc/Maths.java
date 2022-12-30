package j3D.engine.misc;

import org.joml.Matrix4f;

import j3D.engine.model.Transform;

public class Maths {

	private static Matrix4f transformationMatrix = new Matrix4f(), projectionMatrix = new Matrix4f(), viewMatrix = new Matrix4f();
	
	public static Matrix4f createTransformationMatrix(Transform transform) {
		transformationMatrix.identity().translate(transform.position).
			rotateX((float) Math.toRadians(transform.rotation.x)).
			rotateY((float) Math.toRadians(transform.rotation.y)).
			rotateZ((float) Math.toRadians(transform.rotation.z)).
			scale(transform.scale);
        return transformationMatrix;
	}
	
    public static Matrix4f createProjectionMatrix(int width, int height, float fov, float nearPlane, float farPlane) {
        float aspectRatio = (float) width / (float) height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, nearPlane, farPlane);
        return projectionMatrix;
    }
    
    public static Matrix4f createViewMatrix(Transform camera) {
        viewMatrix.identity();
        viewMatrix.rotateX((float) Math.toRadians(camera.rotation.x)).
				   rotateY((float) Math.toRadians(camera.rotation.y)).
				   rotateZ((float) Math.toRadians(camera.rotation.z));
        viewMatrix.translate(-camera.position.x, -camera.position.y, -camera.position.z);

        return viewMatrix;
    }
	
}
