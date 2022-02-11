import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.io.*;
/*
 * This is a program that resembles the EightPuzzle game. It uses Manhattan distance and the number of misplaced tiles 
 * as heuristics in searching algorithms such as A star and beam search to calculate the best possible path to the 
 * solution, and the number of moves it takes. It includes a variety of helper methods such as the move and canMove 
 * methods, the Manhattan method, and the misplaced tiles method to implement these searching algorithms effectively. 
 * This program also implements a file reader and reads commands from  a text file and outputs the results of the commands
 * to the terminal. 
 */
public class EightPuzzle {
	
	//Stores puzzle value.
	private char[][] puzzle; 
	//Stores the row of the blank tile. 
	private int rowOfBlank; 
	//Stores the column of the blank tile. 
	private int colOfBlank; 
	//Stores the goal state. 
	private final char[][] goal = {{'b', '1', '2'}, {'3', '4', '5'}, {'6', '7', '8'}};
	//Stores the maximum number of nodes to be searched in A-star and beam search. 
	private Integer maxNodes = null; 
	
	//Constructor for the 8 Puzzle. 10 is the default value for the row and col of blank until find blank is called. 
	public EightPuzzle(char[][] puzzle) {
		this.puzzle = puzzle; 
		this.rowOfBlank = 10; 
		this.colOfBlank = 10; 
	}
	
	//Searches for the location of the blank tile in the puzzle. Used in the move method. 
	public void findBlank() {
		if(rowOfBlank == 10 && colOfBlank == 10) {
			for(int i = 0; i < puzzle.length; i++) {
				for(int j = 0; j < puzzle[0].length; j++) {
					if(puzzle[i][j] == 'b') {
					this.rowOfBlank = i; 
					this.colOfBlank = j; 
					}
				}
			}
		}
		else return; 
	}
	
	//Prints the state of the puzzle. 
	public void printState() {
		for(int i = 0; i < puzzle.length; i++) {
			System.out.println();
			for(int j = 0; j < puzzle[0].length; j++) {
				System.out.print(puzzle[i][j] + " ");
			}
		}
		System.out.println();
	}
	
	//Sets the state of the puzzle. 
	public void setState(String state) {
		for(int i = 0; i < puzzle.length; i++) {
			for(int j = 0; j < puzzle[0].length; j++) {
				if(i == 0) {
					puzzle[i][j] = state.charAt(j);
				}
				else if(i == 1) {
					puzzle[i][j] = state.charAt(j + 4);
				}
				else {
					puzzle[i][j] = state.charAt(j + 8);
				}
			}
		}
	}
	
