package j3D.engine.shaders;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import j3D.engine.io.FileIO;

public abstract class Shader {

	private int[] shaderIDs;
	private int shaderProgramID;
	
	
	public Shader(String vertexFile, String fragmentFile) {
		shaderIDs = new int[2];
		
		shaderIDs[0] = createShader(GL20.GL_VERTEX_SHADER, vertexFile);
		shaderIDs[1] = createShader(GL20.GL_FRAGMENT_SHADER, fragmentFile);
		
		createProgram();
	}
	
	public abstract void bindAtributes();
	public abstract void getUniformLocations();
	
	public int getUniformLocation(String uniformName) {
		int loc = GL20.glGetUniformLocation(shaderProgramID, uniformName);
		if (loc < 0) {
		    System.out.println("ERROR: Could not locate uniform with name \"" + uniformName + "\"");
		}
		return loc;
	}
	
	public void bindAttribute(int index, String caseSensitiveName) {
		GL20.glBindAttribLocation(shaderProgramID, index, caseSensitiveName);
	}
	
	public void bind() {
		GL20.glUseProgram(shaderProgramID);
	}
	
	public static void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void delete() {
		unbind();
		for (int shaderID : shaderIDs) {
			GL20.glDetachShader(shaderProgramID, shaderID);
			GL20.glDeleteShader(shaderID);
		}
		GL20.glDeleteProgram(shaderProgramID);
	}
	
	private void createProgram() {
		shaderProgramID = GL20.glCreateProgram();
		
		for (int shaderID : shaderIDs) {
			GL20.glAttachShader(shaderProgramID, shaderID);
		}
		bindAtributes();
		GL20.glLinkProgram(shaderProgramID);
		if(GL20.glGetProgrami(shaderProgramID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.out.println("Error creating program shader: ");
			System.out.println(GL20.glGetProgramInfoLog(shaderProgramID, 1024));
		    System.exit(1);
		    return;
		}	
		GL30.glValidateProgram(shaderProgramID);
		bind();
		getUniformLocations();
		unbind();
	}
	
	private int createShader(int SHADER_TYPE, String filePath) {
		int shaderID = GL20.glCreateShader(SHADER_TYPE);
		GL20.glShaderSource(shaderID, FileIO.getContentsOfPath(filePath));
		GL20.glCompileShader(shaderID);
		
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Error compiling shader: " + filePath);
			System.out.println("Error Message:\n" + GL20.glGetShaderInfoLog(shaderID, 1024));
			System.exit(1);
			return -1;
		}
		return shaderID;
	}
	
	//
	

    public void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    public void loadInt(int location, int value) {
        GL20.glUniform1i(location, value);
    }

    public void load2DVector(int location, Vector2f vector) {
        GL20.glUniform2f(location, vector.x, vector.y);
    }

    public void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    public void loadVector(int location, Vector4f vector) {
        GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    public void loadBoolean(int location, boolean value) {
        GL20.glUniform1f(location, (value ? 1 : 0));
    }

    public void loadMatrix(int location, Matrix4f matrix) {
    	try (MemoryStack stack = MemoryStack.stackPush()) {
    		FloatBuffer buffer = stack.mallocFloat(16);
    		matrix.get(buffer);
        	GL20.glUniformMatrix4fv(location, false, buffer);
    	}
    }
	
}
