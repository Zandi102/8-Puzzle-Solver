import java.util.Comparator;
/*
 *  * This is a comparator that helps sort the nodes into a priority queue for
 *  the A star search, and sorts the nodes in an ArrayList for the beam search 
 *  based on the values of their depth and their Manhattan distance. 
 */
public class NodeComparatorManhattan implements Comparator<Node>{
	
	public int compare(Node a, Node b) {
		int fnA = a.getManhattanDistance() + a.getDepth();
		int fnB = b.getManhattanDistance() + b.getDepth(); 
		
		if(fnA > fnB) {
			return 1; 
		}
		else if(fnA < fnB) {
			return -1; 
		}
		return 0;
	}
}
