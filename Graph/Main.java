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
		
		int[] size1 = {1,5,10,20,30,40,50,60};
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
		System.out.println(g.all_triangles.size());

		//改变原图的平衡度
		double balanceRatio = 0;
		while(balanceRatio <= 1)
		{	
			
			String br = df1.format(balanceRatio);
			g = new Graph();
			g.ReadGraph(filename, true);
			g.importTriangles(tr1, tr2);
			System.out.println(g.all_triangles.size());
			String tr3 = path2+File.separator+"tempGraph_BR"+br+".txt";
			g.generateWeight(balanceRatio);  //Change the balance ratio.
			g.WriteGraph(tr3);
			System.out.println(g.getBalanceRatio());	
			
			//固定新加团与原图形成新边的数量，改变新加团的大小
			for(int i = 0; i < 8;i++)
			{
				double newEdgeNumber = 0.02;
				String en = df2.format(newEdgeNumber);
				
				g = new Graph();
				g.ReadGraph(tr3, false);
				
				String clicName = path0+File.separator+"clique"+Integer.toString(size1[i])+".txt";
				String diffFile = path1+File.separator+"diff_BR"+br+"_SZ"+Integer.toString(size1[i])+"_EN"+en+".txt";
				
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
			
			//固定新加团的大小，改变新加团与原图形成新边的数量
			for(int i = 0; i < 5;i++)
			{		
				double newEdgeNumber = size2[i];
				String en = df2.format(newEdgeNumber);
			
				g = new Graph();
				g.ReadGraph(tr3, false);
	
				String clicName = path0+File.separator+"clique2.txt";
				String diffFile = path1+File.separator+"diff_BR"+br+"_EN"+en+"_SZ2.txt";
				
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
						
			
			balanceRatio += 0.1;	
		}
	}
		
}
