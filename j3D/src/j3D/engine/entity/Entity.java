package j3D.engine.entity;

import j3D.engine.model.Model;
import j3D.engine.model.Transform;

public class Entity {

	public Model model;
	public Transform transform;
	
	public Entity(Model model, Transform transform) {
		this.model = model;
		this.transform = transform;
	}
	
}
