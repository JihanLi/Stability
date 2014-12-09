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
		new Main().main3();
				
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
			SimpleWeightedGraph<String,LJEdge>  graph = g.getGraph();
			for(LJEdge e : graph.edgeSet())
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
			SimpleWeightedGraph<String,LJEdge>  graph = g.getGraph();
			for(LJEdge e : graph.edgeSet())
			{
//				System.out.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
				output.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
				output.flush();
			}
			
			output.close();
			n += 2;
		}
	}
	
	public void main3() throws IOException
	{
		String filename = "test_graph.txt";
		Graph g = new Graph();
		g.ReadGraph(filename, true);
		g.findTriangles();
		
		System.out.println("All triangles: ");
		for(Triangle tri : g.all_triangles)
		{
			System.out.println(tri);
		}
		System.out.println();
		
		System.out.println("All edges and triangles they belong to: ");
		for(LJEdge e : g.graph.edgeSet())
		{
			System.out.println(e+" belongs to:");
			for(Triangle tri : e.getTriangles())
			{
				System.out.println(tri);
			}
		}
	}
}
