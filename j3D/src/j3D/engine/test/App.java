package j3D.engine.test;

public class App {

	private static final int START_SCREEN_WIDTH = 1024, START_SCREEN_HEIGHT = 768;
	private static final String START_SCREEN_TITLE = "j2D";
	
	public static void main(String[] args) {
		Game game = new Game(START_SCREEN_WIDTH, START_SCREEN_HEIGHT, START_SCREEN_TITLE);
		game.run();
	}
	
}
