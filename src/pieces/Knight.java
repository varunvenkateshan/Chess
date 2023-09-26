package pieces;

import java.util.*;
import java.awt.Point;
import board.Board;

//Created by Varun Venkateshan and Yashwant Balaji
public class Knight extends Piece {

	// Constructor
	public Knight(String color, int x, int y) {
		super(color, x, y);
		if (color.equals("white"))
			name = "wN ";
		else
			name = "bN ";
	}

	// Knight Movement Method
	public void knightMovement(Board board, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;

		Point pos1 = new Point(xPos - 2, yPos + 1);
		Point pos2 = new Point(xPos - 2, yPos - 1);
		Point pos3 = new Point(xPos + 2, yPos + 1);
		Point pos4 = new Point(xPos + 2, yPos - 1);
		Point pos5 = new Point(xPos - 1, yPos + 2);
		Point pos6 = new Point(xPos - 1, yPos - 2);
		Point pos7 = new Point(xPos + 1, yPos + 2);
		Point pos8 = new Point(xPos + 1, yPos - 2);

		getMoves.add(pos1);
		getMoves.add(pos2);
		getMoves.add(pos3);
		getMoves.add(pos4);
		getMoves.add(pos5);
		getMoves.add(pos6);
		getMoves.add(pos7);
		getMoves.add(pos8);

		Iterator<Point> iterator1 = getMoves.iterator();
		while (iterator1.hasNext()) {
			Point p = iterator1.next();

			if (p.x > 7 || p.x < 0 || p.y > 7 || p.y < 0)
			iterator1.remove();
		}

		Iterator<Point> iterator2 = getMoves.iterator();
		while (iterator2.hasNext()) {
			Point p = iterator2.next();

			if (this.color.equals("white")) {
				if ((board.getPieceAt(p) != null) && ((board.getPieceAt(p)).color).equals("white")) {
					iterator2.remove();
				}
			}
			if (this.color.equals("black")) {
				if (board.getPieceAt(p) != null && ((board.getPieceAt(p)).color).equals("black")) {
					iterator2.remove();
				}
			}
		}
	}

	//All Possible Moves for Knight
	public ArrayList<Point> getMoves(Board board, boolean inCheck) {
		ArrayList<Point> moves = new ArrayList<Point>();
		knightMovement(board, moves);

		Iterator<Point> iterator = moves.iterator();

		if (inCheck) {
			while (iterator.hasNext()) {
				Point possiblePoint = iterator.next();
				Board helper = board.tryMove(new Point[] {this.location, possiblePoint});
				if (helper.kingInCheck()) {
					iterator.remove();
				}
			}
		}
		return moves;
	}

	// Getter Method
	public String getName() {
		return name;
	}

}
