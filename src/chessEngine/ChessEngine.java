package chessEngine;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;


public class ChessEngine {

	
	public void startEngine(){
		// Run a java app in a separate system process
		Process proc;
		try {
			proc = Runtime.getRuntime().exec("java -jar MagnumChess.jar");
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			InputStream err = proc.getErrorStream();
			
			PrintStream ps = new PrintStream( new BufferedOutputStream(proc.getOutputStream()));
			ps.print("uci");
			System.out.println(in.readLine());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Then retreive the process output
		
	}

}
