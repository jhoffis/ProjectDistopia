package window;

import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

	private GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint();
	private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
				glfwSetWindowShouldClose(window, true);
			}
		}
	};
	
	public Window(int width, int height, String title) {
		
		glfwSetErrorCallback(errorCallback);
		
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to init GLFW");
		}
		
		//We're not going to use monitor and share atm
		long window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL) {
			glfwTerminate();
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		glfwSetKeyCallback(window, keyCallback);
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(1);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
//		glfwMaximizeWindow(window);
		
		IntBuffer w = MemoryUtil.memAllocInt(1);
		IntBuffer h = MemoryUtil.memAllocInt(1);
		
		//Update and rendering loop
		while (!glfwWindowShouldClose(window)) {
			//do stuff
			float ratio;
			double time = glfwGetTime();
			
			glfwGetFramebufferSize(window, w, h);
			ratio = w.get() / (float) h.get();
			
			w.rewind();
			h.rewind();
			
			glViewport(0, 0, w.get(), h.get());
			glClear(GL_COLOR_BUFFER_BIT);
			
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(-ratio, ratio, -1f, 1f, 1f, -1f);
			glMatrixMode(GL_MODELVIEW);
			
			glLoadIdentity();
			glRotatef((float) time * 50f, 0f, 0f, 1f);
			
			glBegin(GL_TRIANGLES);
			glColor3f(1f, 0f, 0f);
			glVertex3f(-0.6f, -0.4f, 0f);
			glColor3f(0f, 1f, 0f);
            glVertex3f(0.6f, -0.4f, 0f);
            glColor3f(0f, 0f, 1f);
            glVertex3f(0f, 0.6f, 0f);
            glEnd();
            
            glfwSwapBuffers(window);
    		glfwPollEvents();
    		
    		w.flip();
    		h.flip();
		}
		
		MemoryUtil.memFree(w);
		MemoryUtil.memFree(h);
		
		glfwDestroyWindow(window);
		keyCallback.free();
		
		glfwTerminate();
		errorCallback.free();
	}
}
