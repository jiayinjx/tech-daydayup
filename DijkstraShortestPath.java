/** Algorithm: Dijkstra's Algorithm
 * Goal: Find single source shortest path.
 *
 * Time: O(ElogV).
 * Space: O(E+V).
 *
 */

import java.util.Map;
import java.util.HashMap;

public class DijkstraShortestPath {

    public Map<Vertex<Integer>, Integer> shortestPath(Graph<Integer> graph, Vertex<Integer> sourceVertex) {

        // heap + map data structure
        BinaryMinHeap<Vertex<Integer>> minHeap = new BinaryMinHeap<>();

        // stores the shortest distance from root to every vertex
        Map<Vertex<Integer>, Integer> distanceMap = new HashMap<>();

        // stores parent of every vertex in shortest distance path
        Map<Vertex<Integer>, Vertex<Integer>> parentMap = new HashMap<>();

        // initialize all vertex with infinite distance from sourceVertex, weight-key pair
        for(Vertex<Integer> vertex : graph.getAllVertex()) {
            minHeap.add(Integer.MAX_VALUE, vertex);
        }

        // set distance of sourceVertex to 0, and put it in the distance map, set sourceVertex's parent to null
        minHeap.decrease(sourceVertex, 0);
        distanceMap.put(sourceVertex, 0);
        parentMap.put(sourceVertex, null);

        while(!minHeap.empty()) {
            // get the min value from heap node which has vertex and distance of that vertex from sourceVertex
            Node<Integer> heapNode = minHeap.extractMinNode();
            Vertex<Integer> currVertex = heapNode.getKey();

            // update the shortest distance of current vertex from sourceVertex
            distanceMap.put(currVertex, heapNode.getWeight());

            // iterate through all edges of currVertex
            for(Edge<Integer> edge : currVertex.getEdges()) {

                // get adjacent vertexes
                Vertex<Integer> adjacentVertex = getVertexForEdge(currVertex, edge);

                // if heap doesn't contain adjacentVertex --> means adjacentVertex already has shortest distance from
                // sourceVertex.
                if(!minHeap.containsData(adjacentVertex)) {
                    continue;
                }

                // add distance of currVertex edge weight to get distance of adjacentVertex from sourceVertex when it
                // goes through currVertex
                int newDistance = distanceMap.get(currVertex) + edge.getWeight();

                // compare
                if(minHeap.getWeight(adjacentVertex) > newDistance) {
                    minHeap.decrease(adjacentVertex, newDistance);
                    parentMap.put(adjacentVertex, currVertex);
                }
            }
        }

        return distanceMap;
    }

    private Vertex<Integer> getVertexForEdge(Vertex<Integer> v, Edge<Integer> e) {
        return e.getVertex1().equals(v) ? e.getVertex2() : e.getVertex1();
    }

    public static void main(String args[]){
        Graph<Integer> graph = new Graph<>(false);

        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 2);
        graph.addEdge(1, 4, 9);
        graph.addEdge(1, 5, 3);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 4, 2);
        graph.addEdge(3, 4, 3);

        DijkstraShortestPath dsp = new DijkstraShortestPath();
        Vertex<Integer> sourceVertex = graph.getVertex(1);
        Map<Vertex<Integer>,Integer> distance = dsp.shortestPath(graph, sourceVertex);
        System.out.print(distance);
    }
}

