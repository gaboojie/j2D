package j3D.engine.model;

import org.joml.Vector3f;

public class Transform {

	public Vector3f position, rotation, scale;
	
	public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Transform(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		this.scale = new Vector3f(1,1,1);
	}
	
	public Transform(Vector3f position) {
		this.position = position;
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1,1,1);
	}
	
	public Transform() {
		this.position = new Vector3f(0,0,0);
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1,1,1);
	}
	
}
