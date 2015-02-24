package pathfinding;

public class Command {
	public Direction startDirection, endDirection;
	public PathGridSquare startPoint, endPoint, adjustedStart, adjustedEnd;
	public boolean isRook;

	public void adjustEndpointsRook() {
		adjustedStart = adjustRook(startPoint, startDirection, false);
		adjustedEnd = adjustRook(endPoint, endDirection, true);
		isRook = true;
	}
	
	public void adjustEndpointsNormal(){
		adjustedStart = adjust(startPoint);
		adjustedEnd = adjust(endPoint);
		isRook = false;
	}

	private PathGridSquare adjustRook(PathGridSquare point, Direction dir,
			boolean end) {
		PathGridSquare adjusted = new PathGridSquare(point.x, point.y);
		adjusted.x *= 2;
		adjusted.y *= 2;

		int change = 1;
		if (end)
			change = -1;
		switch (dir) {
		case Down:
			adjusted.y += change;
			break;
		case Left:
			adjusted.x -= change;
			break;
		case Right:
			adjusted.x += change;
			break;
		case Up:
			adjusted.y -= change;
			break;
		}
		return adjusted;
	}
	
	private PathGridSquare adjust(PathGridSquare point) {
		PathGridSquare adjusted = new PathGridSquare(point.x, point.y);
		adjusted.x *= 2;
		adjusted.y *= 2;
		return adjusted;
	}

}
