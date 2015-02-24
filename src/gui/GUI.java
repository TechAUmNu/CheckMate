package gui;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import chessEngine.ChessEngine;
import util.Game;


public class GUI extends Game {

	
	
	Board board;
	boolean playerMoved;

	
	@Override
	public void init() {
		Display.setTitle("ChessMate V0.01");
		
		board = new Board();
		board.init();

		glMatrixMode(GL_PROJECTION);
		glOrtho(0, 800, 800, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(1f, 1f, 1f, 0f);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void update(long elapsedTime) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Game.end();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			System.out.println("Starting engine");
			ChessEngine.getInstance();
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_1)){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			board.movePiece("a2a4");
			
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			board.movePiece(ChessEngine.getInstance().move("a2a4"));
		
		if (Keyboard.isKeyDown(Keyboard.KEY_3)){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			board.movePiece("g1f3");
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_4))
			board.movePiece(ChessEngine.getInstance().move("g1f3"));
		//We process the users interactions here
		// if(somethingchanged){
		// 		playerMoved = true;
		
		
		
		if(playerMoved){
			
			playerMoved = false;
		}
	}

	@Override
	public void render() {
		// Clear the screen
		glClear(GL_COLOR_BUFFER_BIT);
		board.draw();
	}

	@Override
	public void resized() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	@Override
	public void dispose() {
		board.dispose();
	}

	public static void main(String[] args) {
		new GUI();
	}
}
