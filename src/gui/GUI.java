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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	BufferedWriter outs;
	BufferedReader ins;
	private int lineNumber = 1;

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
		lineNumber = 1;
		serial = new NRSerialPort("COM6", 250000);
		serial.connect();
		ins = new BufferedReader(new InputStreamReader(serial.getInputStream()));

		outs = new BufferedWriter(new OutputStreamWriter(serial.getOutputStream()));

		String input = "";
		while (!input.equals("echo:SD card ok") ) {
			try {
				if (ins.ready()) {
					input = ins.readLine();
					System.out.println(input);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("I am here!");
		
		runCommand("G0 F10000");
		runCommand("G28");
		
		
		
		
		
		moveArm("20");
		
		runCommand("G0 X300 Z300");
		
		moveArm("70");
		
		runCommand("G0 X300 Z100");
		
		moveArm("20");
		
		runCommand("G0 X100 Z300");
		
		moveArm("70");
		
		runCommand("G0 X300 Z300");
		
		moveArm("20");
		
		runCommand("G0 X50 Z30");
		
		moveArm("70");
		
		runCommand("G0 X300 Z300");
		
		moveArm("20");
		
		runCommand("G0 X0 Z0");
		
		runCommand("M400");
		
		
		
	}

	private void moveArm(String string) {
		runCommand("M280 P0 S" + string);
		
	}

	private void sendString(String message) {
		System.out.println("LineNumber: " + lineNumber);
		try {
			message = "N" + lineNumber++ + " " + message;
			
			message = message.replaceAll("[;(]+.*[\n)]*", ""); 
			message = message.replace(" ", "");
			
			message = message + "*";
			
			int cs = 0;
			char [] cmd = message.toCharArray();
			for(int i = 0; cmd[i] != '*'; i++){
				cs = cs ^ cmd[i];
			}
			cs &= 0xff;
			
			message = message + cs;
			
			outs.write(message);
			System.out.println("Message To send: " + message);
			outs.newLine();
			outs.flush();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void runCommand(String gcode){
		sendString(gcode);
		
		String input = "";
		while (!input.equals("ok") ) {
			try {
				if (ins.ready()) {
					input = ins.readLine();
					System.out.println(input);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	

	@Override
	public void update(long elapsedTime) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Game.end();

		if (Keyboard.isKeyDown(Keyboard.KEY_T) && playerMoved == false)
			playerMoved = true;

		if (playerMoved) {
			String playerMove = "";
			board.movePiece(ChessEngine.getInstance().move(playerMove));

			if (playerMoved) {

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
		serial.disconnect();
	}

	public static void main(String[] args) {
		System.out.println("Starting Test..");
		try {
			Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
			while (ports.hasMoreElements())
				System.out.println(ports.nextElement().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Error er) {
			er.printStackTrace();
		}
		new GUI();
	}
}
