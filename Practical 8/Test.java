import java.util.ArrayList;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        Graph graph = new Graph();
        for (int k = 0; k < 100; k++) {
            vertices.add(new Vertex(Integer.toString(k)));
        }

        Random r1 = new Random(123456789);
        Random r2 = new Random(456123456);

        for (int j = 0; j < 60; j++) {
            int i1, i2;
            i1 = r1.nextInt(vertices.size());
            i2 = r2.nextInt(vertices.size());
            vertices.get(i1).addNeighbour(new Edge(vertices.get(i1), vertices.get(i2), 1));
        }

        for (Vertex v : vertices)
            graph.addVertex(v);

        for (Vertex startVertex : vertices) {
            for (Vertex endVertex : vertices) {
                System.out.println("Minimum distance from " + startVertex.getName() + " to " + endVertex.getName() + " : " + graph.getShortestPathDistance(startVertex, endVertex));
                System.out.println("Shortest Path from " + startVertex.getName() + " to " + endVertex.getName() + " : " + graph.getShortestPath(startVertex, endVertex));
            }
        }

    }
}
