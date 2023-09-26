package board;

import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.*;
import pieces.*;

//Created by Varun Venkateshan and Yashwant Balaji
public class Board implements Serializable {

	// Current Pieces that are in the game
	public ArrayList<Piece> pieces;

	// Person to Play
	public String currentPlayer;

	public Piece kingInCheck = null;

	public Board previousMove = null;

	// Creates the Initial Chess Board Setup with White to Play
	public Board() {

		currentPlayer = "White";

		pieces = new ArrayList<Piece>();

		// All White Pawns
		pieces.add(new Pawn("white", 6, 0, false));
		pieces.add(new Pawn("white", 6, 1, false));
		pieces.add(new Pawn("white", 6, 2, false));
		pieces.add(new Pawn("white", 6, 3, false));
		pieces.add(new Pawn("white", 6, 4, false));
		pieces.add(new Pawn("white", 6, 5, false));
		pieces.add(new Pawn("white", 6, 6, false));
		pieces.add(new Pawn("white", 6, 7, false));

		// All Black Pawns
		pieces.add(new Pawn("black", 1, 0, false));
		pieces.add(new Pawn("black", 1, 1, false));
		pieces.add(new Pawn("black", 1, 2, false));
		pieces.add(new Pawn("black", 1, 3, false));
		pieces.add(new Pawn("black", 1, 4, false));
		pieces.add(new Pawn("black", 1, 5, false));
		pieces.add(new Pawn("black", 1, 6, false));
		pieces.add(new Pawn("black", 1, 7, false));

		// All Other White Pieces
		pieces.add(new Rook("white", 7, 0, true));
		pieces.add(new Rook("white", 7, 7, true));
		pieces.add(new Knight("white", 7, 1));
		pieces.add(new Knight("white", 7, 6));
		pieces.add(new Bishop("white", 7, 2));
		pieces.add(new Bishop("white", 7, 5));
		pieces.add(new Queen("white", 7, 3));
		pieces.add(new King("white", 7, 4, true));

		// All Other Black Pieces
		pieces.add(new Rook("black", 0, 0, true));
		pieces.add(new Rook("black", 0, 7, true));
		pieces.add(new Knight("black", 0, 1));
		pieces.add(new Knight("black", 0, 6));
		pieces.add(new Bishop("black", 0, 2));
		pieces.add(new Bishop("black", 0, 5));
		pieces.add(new Queen("black", 0, 3));
		pieces.add(new King("black", 0, 4, true));
	}

	// Method to copy the chess board
	public Board boardCopy() throws Exception {
		ByteArrayOutputStream byteOuputStrem = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOuputStrem);
		objectOutputStream.writeObject(this);

		ByteArrayInputStream bis = new ByteArrayInputStream(byteOuputStrem.toByteArray());
		ObjectInputStream in = new ObjectInputStream(bis);
		Board copied = (Board) in.readObject();

