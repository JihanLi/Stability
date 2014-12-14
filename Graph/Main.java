package Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Main {

	//The main function running the algorithm.	
	public static void main1(String[] args) throws Exception
	{
		
		String path0 = "source";
		double newEdgeNumber = 0.2;
		
		File dir0 = new File(path0);
		if (!dir0.exists()) 
			dir0.mkdirs();
		
		String path1 = "result";
		File dir1 = new File(path1);
		if (!dir1.exists()) 
			dir1.mkdirs();
		
		String path2 = "temp";
		File dir2 = new File(path2);
		if (!dir2.exists()) 
			dir2.mkdirs();
		
		DecimalFormat df=new DecimalFormat("#.#");
		String filename = path0+File.separator+"TrG.txt";
		String tr1 = path2+File.separator+"triangle_data.txt";
		String tr2 = path2+File.separator+"edge2tri_data.txt";
			
		Graph g = new Graph();
		g.ReadGraph(filename, true);
		g.findTriangles();
			
		g.exportTriangles(tr1,tr2);
		System.out.println(g.all_triangles.size());

		
		double balanceRatio = 0;
		while(balanceRatio <= 1)
		{	
			
			String br = df.format(balanceRatio);
			g = new Graph();
			g.ReadGraph(filename, true);
			g.importTriangles(tr1, tr2);
			System.out.println(g.all_triangles.size());
			String tr3 = path2+File.separator+"tempGraph_BR"+br+".txt";
			g.generateWeight(balanceRatio);  //Change the balance ratio.
			g.WriteGraph(tr3);
			System.out.println(g.getBalanceRatio());	
			
			
			for(int i = 1; i <= 10;i++)
			{
				g = new Graph();
				g.ReadGraph(tr3, false);
				g.importTriangles(tr1,tr2);
				System.out.println(g.all_triangles.size());
				System.out.println(g.getBalanceRatio());
				
				
				String clicName = path0+File.separator+"clique"+Integer.toString(i)+".txt";
				String diffFile = path1+File.separator+"diff_BR"+br+"_EN"
							+df.format(newEdgeNumber)+"_SZ"+Integer.toString(i)+".txt";
				
				Graph c = new Graph();
				c.ReadGraph(clicName, true);
				c.findTriangles();
				c.generateClique(0.5);  //the number of positive edges in the clique
				System.out.println(c.getBalanceRatio());
				
				g.addClique(c, newEdgeNumber);    //Change the number of newly added edges between clique and the graph.
				 
				int iter = g.stabilize(diffFile);
				
				System.out.println(iter);
			}
						
			
			balanceRatio += 0.1;	
		}
	}
	
	public static void main2(String[] args) throws Exception
	{
		
		String path0 = "source";
		double[] size = {0,0.05,0.1,0.15,0.2};
		
		File dir0 = new File(path0);
		if (!dir0.exists()) 
			dir0.mkdirs();
		
		String path1 = "result";
		File dir1 = new File(path1);
		if (!dir1.exists()) 
			dir1.mkdirs();
		
		String path2 = "temp";
		File dir2 = new File(path2);
		if (!dir2.exists()) 
			dir2.mkdirs();
		
		DecimalFormat df1=new DecimalFormat("#.###");
		DecimalFormat df2=new DecimalFormat("#.#");
		String filename = path0+File.separator+"TrG.txt";
			
		Graph g = new Graph();
/*		g.ReadGraph(filename, true);
		g.findTriangles();*/

		String tr3 = path2+File.separator+"tempGraph_EN.txt";
/*		g.generateWeight(balanceRatio);  //Change the balance ratio.
		g.WriteGraph(tr3);
		System.out.println(g.getBalanceRatio());	*/
		
		double balanceRatio = 0.7;
		String br = df2.format(balanceRatio);
			
		
		for(int i = 0; i < 5;i++)
		{		
			double newEdgeNumber = size[i];
			String en = df1.format(newEdgeNumber);
		
			g = new Graph();
			g.ReadGraph(tr3, false);

			String clicName = path0+File.separator+"clique2.txt";
			String diffFile = path1+File.separator+"diff_EN"+en+"_BR"+br+"_SZ2.txt";
			
			Graph c = new Graph();
			c.ReadGraph(clicName, true);
			c.generateClique(0.5);  //the number of positive edges in the clique

			
			g.addClique(c, newEdgeNumber);    //Change the number of newly added edges between clique and the graph.
			g.findTriangles();
			
			System.out.println(g.all_triangles.size());
			System.out.println(g.getBalanceRatio());
			
			int iter = g.stabilize(diffFile);
			
			System.out.println(iter);		
		
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		String path0 = "source";
		File dir0 = new File(path0);
		if (!dir0.exists()) 
			dir0.mkdirs();
		
		String path1 = "result";
		File dir1 = new File(path1);
		if (!dir1.exists()) 
			dir1.mkdirs();
		
		String path2 = "temp";
		File dir2 = new File(path2);
		if (!dir2.exists()) 
			dir2.mkdirs();
		
		int[] size1 = {1,5,10,20,30};
		double[] size2 = {0,0.05,0.1,0.15,0.2};
		
		DecimalFormat df1=new DecimalFormat("#.#");
		DecimalFormat df2=new DecimalFormat("#.###");
		
		String filename = path0+File.separator+"TrG.txt";
		String tr1 = path2+File.separator+"triangle_data.txt";
		String tr2 = path2+File.separator+"edge2tri_data.txt";
			
		Graph g = new Graph();
		g.ReadGraph(filename, true);
		g.findTriangles();
			
		g.exportTriangles(tr1,tr2);

		//Change the balance ratio.
		double balanceRatio = 0;
		while(balanceRatio <= 1)
		{	
			
			String br = df1.format(balanceRatio);
			g = new Graph();
			g.ReadGraph(filename, true);
			g.importTriangles(tr1, tr2);
			String tr3 = path2+File.separator+"tempGraph_BR"+br+".txt";
			g.generateWeight(balanceRatio);  
			g.WriteGraph(tr3);
			
			//Change the size of clique.
			for(int i = 0; i < 5;i++)
			{
				double newEdgeNumber = 0.02;
				String en = df2.format(newEdgeNumber);
				
				g = new Graph();
				g.ReadGraph(tr3, false);
				g.importTriangles(tr1, tr2);
				System.out.println(g.all_triangles.size());
				System.out.println(g.getBalanceRatio());	
				
				String clicName = path0+File.separator+"clique"+Integer.toString(size1[i])+".txt";
				String diffFile = path1+File.separator+"diff_BR"+br+"_SZ"+Integer.toString(size1[i])+"_EN"+en+".txt";
				
				Graph c = new Graph();
				c.ReadGraph(clicName, true);
				c.generateClique(0.5);
				
				g.addClique(c, newEdgeNumber);
				
				System.out.println(g.all_triangles.size());
				System.out.println(g.getBalanceRatio());
				 
				int iter = g.stabilize(diffFile);
				
				System.out.println(iter);
			}
			
			//Change the number of newly added edges between clique and the graph.
			for(int i = 0; i < 5;i++)
			{		
				double newEdgeNumber = size2[i];
				String en = df2.format(newEdgeNumber);
			
				g = new Graph();
				g.ReadGraph(tr3, false);
				g.importTriangles(tr1, tr2);
				System.out.println(g.all_triangles.size());
				System.out.println(g.getBalanceRatio());
	
				String clicName = path0+File.separator+"clique2.txt";
				String diffFile = path1+File.separator+"diff_BR"+br+"_EN"+en+"_SZ2.txt";
				
				Graph c = new Graph();
				c.ReadGraph(clicName, true);
				c.generateClique(0.5);
	
				
				g.addClique(c, newEdgeNumber);
				
				System.out.println(g.all_triangles.size());
				System.out.println(g.getBalanceRatio());
				
				int iter = g.stabilize(diffFile);
				
				System.out.println(iter);		
			
			}
						
			
			balanceRatio += 0.1;	
		}
	}
	
		
}
