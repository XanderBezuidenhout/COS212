import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//Name: Xander Bezuidenhout
//Student Number: 20425997
public class WeightedDirectedGraph {

	private List<Vertex> verticesList; //contains all vertices in this graph

	public WeightedDirectedGraph() {
		this.verticesList = new ArrayList<>();
	}

	public void addVertex(Vertex vertex) {
		this.verticesList.add(vertex);
	}

	////// Implement the methods below this line //////

	public List<Vertex> getShortestPath(Vertex sourceVertex, Vertex targetVertex) 
	{
		// your code goes here
		//first find all edges
		//find order of the list of edges
		//iterate through ordered list of edges
		ArrayList<Vertex> vertexPathList=new ArrayList<Vertex>();
		if (sourceVertex==null||targetVertex==null||verticesList.isEmpty()||!verticesList.contains(sourceVertex)||!verticesList.contains(targetVertex))
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

	public boolean Bellman(Vertex sourceVertex,ArrayList<Edge> edges)
	{
		sourceVertex.setDistance(0);
		boolean impovement=true;
		Vertex u=null;
		Vertex v=null;
		while (impovement)
		{
			impovement=false;
			for (Edge edge : edges) 
			{
				u=edge.getStartVertex();
				v=edge.getEndVertex();
				if (u.getDistance()>v.getDistance()+edge.getWeight())
				{
					v.numvisits++;
					if (v.numvisits>=verticesList.size())
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
	
	public void resetAllVertices()
	{
		verticesList.forEach(vertex->vertex.resetVertex());	
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