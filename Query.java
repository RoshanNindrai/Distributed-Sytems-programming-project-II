
public class Query {

	/**
	 * Class Query Denotes the query that is being propagated along the network
	 * @authors Roshan Balaji
	 */
	// Denotes the destination NodeID for this simulation
	int Searchkey=0;
	// The hopcount denotes the number of hop in the network that took tis query to 
	//reach the destination Node adn to return back.
	int hopcount=0;
	// The NodeID of the source Node where the query initially entered.
	Integer initial_node;
	/**
	 * Construct a new Query object.
	 * 
	 * @param  _Searchkey  int The NodeID of the Destination Node.
	 * 
	 * @param _node_id Integer The source NodeID.
	 *
	 * 
	 */
	public Query(int _Searchkey,Integer _node_id){
		Searchkey=_Searchkey;
		initial_node=_node_id;
		hopcount=0;
	}
	
}
