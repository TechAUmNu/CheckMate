package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Piece {

	private Texture texture;

	public Piece(PieceType type) {
		try {
			switch (type) {
			case Black_Bishop:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/blackBishop.png"))));
				break;
			case Black_King:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/blackKing.png"))));
				break;
			case Black_Knight:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/blackKnight.png"))));
				break;
			case Black_Pawn:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/blackPawn.png"))));
				break;
			case Black_Queen:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/blackQueen.png"))));
				break;
			case Black_Rook:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/blackRook.png"))));
				break;
			case White_Bishop:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/whiteBishop.png"))));
				break;
			case White_King:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/whiteKing.png"))));
				break;
			case White_Knight:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/whiteKnight.png"))));
				break;
			case White_Pawn:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/whitePawn.png"))));
				break;
			case White_Queen:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/whiteQueen.png"))));
				break;
			case White_Rook:
				setTexture(TextureLoader.getTexture("PNG", new FileInputStream(
						new File("res/images/whiteRook.png"))));
				break;
			default:
				break;

			}

		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public enum PieceType {
		Black_Pawn((byte) 0), Black_Bishop((byte) 1), Black_Rook((byte) 2), Black_Knight(
				(byte) 3), Black_King((byte) 4), Black_Queen((byte) 5), White_Pawn(
				(byte) 6), White_Bishop((byte) 7), White_Rook((byte) 8), White_Knight(
				(byte) 9), White_King((byte) 10), White_Queen((byte) 11);
		private byte PieceID;

		PieceType(byte i) {
			PieceID = i;
		}

		public byte GetType() {
			return PieceID;
		}

		public static PieceType fromByte(byte b) {
			switch (b) {
			case (byte) 0:
				return Black_Pawn;
			case (byte) 1:
				return Black_Bishop;
			case (byte) 2:
				return Black_Rook;
			case (byte) 3:
				return Black_Knight;
			case (byte) 4:
				return Black_King;
			case (byte) 5:
				return Black_Queen;
			case (byte) 6:
				return White_Pawn;
			case (byte) 7:
				return White_Bishop;
			case (byte) 8:
				return White_Rook;
			case (byte) 9:
				return White_Knight;
			case (byte) 10:
				return White_King;
			case (byte) 11:
				return White_Queen;
			}
			return null;
		}
	}

}
