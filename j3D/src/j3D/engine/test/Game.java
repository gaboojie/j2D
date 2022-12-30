package j3D.engine.test;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import j3D.engine.entity.Entity;
import j3D.engine.main.Engine;
import j3D.engine.misc.Maths;
import j3D.engine.model.Model;
import j3D.engine.model.Transform;
import j3D.engine.shaders.Shader;
import j3D.engine.shaders.StaticShader;

public class Game extends Engine {
	
	public static final float FOV = (float) Math.toRadians(60.0f), NEAR_PLANE = 0.1f, FAR_PLANE = 100f;
	
	public Transform camera;
	public StaticShader shader;
	public Entity entity;
	
	public Game(int width, int height, String title) {
		super(width, height, title);
	}

	@Override
	public void start() {
		shader = new StaticShader();
		
		Model model = Model.load("C:\\Users\\gaboo\\eclipse-workspace\\j3D\\res\\scenes\\test.dae");
		Transform transform = new Transform(new Vector3f(0,0,-5));
		entity = new Entity(model, transform);
		camera = new Transform(new Vector3f(0,0,0));
		
		shader.bind();
		shader.loadProjectionMatrix(Maths.createProjectionMatrix(getWidth(), getHeight(), FOV, NEAR_PLANE, FAR_PLANE));
		Shader.unbind();
	}

	@Override
	public void render() {
		shader.bind();
		shader.loadColor(new Vector3f(1,0,0));
		shader.loadViewMatrix(Maths.createViewMatrix(camera));
		shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.transform));
		GL30.glBindVertexArray(entity.model.meshes[0].vaoID);
		GL20.glEnableVertexAttribArray(0);
		
		GL20.glDrawArrays(GL11.GL_TRIANGLES, 0, 36);
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		Shader.unbind();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void finish() {
		entity.model.meshes[0].delete();
		shader.delete();
	}

}
