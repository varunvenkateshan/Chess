package pieces;

import java.util.*;
import java.awt.Point;
import board.Board;

//Created by Varun Venkateshan and Yashwant Balaji
public class Pawn extends Piece {

	public boolean enpassant;

	// Constructor
	public Pawn(String color, int x, int y, boolean enpassant) {
		super(color, x, y);
		if (color.equals("white"))
			name = "wp ";
		else
			name = "bp ";
		this.enpassant = enpassant;
	}

	// Pawn Movement Method
	public void pawnMovement(Board b, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;

		if (this.color.equals("white") && (xPos == 6)) {
			Point point1 = new Point(5, yPos);
			Point point2 = new Point(4, yPos);
			if (b.getPieceAt(point1) == null) {
				getMoves.add(point1);
			}
			if (b.getPieceAt(point1) == null && b.getPieceAt(point2) == null) {
				getMoves.add(point2);
			}
		} else if (this.color.equals("white") && !(xPos == 6) && (xPos != 0)) {
			Point p3 = new Point(xPos - 1, yPos);
			if (b.getPieceAt(p3) == null) {
				getMoves.add(p3);
			}
		}

		if (this.color.equals("black") && (xPos == 1)) {
			Point p = new Point(2, yPos);
			Point p2 = new Point(3, yPos);
			if (b.getPieceAt(p) == null) {
				getMoves.add(p);
			}
			if (b.getPieceAt(p) == null && b.getPieceAt(p2) == null) {
				getMoves.add(p2);
			}
		} else if (this.color.equals("black") && !(xPos == 1) && (xPos != 7)) {
			Point p3 = new Point(xPos + 1, yPos);
			if (b.getPieceAt(p3) == null) {
				getMoves.add(p3);
			}
		}
	}

	// Pawn Capture Method
	public void pawnCapture(Board b, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;
		if (this.color.equals("white") && ((yPos != 0) && (yPos != 7))) {
			Point left = new Point(xPos - 1, yPos - 1);
			Point right = new Point(xPos - 1, yPos + 1);
			if ((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("black"))) {
				getMoves.add(left);
			}
			if ((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("black"))) {
				getMoves.add(right);
			}
		}
		if (this.color.equals("white") && ((yPos == 0))) {
			Point right = new Point(xPos - 1, yPos + 1);
			if ((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("black"))) {
				getMoves.add(right);
			}
		}
		if (this.color.equals("white") && ((yPos == 7))) {
			Point left = new Point(xPos - 1, yPos - 1);
			if ((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("black"))) {
				getMoves.add(left);
			}
		}
		if (this.color.equals("black") && ((yPos != 0) && (yPos != 7))) {
			Point left = new Point(xPos + 1, yPos - 1);
			Point right = new Point(xPos + 1, yPos + 1);
			if ((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("white"))) {
				getMoves.add(left);
			}
			if ((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("white"))) {
				getMoves.add(right);
			}
		}
		if (this.color.equals("black") && ((yPos == 0))) {
			Point right = new Point(xPos + 1, yPos + 1);
			if ((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("white"))) {
				getMoves.add(right);
			}
		}
		if (this.color.equals("black") && ((yPos == 7))) {
			Point left = new Point(xPos + 1, yPos - 1);
			if ((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("white"))) {
				getMoves.add(left);
			}
		}
	}

	// EnPassant Method
	public void enPassant(Board b, ArrayList<Point> getMoves) {
		int row = this.location.x;
		int col = this.location.y;

		if (row == 3) {
			if (col == 0) {
				Piece p = b.getPieceAt(new Point(row, col + 1));
				if (p != null
						&& p instanceof Pawn
						&& !(p.color).equals(this.color)
						&& ((Pawn) p).getEnPassant()) {
					getMoves.add(new Point(row - 1, col + 1));
				}
			} else if (col == 7) {
				Piece p = b.getPieceAt(new Point(row, col - 1));
				if (p != null
						&& p instanceof Pawn
						&& !(p.color).equals(this.color)
						&& ((Pawn) p).getEnPassant()) {
					getMoves.add(new Point(row - 1, col - 1));
				}
			} else {
				Piece right = b.getPieceAt(new Point(row, col + 1));
				Piece left = b.getPieceAt(new Point(row, col - 1));
				if (right != null
						&& right instanceof Pawn
						&& !(right.color).equals(this.color)
						&& ((Pawn) right).getEnPassant()) {
					getMoves.add(new Point(row - 1, col + 1));
				}
				if (left != null
						&& left instanceof Pawn
						&& !(left.color).equals(this.color)
						&& ((Pawn) left).getEnPassant()) {
					getMoves.add(new Point(row - 1, col - 1));
				}
			}
		}

		if (row == 4) {
			if (col == 0) {
				Piece p = b.getPieceAt(new Point(row, col + 1));
				if (p != null
						&& p instanceof Pawn
						&& !(p.color).equals(this.color)
						&& ((Pawn) p).getEnPassant()) {
					getMoves.add(new Point(row + 1, col + 1));
				}
			} else if (col == 7) {
				Piece p = b.getPieceAt(new Point(row, col - 1));
				if (p != null
						&& p instanceof Pawn
						&& !(p.color).equals(this.color)
						&& ((Pawn) p).getEnPassant()) {
					getMoves.add(new Point(row + 1, col - 1));
				}
			} else {
				Piece right = b.getPieceAt(new Point(row, col + 1));
				Piece left = b.getPieceAt(new Point(row, col - 1));
				if (right != null
						&& right instanceof Pawn
						&& !(right.color).equals(this.color)
						&& ((Pawn) right).getEnPassant()) {
					getMoves.add(new Point(row + 1, col + 1));
				}
				if (left != null
						&& left instanceof Pawn
						&& !(left.color).equals(this.color)
						&& ((Pawn) left).getEnPassant()) {
					getMoves.add(new Point(row + 1, col - 1));
				}
			}
		}
	}

	// All Possible Moves for Pawn
	public ArrayList<Point> getMoves(Board board, boolean inCheck) {
		ArrayList<Point> moves = new ArrayList<Point>();
		pawnMovement(board, moves);
		pawnCapture(board, moves);
		enPassant(board, moves);

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

	// Getter for EnPassant
	public boolean getEnPassant() {
		return enpassant;
	}

	// Setter for EnPassant
	public void setEnPassant(boolean b) {
		this.enpassant = b;
	}

}
