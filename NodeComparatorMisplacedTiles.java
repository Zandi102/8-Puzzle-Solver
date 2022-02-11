import java.util.Comparator;
/*
 * This is a comparator that helps sort the nodes into a priority queue based on the values of 
 * their depth and their number of misplaced tiles
 */
public class NodeComparatorMisplacedTiles implements Comparator<Node>{
	
	public int compare(Node a, Node b) {
		int fnA = a.getMisplacedTiles() + a.getDepth();
		int fnB = b.getMisplacedTiles() + b.getDepth(); 
		
		if(fnA > fnB) {
			return 1; 
		}
		else if(fnA < fnB) {
			return -1; 
		}
		return 0;
	}
}
