/*
 * This is a node class that was used to store the puzzle, the parent, the path to 
 * the solution, the Manhattan distance, the number of misplaced tiles, and the depth 
 * of the node throughout the search(how many moves it took to get to that said node.)
 */
public class Node {
	//Private instance data stored
	private EightPuzzle puzzle; 
	private Node parent; 
	private String pathToSolution = "";
	private int manhattanDistance; 
	private int misplacedTiles; 
	private int depth; 
	
	//constructor for the node
	public Node(EightPuzzle puzzle) {
		this.puzzle = puzzle; 
	}
	
	//Below are getters and setters for the private instance data. 
	public EightPuzzle getPuzzle() {
		return puzzle;
	}
	
	public void setPuzzle(EightPuzzle puzzle) {
		this.puzzle = puzzle;
	}  
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public String getPathToSolution() {
		return pathToSolution;
	}

	public void setPathToSolution(String pathToSolution) {
		this.pathToSolution = pathToSolution;
	}
	
	public int getManhattanDistance() {
		return manhattanDistance;
	}
	
	public void setManhattanDistance(int manhattanDistance) {
		this.manhattanDistance = manhattanDistance;
	}
	
	public int getMisplacedTiles() {
		return misplacedTiles;
	}
	
	public void setMisplacedTiles(int misplacedTiles) {
		this.misplacedTiles = misplacedTiles;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}
	