		return copied;
	}

	// Removes piece from board
	public void removePiece(Piece piece) {
		pieces.remove(piece);
	}

	// Adds piece to board
	public void addPiece(Piece piece) {
		pieces.add(piece);
	}

	// Gets the Current Piece at a certain Location
	public Piece getPieceAt(Point point) {
		for (Piece piece : pieces) {
			if (piece.location.equals(point)) {
				return piece;
			}
		}
		return null;
	}

	// Method to see if King is in Check
	public boolean kingInCheck() {
		for (Piece p : pieces) {
			if ((p.color).equals(currentPlayer.toLowerCase())) {
				for (Point point : p.getMoves(this, false)) {
					if (getPieceAt(point) instanceof King) {
						if (!getPieceAt(point).color.equals(currentPlayer.toLowerCase())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// Checks if the move is valid on the board
	public boolean isValidMove(Point[] points) {
		Piece moving = this.getPieceAt(points[0]);
		if (moving == null)
			return false;
		if (points[1].x > 7 || points[1].x < 0 || points[1].y < 0 || points[1].y > 7)
			return false;
		if (!(moving.color).equals(currentPlayer.toLowerCase())) {
			return false;
		}
		if (!(moving.getMoves(this, true).contains(points[1]))) {
			return false;
		}
		return true;
	}

	// Conversions of File Letter to Integers
	public int fileToInt(String s) {
		switch (s) {
			case "a":
				return 0;
			case "b":
				return 1;
			case "c":
				return 2;
			case "d":
				return 3;
			case "e":
				return 4;
			case "f":
				return 5;
			case "g":
				return 6;
			case "h":
				return 7;
		}
		return -1;
	}

	// Method to see if the current player has to defend a check
	public Piece check() {
		for (Piece p : pieces) {
			if (!((p.color).equals(currentPlayer.toLowerCase()))) {
				for (Point point : p.getMoves(this, false)) {
					if ((getPieceAt(point) instanceof King)
							&& (getPieceAt(point)).color.equals(currentPlayer.toLowerCase())) {
						kingInCheck = getPieceAt(point);
						return getPieceAt(point);
					}
				}
			}
		}
		return null;
	}

	// Converts User Input to Actionable Points
	public Point[] move(String start, String end) {
		Point startPoint = new Point();
		Point endPoint = new Point();
		Point[] points = new Point[2];

		startPoint.y = this.fileToInt(start.substring(0, 1));
		endPoint.y = this.fileToInt(end.substring(0, 1));

		startPoint.x = 7 - (Integer.parseInt(start.substring(1, 2))) + 1;
		endPoint.x = 7 - (Integer.parseInt(end.substring(1, 2))) + 1;

		points[0] = startPoint;
		points[1] = endPoint;

		return points;
	}

	// Tests the move out on a copy of the current board
	public Board tryMove(Point[] points) {
		try {
			Board copy = this.boardCopy();
			copy.makeMove(points);
			return copy;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	//Undoes a move if it is illegal
	public void undoMove() {
		if (previousMove == null) {
			System.out.println("Illegal move, try again \n" + this.currentPlayer + "\'s move: ");
		} else {
			this.pieces = previousMove.pieces;
			this.currentPlayer = previousMove.currentPlayer;
			this.kingInCheck = previousMove.kingInCheck;
			this.previousMove = previousMove.previousMove;
			this.drawBoard();
		}
	}

	// Officially Makes a move after all testing is complete
	public void makeMove(Point[] points) {
		try {
			previousMove = this.boardCopy();
			Piece pieceOnBoard = this.getPieceAt(points[0]);
			if (this.getPieceAt(points[1]) != null) {
				pieces.remove(this.getPieceAt(points[1]));
			}
			pieceOnBoard.location = points[1];

			if (currentPlayer.equals("White"))
				currentPlayer = "Black";
			else
				currentPlayer = "White";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Computer Move
	public Point[] computerMove() {
		Piece random = null;
		ArrayList<Point> randomMoves = null;
		for (Piece p : pieces) {
			if (p.color.equals(this.currentPlayer.toLowerCase()) && !p.getMoves(this, true).isEmpty()) {
				random = p;
				randomMoves = p.getMoves(this, true);
				break;
			}
		}

		Point[] aimove = new Point[2];
		aimove[0] = random.location;
		aimove[1] = randomMoves.get(0);

		return aimove;
	}

	//If King is Moved or either rooks are moved, changes castling to false for that piece
	public void firstMove(Point[] points) {
		Piece piece = this.getPieceAt(points[0]);
		if (piece instanceof Rook) {
			((Rook) piece).setCastling(false);
		}

		if (piece instanceof King) {
			((King) piece).setCastling(false);
			;
		}
	}

	//Allows Castling and moves King and Rook to respective places
	public void doCastle(Point[] points) {
		if (points[0].equals(new Point(7, 4)) && points[1].equals(new Point(7, 6))) {
			Piece rook = this.getPieceAt(new Point(7, 7));
			rook.location = new Point(7, 5);
		} else if (points[0].equals(new Point(7, 4)) && points[1].equals(new Point(7, 2))) {
			Piece rook = this.getPieceAt(new Point(7, 0));
			rook.location = new Point(7, 3);
		} else if (points[0].equals(new Point(0, 4)) && points[1].equals(new Point(0, 6))) {
			Piece rook = this.getPieceAt(new Point(0, 7));
			rook.location = new Point(0, 5);
		} else if (points[0].equals(new Point(0, 4)) && points[1].equals(new Point(0, 2))) {
			Piece rook = this.getPieceAt(new Point(0, 0));
			rook.location = new Point(0, 3);
		}
	}

	//Checks if a pawn is eligible for promotion
	public boolean checkPromotion(Point[] points) {
		if (this.getPieceAt(points[0]) instanceof Pawn && points[0].x == 1
				&& ((this.getPieceAt(points[0])).color).equals("white")) {
			return true;
		} else if ((this.getPieceAt(points[0]) instanceof Pawn && points[0].x == 6
				&& ((this.getPieceAt(points[0])).color).equals("black"))) {
			return true;
		} else {
			return false;
		}
	}

	//Adds a new piece in place of the pawn during promotion
	public void promotion(Piece promotionPiece, Point[] points) {
		try {
			this.previousMove = this.boardCopy();
			if (this.getPieceAt(points[1]) == null) {
				pieces.remove(this.getPieceAt(points[0]));
				pieces.add(promotionPiece);
				promotionPiece.location = points[1];
			} else {
				pieces.remove(this.getPieceAt(points[1]));
				pieces.remove(this.getPieceAt(points[0]));
				pieces.add(promotionPiece);
				promotionPiece.location = points[1];
			}
			if (currentPlayer.equals("White"))
				currentPlayer = "Black";
			else
				currentPlayer = "White";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Checks to see if current player is in Checkmate
	public boolean checkmate() {

		ArrayList<Point> white = new ArrayList<Point>();
		ArrayList<Point> black = new ArrayList<Point>();

		for (Piece p : pieces) {
			if (p.color.equals("white")) {
				for (Point point : p.getMoves(this, true)) {
					white.add(point);
				}
			} else
				for (Point point : p.getMoves(this, true)) {
					black.add(point);
				}
		}

		return (white.size() == 0 || black.size() == 0) ? true : false;
	}

	//Checks EnPassant Eligibility
	public void enPassant(Point[] points) {
		for (Piece piece : pieces) {
			if (piece instanceof Pawn && piece.color.equals(currentPlayer.toLowerCase())) {
				((Pawn) piece).setEnPassant(false);
			}
		}

		Piece pawn = getPieceAt(points[0]);
		if (pawn instanceof Pawn && pawn.color.equals("white")) {
			if (points[1].x == 4 && points[0].x == 6) {
				((Pawn) pawn).setEnPassant(true);
			}
		}

		if (pawn instanceof Pawn && pawn.color.equals("black")) {
			if (points[1].x == 3 && points[0].x == 1) {
				((Pawn) pawn).setEnPassant(true);
			}
		}
	}

	//Perform EnPassant
	public void performEnPassant(Point[] points) {
		try {
			previousMove = this.boardCopy();
			Piece pawn = getPieceAt(points[0]);
			pawn.location = points[1];
			this.removePiece(this.getPieceAt(new Point(points[0].x, points[1].y)));

			if (currentPlayer.equals("White"))
				currentPlayer = "Black";
			else
				currentPlayer = "White";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Checks if the move is an EnPassant
	public boolean checkEnPassant(Point[] points) {
		if (!(this.getPieceAt(points[0]) instanceof Pawn))
			return false;
		if (this.getPieceAt(points[1]) != null)
			return false;
		if (points[0].y == points[1].y)
			return false;

		return true;
	}

	//Print Board to Terminal
	public void drawBoard() {
		System.out.println();
		String[][] board = new String[8][8];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				for (Piece p : pieces) {
					if (p.location.equals(new Point(i, j))) {
						board[i][j] = p.getName();
						break;
					}
				}
				if (board[i][j] == null) {
					if ((i + j) % 2 == 1)
						board[i][j] = "## ";
					else
						board[i][j] = "   ";
				}
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j]);
			}
			System.out.print(7 - i + 1);

			System.out.println();
		}

		System.out.println(" a  b  c  d  e  f  g  h ");
		System.out.println();

		if (this.check() != null && !this.checkmate()) {
			System.out.println("Check");
		}
		if (!this.checkmate()) {
			System.out.print(currentPlayer + "\'s move: ");
		}
	}

}
