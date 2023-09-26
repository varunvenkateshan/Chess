package chess;

import java.awt.Point;
import java.util.*;
import board.Board;
import pieces.*;

//Created by Varun Venkateshan and Yashwant Balaji
public class Chess {

	public static void main(String[] args) {
		Board game = new Board();
		Scanner scanner = new Scanner(System.in);
		game.drawBoard();
		
		boolean resign = false;
		boolean drawPrompted = false;
		boolean draw = false;
		
		while (!game.checkmate() && !resign && !draw) {
			
			String input = scanner.nextLine();
			if (input.toLowerCase().equals("resign") && !drawPrompted) {
				resign = true;
				break;
			} 
			if (input.toLowerCase().equals("draw")) {
				drawPrompted = true;
				System.out.println(game.currentPlayer + " has requested a draw, do you accept?");
				String drawInput = scanner.nextLine();
				if (drawInput.toLowerCase().equals("y")) {
					draw = true;
					break;
				} else {
					draw = false;
					drawPrompted = false;
					System.out.println(game.currentPlayer + "\'s move:");
					continue;
				}
			}
			if (input.toLowerCase().equals("undo")) {
				game.undoMove();
			} else if (drawPrompted) {
				if (!input.equals("draw")) {
					System.out.println("Illegal move, try again \n" + game.currentPlayer + "\'s move: ");
				} else {
					draw = true;
					break;
				}
			} else {
				
				Point[] move = new Point[2];
				if (input.toLowerCase().equals("ai")) {
					move = game.computerMove();
				} else {
					move = game.move(input.substring(0,2), input.substring(3,5));
				}
				
				if (game.isValidMove(move)) {
					if (game.checkPromotion(move)) {
						if (input.length() ==  7) {
							//can either be Q, N, R, B
							switch (input.charAt(6)) {
								case 'Q':
									game.promotion(new Queen(game.currentPlayer.toLowerCase(),0,0), move);
									game.drawBoard();
									break;
								case 'N':
									game.promotion(new Knight(game.currentPlayer.toLowerCase(),0,0), move);
									game.drawBoard();
									break;
								case 'R':
									game.promotion(new Rook(game.currentPlayer.toLowerCase(),0,0, true), move);
									game.drawBoard();
									break;
								case 'B':
									game.promotion(new Bishop(game.currentPlayer.toLowerCase(),0,0), move);
									game.drawBoard();
									break;
								default:
									System.out.println("Illegal move, try again \n" + game.currentPlayer + "\'s move: ");
									break;
							}
						} else {
							game.promotion(new Queen(game.currentPlayer.toLowerCase(),0,0), move);
							game.drawBoard();
						}
					} else {
						if (input.length() == 11 && input.substring(6,11).equals("draw?")) {
							drawPrompted = true;
						}

						game.firstMove(move); 
						game.enPassant(move);
						if (game.checkEnPassant(move)) {
							game.performEnPassant(move);
							game.drawBoard();
						} else {
							game.doCastle(move); 
							game.makeMove(move); 
							game.drawBoard(); 
						}
					}
				} else {
					System.out.print("Illegal move, try again \n" + game.currentPlayer + "\'s move: ");
				}	
			}
		}
		
		if (!draw && !resign) {
			System.out.println("Checkmate");
		}
		
		if (!draw) {
			if (game.currentPlayer.toLowerCase().equals("white"))
				System.out.println("Black wins");
			else 
				System.out.println("White wins");
		}

		if (drawPrompted && draw) {
			System.out.println("Game is Drawn");
		}
		
		scanner.close();
	}
}