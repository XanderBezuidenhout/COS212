import java.util.ArrayList;
import java.util.List;
//Name: Xander Bezuidenhout
//Student Number: 20425997
public class UnweightedUndirectedGraph {

	private List<Vertex> verticesList; //contains all vertices in this graph
	private List<Vertex> articulationPoints; // add articulation points for this graph to this list
	private int i=1;
	private ArrayList<Edge> edgeStack;
	//private ArrayList<Edge> corrEdge;
	public UnweightedUndirectedGraph() 
	{
		this.verticesList = new ArrayList<>();
		this.articulationPoints = new ArrayList<>();
		edgeStack= new ArrayList<Edge>();
	//	corrEdge=new ArrayList<Edge>();
	}

	public void addVertex(Vertex vertex) {
		this.verticesList.add(vertex);
	}

	/*Sorts the articulationPoints list based on the names of the vertices. Do not modify this*/
	public void sortArticulationPoints() {
		if (articulationPoints != null)
			articulationPoints.sort(new VertexNameSorter());
	}

	////// Implement the methods below this line //////

	public List<Vertex> getArticulationPoints() {
		/* 
		  Your code to add articulation points to the list goes here. 
		You may add helper functions and fields. No extra imports or classes.
		 */
		resetAllVertices();
		i=1;
		if (verticesList.isEmpty())
		{
			return articulationPoints;
		}
		Vertex unvisitedVertex=findOneChildVertex();
		if (unvisitedVertex==null)
		{
			unvisitedVertex=findUnvisitedVertex();
		}
		while(unvisitedVertex!=null)
		{
			blockDFS(unvisitedVertex);
			unvisitedVertex=findUnvisitedVertex();
		}
		/*if (verticesList.get(0).pred!=verticesList.get(0).numvisits&&!articulationPoints.contains(verticesList.get(0)))
		{
			articulationPoints.add(verticesList.get(0));
		}*/
		/*for (Vertex vertex : verticesList) {
			for (Vertex vertex2 : verticesList) {
				if (vertex!=vertex2)
				{
					if (!connects(vertex, vertex2, vertex,vertex))
					{
						if (!articulationPoints.contains(vertex))
						{
							articulationPoints.add(vertex);
						}
					}
				}	
			}
		}*/
		sortArticulationPoints(); // sort list before returning
		return articulationPoints;
	}

	public void blockDFS(Vertex v)
	{
		v.pred=v.numvisits=i++;
		ArrayList<Edge> edges=getEdges(v);
		if (edges.isEmpty())
		{
			return;
		}
		ArrayList<Vertex> neighbours=getNeighbours(edges,v);
		//
		Edge vu=null;
		Vertex u=null;
		Edge e=null;
		v.setVisited(true);
		for (int j=0;j<neighbours.size();j++) 
		{
			u=neighbours.get(j);
			vu=edges.get(j);
			if (u==null||vu==null)
			{
				continue;
			}
			if (!vu.processed)
			{
				edgeStack.add(0,vu);
				vu.processed=true;
			}
			if (u.numvisits==0)
			{
				blockDFS(u);
				if (u.pred>=v.numvisits)
				{
					e=edgeStack.remove(0);
					while (e!=vu&&!edgeStack.isEmpty())
					{
						//output e;
						e=edgeStack.remove(0);
					}
					if (v.numvisits!=1)
					{
						if (!articulationPoints.contains(v))
						{
							articulationPoints.add(v);
						}
					}
					else if (findUnvisitedVertex()!=null)
					{
						if (!articulationPoints.contains(v))
						{
							articulationPoints.add(v);
						}
					}
					
					//output e
				}
				else
				{
					v.pred=Integer.min(v.pred,u.pred);
				}
				
			}
			else if (u.numvisits!=v.numvisits+1)
			{
				v.pred=Integer.min(v.pred,u.numvisits);
			}	
		}
	}

	public ArrayList<Vertex> getNeighbours(ArrayList<Edge> edges, Vertex v)
	{
		ArrayList<Vertex> neighbours=new ArrayList<Vertex>();
		for (Edge edge : edges) 
		{
			/*if (edge.getOtherVertex(v)!=null&&edge.getOtherVertex(v)!=v&&!neighbours.contains(edge.getOtherVertex(v))) //eliminates duplicate edges and self neighbour
			{
				neighbours.add(edge.getOtherVertex(v));
				//corrEdge.add(edge);
			}*/
			neighbours.add(edge.getOtherVertex(v));
		}
		return neighbours;
	}

	public ArrayList<Edge> getEdges(Vertex v)
	{
		ArrayList<Edge> corrEdge=new ArrayList<Edge>();
		for (Edge edge : v.getAdjacenciesList()) 
		{
			if (edge.getOtherVertex(v)!=v&&!corrEdge.contains(edge)) //eliminates duplicate edges and self neighbour
			{
				//neighbours.add(edge.getOtherVertex(v));
				corrEdge.add(edge);
			}
		}
		return corrEdge;
	}
	public Vertex findOneChildVertex()
	{
		for (Vertex vertex : verticesList) {
			if (vertex.getAdjacenciesList().size()==1)
			{
				return vertex;
			}
		}
		return null;
	}

	public void resetAllVertices()
	{
		verticesList.forEach(vertex->vertex.resetVertex());
		edgeStack.clear();
		//corrEdge.clear();
		verticesList.forEach(vertex->vertex.getAdjacenciesList().forEach(Edge->Edge.processed=false));
		articulationPoints.clear();
	}

	public Vertex findUnvisitedVertex()
	{
		for (Vertex vertex : verticesList) 
		{
			if (!vertex.isVisited())
			{
				return vertex;
			}	
		}
		return null;
	}

	public boolean connects(Vertex start,Vertex end,Vertex prev)
	{
		if (start==null||end==null||prev==null)
		{
			return false;
		}
		ArrayList<Edge> edges=getEdges(start);
		if (edges.isEmpty())
		{
			return false;
		}
		ArrayList<Vertex> neighbours=getNeighbours(edges,start);
		for (Vertex vertex : neighbours) {
			if (vertex==end&&end!=prev)
			{
				return true;
			}
			else if (prev!=vertex&&(connects(vertex, end, start)))
			{
				return true;
			}
		}
		return false;
	}
}
