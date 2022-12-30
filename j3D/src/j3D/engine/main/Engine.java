package j3D.engine.main;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL14;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public abstract class Engine {

	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private String SCREEN_TITLE;
	private long WINDOW_ID;
	
	public Engine(int width, int height, String title) {
		this.SCREEN_WIDTH = width;
		this.SCREEN_HEIGHT = height;
		this.SCREEN_TITLE = title;
	}
	
	public void run() {
		System.out.println("Running LWJGL " + Version.getVersion() + "!");
		
		// Initialize
		init();
		start();
		
		// Game loop
		loop();
		
		// Finish
		finish();
		terminate();
	}
	
	public abstract void start();
	public abstract void render();
	public abstract void update();
	public abstract void finish();
	
	//
	
	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		if ( !GLFW.glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // the window will be resizable
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);
		
		// Create the window
		WINDOW_ID = GLFW.glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_TITLE, MemoryUtil.NULL, MemoryUtil.NULL);
		if (WINDOW_ID == MemoryUtil.NULL) throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		GLFW.glfwSetKeyCallback(WINDOW_ID, (window, key, scancode, action, mods) -> {
			if ( key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE )
				GLFW.glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = MemoryStack.stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			GLFW.glfwGetWindowSize(WINDOW_ID, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			// Center the window
			GLFW.glfwSetWindowPos(
				WINDOW_ID,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		GLFW.glfwMakeContextCurrent(WINDOW_ID);
		// Enable v-sync
		GLFW.glfwSwapInterval(1);

		GLFW.glfwShowWindow(WINDOW_ID);
		
		GL.createCapabilities();

		GL14.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		GL14.glEnable(GL14.GL_DEPTH_TEST);
	}
	
	private void loop() {
		while ( !GLFW.glfwWindowShouldClose(WINDOW_ID) ) {
			GLFW.glfwPollEvents();
			//GL14.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
			GL14.glClear(GL14.GL_COLOR_BUFFER_BIT | GL14.GL_DEPTH_BUFFER_BIT); 
			
			update();
			render();
			
			GLFW.glfwSwapBuffers(WINDOW_ID); 
		}
	}
	
	private void terminate() {
		Callbacks.glfwFreeCallbacks(WINDOW_ID);
		GLFW.glfwDestroyWindow(WINDOW_ID);

		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
	public int getWidth() {
		return SCREEN_WIDTH;
	}
	
	public int getHeight() {
		return SCREEN_HEIGHT;
	}
	
}
