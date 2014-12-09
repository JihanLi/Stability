package Graph;

import java.util.ArrayList;

import org.jgrapht.graph.DefaultWeightedEdge;

public class LJEdge extends DefaultWeightedEdge
{
	private ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	private int index = 0;
	
	public LJEdge() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LJEdge(int index) {
		super();
		this.index = index;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return super.getWeight();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(ArrayList<Triangle> triangles) {
		this.triangles = triangles;
	}
	
	
}
