package pathfinding;

import gui.Square;

//Class that finds a path from one square to another based on the direction of the travel
public class Pathfinder {

	public static Command path(Square from, Square to) {
		// TODO Auto-generated method stub
		// Ok so the first thing we need to do is work out the direction we are
		// going in
		Command path = new Command();
		int xChange = to.x - from.x;
		int yChange = to.y - from.y;

		// So now we use the changes to see which direction to start with

		path.startPoint = new PathGridSquare(from.gridX, from.gridY);
		path.endPoint = new PathGridSquare(to.gridX, to.gridY);
		if (xChange > 0 && yChange > 0) { // Right and Down
			path.startDirection = Direction.Down;

			// |_______
			// |
			// |
			// |_

			path.endDirection = Direction.Right;
		} else if (xChange < 0 && yChange < 0) { // Left and Up
			path.startDirection = Direction.Up;
			// _
			// |
			// |
			// |________
			// |

			path.endDirection = Direction.Left;
		} else if (xChange > 0 && yChange < 0) { // Right and Up
			path.startDirection = Direction.Up;
			// _
			// |
			// |
			// _______|
			// |

			path.endDirection = Direction.Right;
		} else if (xChange < 0 && yChange > 0) { // Left and Down
			path.startDirection = Direction.Down;

			// _______|
			// |
			// |
			// _|

			path.endDirection = Direction.Left;
		} else if (xChange == 0 && (yChange < 0 || yChange > 0)) { // Up / Down
			path.startDirection = Direction.Right;

			// _
			// |
			// |
			// _|

			path.endDirection = Direction.Left;
		} else if ((xChange < 0 || xChange > 0) && yChange == 0) { // Up / Down
			path.startDirection = Direction.Down;

			// |_______|

			path.endDirection = Direction.Up;
		}

		path.adjustEndpoints();
		return path;
	}

}
