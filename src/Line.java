import java.awt.Point;

public class Line extends Command {
	Point startPoint;
	Point endPoint;
	
	Line(Point start, Point end) {
		startPoint = start;
		endPoint = end;
	}
	
	@Override
	public String toString() {
		return "PAINT_LINE " + startPoint.x + " " + startPoint.y + " " + endPoint.x + " " + endPoint.y;
	}
}
