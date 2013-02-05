
import java.util.ArrayList;
import java.util.HashMap;
import edu.rit.util.Random;
public class Node {
	/**
	 * Class Node Denotes the Node present within the Random DHT Network
	 * @authors Roshan Balaji
	 */
	// This nodes the unique NodeID for eachnode
	int node_id;
	// The hash map stores the content of the node, which is Hashed NodeID mapped to a Integer NodeID object.
	public HashMap<Integer, Integer> DHT=new HashMap<Integer, Integer>();
	// A repository that holds the list of neighbors this node has.
	public final ArrayList<Node> neighbours=new ArrayList<Node>();
	/**
	 * Construct a new Node object.
	 * 
	 * @param  _node_id  int Depicts the NodeID.
	 * 
	 * @param _content Integer Integer object that has NodeID as its value.
	 *
	 * 
	 */
	public Node(int _node_id,Integer _content){
		node_id=_node_id;
		DHT.put(_content.hashCode(),_content);
	}
	/**
	 * This function adds neighbors to the calling Node
	 *
	 * @param  _neighbours Node The Node which is a neighbor to the calling Node.
	 *
	 * @exception  none.
	 */
	public void addNeighbours(Node _neighbours){
		this.neighbours.add(_neighbours);
	}
	/**
	 * This function returns the NodeID of the calling Node
	 *
	 * @param none.
	 * 
	 * @return  node_id Integer the NodeID of the calling Node.
	 *
	 * @exception  none.
	 */
	public Integer getID(){
		return this.node_id;
	}
	/**
	 * This function is used to query the calling Node, to check if it has the queried content.
	 *
	 * @param _query Query The actual query object from the client.
	 * 
	 * @param rand Random the random generator that is used to randomly identify the next neighbor.
	 * 
	 * @return  Boolean true if the queried content is in this Node.
	 *
	 * @exception  none.
	 */
	public boolean query(Query _query,edu.rit.util.Random rand,int _N) throws InterruptedException{
		// The hop count of the query is incremented.
			_query.hopcount++;
			//To check if the queried content is in the Node
			if(this.DHT.containsKey(_query.Searchkey)){
				// to change the destination back to the source node, to simulate returning of the query to the client
				
					return true;
			}
			else{
				// if the queried content is not found in the node, the forward function is called.
				return forward(_query,rand,_N);
			}
		}

	
	/**
	 * This function is used to forward the query to one of its neighbors
	 *
	 * @param _query Query The actual query object from the client.
	 * 
	 * @param rand Random the random generator that is used to randomly identify the next neighbor.
	 * 
	 * @return  Boolean true if the queried content is in this Node.
	 *
	 * @exception  none.
	 */
	public boolean forward(Query _query,Random _rand,int _N) throws InterruptedException{
		// this is used to generate different seed for the random function
			Thread.sleep(2);
			// a random neighbor NodeID is selected from the neighbor list.
			int randomneighbours=_rand.nextInt(_N);
		 	// query is sent to the selected neighbor.
		 	return neighbours.get(randomneighbours).query(_query,_rand,_N);
	}
}
