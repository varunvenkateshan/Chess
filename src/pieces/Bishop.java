package pieces;

import java.util.*;
import java.awt.Point;
import board.Board;

//Created by Varun Venkateshan and Yashwant Balaji
public class Bishop extends Piece {

	// Constructor
	public Bishop(String color, int x, int y) {
		super(color, x, y);
		if (color.equals("white")) {
			name = "wB ";
		} else {
			name = "bB ";
		}
	}

	//Bishop Movement Method
	public void bishopMovement(Board board, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;

		int i = xPos + 1;
		int j = yPos + 1;
		while (i < 8 && j < 8) {
			Point newPoint  = new Point(i, j);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				break;
			}
			i++;
			j++;
		}

		i = xPos + 1;
		j = yPos - 1;
		while (i < 8 && j >= 0) {
			Point newPoint  = new Point(i, j);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				break;
			}
			i++;
			j--;
		}

		i = xPos - 1;
		j = yPos + 1;
		while (i >= 0 && j < 8) {
			Point newPoint  = new Point(i, j);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				break;
			}
			i--;
			j++;
		}

		i = xPos - 1;
		j = yPos - 1;
		while (i >= 0 && j >= 0) {
			Point newPoint  = new Point(i, j);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				break;
			}
			i--;
			j--;
		}
	}

	//All Possible Moves for Bishop
	public ArrayList<Point> getMoves(Board board, boolean inCheck) {
		ArrayList<Point> moves = new ArrayList<Point>();
		bishopMovement(board, moves);

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
