package Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Graph {
	
	private SimpleWeightedGraph<String, DefaultWeightedEdge>  graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
	private HashSet<String> VertexSet1 = new HashSet<String>();
	private HashSet<String> VertexSet2 = new HashSet<String>();
	
	
	//Read the data into the graph.
	public void ReadGraph(String filename, boolean training) throws IOException
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
        
        if(training == true)
        {
	        try {
					while((line = br.readLine()) != null)
					{
						String[] nodes= line.split(" ");
						if(nodes.length < 2)
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
		       		 	}				
					}
			} catch (Exception e) {
				e.printStackTrace();
			}        	
        }
        else
        {
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
        }
        reader.close();
        br.close();
	}	
	
	public SimpleWeightedGraph<String, DefaultWeightedEdge> getGraph()
	{
		return graph;
	}
	
	public void setGraph(SimpleWeightedGraph<String, DefaultWeightedEdge> g)
	{
		this.graph = g;
	}
	
	public void generateGraph(double ratio)
	{
		Random rand = new Random();
		if(ratio == 1)
		{
			for(DefaultWeightedEdge e : graph.edgeSet())
			{
				double weight = rand.nextGaussian()/6+0.5;
				while(weight <= 0 || weight > 1)
				{
					weight = rand.nextGaussian()/6+0.5;
				}
				graph.setEdgeWeight(e, weight);
			}
		}
		else
		{
			int numVertex = (int) (graph.vertexSet().size()*(1+ratio)/2);
			int i = 0;
			while(i < numVertex)
			{
				Integer	ram = (int)(Math.random()*graph.vertexSet().size());
				String ras = ram.toString();
				if(!VertexSet1.contains(ras))
				{
					VertexSet1.add(ras);
					i++;
				}
			}
			for(String v : graph.vertexSet())
			{
				if(!VertexSet1.contains(v))
				{
					VertexSet2.add(v);
				}
			}
			
		}
		
		for(DefaultWeightedEdge e : graph.edgeSet())
		{
			String v1 = graph.getEdgeSource(e);
			String v2 = graph.getEdgeTarget(e);

			if(VertexSet1.contains(v1) && VertexSet1.contains(v2))
			{
				double weight;
				double pos = 0;
				double neg = 0;
				for(DefaultWeightedEdge ev1 : graph.edgesOf(v1))
				{
					String source = graph.getEdgeSource(ev1);
					if(source.equals(v1))
					{
						source = graph.getEdgeTarget(ev1);
					}
					if(VertexSet1.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				for(DefaultWeightedEdge ev2 : graph.edgesOf(v2))
				{
					String source = graph.getEdgeSource(ev2);
					if(source.equals(v1))
					{
						source = graph.getEdgeTarget(ev2);
					}
					if(VertexSet1.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				
				weight = pos/(pos+neg);
				graph.setEdgeWeight(e, weight);
			}
			else if(VertexSet2.contains(v1) && VertexSet2.contains(v2))
			{
				double weight;
				double pos = 0;
				double neg = 0;
				for(DefaultWeightedEdge ev1 : graph.edgesOf(v1))
				{
					String source = graph.getEdgeSource(ev1);
					if(source.equals(v1))
					{
						source = graph.getEdgeTarget(ev1);
					}
					if(VertexSet2.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				for(DefaultWeightedEdge ev2 : graph.edgesOf(v2))
				{
					String source = graph.getEdgeSource(ev2);
					if(source.equals(v2))
					{
						source = graph.getEdgeTarget(ev2);
					}
					if(VertexSet2.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				
				weight = pos/(pos+neg);
				graph.setEdgeWeight(e, weight);
			}
			else if(VertexSet1.contains(v1) && VertexSet2.contains(v2))
			{
				double weight;
				double pos = 0;
				double neg = 0;
				for(DefaultWeightedEdge ev1 : graph.edgesOf(v1))
				{
					String source = graph.getEdgeSource(ev1);
					if(source.equals(v1))
					{
						source = graph.getEdgeTarget(ev1);
					}
					if(VertexSet1.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				for(DefaultWeightedEdge ev2 : graph.edgesOf(v2))
				{
					String source = graph.getEdgeSource(ev2);
					if(source.equals(v2))
					{
						source = graph.getEdgeTarget(ev2);
					}
					if(VertexSet2.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				
				weight = -neg/(pos+neg);
				graph.setEdgeWeight(e, weight);
			}
			else if(VertexSet2.contains(v1) && VertexSet1.contains(v2))
			{
				double weight;
				double pos = 0;
				double neg = 0;
				for(DefaultWeightedEdge ev1 : graph.edgesOf(v1))
				{
					String source = graph.getEdgeSource(ev1);
					if(source.equals(v1))
					{
						source = graph.getEdgeTarget(ev1);
					}
					if(VertexSet2.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				for(DefaultWeightedEdge ev2 : graph.edgesOf(v2))
				{
					String source = graph.getEdgeSource(ev2);
					if(source.equals(v2))
					{
						source = graph.getEdgeTarget(ev2);
					}
					if(VertexSet1.contains(source))
					{
						pos++;
					}
					else
						neg++;
				}
				
				weight = -neg/(pos+neg);
				graph.setEdgeWeight(e, weight);
			}
		}
		
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
