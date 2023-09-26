package pieces;

import java.awt.Point;
import java.io.Serializable;
import java.util.*;
import board.Board;

//Created by Varun Venkateshan and Yashwant Balaji
public abstract class Piece implements Serializable{
	
	//White or Black
	public String color;
	
	//Placement on Chess Board
	public Point location;
	
	//Piece name
	public String name;
	
	//Constructor
	public Piece (String color, int x, int y) {
		this.color = color;
		this.location = new Point(x,y);
		name = "";
	}
	
	//Returns an ArrayList of possible moves for a piece when implemented in subclass
	public abstract ArrayList<Point> getMoves(Board board, boolean inCheck);

	//Returns Color of Piece in Question
	public String color(Piece p) {
		return color;
	}
	
	//Returns Name of Piece when implemented in subclass
	public abstract String getName();
	
}
