package j3D.engine.model;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

public class Model {

	public Mesh[] meshes;
	
	public Model(Mesh[] meshes) {
		this.meshes = meshes;
	}
	
	public static Model load(String filePath) {
		AIScene scene = Assimp.aiImportFile(filePath, Assimp.aiProcess_Triangulate | Assimp.aiProcess_JoinIdenticalVertices);
		if (scene == null) {
			System.out.println("Error trying to open model: " + filePath);
			System.exit(1);
			return null;
		}
		Mesh[] meshes = new Mesh[scene.mNumMeshes()];
		PointerBuffer meshesBuffer = scene.mMeshes();
		for (int i = 0; i < meshes.length; i++) {
			meshes[i] = loadMesh(AIMesh.create(meshesBuffer.get(i)));
		}
		return new Model(meshes);
	}
	
	private static Mesh loadMesh(AIMesh aiMesh) {
		return new Mesh(getVertices(aiMesh), getFaces(aiMesh));
	}
	
	private static IntBuffer getFaces(AIMesh mesh) {
		IntBuffer buffer = BufferUtils.createIntBuffer(mesh.mNumFaces() * 3);
		
		AIFace.Buffer aiBuffer = mesh.mFaces();
		for (int i = 0; i < mesh.mNumFaces(); i++) {
			AIFace face = aiBuffer.get(i);
			if (face.mNumIndices() != 3) {
				throw new IllegalStateException("Mesh has faces that are not triangulated (having 3 indices)");
			}
			buffer.put(face.mIndices());
		}
		buffer.flip();
		return buffer;
	}
	
	private static float[] getVertices(AIMesh mesh) {
		float[] verticesArray = new float[mesh.mNumVertices() * 3];
		AIVector3D.Buffer vertices = mesh.mVertices();
		int i = 0;
		while (vertices.remaining() > 0) {
			AIVector3D vertex = vertices.get();
			verticesArray[i] = vertex.x();
			verticesArray[i+1] = vertex.y();
			verticesArray[i+2] = vertex.z();
			
			System.out.println("Vertex: " + verticesArray[i] + " " + verticesArray[i+1] + " " + verticesArray[i+2]);
			i += 3;
		}
		return verticesArray;
	}
	
	
}
