package chessEngine;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;


public class ChessEngine {
	static ChessEngine instance;
	Process proc;
	BufferedReader in;
	PrintStream ps;
	boolean isInitialized;
	ArrayList<String> moves;
	
	static {
		instance = new ChessEngine();
	}
	private ChessEngine(){
		startEngine();
	}
	
	public static ChessEngine getInstance(){
		return instance;
	}
	
	private void startEngine(){
		// Run a java app in a separate system process
		
		try {
			isInitialized = false;
			moves = new ArrayList<String>();
			//proc = Runtime.getRuntime().exec("java -jar C:\\engines\\MagnumChess.jar");
			proc = Runtime.getRuntime().exec("Rybkav.exe");
			in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			InputStream err = proc.getErrorStream();
			
			ps = new PrintStream( new BufferedOutputStream(proc.getOutputStream()));
			isInitialized = true;
			//waitForString("to play in UCI mode type \"uci\"");
			sendMessage("uci");
			waitForString("uciok");
			sendMessage("isready");
			waitForString("readyok");
			System.out.println("Ready to start");
			
			
			
			startNewGame();
			
			
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
	}

	private void sendMessage(String send) {
		System.out.println("Sending: " + send);
		ps.println(send);
		ps.flush();
	}

	private void waitForString(String message) {
		String input = "";
		while(!input.equals(message)){	
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(input);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String waitForContainsString(String message) {
		String input = "";
		while(!input.contains(message)){	
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(input);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return input;
	}
	
	public void startNewGame(){
		sendMessage("ucinewgame");
		sendMessage("isready");
		waitForString("readyok");
	}
	
	private void sendMoves(){
		StringBuilder sb = new StringBuilder();
		sb.append("position startpos moves");
		for(String s : moves){
			sb.append(" ");
			sb.append(s);
		}
		
		sendMessage(sb.toString());
	}
	
	public String move(String move){
		moves.add(move);
		sendMoves();
		
		sendMessage("go infinite");
		wait(1000);
		sendMessage("stop");		
		String engineMove = waitForContainsString("bestmove");
		engineMove = engineMove.replace("bestmove ", "");
		System.out.println(engineMove);
		moves.add(engineMove);
		return engineMove;
	}

	private void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
