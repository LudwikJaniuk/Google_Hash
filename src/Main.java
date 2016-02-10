import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
	static Scanner scanner;

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		// rows x columns
		Point dims = parsePoint(readLines(1)[0]);

		boolean[][] picture = parsePicture(readLines(dims.x));
		//System.out.println();
		//for (boolean[] bs : picture) {
		//	System.out.println(Arrays.toString(bs));
		//}
		
		List<Command> commands = commandsToPaint(picture);
		
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		
		writer.println(commands.size());
		for (Command c : commands) {
			writer.println(c.toString());
		}
		
		writer.close();

		scanner.close();
	}
	
	/**
	 * Modifies the input array!
	 * @param picture
	 * @return
	 */
	public static List<Command> commandsToPaint(boolean[][] picture) {
		ArrayList<Command> result = new ArrayList<>();
		for (int i = 0; i < picture.length; i++) {
			for (int j = 0; j < picture[i].length; j++) {
				if (picture[i][j]) {
					result.add(extractLineCommand(picture, new Point(i, j)));
				}
			}
		}
		return result;
	}
	
	// Assumes position is filled in, does not check.
	public static Line extractLineCommand(boolean[][] picture, Point position) {
		Point dims = new Point(picture.length, picture[0].length);
		int horizontalLength = 0;
		int verticalLength = 0;
		Line result;
		
		Point currPos = (Point)position.clone();
		currPos.y++;
		while(inBounds(currPos, dims)) {
			// To the right!
			if (picture[currPos.x][currPos.y]) {
				horizontalLength++;
			} else {
				break;
			}
			currPos.y++;
		}
		
		currPos = (Point)position.clone();
		currPos.x++;
		while(inBounds(currPos, dims)) {
			// To the right!
			if (picture[currPos.x][currPos.y]) {
				verticalLength++;
			} else {
				break;
			}
			currPos.x++;
		}
		
		Point endPoint = (Point)position.clone();
		if (verticalLength > horizontalLength) {
			endPoint.translate(verticalLength, 0);
		} else {
			endPoint.translate(0, horizontalLength);
		}
		
		// X coordinate translates vertically
		result = new Line(position, endPoint);
		
		eraseLine(picture, result);
		
		return result;
	}
	
	public static void eraseLine(boolean[][] picture, Line line) {
		Point dims = new Point(picture.length, picture[0].length);
		Point curr = (Point)line.startPoint.clone();
		Point delta;
		if (line.startPoint.equals(line.endPoint)) {
			delta = new Point(0,0);
		} else if (line.startPoint.x == line.endPoint.x) {
			delta = new Point(0, 1);
		} else {
			delta = new Point(1, 0);
		}
		
		picture[curr.x][curr.y] = false;
		do {
			curr.translate(delta.x, delta.y);
			picture[curr.x][curr.y] = false;
			
		} while(!curr.equals(line.endPoint));
	}
	
	public static boolean inBounds(Point p, Point dims) {
		return p.x >= 0 &&
			   p.y >= 0 &&
			   p.x < dims.x &&
			   p.y < dims.y;
	}
	
	

	/**
	 * 
	 * @param input
	 * @return
	 */
	private static Point parsePoint(String input) {
		String[] ints = input.split("\\s");
		return new Point(Integer.parseInt(ints[0]), Integer.parseInt(ints[1]));
	}
	
	/**
	 * 
	 * @param linesAmount
	 * @return
	 */
	private static String[] readLines(int linesAmount) {
		if (scanner == null) {scanner = new Scanner(System.in);}
		String[] lineArr = new String[linesAmount];
		for (int i = 0; i < linesAmount; i++){
			lineArr[i] = scanner.nextLine();
		}


		return lineArr;
	}
	/**
	 * 
	 * @param input
	 * @return
	 */
	private static boolean[][] parsePicture(String[] input) {
		boolean[][] result = new boolean[input.length][];
		for (int i = 0; i < input.length; i++)
		{
			String thisLine = input[i];
			result[i] = new boolean[thisLine.length()];
			for (int j = 0; j < thisLine.length(); j++)
			{
				result[i][j] = thisLine.charAt(j) == '#';
			}
		}
		return result;

	}
}