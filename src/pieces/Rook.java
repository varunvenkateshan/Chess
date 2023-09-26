package pieces;

import java.util.*;
import java.awt.Point;
import board.Board;

//Created by Varun Venkateshan and Yashwant Balaji
public class Rook extends Piece {

	public boolean castling;

	// Constructor
	public Rook(String color, int x, int y, boolean castling) {
		super(color, x, y);
		this.castling = castling;
		if (color.equals("white"))
			name = "wR ";
		else
			name = "bR ";
	}

	// Rook Movement Method
	public void rookMovement(Board b, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;

		for (int i = xPos + 1; i < 8; i++) {
			Point p = new Point(i, yPos);
			if (b.getPieceAt(p) == null) {
				getMoves.add(p);
			} else if (!(b.getPieceAt(p).color.equals(this.color))) {
				getMoves.add(p);
				break;
			} else {
				break;
			}
		}

		for (int i = xPos - 1; i >= 0; i--) {
			Point p = new Point(i, yPos);
			if (b.getPieceAt(p) == null) {
				getMoves.add(p);
			} else if (!(b.getPieceAt(p).color.equals(this.color))) {
				getMoves.add(p);
				break;
			} else {
				break;
			}
		}

		for (int j = yPos + 1; j < 8; j++) {
			Point p = new Point(xPos, j);
			if (b.getPieceAt(p) == null) {
				getMoves.add(p);
			} else if (!(b.getPieceAt(p).color.equals(this.color))) {
				getMoves.add(p);
				break;
			} else {
				break;
			}
		}

		for (int j = yPos - 1; j >= 0; j--) {
			Point p = new Point(xPos, j);
			if (b.getPieceAt(p) == null) {
				getMoves.add(p);
			} else if (!(b.getPieceAt(p).color.equals(this.color))) {
				getMoves.add(p);
				break;
			} else {
				break;
			}
		}

	}

	// All Possible Moves for Rook
	public ArrayList<Point> getMoves(Board board, boolean inCheck) {
		ArrayList<Point> moves = new ArrayList<Point>();
		rookMovement(board, moves);

		Iterator<Point> iterator = moves.iterator();

		if (inCheck) {
			while (iterator.hasNext()) {
				Point possiblePoint = iterator.next();
				Board helper = board.tryMove(new Point[] { this.location, possiblePoint });
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

	// Setter for Castling
	public void setCastling(boolean b) {
		this.castling = b;
	}

}
