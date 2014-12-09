package Graph;

import org.jgrapht.graph.DefaultWeightedEdge;


public class Triangle 
{
	private LJEdge edge1;
	private LJEdge edge2;
	private LJEdge edge3;
	private int negNum;
	public int index;
	
	public Triangle(LJEdge edge1, LJEdge edge2, LJEdge edge3, int index) {
		super();
		this.edge1 = edge1;
		this.edge2 = edge2;
		this.edge3 = edge3;
		this.index = index;
		calNegNum();  // calculate the number of negative edges
	}
	@Override
	public String toString() {
		return "Triangle [edge1=" + edge1 + ", edge2=" + edge2 + ", edge3="
				+ edge3 + ", negNum=" + negNum + "]";
	}
	
	public LJEdge getEdge1() {
		return edge1;
	}
	public void setEdge1(LJEdge edge1) {
		this.edge1 = edge1;
	}
	public LJEdge getEdge2() {
		return edge2;
	}
	public void setEdge2(LJEdge edge2) {
		this.edge2 = edge2;
	}
	public LJEdge getEdge3() {
		return edge3;
	}
	public void setEdge3(LJEdge edge3) {
		this.edge3 = edge3;
	}
	public int getNegNum() {
		return negNum;
	}
	public void setNegNum(int negNum) {
		this.negNum = negNum;
	}
	
	public void calNegNum()
	{
		int num = 0;
		if(this.edge1.getWeight() < 0)
			num++;
		if(this.edge2.getWeight() < 0)
			num++;
		if(this.edge3.getWeight() < 0)
			num++;
		setNegNum(num);
	}
	
}
