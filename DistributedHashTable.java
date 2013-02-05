import edu.rit.numeric.ListSeries;
import edu.rit.numeric.ListXYSeries;
import edu.rit.numeric.Series;
import edu.rit.numeric.Statistics;
import edu.rit.numeric.plot.Plot;
import edu.rit.numeric.plot.Strokes;
import edu.rit.util.Random;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class DistributedHashTable
	{/**
	 * Class DistributedHashTable Denotes the layout over which the Random DHT Network is layed out.
	 * @authors Roshan Balaji
	 */
	// A repository to hold the Nodes in the Network
		public Map<Integer,Node> DHTgraph = new HashMap<Integer,Node>();
public static void main
		(String[] args) throws InterruptedException
		{
		// checking the command line arguments for missing values
		if (args.length != 4) usage();
		int lowerBound = Integer.parseInt (args[0]); // Network Dimension lower bound
		int upperBound = Integer.parseInt (args[1]); // network Dimension upper bound
		int numberofTrials = Integer.parseInt (args[2]); // Number of Iteration 
		long seed = Long.parseLong (args[3]); //  seed
		// Class object is used to represent the layout.
		DistributedHashTable graphlayout=new DistributedHashTable();
		// A random object was created to provide random initialNode and Searkkey
		Random prng = Random.getInstance (seed);
		System.out.printf ("N\tV(average of 30 iterations)%n");
		//This series store NodeID and average hopcount for graph plotting
		ListXYSeries NHseries = new ListXYSeries();
		double chi2 = 0.0;
		// This is used to add N neighbours to the nodes in the Network
		for (int nodes = lowerBound; nodes <=upperBound; ++ nodes)
		{
			//to check if the node is already in the network layout
		if(!graphlayout.DHTgraph.containsKey(nodes)){
			// the first node in the network is created
			Node source=new Node(nodes,nodes);
			// the node is added to the layout
			graphlayout.DHTgraph.put(nodes,source);
		}
		// its neighbors are created and added
		for(int index=lowerBound;index<=upperBound;index++){
			// this is to prevent repetition of nodes in the layout.
			if(!graphlayout.DHTgraph.containsKey(index)){
					Node neighbour=new Node(index,index);
					graphlayout.DHTgraph.put(index,neighbour);
			}
			// The neighbor nodes are added
		graphlayout.DHTgraph.get(nodes).addNeighbours(graphlayout.DHTgraph.get(index));
		
	}
		}
		for (int nodes = lowerBound; nodes <= upperBound; ++ nodes)
			{
				ListSeries HMeanSeries = new ListSeries();
			for (int i = 0; i < numberofTrials; ++ i)
				{
				ListSeries Hseries = new ListSeries();
				for (int index = 0; index < numberofTrials; ++ index)
					{
					Integer searchid=Integer.valueOf(1+prng.nextInt(nodes));
					int destination=1+prng.nextInt(nodes);
					Query _query=new Query(searchid.hashCode(),destination);
					// the random InitialNode is queried.
					graphlayout.DHTgraph.get(destination).query(_query,prng,nodes);
					Hseries.add (_query.hopcount);
					}
				Series.Stats stats = Hseries.stats();
				double H = stats.meanX;
				HMeanSeries.add (H);
				}
			Series.Stats stats = HMeanSeries.stats();
			// Holds the mean value of query hopcount for the present Node size
			double HMean = stats.meanX;
			// Holds the standard deviation value
			double HStddev = stats.stddevX;
			// to prevent infinity answer
			if(HStddev!=0){
			System.out.printf ("%d \t %.4f, Standard Deviation = %.4f%n",
				nodes,HMean, HStddev);
			NHseries.add (nodes, HMean);
			//The value of chi is being calculated
			double chi = (HMean - nodes)/HStddev;
			// chi square is calcualted
			chi2 += chi*chi;
			}
			}
		System.out.printf ("chi^2   = %.5f%n", chi2);
		System.out.printf ("p-value = %.5f%n",
			Statistics.chiSquarePvalue (upperBound - lowerBound + 1, chi2));
		// a plot object is created to plot the graph with 
		// numberofnodes as x-axis and average hopcount as y axis.
		// a line that denotes number of nodes for reference is also drawn.
		new Plot()
			.xAxisTitle ("Dimension N")
			.yAxisTitle (" # of nodes visted V")
			.seriesStroke (null)
			.xySeries (NHseries)
			.seriesStroke (Strokes.solid (1))
			.seriesColor (Color.RED)
			.seriesDots (null)
			.xySeries (0, 0, upperBound, upperBound)
			.getFrame()
			.setVisible (true);

		}

	private static void usage()
		{
		System.err.println ("Usage: java Random DHT <lowerBound> <upperBound> <numberofTrials> <seed>");
		System.exit (1);
		}
	}