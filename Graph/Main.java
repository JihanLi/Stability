package Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Main {

	//The main function running the algorithm.
/*	public static void main1(String[] args) throws IOException
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
			SimpleWeightedGraph<String, LJEdge>  graph = g.getGraph();
			for(LJEdge e : graph.edgeSet())
			{
				output.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
				output.flush();
			}
			
			output.close();
		}
				
	}*/
	
	public static void main(String[] args) throws Exception
	{
		String filename = "TrG.txt";
		Graph g = new Graph();
		g.ReadGraph(filename, true);
		g.findTriangles();
		System.out.println(g.all_triangles.size());
		g.generateWeight(0.9);
		
/*		String wFilename = "Weight0.1.txt";
		File f = new File(wFilename);
		PrintWriter output = new PrintWriter(new FileWriter(f,false));
		
		SimpleWeightedGraph<String, LJEdge>  graph = g.getGraph();
		for(LJEdge e : graph.edgeSet())
		{
			output.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
			output.flush();
		}
		
		output.close();*/
		System.out.println(g.getBalanceRatio());
		
		
		String clicName = "clique1.txt";
		Graph c = new Graph();
		c.ReadGraph(clicName, true);
		c.findTriangles();
		c.generateClique(0.5);
		System.out.println(c.getBalanceRatio());
		
		g.addClique(c, 0.2);
		
		int iter = g.stabilize();
		
		System.out.println(iter);
		
		
		/*String filename = "Weight.txt";
		Graph g = new Graph();
		g.ReadGraph(filename, false);
		g.findTriangles();*/
		
		
		
		
		
		
		
		/*for(Triangle tri : g.all_triangles)
		{
			System.out.println(tri.toString());
		}*/
		
		/*for(LJEdge e : g.graph.edgeSet())
		{
			System.out.println(e+" belongs to:");
			for(Triangle tri : e.getTriangles())
			{
				System.out.println(tri.toString());
			}
		}*/
	}
		
}
