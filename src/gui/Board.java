package gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;
import gui.Piece.PieceType;
import pathfinding.Command;
import pathfinding.Pathfinder;

public class Board {
	// A board is a grid of squares, we also want to know where each of the
	// pieces is on it and then either put a blank square or a piece.

	// So we want to have a 2D array of squares
	// Each of the squares can be either blanksquares or pieces
	// If a square is blank then it will have a piece that is null
	private Square[][] grid = new Square[8][8];
	public int squareSize = 100;

	public String movePiece(String move) {
		String from = move.substring(0, 2);

		System.out.println("From: " + from);
		int fromColumn = from.charAt(0) - 97;
		int fromRow = from.charAt(1) - 48;
		fromRow = 8 - fromRow;

		String to = move.substring(2, 4);
		System.out.println("To: " + to);
		int toColumn = to.charAt(0) - 97;
		int toRow = to.charAt(1) - 48;
		toRow = 8 - toRow;

		movePieceSquares(grid[fromColumn][fromRow], grid[toColumn][toRow]);
		return move;
	}

	private void movePieceSquares(Square from, Square to) {
		to.setPiece(from.getPiece());
		from.setPiece(null);
	}

	// We need to be able to calculate a path so this works out how to do it
	public void showPath(Square from, Square to, boolean isRook) {
		Command path = Pathfinder.path(from, to, isRook);

		// Now that we have the path we can draw it

		// First we draw the start point in green
		glDisable(GL_TEXTURE_2D);
		glColor3f(0.0f, 1.0f, 0.0f);
		glBegin(GL_QUADS);

		glVertex2i(path.startPoint.x * squareSize - squareSize / 10 + squareSize /2,
				path.startPoint.y * squareSize - squareSize / 10 + squareSize /2); // Upper-left
		glVertex2i(path.startPoint.x * squareSize + squareSize / 10 + squareSize /2,
				path.startPoint.y * squareSize - squareSize / 10 + squareSize /2); // Upper-right
		glVertex2i(path.startPoint.x * squareSize + squareSize / 10 + squareSize /2,
				path.startPoint.y * squareSize + squareSize / 10 + squareSize /2); // Bottom-right
		glVertex2i(path.startPoint.x * squareSize - squareSize / 10 + squareSize /2,
				path.startPoint.y * squareSize + squareSize / 10 + squareSize /2); // Bottom-left

		glEnd();

		// Second we draw the end point in blue
		glColor3f(0.0f, 0.0f, 1.0f);
		glBegin(GL_QUADS);

		glVertex2i(path.endPoint.x * squareSize - squareSize / 10 + squareSize /2,
				path.endPoint.y * squareSize - squareSize / 10 + squareSize /2); // Upper-left
		glVertex2i(path.endPoint.x * squareSize + squareSize / 10 + squareSize /2,
				path.endPoint.y * squareSize - squareSize / 10 + squareSize /2); // Upper-right
		glVertex2i(path.endPoint.x * squareSize + squareSize / 10 + squareSize /2,
				path.endPoint.y * squareSize + squareSize / 10 + squareSize /2); // Bottom-right
		glVertex2i(path.endPoint.x * squareSize - squareSize / 10 + squareSize /2,
				path.endPoint.y * squareSize + squareSize / 10 + squareSize /2); // Bottom-left

		glEnd();

		// Then we draw the adjusted points

		// Start - Cyan
		glColor3f(0.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);

		glVertex2i(path.adjustedStart.x * squareSize /2 - squareSize / 10 + squareSize /2,
				path.adjustedStart.y * squareSize /2 - squareSize / 10 + squareSize /2); // Upper-left
		glVertex2i(path.adjustedStart.x * squareSize /2 + squareSize / 10 + squareSize /2,
				path.adjustedStart.y * squareSize /2 - squareSize / 10 + squareSize /2); // Upper-right
		glVertex2i(path.adjustedStart.x * squareSize /2 + squareSize / 10 + squareSize /2,
				path.adjustedStart.y * squareSize /2 + squareSize / 10 + squareSize /2); // Bottom-right
		glVertex2i(path.adjustedStart.x * squareSize /2 - squareSize / 10 + squareSize /2,
				path.adjustedStart.y * squareSize /2 + squareSize / 10 + squareSize /2); // Bottom-left

		glEnd();

		// End - Yellow
		glColor3f(1.0f, 1.0f, 0.0f);
		glBegin(GL_QUADS);

		glVertex2i(path.adjustedEnd.x * squareSize /2 - squareSize / 10 + squareSize /2,
				path.adjustedEnd.y * squareSize /2 - squareSize / 10 + squareSize /2); // Upper-left
		glVertex2i(path.adjustedEnd.x * squareSize /2 + squareSize / 10 + squareSize /2,
				path.adjustedEnd.y * squareSize /2 - squareSize / 10 + squareSize /2); // Upper-right
		glVertex2i(path.adjustedEnd.x * squareSize /2 + squareSize / 10 + squareSize /2,
				path.adjustedEnd.y * squareSize /2 + squareSize / 10 + squareSize /2); // Bottom-right
		glVertex2i(path.adjustedEnd.x * squareSize /2 - squareSize / 10 + squareSize /2,
				path.adjustedEnd.y * squareSize /2 + squareSize / 10 + squareSize /2); // Bottom-left

		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);
	}

	public void init() {

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if ((x % 2 == 0) && (y % 2 == 0))
					grid[x][y] = new Square(x, y, x * squareSize, y * squareSize, squareSize, squareSize,
							false);
				else if ((x % 2 == 1) && (y % 2 == 1))
					grid[x][y] = new Square(x, y, x * squareSize, y * squareSize, squareSize, squareSize,
							false);
				else
					grid[x][y] = new Square(x, y, x * squareSize, y * squareSize, squareSize, squareSize,
							true);

			}
		}
		// To initialise the board we set it up just like normal chess

		// First we do the black pieces

		// Rooks
		grid[0][0].setPiece(new Piece(PieceType.Black_Rook));
		grid[7][0].setPiece(new Piece(PieceType.Black_Rook));

		// Knight
		grid[1][0].setPiece(new Piece(PieceType.Black_Knight));
		grid[6][0].setPiece(new Piece(PieceType.Black_Knight));

		// Bishop
		grid[2][0].setPiece(new Piece(PieceType.Black_Bishop));
		grid[5][0].setPiece(new Piece(PieceType.Black_Bishop));

		// King
		grid[4][0].setPiece(new Piece(PieceType.Black_King));

		// Queen
		grid[3][0].setPiece(new Piece(PieceType.Black_Queen));

		for (int x = 0; x < 8; x++) {
			grid[x][1].setPiece(new Piece(PieceType.Black_Pawn));
		}
		// Then we do the white pieces

		// Rooks
		grid[0][7].setPiece(new Piece(PieceType.White_Rook));
		grid[7][7].setPiece(new Piece(PieceType.White_Rook));

		// Knight
		grid[1][7].setPiece(new Piece(PieceType.White_Knight));
		grid[6][7].setPiece(new Piece(PieceType.White_Knight));

		// Bishop
		grid[2][7].setPiece(new Piece(PieceType.White_Bishop));
		grid[5][7].setPiece(new Piece(PieceType.White_Bishop));

		// King
		grid[4][7].setPiece(new Piece(PieceType.White_King));

		// Queen
		grid[3][7].setPiece(new Piece(PieceType.White_Queen));

		for (int x = 0; x < 8; x++) {
			grid[x][6].setPiece(new Piece(PieceType.White_Pawn));
		}
		
		// movePiece(grid[2][6], grid[2][4]);
	}

	public void draw() {

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				grid[x][y].draw();
			}
		}

		//showPath(grid[6][7], grid[7][5], true);
		
	}


	
	public void dispose() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				grid[x][y].dispose();
			}
		}
	}

}
