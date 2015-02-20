package gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

public class Square {
	// This is either a blank square or a piece

	// Initially there is no piece on this square and it is therefore blank
	private Piece piece = null;
	public int gridX, gridY;
	public int x, y, width, height;
	private boolean black;

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Square(int gridX, int gridY, int x, int y, int width, int height,
			boolean black) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.black = black;
	}

	public void draw() {
		// Draw the square colour
		glDisable(GL_TEXTURE_2D);
		if (black) {
			glColor3f(0.719f, 0.601f, 0.405f);
		} else {
			glColor3f(0.960f, 0.870f, 0.701f);
		}

		glBegin(GL_QUADS);

		glVertex2i(x, y); // Upper-left

		glVertex2i(x + width, y); // Upper-right

		glVertex2i(x + width, y + height); // Bottom-right

		glVertex2i(x, y + height); // Bottom-left
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		// Then if there is a piece draw it on top
		if (piece != null) {
			glEnable(GL_TEXTURE_2D);
			piece.getTexture().bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2i(x, y); // Upper-left
			glTexCoord2f(1, 0);
			glVertex2i(x + width, y); // Upper-right
			glTexCoord2f(1, 1);
			glVertex2i(x + width, y + height); // Bottom-right
			glTexCoord2f(0, 1);
			glVertex2i(x, y + height); // Bottom-left
			glEnd();
		}

	}

	public void dispose() {
		if (piece != null) {
			piece.getTexture().release();
		}
	}
}
