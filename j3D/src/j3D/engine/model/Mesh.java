package j3D.engine.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

	public int numVertices;
	public int vaoID;
	public int[] vboIDs;
	
	public Mesh(float[] vertices, IntBuffer indices) {
		vaoID = createVAO();
		vboIDs = new int[1];
		vboIDs[0] = storeDataInAttributeList(0, 3, vertices);
		
		for (int i = 0; i < indices.capacity(); i++)
			System.out.print(indices.get(i) + " ");
		
		numVertices = vertices.length / 3;
		unbindVAO();
	}
	
	public void delete() {
		Mesh.deleteVAO(vaoID);
		for (int vboID : vboIDs) {
			Mesh.deleteVBO(vboID);
		}
	}
	
	//
	
	private static int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	public static void deleteVAO(int vaoID) {
		GL30.glDeleteVertexArrays(vaoID);
	}
	
	public static void deleteVBO(int vboID) {
		GL15.glDeleteBuffers(vboID);
	}
	
	public static void deleteTextures(int textureID) {
		GL11.glDeleteTextures(textureID);
	}
	
	private static int storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		return vboID;
	}
	
    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }
}
