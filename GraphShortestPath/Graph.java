// Name: Xander Bezuidenhout
// Student number: 20425997
import java.util.ArrayList;
import java.util.List;
 
public class Graph {
 
	private List<Vertex> verticesList;

	public Graph() {
		this.verticesList = new ArrayList<>();
	}

	public void addVertex(Vertex vertex) {
		this.verticesList.add(vertex);
	}

	public void reset() {
		for(Vertex vertex : verticesList) {
			vertex.setVisited(false);
			vertex.setPredecessor(null);
			vertex.setDistance(Double.POSITIVE_INFINITY);
		}
	}

	////// Implement the methods below this line //////

	public List<Vertex> getShortestPath(Vertex sourceVertex, Vertex targetVertex) {
		
		// Your code here
		ArrayList<Vertex> vertexPathList=new ArrayList<Vertex>();
		if (sourceVertex==null||targetVertex==null||verticesList.isEmpty()||!verticesList.contains(sourceVertex)||!verticesList.contains(targetVertex)||sourceVertex==targetVertex)
		{
			return vertexPathList;
		}
		else if (targetVertex.getAdjacenciesList()==null||sourceVertex.getAdjacenciesList()==null)
		{
			return vertexPathList;
		}
		resetAllVertices();
		ArrayList<Edge> edges=getEdges(sourceVertex);
		//edges=sortEdges(edges);
		boolean cycle=Bellman(targetVertex, edges);
		if (sourceVertex.getDistance()==Double.POSITIVE_INFINITY||targetVertex.getDistance()==Double.POSITIVE_INFINITY)
		{
			return vertexPathList;
		}
		else if (cycle)
		{
			return null;
		}
	//	edges.forEach(edge->printSomething(edge));
		vertexPathList=constructPath(sourceVertex,targetVertex);
		return vertexPathList;
	}

	public double getShortestPathDistance(Vertex sourceVertex, Vertex targetVertex) 
	{
		resetAllVertices();
		if (sourceVertex==targetVertex)
		{
			return 0;
		}
		List<Vertex> vertexPathList=getShortestPath(sourceVertex, targetVertex);
		if (vertexPathList==null)
		{
			return Double.NEGATIVE_INFINITY;
		}
		else if (vertexPathList.isEmpty())
		{
			return Double.POSITIVE_INFINITY;
		}
		return sourceVertex.getDistance();
		// Your code here
	}

	public void resetAllVertices()
	{
		//verticesList.forEach(vertex->vertex.resetVertex());	
		reset();
	}

	public ArrayList<Vertex> constructPath(Vertex startVertex, Vertex destVertex)
	{
		ArrayList<Vertex> path=new ArrayList<Vertex>();
		path.add(startVertex);
		while (startVertex!=destVertex&&startVertex!=null)
		{
			path.add(startVertex.getPredecessor());
			startVertex=startVertex.getPredecessor();
		}
		return path;
	}
	public boolean Bellman(Vertex sourceVertex,ArrayList<Edge> edges)
	{
		sourceVertex.setDistance(0);
		boolean impovement=true;
		Vertex u=null;
		Vertex v=null;
		int totalvisits=0;
		while (impovement)
		{
			impovement=false;
			for (Edge edge : edges) 
			{
				u=edge.getStartVertex();
				v=edge.getEndVertex();
				if (u.getDistance()>v.getDistance()+edge.getWeight())
				{
					totalvisits++;
					if (totalvisits>=verticesList.size()*verticesList.size()*verticesList.size())
					{
						return true;
					}
					u.setDistance(v.getDistance()+edge.getWeight());
					u.setPredecessor(v);
					impovement=true;
				}
			}
		}
		return false;
	}

	public ArrayList<Edge> getEdges(Vertex startVertex)
	{
		ArrayList<Edge> edgeList= new ArrayList<Edge>();
		for (Vertex vertex : verticesList) 
		{
			ArrayList<Edge> adjacencyList=(ArrayList<Edge>) vertex.getAdjacenciesList();
			for (Edge edge : adjacencyList) 
			{
				if (!edgeList.contains(edge)&&edge.getStartVertex()!=edge.getEndVertex()) //prevents duplicate edges and self-cycles
				{
					edgeList.add(edge);
				}
			}	
		}
		return edgeList;
	}

	public ArrayList<Edge> sortEdges(ArrayList<Edge> edgeList)
	{
		Edge temp=null;
		String minName="";
		
		for (int i=1;i<edgeList.size();i++) 
		{
			temp=edgeList.get(i);
			minName=temp.getStartVertex().toString();
			int j=i-1;
			for (j=i-1;j>=0&&edgeList.get(j).getStartVertex().toString().compareTo(minName)>0;j--)
			{
				edgeList.set(j+1, edgeList.get(j));
			}
			edgeList.set(j+1, temp);
		}
		return edgeList; 
	}
	public void printSomething(Edge edge)
	{
		System.out.println("["+edge.getStartVertex().toString()+","+edge.getEndVertex().toString()+"]");
	}
}