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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import chessEngine.ChessEngine;
import gnu.io.CommPortIdentifier;
import gnu.io.NRSerialPort;
import util.Game;


public class GUI extends Game {

	
	
	
	boolean playerMoved;
	NRSerialPort serial;
	
	@Override
	public void init() {
		Display.setTitle("ChessMate V0.01");

		ChessEngine.getInstance();

		setupSerialConnection();

		board.init();

		glMatrixMode(GL_PROJECTION);
		glOrtho(0, board.squareSize * 8, board.squareSize * 8, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(1f, 1f, 1f, 0f);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
	}

	private void setupSerialConnection() {
		serial = new NRSerialPort("COM6", 115200);
		serial.connect();
		DataInputStream ins = new DataInputStream(serial.getInputStream());

		DataOutputStream outs = new DataOutputStream(serial.getOutputStream());

		int b = 0;
		try {
			b = ins.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			outs.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		serial.disconnect();
	}

	@Override
	public void update(long elapsedTime) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Game.end();
		

		if (Keyboard.isKeyDown(Keyboard.KEY_T) && playerMoved == false)
			playerMoved = true;
				
		if(playerMoved){
			String playerMove = "";
			board.movePiece(ChessEngine.getInstance().move(playerMove));

		
		
		if(playerMoved){
			

			playerMoved = false;
		}
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
		System.out.println("Starting Test..");
		try{
			Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
			while(ports.hasMoreElements())
				System.out.println(ports.nextElement().getName());
		}catch(Exception ex){
			ex.printStackTrace();
		}catch(Error er){
			er.printStackTrace();
		}
		new GUI();
	}
}
