package Graph;

import java.io.IOException;

public class Main {

	//The main function running the algorithm.
	public static void main(String[] args) throws IOException
	{
		String filename = "graph.txt";
		Graph g = new Graph();
		
		g.ReadFile(filename);
	}
		
}
