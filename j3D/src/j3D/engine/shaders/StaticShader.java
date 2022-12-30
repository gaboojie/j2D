package j3D.engine.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class StaticShader extends Shader {

	private static final String VERTEX_SHADER_PATH = "C:\\Users\\gaboo\\eclipse-workspace\\j3D\\res\\shaders\\vertexShader.txt";
	private static final String FRAGMENT_SHADER_PATH = "C:\\Users\\gaboo\\eclipse-workspace\\j3D\\res\\shaders\\fragmentShader.txt";
	
	private int locationTransMat, locationProjMat, locationViewMat, locationColor;
	
	public StaticShader() {
		super(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);
	}

	@Override
	public void bindAtributes() {
		bindAttribute(0, "pos");
	}

	@Override
	public void getUniformLocations() {
		locationTransMat = getUniformLocation("transformationMatrix");
		locationColor = getUniformLocation("color");
		locationProjMat = getUniformLocation("projectionMatrix");
		locationViewMat = getUniformLocation("viewMatrix");
	}
	
	public void loadColor(Vector3f color) {
		super.loadVector(locationColor, color);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(locationTransMat, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(locationProjMat, matrix);
	}
	
	public void loadViewMatrix(Matrix4f matrix) {
		super.loadMatrix(locationViewMat, matrix);
	}
}
