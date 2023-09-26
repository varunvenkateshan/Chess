package pieces;

import java.util.*;
import java.awt.Point;
import board.Board;

//Created by Varun Venkateshan and Yashwant Balaji

public class King extends Piece {

	public boolean castling;

	// Constructor
	public King(String color, int x, int y, boolean castling) {
		super(color, x, y);
		if (color.equals("white"))
			name = "wK ";
		else
			name = "bK ";

		this.castling = castling;
	}

	// King Movement Method
	public void kingMovement(Board b, ArrayList<Point> getMoves) {
		int xPos = location.x;
		int yPos = location.y;

		Point up;
		Point down;
		Point right;
		Point left;
		Point upRight;
		Point upLeft;
		Point downRight;
		Point downLeft;

		if (this.color.equals("white")) {
			up = new Point(xPos - 1, yPos);
			down = new Point(xPos + 1, yPos);
			right = new Point(xPos, yPos + 1);
			left = new Point(xPos, yPos - 1);
			upRight = new Point(xPos - 1, yPos + 1);
			upLeft = new Point(xPos - 1, yPos - 1);
			downRight = new Point(xPos + 1, yPos + 1);
			downLeft = new Point(xPos + 1, yPos - 1);

		} else {
			up = new Point(xPos - 1, yPos);
			down = new Point(xPos + 1, yPos);
			right = new Point(xPos, yPos + 1);
			left = new Point(xPos, yPos - 1);
			upRight = new Point(xPos - 1, yPos + 1);
			upLeft = new Point(xPos - 1, yPos - 1);
			downRight = new Point(xPos + 1, yPos + 1);
			downLeft = new Point(xPos + 1, yPos - 1);
		}

		getMoves.add(up);
		getMoves.add(down);
		getMoves.add(right);
		getMoves.add(left);
		getMoves.add(upRight);
		getMoves.add(upLeft);
		getMoves.add(downRight);
		getMoves.add(downLeft);

		Iterator<Point> iterator1 = getMoves.iterator();
		while (iterator1.hasNext()) {
			Point newPoint = iterator1.next();

			if (newPoint.x > 7 || newPoint.x < 0 || newPoint.y > 7 || newPoint.y < 0) {
				iterator1.remove();
			}
		}

		Iterator<Point> iterator2 = getMoves.iterator();
		while (iterator2.hasNext()) {
			Point p = iterator2.next();

			if (this.color.equals("white")) {
				if ((b.getPieceAt(p) != null) && ((b.getPieceAt(p)).color).equals("white")) {
					iterator2.remove();
				}
			}
			if (this.color.equals("black")) {
				if (b.getPieceAt(p) != null && ((b.getPieceAt(p)).color).equals("black")) {
					iterator2.remove();
				}
			}
		}
	}

	// Method for Castling if it is still available
	public void castling(Board board, ArrayList<Point> moves) {

		// Castling is not allowed because king moved or rooks moved already
		if (!this.castling) {
			return;
		}

		// Cannot castle if king is in check
		if (board.kingInCheck != null && board.kingInCheck.equals(this)) {
			return;
		}

		ArrayList<Piece> castleRook = new ArrayList<Piece>();
		for (Piece p : board.pieces) {
			if (p instanceof Rook && p.color.equals(this.color) && ((Rook) p).castling) {
				castleRook.add(p);
			}
		}
		if (castleRook.isEmpty())
			return;

		Iterator<Piece> iterator1 = castleRook.iterator();
		while (iterator1.hasNext()) {
			Piece p = iterator1.next();
			if (p.location.y == 0) {
				if (board.getPieceAt(new Point(p.location.x, 1)) != null
						|| board.getPieceAt(new Point(p.location.x, 2)) != null
						|| board.getPieceAt(new Point(p.location.x, 3)) != null) {

					iterator1.remove();
				}
			} else if (p.location.y == 7) {
				if (board.getPieceAt(new Point(p.location.x, 5)) != null
						|| board.getPieceAt(new Point(p.location.x, 6)) != null) {
					iterator1.remove();
				}
			}
		}
		if (castleRook.isEmpty())
			return;

		Iterator<Piece> iterator2 = castleRook.iterator();
		while (iterator2.hasNext()) {
			Piece p = iterator2.next();
			if (p.location.y == 0) {
				for (int i = 1; i < 4; i++) {
					if (this.kingInCheck(board, new Point(this.location.x, i))) {
						iterator2.remove();
						break;
					}
				}
			} else if (p.location.y == 7) {
				for (int i = 5; i < 7; i++) {
					if (this.kingInCheck(board, new Point(this.location.x, i))) {
						iterator2.remove();
						break;
					}
				}
			}
		}
		if (castleRook.isEmpty())
			return;

		Iterator<Piece> iterator3 = castleRook.iterator();
		while (iterator3.hasNext()) {
			Piece p = iterator3.next();
			if (p.location.y == 0 && p.location.x == 7) {
				if (board.getPieceAt(new Point(6, 2)) instanceof King
						|| board.getPieceAt(new Point(6, 1)) instanceof King) {
					iterator3.remove();
				}
			}
			if (p.location.y == 7 && p.location.x == 7) {
				if (board.getPieceAt(new Point(6, 6)) instanceof King) {
					iterator3.remove();
				}
			}
			if (p.location.y == 0 && p.location.x == 0) {
				if (board.getPieceAt(new Point(1, 1)) instanceof King
						|| board.getPieceAt(new Point(1, 2)) instanceof King) {
					iterator3.remove();
				}
			}
			if (p.location.y == 7 && p.location.x == 0) {
				if (board.getPieceAt(new Point(1, 6)) instanceof King) {
					iterator3.remove();
				}
			}
		}

		Iterator<Piece> iterator4 = castleRook.iterator();
		while (iterator4.hasNext()) {
			Piece p = iterator4.next();
			if (p.location.y == 0) {
				moves.add(new Point(this.location.x, 2));
			} else if (p.location.y == 7) {
				moves.add(new Point(this.location.x, 6));
			}
		}

	}

	// Method to see if the King is in Check
	public boolean kingInCheck(Board board, Point point) {
		ArrayList<Piece> opposingPieces = new ArrayList<Piece>();
		for (Piece p : board.pieces) {
			if (!p.color.equals(this.color) && !(p instanceof King)) {
				opposingPieces.add(p);
			}
		}

		for (Piece p : opposingPieces) {
			for (Point point1 : p.getMoves(board, false)) {
				if (point1.equals(point)) {
					return true;
				}
			}
		}
		return false;
	}

	// All Possible Moves for King
	public ArrayList<Point> getMoves(Board board, boolean check) {
		ArrayList<Point> moves = new ArrayList<Point>();
		kingMovement(board, moves);
		castling(board, moves);

		Iterator<Point> iterator = moves.iterator();

		if (check) {
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

	// Setter for Castling
	public void setCastling(boolean c) {
		this.castling = c;
	}

}
