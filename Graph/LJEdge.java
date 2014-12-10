package Graph;

import java.util.ArrayList;

import org.jgrapht.graph.DefaultWeightedEdge;

public class LJEdge extends DefaultWeightedEdge
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	private int index = 0;
	private ArrayList<Float> property = new ArrayList<Float>();
	
	public LJEdge() {
		super();
	}
	
	public LJEdge(int index) {
		super();
		this.index = index;
	}

	@Override
	public double getWeight() {
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
	
	public ArrayList<Float> getProperty() {
		return property;
	}
	
	public void setProperty(ArrayList<Float> p) {
		this.property = p;
	}
	
	public void computeProperty() {
		for(int i=0;i<4;i++)
		{
			property.add((float) 0);
		}
		
		for(Triangle t : triangles)
		{
			property.set(t.getNegNum(), property.get(t.getNegNum())+1);
		}
				
	}
	
	@Override
	public Object clone()
	{
		Object cloned = super.clone();
		LJEdge clonedEdge = (LJEdge) cloned;
		clonedEdge.setIndex(index);
		
		return cloned;
	}
}
