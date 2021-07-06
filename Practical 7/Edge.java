//Name: Xander Bezuidenhout
//Student Number: 20425997
public class Edge {

	private Vertex startVertex;
	private Vertex endVertex;
	private double weight;
	public boolean processed=false;

	public Edge(Vertex startVertex, Vertex endVertex, double weight) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.weight = weight;
		processed=false;
	}

	/*Returns the other vertex connected to the vertex in the argument*/
	public Vertex getOtherVertex(Vertex in) {
		if (startVertex == in)
			return endVertex;
		else if (endVertex == in)
			return startVertex;
		else
			return null;
	}

	public Vertex getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(Vertex startVertex) {
		this.startVertex = startVertex;
	}

	public Vertex getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(Vertex endVertex) {
		this.endVertex = endVertex;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	
}