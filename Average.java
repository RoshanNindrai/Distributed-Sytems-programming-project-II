import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.rit.numeric.ListSeries;
import edu.rit.numeric.ListXYSeries;
import edu.rit.numeric.Series;
import edu.rit.numeric.Statistics;
import edu.rit.numeric.XYSeries.Stats;
import edu.rit.numeric.plot.Plot;
import edu.rit.numeric.plot.Strokes;


public class Average {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File input=new File("input.txt");
		Scanner scan=new Scanner(input);
		double total=0;
		ListXYSeries NHseries = new ListXYSeries();
		
		int a=1;
		System.out.println("N"+"\t"+"Average hop");
		double chisquare=0.0;
		while(scan.hasNext())
		{
			ListSeries HMeanSeries = new ListSeries();
				total=0;
				for(int i=0;i<3;i++){
					double temp=scan.nextDouble();
						total=total+temp;
						HMeanSeries.add(temp);
				}
		a++;
		
		
		Series.Stats stats = HMeanSeries.stats();
		System.out.println(a+"\t"+stats.meanX);
		NHseries.add(a,stats.meanX);
		System.out.println(stats.stddevX);
		System.out.println(stats.meanX - 2*a);
		double chi =(stats.meanX - 2*a)/stats.stddevX;
		if(stats.stddevX!=0){
		System.out.printf ("chi steps   = %.5f%n", chi);
		chisquare += chi*chi;
		}
		}
		System.out.printf ("chi^2   = %.5f%n", chisquare);
		System.out.printf ("p-value = %.5f%n",
			Statistics.chiSquarePvalue (50-1+1 , chisquare));
		new Plot()
		.xAxisTitle ("Dimension N")
		.yAxisTitle ("Average Hops H")
		.seriesStroke (null)
		.xySeries (NHseries)
		.seriesStroke (Strokes.solid (1))
		.seriesColor (Color.RED)
		.seriesDots (null)
		.xySeries (0, 0, a, 2*a)
		.getFrame()
		.setVisible (true);
		
	}

}
