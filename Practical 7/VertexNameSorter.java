//DO NOT MODIFY THIS FILE
import java.util.Comparator;

public class VertexNameSorter implements Comparator<Vertex> {
	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v1.getName().compareTo(v2.getName());
	}
}