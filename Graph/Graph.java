package Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Graph {
	
	public SimpleWeightedGraph<String, LJEdge>  graph = new SimpleWeightedGraph<String, LJEdge>(LJEdge.class); 
	public SimpleWeightedGraph<String, LJEdge>  originalGraph = new SimpleWeightedGraph<String, LJEdge>(LJEdge.class);
	public ArrayList<Triangle> all_triangles = new ArrayList<Triangle>();
    private int curr_index = 0;
	
	//Get the graph.
	public SimpleWeightedGraph<String, LJEdge> getGraph()
	{
		return graph;
	}
	
	//Set the graph.
	public void setGraph(SimpleWeightedGraph<String, LJEdge> g)
	{
		this.graph = g;
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
		       			 	originalGraph.addVertex(nodes[0]);
		       		 	}
		       		 	if(!graph.containsVertex(nodes[1]))
		       		 	{
		       		 		graph.addVertex(nodes[1]);
		       		 		originalGraph.addVertex(nodes[1]);
		       		 	}
		       		 	if(!graph.containsEdge(nodes[0], nodes[1]) && !(nodes[0].equals(nodes[1])))
		       		 	{
		       		 		LJEdge new_edge = new LJEdge(curr_index);
		       		 		LJEdge new_edge2 = new LJEdge(curr_index);
		       		 		curr_index++;
		       		 		graph.addEdge(nodes[0], nodes[1], new_edge);
		       		 		originalGraph.addEdge(nodes[0], nodes[1], new_edge2);
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
		       			 	originalGraph.addVertex(nodes[0]);
		       		 	}
		       		 	if(!graph.containsVertex(nodes[1]))
		       		 	{
		       		 		graph.addVertex(nodes[1]);
		       		 		originalGraph.addVertex(nodes[1]);
		       		 	}
		       		 	if(!graph.containsEdge(nodes[0], nodes[1]) && !(nodes[0].equals(nodes[1])))
		       		 	{
		       		 		LJEdge new_edge = new LJEdge(curr_index);
		       		 		LJEdge new_edge2 = new LJEdge(curr_index);
		       		 		curr_index++;
		       		 		graph.addEdge(nodes[0], nodes[1], new_edge);
		       		 		graph.setEdgeWeight(graph.getEdge(nodes[0], nodes[1]), Double.parseDouble(nodes[2]));
		       		 		originalGraph.addEdge(nodes[0], nodes[1], new_edge2);
		       		 		originalGraph.setEdgeWeight(originalGraph.getEdge(nodes[0], nodes[1]), Double.parseDouble(nodes[2]));
		       		 	}				
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        reader.close();
        br.close();
	}	
	
	//Output a graph.
	public void WriteGraph(String filename) throws IOException
	{
		File f = new File(filename);
		PrintWriter output = new PrintWriter(new FileWriter(f,false));
		for(LJEdge e : graph.edgeSet())
		{
			output.println(graph.getEdgeSource(e)+" "+graph.getEdgeTarget(e)+" "+graph.getEdgeWeight(e));
			output.flush();
		}
		output.close();
	}

	//Generate weights for the graph.
	public void generateWeight(double ratio)
	{
		for(LJEdge e : graph.edgeSet())
		{
			double ram = Math.random()-0.5;
			if(ram > 0)
			{
				graph.setEdgeWeight(e,1);
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e)),1);
			}
			else
			{
				graph.setEdgeWeight(e,-1);
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e)),-1);
			}
		}
		for(Triangle tri : all_triangles)
		{
			double ram = Math.random();
			if(ram < 0.9*ratio && ram >= 0)
			{
				graph.setEdgeWeight(tri.getEdge1(),1);
				graph.setEdgeWeight(tri.getEdge2(),1);
				graph.setEdgeWeight(tri.getEdge3(),1);
				
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),1);
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),1);
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),1);
			}
			else if(ram < ratio && ram >= 0.9*ratio)
			{
				int ran = (int) Math.floor(Math.random()*3);
				if(ran == 0)
				{
					graph.setEdgeWeight(tri.getEdge1(),1);
					graph.setEdgeWeight(tri.getEdge2(),-1);
					graph.setEdgeWeight(tri.getEdge3(),-1);
					
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),-1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),-1);
				}
				else if(ran == 1)
				{
					graph.setEdgeWeight(tri.getEdge1(),-1);
					graph.setEdgeWeight(tri.getEdge2(),1);
					graph.setEdgeWeight(tri.getEdge3(),-1);
					
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),-1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),-1);
				}
				else if(ran == 2)
				{
					graph.setEdgeWeight(tri.getEdge1(),-1);
					graph.setEdgeWeight(tri.getEdge2(),-1);
					graph.setEdgeWeight(tri.getEdge3(),1);
					
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),-1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),-1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),1);
				}
			}
			else if(ram < 0.3*ratio+0.7 && ram >= ratio)
			{
				int ran = (int) Math.floor(Math.random()*3);
				if(ran == 0)
				{
					graph.setEdgeWeight(tri.getEdge1(),-1);
					graph.setEdgeWeight(tri.getEdge2(),1);
					graph.setEdgeWeight(tri.getEdge3(),1);
					
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),-1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),1);
				}
				else if(ran == 1)
				{
					graph.setEdgeWeight(tri.getEdge1(),1);
					graph.setEdgeWeight(tri.getEdge2(),-1);
					graph.setEdgeWeight(tri.getEdge3(),1);
					
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),-1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),1);
				}
				else if(ran == 2)
				{
					graph.setEdgeWeight(tri.getEdge1(),1);
					graph.setEdgeWeight(tri.getEdge2(),1);
					graph.setEdgeWeight(tri.getEdge3(),-1);
					
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),1);
					originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),-1);
				}
			}
			else if(ram >= 0.3*ratio+0.7 && ram < 1)
			{
				graph.setEdgeWeight(tri.getEdge1(),-1);
				graph.setEdgeWeight(tri.getEdge2(),-1);
				graph.setEdgeWeight(tri.getEdge3(),-1);
				
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge1()), graph.getEdgeTarget(tri.getEdge1())),-1);
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge2()), graph.getEdgeTarget(tri.getEdge2())),-1);
				originalGraph.setEdgeWeight(originalGraph.getEdge(graph.getEdgeSource(tri.getEdge3()), graph.getEdgeTarget(tri.getEdge3())),-1);
			}
			
		}		
		
	}
	
	//Generate a new balanced clique.
	public void generateClique(double ratio)
	{
		int numVertex = (int) (graph.vertexSet().size()*(1+ratio)/2);
		HashSet<String> ver1 = new HashSet<String>();
		HashSet<String> ver2 = new HashSet<String>();
		
		int i = 0;
		while(i < numVertex)
		{
			int	ram = (int)(Math.random()*graph.vertexSet().size());
			String ras = Integer.toString(ram);
			if(!ver1.contains(ras))
			{
				ver1.add(ras);
				i++;
			}
		}
		for(String v : graph.vertexSet())
		{
			if(!ver1.contains(v))
			{
				ver2.add(v);
			}
		}
		
		for(LJEdge e : graph.edgeSet())
		{
			String v1 = graph.getEdgeSource(e);
			String v2 = graph.getEdgeTarget(e);

			if((ver1.contains(v1) && ver1.contains(v2)) || (ver2.contains(v1) && ver2.contains(v2)))
			{
				graph.setEdgeWeight(e, 1);
			}
			else
			{
				graph.setEdgeWeight(e, -1);
			}
		}
	}

	//Find all triangles in the graph.
	public void findTriangles()
	{
		int triangle_index = 0;
		for(LJEdge e1 : graph.edgeSet())
		{
			String v2 = graph.getEdgeSource(e1);
			String v3 = graph.getEdgeTarget(e1);
			for(String v1 : graph.vertexSet())
			{
				LJEdge e2 = graph.getEdge(v1, v2);
				LJEdge e3 = graph.getEdge(v1, v3);
				if(e2 != null && e3 != null)
				{
					if (e1.getIndex()<e2.getIndex() && e1.getIndex()<e3.getIndex())
					{
						Triangle new_tri = new Triangle(e1, e2, e3, triangle_index);
						this.all_triangles.add(new_tri);
						triangle_index++;
						e1.getTriangles().add(new_tri);
						e2.getTriangles().add(new_tri);
						e3.getTriangles().add(new_tri);
					}
				}
				
			}
		}
	}
	
	//Export triangle files.
	public void exportTriangles(String f1, String f2) throws IOException
	{
		File tri_data = new File(f1);
		File edge2tri_data = new File(f2);
		PrintWriter tri_output = new PrintWriter(new FileWriter(tri_data,false));
		PrintWriter edge2tri_output = new PrintWriter(new FileWriter(edge2tri_data,false));
		
		for(Triangle tri : this.all_triangles)
		{
			LJEdge e1 = tri.getEdge1();
			LJEdge e2 = tri.getEdge2();
			LJEdge e3 = tri.getEdge3();
			tri_output.print(e1.toString()+"#"+e2.toString()+"#"+e3.toString()+"\n");
			tri_output.flush();
		}
		for(LJEdge e : graph.edgeSet())
		{
			edge2tri_output.print(e.toString()+"#");
			for(Triangle tri1 : e.getTriangles())
				edge2tri_output.print(Integer.toString(tri1.index)+"#");
			edge2tri_output.print("\n");
		}
		
		tri_output.close();
		edge2tri_output.close();
	}
	
	//Import triangle files.
	public void importTriangles(String f1, String f2) throws IOException
	{
		File tri_data = new File(f1);
		File edge2tri_data = new File(f2);
        BufferedReader tri_br = new BufferedReader(new InputStreamReader(new FileInputStream(tri_data)));
        BufferedReader edge2tri_br = new BufferedReader(new InputStreamReader(new FileInputStream(edge2tri_data)));
        
        String line = "";
        int tri_index = 0;
        while((line = tri_br.readLine()) != null)
		{
        	LJEdge e1 = new LJEdge();
        	LJEdge e2 = new LJEdge();
        	LJEdge e3 = new LJEdge();
        	String[] edges = line.split("#");
        	
        	String e1_str = edges[0];
    		String[] nodes = e1_str.split(" : ");
    		String node1 = nodes[0].substring(1);
    		String node2 = nodes[1].substring(0, nodes[1].length()-1);
    		e1 = graph.getEdge(node1, node2);
    		
    		String e2_str = edges[1];
    		nodes = e2_str.split(" : ");
    		node1 = nodes[0].substring(1);
    		node2 = nodes[1].substring(0, nodes[1].length()-1);
    		e2 = graph.getEdge(node1, node2);
    		
    		String e3_str = edges[2];
    		nodes = e3_str.split(" : ");
    		node1 = nodes[0].substring(1);
    		node2 = nodes[1].substring(0, nodes[1].length()-1);
    		e3 = graph.getEdge(node1, node2);
    		
    		Triangle new_tri = new Triangle(e1, e2, e3, tri_index);
			this.all_triangles.add(new_tri);
			tri_index++;
		}
        
        line = "";
        while((line = edge2tri_br.readLine()) != null)
		{
        	String[] items = line.split("#");
        	String edge_str = items[0];
        	String[] nodes = edge_str.split(" : ");
    		String node1 = nodes[0].substring(1);
    		String node2 = nodes[1].substring(0, nodes[1].length()-1);
    		LJEdge e = graph.getEdge(node1, node2);
    		for(int i=1; i<items.length; i++)
    		{
    			int curr_tri_index = Integer.parseInt(items[i]);
    			Triangle curr_tri = this.all_triangles.get(curr_tri_index);
    			e.getTriangles().add(curr_tri);
    		}
		}
        
        tri_br.close();
        edge2tri_br.close();
	}
	
	//Add a clique to the graph.
	public void addClique(Graph clique, double d)
	{
		int triangle_index = all_triangles.size();
		int size1 = graph.vertexSet().size();
		int size2 = clique.getGraph().vertexSet().size();
		int total = (int)(d*size1*size2);
		int i = 0;
		
		Vector<LJEdge> newEdges = new Vector<LJEdge>();
		
		for(LJEdge edge : clique.getGraph().edgeSet())
		{
			int n = Integer.parseInt(clique.getGraph().getEdgeSource(edge))+size1;
			String nodes1 = Integer.toString(n);
			n = Integer.parseInt(clique.getGraph().getEdgeTarget(edge))+size1;
			String nodes2 = Integer.toString(n);
			double weight = clique.getGraph().getEdgeWeight(edge);
			
			if(!graph.containsVertex(nodes1))
   		 	{
   			 	graph.addVertex(nodes1);
   		 	}
   		 	if(!graph.containsVertex(nodes2))
   		 	{
   		 		graph.addVertex(nodes2);
   		 	}
   		 	if(!graph.containsEdge(nodes1, nodes2) && !(nodes1.equals(nodes2)))
   		 	{
   		 		LJEdge new_edge = new LJEdge(curr_index);
   		 		curr_index++;
   		 		graph.addEdge(nodes1, nodes2, new_edge);
   		 		graph.setEdgeWeight(graph.getEdge(nodes1, nodes2), weight);
   		 		newEdges.add(graph.getEdge(nodes1, nodes2));
   		 	}				
		}
		
		while(i < total)
		{
			String node1 = Integer.toString((int)(Math.random()*size2)+size1);
			String node2 = Integer.toString((int)(Math.random()*size1));
			double weight;
			
			if(!graph.containsEdge(node1, node2))
   		 	{
				double rand = Math.random()-0.5;
				if(rand < 0)
				{
					weight = -1;
				}
				else
				{
					weight = 1;
				}
   		 		LJEdge new_edge = new LJEdge(curr_index);
   		 		curr_index++;
   		 		graph.addEdge(node1, node2, new_edge);
   		 		graph.setEdgeWeight(graph.getEdge(node1, node2), weight);
   		 		i++;
   		 		newEdges.add(graph.getEdge(node1, node2));
   		 	}		
			
		}
		
		for(LJEdge e1 : newEdges)
		{
			String v2 = graph.getEdgeSource(e1);
			String v3 = graph.getEdgeTarget(e1);
			for(String v1 : graph.vertexSet())
			{
				LJEdge e2 = graph.getEdge(v1, v2);
				LJEdge e3 = graph.getEdge(v1, v3);
				if(e2 != null && e3 != null)
				{
					boolean flag1 = true,flag2 = true;
					if(newEdges.contains(e2))
						flag1 = e1.getIndex()<e2.getIndex();
					if(newEdges.contains(e3))
						flag2 = e1.getIndex()<e3.getIndex();
					
					if (flag1 && flag2)
					{
						Triangle new_tri = new Triangle(e1, e2, e3, triangle_index);
						this.all_triangles.add(new_tri);
						triangle_index++;
						e1.getTriangles().add(new_tri);
						e2.getTriangles().add(new_tri);
						e3.getTriangles().add(new_tri);
					}
				}
				
			}
		}
		
	}

	//Iterate to stabilization.
	public int stabilize(String filename) throws Exception
	{
		final int MAXITER = 100;
		final double THRESHOLD = 0.001;
		
		int i = 0;
		double diff1 = 1;
		double diff2 = 0;
		File f = new File(filename);
		PrintWriter output = new PrintWriter(new FileWriter(f,false));
		double diff = Math.abs(diff1-diff2);
		
		while (i < MAXITER && diff > THRESHOLD)
		{
			for (Triangle tri : all_triangles)
			{
				tri.calNegNum();
				if ((tri.getNegNum() == 1) || (tri.getNegNum() == 3))
				{
					int ram = (int)(Math.random()*3);
					if(ram == 0)
					{
						graph.setEdgeWeight(tri.getEdge1(), -tri.getEdge1().getWeight());
					}
					else if(ram == 1)
					{
						graph.setEdgeWeight(tri.getEdge2(), -tri.getEdge2().getWeight());
					}
					else
					{
						graph.setEdgeWeight(tri.getEdge3(), -tri.getEdge3().getWeight());
					}
				}	
			}
			
			diff1 = diff2;
			diff2 = graphDiff(originalGraph, graph);
			diff = Math.abs(diff1-diff2);
			output.println(diff2);
			output.flush();
			i++;
		}
		output.close();
		
		return i;
		
	}
	
	//Get the balance ratio of a graph.
	public double getBalanceRatio() 
	{
		int balance = 0;
		int inbalance = 0;
		double ratio = 0;
		
		for(Triangle tri : all_triangles)
		{
			tri.calNegNum();
			if(tri.getNegNum() == 0 || tri.getNegNum() == 2)
				balance++;
			else
				inbalance++;
		}
		
		ratio = (double)balance/(double)(balance+inbalance);
		return ratio;
	}
	
	//Calculate the average difference of each pair of edges in g1 and g2.
	public static double graphDiff(SimpleWeightedGraph<String, LJEdge> g1, SimpleWeightedGraph<String, LJEdge> g2) throws Exception 
	{
		double diff = 0;
		
		int edgeNum = g1.edgeSet().size();
		
		for(LJEdge e1 : g1.edgeSet())
		{
			String node1 = g1.getEdgeSource(e1);
			String node2 = g1.getEdgeTarget(e1);
			LJEdge e2 = g2.getEdge(node1, node2);
			if(e1.getWeight() != e2.getWeight())
			{
				diff++;
			}
		}
		
		return diff/edgeNum;
	}
	
}
