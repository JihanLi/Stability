package Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.jgrapht.graph.SimpleWeightedGraph;

public class Graph {
	
	public SimpleWeightedGraph<String, LJEdge>  graph = new SimpleWeightedGraph<String, LJEdge>(LJEdge.class); 
	private HashSet<String> VertexSet1 = new HashSet<String>();
	private HashSet<String> VertexSet2 = new HashSet<String>();
	private ArrayList<HashSet<String>> nodeSets = new ArrayList<HashSet<String>>();
	public ArrayList<Triangle> all_triangles = new ArrayList<Triangle>();
	
	public static void main(String[] args)
	{
		Graph g = new Graph();
		g.generateGraph2(31, 5);
	}
	
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
        int curr_index = 0;
        
        if(training == true)
        {
	        try
	        {
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
	       		 		LJEdge new_edge = new LJEdge(curr_index);
	       		 		curr_index++;
	       		 		graph.addEdge(nodes[0], nodes[1], new_edge);
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
	
	public SimpleWeightedGraph<String, LJEdge> getGraph()
	{
		return graph;
	}
	
	public void setGraph(SimpleWeightedGraph<String, LJEdge> g)
	{
		this.graph = g;
	}
	
	public void generateGraph(double ratio)
	{
		Random rand = new Random();
		if(ratio == 1)  // if is all pos
		{
			for(LJEdge e : graph.edgeSet())
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
			while(i < numVertex)  // add 
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
		
		for(LJEdge e : graph.edgeSet())
		{
			String v1 = graph.getEdgeSource(e);
			String v2 = graph.getEdgeTarget(e);

			if(VertexSet1.contains(v1) && VertexSet1.contains(v2))  // v1 and v2 belongs to VertexSet1
			{
				double weight;
				double pos = 0;
				double neg = 0;
				for(LJEdge ev1 : graph.edgesOf(v1))  // edges of v1
				{
					String source = graph.getEdgeSource(ev1);  // neighbor of v1
					if(source.equals(v1))
					{
						source = graph.getEdgeTarget(ev1);
					}
					if(VertexSet1.contains(source))  // v1 and neighbor belongs to same set
					{
						pos++;
					}
					else
						neg++;
				}
				for(LJEdge ev2 : graph.edgesOf(v2))
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
				for(LJEdge ev1 : graph.edgesOf(v1))
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
				for(LJEdge ev2 : graph.edgesOf(v2))
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
				for(LJEdge ev1 : graph.edgesOf(v1))
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
				for(LJEdge ev2 : graph.edgesOf(v2))
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
				for(LJEdge ev1 : graph.edgesOf(v1))
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
				for(LJEdge ev2 : graph.edgesOf(v2))
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
	
	public void generateGraph2(int n, int delta)  // the whole graph can be divided into n subgraph with delta 
	{
		Random rand = new Random();
		if(n == 1)  // if is all pos
		{
			for(LJEdge e : graph.edgeSet())
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
			if(n%2 == 0)
			{
				System.out.println("n has to an odd number!");
				return;
			}
			int graph_size = (int) (graph.vertexSet().size());
//			int graph_size = 1300;
			int huge1 = graph_size*1/2;
			int huge2 = graph_size/30;
			graph_size = graph_size-huge1-huge2;
			int avg_num = graph_size/(n-2);
			int rest = graph_size-avg_num*(n-2);
			int half = (n-2)/2;
			ArrayList<Integer> node_num = new ArrayList<Integer>();
			ArrayList<String> allnodes = new ArrayList<String>();
			HashMap<String, Integer> node2set = new HashMap<String, Integer>();
			
// 			// arrage num of nodes for each set
// 			for(int i=half; i>=(-1)*half; i--)
// 				node_num.add(i*delta+avg_num);
// 			int tmp = node_num.get(0);
// 			node_num.set(0, tmp+rest);
// 			node_num.add(huge1);
// 			node_num.add(huge2);
// //			for(int e:node_num)
// //				System.out.println(e);
			
			
			
			// shuffle the nodes
			for(String node:graph.vertexSet())
				allnodes.add(node);
			Collections.shuffle(allnodes);
			
			// add node to set
			int count=0;
			for(int i=0; i<n; i++)
			{
				HashSet<String>set = new HashSet<String>();
				int size = node_num.get(i);
				for(int j=0; j<size; j++)
				{
					String node = allnodes.get(count+j);
					set.add(node);
					node2set.put(node, i);
				}
				nodeSets.add(set);
				count += size;
			}
			
//			for(HashSet<String> set:nodeSets)
//			{
//				System.out.println(set.size());
//			}
			int pos_count = 0;
			for(LJEdge e : graph.edgeSet())
			{
				String v1 = graph.getEdgeSource(e);
				String v2 = graph.getEdgeTarget(e);
				int set1_index = node2set.get(v1);
				int set2_index = node2set.get(v2);
				
				if (set1_index==set2_index)  // belongs to same set
				{
					double weight;
					double pos = 0;
					double neg = 0;
					for(LJEdge ev1 : graph.edgesOf(v1))  // edges of v1
					{
						String source = graph.getEdgeSource(ev1);  // neighbor of v1
						if(source.equals(v1))
							source = graph.getEdgeTarget(ev1);
						if(nodeSets.get(set1_index).contains(source))  // v1 and neighbor belongs to same set
							pos++;
						else
							neg++;
					}
					for(LJEdge ev2 : graph.edgesOf(v2))  // edges of v2
					{
						String source = graph.getEdgeSource(ev2);  // neighbor of v2
						if(source.equals(v2))
							source = graph.getEdgeTarget(ev2);
						if(nodeSets.get(set2_index).contains(source))  // v2 and neighbor belongs to same set
							pos++;
						else
							neg++;
					}
					weight = pos/(pos+neg);
					graph.setEdgeWeight(e, weight);
					pos_count += 1;
				}
				else  // belongs to diff set
				{
					double weight;
					double pos = 0;
					double neg = 0;
					for(LJEdge ev1 : graph.edgesOf(v1))
					{
						String source = graph.getEdgeSource(ev1);
						if(source.equals(v1))
							source = graph.getEdgeTarget(ev1);
						if(nodeSets.get(set1_index).contains(source))
						{
							pos++;
						}
						else
							neg++;
					}
					for(LJEdge ev2 : graph.edgesOf(v2))
					{
						String source = graph.getEdgeSource(ev2);
						if(source.equals(v2))
						{
							source = graph.getEdgeTarget(ev2);
						}
						if(nodeSets.get(set2_index).contains(source))
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
			System.out.println(pos_count);
		}
	}
	
	public void findTriangles()
	{
		for(LJEdge e1 : graph.edgeSet())
		{
//			System.out.println(e1);
			for(String v1 : graph.vertexSet())
			{
				String v2 = graph.getEdgeSource(e1);
				String v3 = graph.getEdgeTarget(e1);
//				if(v2.equals(v1))
//					v2 = graph.getEdgeTarget(e1);
				LJEdge e2 = graph.getEdge(v1, v2);
				LJEdge e3 = graph.getEdge(v1, v3);
				if(e2 != null && e3 != null)
				{
					if (e1.getIndex()<e2.getIndex() && e1.getIndex()<e3.getIndex())
					{
						Triangle new_tri = new Triangle(e1, e2, e3);
						this.all_triangles.add(new_tri);
						e1.getTriangles().add(new_tri);
						e2.getTriangles().add(new_tri);
						e3.getTriangles().add(new_tri);
					}
				}
				
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
