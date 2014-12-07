package Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Graph {
	
	private SimpleWeightedGraph<String, DefaultWeightedEdge>  graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
	
	//Read the data into the graph.
	public void ReadFile(String filename) throws IOException
	{
		File file = new File(filename);
    	InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        
        try {
			while((line = br.readLine()) != null)
			{
				String[] nodes= line.split(" ");
				if(nodes.length < 3)
					continue;
				if(!graph.containsVertex(nodes[0]))
       		 	{
       			 	graph.addVertex(nodes[0]);
       		 	}
       		 	if(!graph.containsVertex(nodes[1]))
       		 	{
       		 		graph.addVertex(nodes[1]);
       		 	}
       		 	if(!graph.containsEdge(nodes[0], nodes[1]) && !(nodes[0].equals(nodes[1])))
       		 	{
       		 		graph.addEdge(nodes[0], nodes[1]);
       		 		graph.setEdgeWeight(graph.getEdge(nodes[0], nodes[1]), Double.parseDouble(nodes[2]));
       		 	}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        reader.close();
        br.close();
	}	

	
	
	public void update()
	{
		
	}
	
	public void stabilize()
	{
		
	}
	
	public void addNode()
	{
		
	}
	
	public void deleteNode()
	{
		
	}
	
	public void changeWeight()
	{
		
	}
	
}
