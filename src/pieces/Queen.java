package pieces;

import java.util.*;
import java.awt.Point;
import board.Board;

//Created by Varun Venkateshan and Yashwant Balaji
public class Queen extends Piece {

	// Constructor
	public Queen(String color, int x, int y) {
		super(color, x, y);
		if (color.equals("white"))
			name = "wQ ";
		else
			name = "bQ ";
	}

	// Queen's Line Movement Ability
	public void queenLineMovement(Board board, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;

		for (int i = xPos + 1; i < 8; i++) {
			Point newPoint = new Point(i, yPos);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				;
				break;
			}
		}

		for (int i = xPos - 1; i >= 0; i--) {
			Point newPoint = new Point(i, yPos);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				break;
			}
		}

		for (int j = yPos + 1; j < 8; j++) {
			Point newPoint = new Point(xPos, j);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				break;
			}
		}

		for (int j = yPos - 1; j >= 0; j--) {
			Point newPoint = new Point(xPos, j);
			if (board.getPieceAt(newPoint) == null) {
				getMoves.add(newPoint);
			} else if (!(board.getPieceAt(newPoint).color.equals(this.color))) {
				getMoves.add(newPoint);
				break;
			} else {
				break;
			}
		}
	}

	// Queen's Diagonal Movement Ability
	public void queenDiagonalMovement(Board board, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;

		int i = xPos + 1;
		int j = yPos + 1;
		while (i < 8 && j < 8) {
			Point newPoint = new Point(i, j);
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
			Point newPoint = new Point(i, j);
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
			Point newPoint = new Point(i, j);
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
			Point newPoint = new Point(i, j);
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

	//All Possible Moves for Queen
	public ArrayList<Point> getMoves(Board board, boolean inCheck) {
		ArrayList<Point> moves = new ArrayList<Point>();
		queenLineMovement(board, moves);
		queenDiagonalMovement(board, moves);

		Iterator<Point> iterator = moves.iterator();

		if (inCheck) {
			while (iterator.hasNext()) {
				Point p = iterator.next();
				Board helper = board.tryMove(new Point[] { this.location, p });
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
