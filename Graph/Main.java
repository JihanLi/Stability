package Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Main {

	//The main function running the algorithm.
	public static void main(String[] args) throws IOException
	{
		new Main().main2();
				
	}
	
	public void main1() throws IOException 
	{
		String filename = "TrG.txt";
		
		for(int rate = 1; rate <= 10;rate++)
		{
			Graph g = new Graph();
			String wFilename = "TwG_"+rate+"0.txt";
			File f = new File(wFilename);
			PrintWriter output = new PrintWriter(new FileWriter(f,false));
			
			g.ReadGraph(filename,true);
			g.generateGraph(rate/10.0);
			SimpleWeightedGraph<String, DefaultWeightedEdge>  graph = g.getGraph();
			for(DefaultWeightedEdge e : graph.edgeSet())
			{
				output.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
				output.flush();
			}
			
			output.close();
		}
	}
	
	public void main2() throws IOException
	{
		String filename = "TrG.txt";
		
		int n = 3;
		for(int j = 7; j <= 9; j++)
		{
			Graph g = new Graph();
			String wFilename = "TwG_"+j+"0.txt";
			File f = new File(wFilename);
			PrintWriter output = new PrintWriter(new FileWriter(f,false));
			
			g.ReadGraph(filename,true);
			g.generateGraph2(n, 6);
			SimpleWeightedGraph<String, DefaultWeightedEdge>  graph = g.getGraph();
			for(DefaultWeightedEdge e : graph.edgeSet())
			{
//				System.out.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
				output.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
				output.flush();
			}
			
			output.close();
			n += 2;
		}
	}
		
}
