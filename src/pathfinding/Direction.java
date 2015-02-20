package pathfinding;

public enum Direction {

	Down(0), Up(1), Right(2), Left(3);
	public int PieceID;

	Direction(int i) {
		PieceID = i;
	}

}
