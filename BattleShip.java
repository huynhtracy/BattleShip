import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class BattleShip {
	
	//Next steps:
	//Play against computer
	//make more efficient

	static int ship2 = 2;
	static int ship3 = 3;
	static int ship4 = 4; 
	static int ship5 = 5;
	
	public static void main(String[] args) {

		
		Scanner myScanner = new Scanner(System.in);
		boolean done = false; 
		int geti=0;
		int getj=0;
		int counter = 0;
		boolean errori = true;
		boolean errorj = true;
		
		String [][] board; 
		String [][] playerBoard; 
		board = getNewBoard();
		playerBoard = getPlayerBoard();
		//printBoard(board);
		printBoard(playerBoard);
		
		System.out.println("Welcome to Battleship. Sink the enemy's ships in the least number of moves!");
		
		while (!done) {
			
			//get row input
			System.out.println("Input a row number between 0-9:");
			
			errori=true;
			errorj=true;
			
			//catch type errors
			while (errori) {
				try {
					geti = myScanner.nextInt();					
					myScanner.nextLine();

					errori = false; 
				}
				catch (InputMismatchException e) {
					myScanner.nextLine();
					System.out.println("Input not valid. Try again: ");
				}
				
			}
			
			//catch out of range errors
			while (!(geti >= 0 && geti <10)) {
				myScanner.nextLine();
				System.out.println("Input not valid. Try again:");
				geti = myScanner.nextInt();
			}
		
			//get column input
			System.out.println("Input a column number between 0-9:");
			
			//catch type errors
			while (errorj) {
				try {
					getj = myScanner.nextInt();
					errorj = false; 
					myScanner.nextLine();
				}
				
				catch (InputMismatchException e) {
					myScanner.nextLine();
					System.out.println("Input not valid. Try again: ");

				}
				
			}
			
			//catch out of range errors
			while (!(getj >= 0 && getj <10)) {
				//while input is not a valid int between 0-9
				myScanner.nextLine();
				System.out.println("Input not valid. Try again:");
				getj = myScanner.nextInt();
			}
			
			
			//shoot
			System.out.println(shoot(board,playerBoard,geti,getj));
			
			
			//printBoard(board);
			printBoard(playerBoard);
			done = isDone(board);
			counter++;
			
		}
		
		System.out.println("Congrats! You completed the game in " + counter + " moves!");
		myScanner.close();

	}
	
	
	public static String [][] getNewBoard(){
		//initializes new board with spaces
		String [][] board = new String [10][10];
		
		for(int i = 0; i <10; i++) {
			for(int j = 0; j < 10; j++) {
				board[i][j]="_";
			}
		}
		
		//positions ships onto board
		addShips(board,2);
		addShips(board,3);
		addShips(board,4);
		addShips(board,5);
		
		
		return board;
	}
	
	public static String [][] getPlayerBoard(){
		//initializes player view board with spaces
		String [][] board = new String [10][10];
		
		for(int i = 0; i <10; i++) {
			for(int j = 0; j < 10; j++) {
				board[i][j]="_";
			}
		}
		
		return board;
	}
	
	public static String [][] addShips(String [][] board, int size){
		//randomly adds ships onto a new board
		
		int randomi = ThreadLocalRandom.current().nextInt(0, 9-size + 1); //start position 1
		int randomj = ThreadLocalRandom.current().nextInt(0, 9 + 1); //start position 2
		int random_orientation = ThreadLocalRandom.current().nextInt(0, 2); //where 0 is horizontal, 1 is vertical
		boolean bool = true;
	
		while(bool) {
		
			if (can_place_ship_at(board, random_orientation, randomi,randomi + size, randomj)) {
				//checks if a ship can be placed in the randomly assigned position
				//mutates the board to add ships 
				mutateBoard(board,randomi, size, randomj,random_orientation);
				
				bool = false;
				
			}
			else {
				//generate new random position if ship cannot be placed in the position
				
				randomi = ThreadLocalRandom.current().nextInt(0, 9-size + 1);
				randomj = ThreadLocalRandom.current().nextInt(0, 9 + 1);
				random_orientation = ThreadLocalRandom.current().nextInt(0, 2);
				
			}
		}
		
		return board;
	}
	
	public static boolean can_place_ship_at(String[][] board, int dir, int start, int end, int location) {
		//return TRUE iff all spots are "_"
		//if horizontal, start/end are columns and location is the row
		//if vertical, start/end are rows and location is the column
		
		if (dir == 1) {
			//vertical

			for (int n = start; n <= end; n++) {
				if (board[n][location] != "_") {
					return false;
				}		
			}			
		}
		
		if (dir == 0) {
			//horizontal
			
			for (int n = start; n <= end; n++) {
				if (board[location][n] != "_") {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static String [][] mutateBoard(String [][] board,int i,int size, int j,int orientation){
		//adds ships onto board
		if (orientation == 0) {
			//horizontal
			for (int k = i; k< i+size; k++) {
				Integer size2 = size;
				String size3 = size2.toString();
				
				board[j][k] = size3;
			}
			
		}
		
		else {
			//vertical
			for (int k = i; k< i+ size; k++) {
				Integer size2 = size;
				String size3 = size2.toString();
				
				board[k][j] = size3;
			}
			
		}
		
		return board;
	}
	
	public static String shoot(String[][] board, String [][] playerBoard, int i, int j) {
		//mutates board and returns hit, miss, or sunk
		String k;
		
				
		if (board[i][j]=="_" || board[i][j] == "x") {
			board [i][j] = "x";
			playerBoard[i][j] = "x";
			return "Miss!";
		}
		else if (board[i][j].length()==2){		
			//it is a "kH" cell (has already been hit)
			return "Hit!";		
			
		}
		else {

			k = board [i][j];
			//change cell to "kH"
			board [i][j] = board [i][j] + "H";
			playerBoard[i][j] = k;
			
			//update counts
			if(k.equals("2")) {
				ship2--;
				if(ship2!=0) {
					return "Hit!";
				}
			}
			if(k.equals("3")) {
				ship3--;
				if(ship3!=0) {
					return "Hit!";
				}
			}
			if(k.equals("4")) {
				ship4--;
				if(ship4!=0) {
					return "Hit!";
				}
			}
			if(k.equals("5")) {
				ship5--;
				if(ship5!=0) {
					return "Hit!";
				}
			}
		}
		
		return "Sunk!";		
		
	}
		
	public static boolean isDone(String[][] board) {
		//checks if all ships have been sunk
		boolean done = (ship2 == 0 && ship3 ==0 && ship4 ==0 && ship5 ==0);
		return done;
	}
	
	
	public static void printBoard(String [][] board) {
			//converts double array to string and prints
			String s = "";
			
			for (String[] row : board) {
				s += Arrays.toString(row) + "\n";	
			}
	
			System.out.println(s);	
		}
		
	//***************************************************************************************
	
	public static boolean is_valid_starting_board(String[][] board) {
				
		int two_counter = 0;
		int three_counter = 0;
		int four_counter = 0;
		int five_counter = 0;		
		
		//check that its 10x10:
		//make sure it is 10 rows:
		if (board.length != 10) {
			return false;	
		}	
		//check if each row has 10 columns:
		for (int i = 0; i < 10; i++) {
		
			if (board[i].length != 10) {
				return false;
			}						
		}
		
		//count number of spots each boat takes up
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {	
				if (board[i][j]== "_") {	
					//do nothing, just to check 
				}
				
				else if (board[i][j]== "2") {
					two_counter++;		
				}
				
				else if (board[i][j]== "3") {
					three_counter++;
				}
				
				else if (board[i][j]== "4") {
					four_counter++;
				}
				
				else if (board[i][j]== "5") {	
					five_counter++;
				}
				
				else {
					//if there is an input that is not _,2,3,4, or 5, its not valid:
					return false;
				}				
			}				
			}
		
		//if the number of spots taken per type of boat is incorrect somehow, not a valid board
		if (two_counter != 2 || three_counter != 3 || four_counter != 4 || five_counter != 5) {
			return false;
		}
		
		//after we check this, we know there are 2 2 boat spots, 3 3 boat spots, etc.
		//so we can initialize these index arrays:
		
		
		int [] itwo = new int [2];
		int [] jtwo = new int [2];
		
		
		int [] ithree = new int [3];
		int [] jthree = new int [3];
		
		
		int [] ifour = new int [4];
		int [] jfour = new int [4];
	
		
		int [] ifive = new int [5];
		int [] jfive = new int [5];
		
		int second_counter_2 =0, second_counter_3 =0, second_counter_4=0, second_counter_5 = 0;
		
		//now must fill out the index matrices above and pass them onto is_contiguous
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
	
				if (board[i][j]== "2") {
					itwo[second_counter_2]=i;
					jtwo[second_counter_2]=j;
					second_counter_2++;

				}
				
				if (board[i][j]== "3") {
					ithree[second_counter_3]=i;
					jthree[second_counter_3]=j;
					second_counter_3++;				
				}
				
				if (board[i][j]== "4") {
					ifour[second_counter_4]=i;
					jfour[second_counter_4]=j;
					second_counter_4++;				
				}
				
				if (board[i][j]== "5") {
					ifive[second_counter_5]=i;
					jfive[second_counter_5]=j;
					second_counter_5++;
					
				}
				}
			}
	
		return (is__Contiguous(itwo, jtwo) && is__Contiguous(ithree, jthree) && is__Contiguous(ifour, jfour) && is__Contiguous(ifive, jfive));
	}
	
	public static boolean is__Contiguous(int [] i, int [] j) {
		//to be contiguous:
		//one of these arrays (i or j) will be the same number all the way through
		//the other array (i or j) will increment 
		int last_i  =  i[0];
		int last_j  =  j[0];
		boolean inci = false;
		boolean incj = false;
		
		for (int n  : i) {
			//loops through elements in i
			
			if (n != last_i) {
				//if i values are not the same then check if they increment by 1
				if (n != (last_i+1)) {
					//if they both don't increment by one or are the same, then not contiguous	
					return false;					
				}
				else {
					inci = true;
				}
				
			}
			last_i= n;
		}
			
		for (int n  : j) {	
			
			if (n!=last_j) {
				//if i values are not the same then check if they increment by 1
				if (n != (last_j +1)) {
					//if they both don't increment by one or are the same, then not contiguous	
					return false;					
				}
				else {
					incj = true;
				}
			}
			
			last_j=n;
		}
		//if they are both true then it means diagonal and return false (not contiguous)
		if (inci && incj) {
			return false;
		}
		
	return true;

	}

}