	//Returns whether the move can occur based on where the blank tile is. 
	public boolean canMove(String direction) {
		findBlank();
		if(direction.equals("up")) {
			if(rowOfBlank == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else if(direction.equals("down")) {
			if(rowOfBlank == 2) {
				return false; 
			}
			else {
				return true; 
			}
		}
		else if(direction.equals("left")) {
			if(colOfBlank == 0) {
				return false; 
			}
			else {
				return true; 
			}
		}
		else {
			if(colOfBlank == 2) {
				return false; 
			}
			else {
				return true;
			}
		}
	}
	
	//Uses helper method canMove to move the tile in the desired location. 
	public void move(String direction) {
		findBlank();
		if(direction.equals("up")) {
			if(canMove("up") == true) {
				char temp = puzzle[rowOfBlank - 1][colOfBlank]; 
				puzzle[rowOfBlank - 1][colOfBlank] = puzzle[rowOfBlank][colOfBlank]; 
				puzzle[rowOfBlank][colOfBlank] = temp; 
				rowOfBlank --; 
			}
		}
		else if(direction.equals("down")) {
			if(canMove("down") == true ){
				char temp = puzzle[rowOfBlank + 1][colOfBlank]; 
				puzzle[rowOfBlank + 1][colOfBlank] = puzzle[rowOfBlank][colOfBlank]; 
				puzzle[rowOfBlank][colOfBlank] = temp; 
				rowOfBlank++; 
			}
		}
		else if(direction.equals("left")) {
			if(canMove("left") == true){
				char temp = puzzle[rowOfBlank][colOfBlank - 1]; 
				puzzle[rowOfBlank][colOfBlank - 1] = puzzle[rowOfBlank][colOfBlank]; 
				puzzle[rowOfBlank][colOfBlank] = temp; 
				colOfBlank--; 
			}
		}
		else {
			if(canMove("right") == true){
				char temp = puzzle[rowOfBlank][colOfBlank + 1]; 
				puzzle[rowOfBlank][colOfBlank + 1] = puzzle[rowOfBlank][colOfBlank]; 
				puzzle[rowOfBlank][colOfBlank] = temp; 
				colOfBlank++; 
			}
		}
		
	}
	
	/*
	 * Makes n random moves from the goal state. 
	 */
	public void randomizeState(int n) {
		for(int i = 0; i < n; i++) {
			//Stores the previous move so the random move doesn't go back to the previous state. 
			String previousMove = "";
			//Random int 0-3 to resemble 4 options for 4 different moves. 
			int random = (int)(Math.random() * 4);
			if(random == 0) {
				if(!canMove("up") || previousMove.equals("down"))
					i--; 
				else {
					move("up");
					previousMove = "up";
				}
			}
			else if(random == 1) {
				if(!canMove("down") || previousMove.equals("up"))
					i--; 
				else {
					move("down");
					previousMove = "down";
				}
			}
			else if(random == 2) {
				if(!canMove("left") || previousMove.equals("right"))
					i--; 
				else {
					move("left");
					previousMove = "left";
				}
			}
			else if(random == 3){
				if(!canMove("right") || previousMove.equals("left"))
					i--; 
				else {
					move("right");
					previousMove = "right";
				}
			}
		}
	}
	
	//Calculates the number of misplaced tiles in the puzzle. 
	public int misplacedTiles() {
		int misplacedTiles = 0; 
		for(int i = 0; i < puzzle.length; i++) {
			for(int j = 0; j < puzzle[0].length; j++) {
				if(puzzle[i][j] == '1') {
					if(i != 0 || j != 1)
						misplacedTiles++; 
				}
				else if(puzzle[i][j] == '2') {
					if(i != 0 || j != 2)
						misplacedTiles++; 
				}
				else if(puzzle[i][j] == '3') {
					if(i != 1 || j != 0)
						misplacedTiles++; 
				}
				else if(puzzle[i][j] == '4') {
					if(i != 1 || j != 1)
						misplacedTiles++; 
				}
				else if(puzzle[i][j] == '5') {
					if(i != 1 || j != 2)
						misplacedTiles++; 
				}
				else if(puzzle[i][j] == '6') {
					if(i != 2 || j != 0)
						misplacedTiles++; 
				}
				else if(puzzle[i][j] == '7') {
					if(i != 2 || j != 1)
						misplacedTiles++; 
				}
				else if(puzzle[i][j] == '8') {
					if(i != 2 || j != 2)
						misplacedTiles++; 
				}
			}
		}
		return misplacedTiles; 
	}
	
	//Calculates the total Manhattan distance for the puzzle state. 
	public int manhattanDistance() {
		int manhattanDistance = 0; 
		for(int i = 0; i < puzzle.length; i++) {
			for(int j = 0; j < puzzle[0].length; j++) {
				if(puzzle[i][j] == '1') {
					manhattanDistance += Math.abs(i - 0) + Math.abs(j - 1);
				}
				if(puzzle[i][j] == '2') {
					manhattanDistance += Math.abs(i - 0) + Math.abs(j - 2);
				}
				if(puzzle[i][j] == '3') {
					manhattanDistance += Math.abs(i - 1) + Math.abs(j - 0);
				}
				if(puzzle[i][j] == '4') {
					manhattanDistance += Math.abs(i - 1) + Math.abs(j - 1);
				}
				if(puzzle[i][j] == '5') {
					manhattanDistance += Math.abs(i - 1) + Math.abs(j - 2);
				}
				if(puzzle[i][j] == '6') {
					manhattanDistance += Math.abs(i - 2) + Math.abs(j - 0);
				}
				if(puzzle[i][j] == '7') {
					manhattanDistance += Math.abs(i - 2) + Math.abs(j - 1);
				}
				if(puzzle[i][j] == '8') {
					manhattanDistance += Math.abs(i - 2) + Math.abs(j - 2);
				}
			}
		}
		return manhattanDistance; 
	}
	
	//Helper method to clone the puzzle state to avoid reference error. 
	public static char[][] cloneArray(char[][] a) {
	    int length = a.length;
	    char[][] target = new char[length][a[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(a[i], 0, target[i], 0, a[i].length);
	    }
	    return target;
	}
	
	//Method to set the max nodes to be searched in A-star and beam search. 
	public void maxNodes(Integer n) {
		this.maxNodes = n;
	}
	
	//A star search method that takes in a heuristic and returns the path and the number of moves for the optimal solution
	public void aStar(String heuristic) { 
		if(heuristic.equals("h1")) {
			int nodesSearched = 0; 
			Node root = new Node(new EightPuzzle(puzzle));
			root.setDepth(0);
			root.setManhattanDistance(this.manhattanDistance());
			//Priority queue using the comparator for Manhattan distance and depth. 
			PriorityQueue<Node> queue= new PriorityQueue<Node>(new NodeComparatorManhattan());
			queue.add(root);	
			while(!queue.isEmpty()) {
				//If the max nodes limit is exceeded, end the search and print error message
				if(this.maxNodes != null && nodesSearched == this.maxNodes) {
					System.out.println("Maximum number of nodes is exceeded");
					break; 
				}
				//remove first element in the sorted queue and set as the current state
				Node currentState = queue.remove(); 
				nodesSearched++;
				currentState.getPuzzle().printState();
				//If the state is the goal solution
				if(currentState.getManhattanDistance() == 0 || currentState.getPuzzle().puzzle == this.goal) {
					currentState.getPuzzle().printState();
					System.out.println();
					System.out.println("The Path to victory is: " + currentState.getPathToSolution());
					System.out.println("The number of moves is" + " " + currentState.getDepth());
					System.out.println("The number of nodes searched is: " + nodesSearched);
					break; 
				}
				else {
					//If the blank tile is able to move left
					if(currentState.getPuzzle().canMove("left")) {
						//make a new puzzle and move it left
						EightPuzzle leftPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						leftPuzzle.move("left");
						//If there is no parent, or the parent is not the same as the child to avoid repeated moves
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, leftPuzzle.puzzle)) {
							//Make new child and set all private instance data needed, then add to the queue
							Node child = new Node(leftPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							child.setManhattanDistance(leftPuzzle.manhattanDistance());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "left");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "left");
							queue.add(child); 
						}
					}
					//repeat for right direction 
					if(currentState.getPuzzle().canMove("right")) {
						EightPuzzle rightPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						rightPuzzle.move("right");
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, rightPuzzle.puzzle)) {
							Node child = new Node(rightPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							child.setManhattanDistance(rightPuzzle.manhattanDistance());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "right");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "right");
							queue.add(child); 
						}
					}
					//repeat for up direction 
					if(currentState.getPuzzle().canMove("up")) {
						EightPuzzle upPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						upPuzzle.move("up");
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, upPuzzle.puzzle)) {
							Node child = new Node(upPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							child.setManhattanDistance(upPuzzle.manhattanDistance());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "up");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "up");
							queue.add(child); 
						}
					}
					//repeat for down direction
					if(currentState.getPuzzle().canMove("down")) {
						EightPuzzle downPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						downPuzzle.move("down");
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, downPuzzle.puzzle)) {
							Node child = new Node(downPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							child.setManhattanDistance(downPuzzle.manhattanDistance());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "down");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "down");
							queue.add(child); 	
						}
					}
				}
			}
		}
		//For h2, the method is the same except the comparator is different, and misplaced tiles is set instead of Manhattan distance
		if(heuristic.equals("h2")) {
			int nodesSearched = 0;
			Node root = new Node(new EightPuzzle(puzzle));
			root.setDepth(0);
			root.setMisplacedTiles(this.misplacedTiles());
			//New queue with misplaced tiles comparator
			PriorityQueue<Node> queue= new PriorityQueue<Node>(new NodeComparatorMisplacedTiles());
			queue.add(root);			
			while(!queue.isEmpty()) {
				//If the maxnodes value is exceeded
				if(this.maxNodes != null && nodesSearched == this.maxNodes) {
					System.out.println("Maximum number of nodes is exceeded");
					break; 
				}
				//sets the current state to the first node in the queue
				Node currentState = queue.remove(); 
				currentState.getPuzzle().printState();
				nodesSearched++; 
				//If the current state is the goal state 
				if(currentState.getMisplacedTiles() == 0 || currentState.getPuzzle().puzzle == this.goal) {
					currentState.getPuzzle().printState();
					System.out.println("The Path to victory is: " + currentState.getPathToSolution());
					System.out.println("The number of moves is" + " " + currentState.getDepth());
					System.out.println("The number of nodes searched is: " + nodesSearched);
					break; 
				}
				else {
					
					if(currentState.getPuzzle().canMove("left")) {
						EightPuzzle leftPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						leftPuzzle.move("left");
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, leftPuzzle.puzzle)) {
							Node child = new Node(leftPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							//The same as h1 variation, except misplaced tiles is set not Manhattan distance
							child.setMisplacedTiles(leftPuzzle.misplacedTiles());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "left");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "left");
							queue.add(child); 
						}
					}
					if(currentState.getPuzzle().canMove("right")) {
						EightPuzzle rightPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						rightPuzzle.move("right");
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, rightPuzzle.puzzle)) {
							Node child = new Node(rightPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							child.setMisplacedTiles(rightPuzzle.misplacedTiles());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "right");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "right");
							queue.add(child); 
						
						}
					}
					if(currentState.getPuzzle().canMove("up")) {
						EightPuzzle upPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						upPuzzle.move("up");
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, upPuzzle.puzzle)) {
							Node child = new Node(upPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							child.setMisplacedTiles(upPuzzle.misplacedTiles());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "up");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "up");
							queue.add(child); 
							
						}
					}
					if(currentState.getPuzzle().canMove("down")) {
						EightPuzzle downPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
						downPuzzle.move("down");
						if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, downPuzzle.puzzle)) {
							Node child = new Node(downPuzzle); 
							child.setParent(currentState);
							child.setDepth(currentState.getDepth() + 1);
							child.setMisplacedTiles(downPuzzle.misplacedTiles());
							if(currentState.getParent() == null)
								child.setPathToSolution(currentState.getPathToSolution() + "down");
							else child.setPathToSolution(currentState.getPathToSolution() + ", " + "down");
							queue.add(child); 	
						}
					}		
				}
			}
		}
	}
	
	/*
	 * This is a beam search that functions similarly to the A star search, except the frontier is limited
	 * to the k value that is put into the method. The method makes sure that the frontier does not exceed
	 * the k value, which allows the method to compare k surrounding states and find the best possible state of those states. 
	 */
	public void beam(int k) {
		int nodesSearched = 0;
		Node root = new Node(new EightPuzzle(puzzle));
		root.setDepth(0);
		root.setManhattanDistance(this.manhattanDistance());
		//creates an arraylist of nodes that is limited to size k. 
		ArrayList<Node> queue = new ArrayList<Node>();
		queue.add(root);	
		while(!queue.isEmpty()) {
			//If the maxnodes limit is reached the search is terminated. 
			if(this.maxNodes != null && nodesSearched == this.maxNodes) {
				System.out.println("Maximum number of nodes is exceeded");
				break; 
			}
			Node currentState = queue.remove(0); 
			nodesSearched++; 
			if(currentState.getManhattanDistance() == 0 || currentState.getPuzzle().puzzle == this.goal) {
				currentState.getPuzzle().printState();
				System.out.println("The Path to victory is: " + currentState.getPathToSolution());
				System.out.println("The number of moves is" + " " + currentState.getDepth());
				System.out.println("The number of nodes searched is: " + nodesSearched);
				break; 
			}
			else { 
				//If the blank tile is able to move left
				if(currentState.getPuzzle().canMove("left")) {
					//make a new puzzle and move it left
					EightPuzzle leftPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
					leftPuzzle.move("left");
					//If the parent of the current state equals the new left puzzle (repeated states) dont add the child to the frontier. 
					if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, leftPuzzle.puzzle)) {
						Node child = new Node(leftPuzzle); 
						child.setParent(currentState);
						child.setDepth(currentState.getDepth() + 1);
						child.setManhattanDistance(leftPuzzle.manhattanDistance());
						//Adds to the solution path
						if(currentState.getParent() == null)
							child.setPathToSolution(currentState.getPathToSolution() + "left");
						else child.setPathToSolution(currentState.getPathToSolution() + ", " + "left");
						/*
						 * If k is one and the queue empty, add to the queue, otherwise check to see if it has a lower manhattan
						 * distance than the first element in the queue. If it does, remove the one in the queue and add the 
						 * child with lower manhattan distance
						 */
						if(k == 1) {
							if(queue.isEmpty()) {
								queue.add(child); 
							}
							if(!queue.isEmpty()) {
								if(queue.get(0).getManhattanDistance() > child.getManhattanDistance()) {
									queue.remove(k - 1);
									queue.add(child);
								}
							}
						}
						//Otherwise, if there is room in the queue, add the child and sort the arraylist using a comparator. 
						else if(queue.size() < k) {
							queue.add(child); 
							Collections.sort(queue, new NodeComparatorManhattan());
						}
						//If the queue is full, remove the last child (worst manhattan value) and add the new value to avoid getting stuck at local maxima. 
						else if(queue.size() == k) {
							queue.remove(k - 1);
							queue.add(child);
							Collections.sort(queue, new NodeComparatorManhattan());
						}
					}					
				}
				//repeat above steps for moving right
				if(currentState.getPuzzle().canMove("right")) {
					EightPuzzle rightPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
					rightPuzzle.move("right");
					if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, rightPuzzle.puzzle)) {
						Node child = new Node(rightPuzzle); 
						child.setParent(currentState);
						child.setDepth(currentState.getDepth() + 1);
						child.setManhattanDistance(rightPuzzle.manhattanDistance());
						if(currentState.getParent() == null)
							child.setPathToSolution(currentState.getPathToSolution() + "right");
						else child.setPathToSolution(currentState.getPathToSolution() + ", " + "right");
						if(k == 1) {
							if(queue.isEmpty()) {
								queue.add(child); 
							}
							if(!queue.isEmpty()) {
								if(queue.get(0).getManhattanDistance() > child.getManhattanDistance()) {
									queue.remove(k - 1);
									queue.add(child);
								}
							}
						}
						if(queue.size() < k) {
							queue.add(child); 
							Collections.sort(queue, new NodeComparatorManhattan());
						}
						else if(queue.size() == k ) {
							queue.remove(k - 1);
							queue.add(child);
							Collections.sort(queue, new NodeComparatorManhattan());
						}
					}
				}
				//repeat above steps for moving up
				if(currentState.getPuzzle().canMove("up")) {
					EightPuzzle upPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
					upPuzzle.move("up");
					if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, upPuzzle.puzzle)) {
						Node child = new Node(upPuzzle); 
						child.setParent(currentState);
						child.setDepth(currentState.getDepth() + 1);
						child.setManhattanDistance(upPuzzle.manhattanDistance());
						if(currentState.getParent() == null)
							child.setPathToSolution(currentState.getPathToSolution() + "up");
						else child.setPathToSolution(currentState.getPathToSolution() + ", " + "up");
						if(k == 1) {
							if(queue.isEmpty()) {
								queue.add(child); 
							}
							if(!queue.isEmpty()) {
								if(queue.get(0).getManhattanDistance() > child.getManhattanDistance()) {
									queue.remove(k - 1);
									queue.add(child);
								}
							}
						}
						if(queue.size() < k) {
							queue.add(child); 
							Collections.sort(queue, new NodeComparatorManhattan());
						}
						else if(queue.size() == k ) {
							queue.remove(k - 1);
							queue.add(child);
							Collections.sort(queue, new NodeComparatorManhattan());
						}
					}
				}
				//repeat above steps for moving down
				if(currentState.getPuzzle().canMove("down")) {
					EightPuzzle downPuzzle = new EightPuzzle(cloneArray(currentState.getPuzzle().puzzle)); 
					downPuzzle.move("down");
					if(currentState.getParent() == null || !Arrays.deepEquals(currentState.getParent().getPuzzle().puzzle, downPuzzle.puzzle)) {
						Node child = new Node(downPuzzle); 
						child.setParent(currentState);
						child.setDepth(currentState.getDepth() + 1);
						child.setManhattanDistance(downPuzzle.manhattanDistance());
						if(currentState.getParent() == null)
							child.setPathToSolution(currentState.getPathToSolution() + "down");
						else child.setPathToSolution(currentState.getPathToSolution() + ", " + "down");
						if(k == 1) {
							if(queue.isEmpty()) {
								queue.add(child); 
							}
							if(!queue.isEmpty()) {
								if(queue.get(0).getManhattanDistance() > child.getManhattanDistance()) {
									queue.remove(k - 1);
									queue.add(child);
								}
							}
						}
						if(queue.size() < k) {
							queue.add(child); 
							Collections.sort(queue, new NodeComparatorManhattan());
						}
						else if(queue.size() == k) {
							queue.remove(k - 1);
							queue.add(child);
							Collections.sort(queue, new NodeComparatorManhattan());
						}	
					}
				}	
			}
		}
	}
	
	
	public static void main(String[] args) {
		//File file = new File(args[0]);
		//BufferedReader b = new BufferedReader(new FileReader(file));
		//System.out.println(b.readLine());
		//String line = "";
		char[][] c = {{'b', '1', '2'}, 
				  	  {'3', '4', '5'}, 
				  	  {'6', '7', '8'}};
		EightPuzzle puzzle = new EightPuzzle(c); 
		puzzle.randomizeState(100);
		puzzle.printState();
		puzzle.aStar("h1");
		
		
//		while((line = b.readLine()) != null) {
//			String command = "";
//			for(int i = 0; i < line.length(); i++) {
//				if(line.charAt(i) == ' ') {
//					break;
//				}
//				command += line.charAt(i);
//			}
//			if(command.equals("setState")) {
//				String input = "";
//				for(int i = 9; i < line.length(); i++) {
//					input += line.charAt(i);
//				}
//				puzzle.setState(input);
//			}
//			if(command.equals("move")) {
//				String input = "";
//				for(int i = 5; i < line.length(); i++) {
//					input += line.charAt(i);
//				}
//				puzzle.move(input);
//			}
//			if(command.equals("printState")) {
//				puzzle.printState();
//			}
//			if(command.equals("randomizeState")) {
//				String input = "";
//				for(int i = 15; i < line.length(); i++) {
//					input += line.charAt(i);
//				}
//				puzzle.randomizeState(Integer.parseInt(input));
//			}
//
//			if(command.equals("solveA-Star")) {
//				String input = "";
//				for(int i = 12; i < line.length(); i++) {
//					input += line.charAt(i);
//				}
//				puzzle.aStar(input);
//			}
//			if(command.equals("solveBeam")) {
//				String input = "";
//				for(int i = 10; i < line.length(); i++) {
//					input += line.charAt(i);
//				}
//				puzzle.beam(Integer.parseInt(input));
//			}
//			if(command.equals("maxNodes")) {
//				String input = "";
//				for(int i = 9; i < line.length(); i++) {
//					input += line.charAt(i);
//				}
//				puzzle.maxNodes(Integer.parseInt(input));
//			}
//		}
	}
}
