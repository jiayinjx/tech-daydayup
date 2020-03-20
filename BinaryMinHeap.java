/** Data Structure: Binary Min Heap
 *
 * A combination of binary heap and hash map.
 *
 * Support operations:
 *       extractMin - O(logn)
 *       addToHeap - O(logn)
 *       containsKey - O(1)
 *       decreaseKey - O(logn)
 *       getKeyWeight - O(1)
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class BinaryMinHeap<T> {
    private List<Node<T>> allNodes = new ArrayList<>();
    private Map<T, Integer> nodePositionMap = new HashMap<>();

    /* Check if the key exists in heap */
    public boolean containsData(T key) {
        return nodePositionMap.containsKey(key);
    }

    /* Add key and its weight to the heap */
    public void add(int weight, T key) {
        Node<T> node = new Node();
        node.setWeight(weight);
        node.setKey(key);
        allNodes.add(node);

        int size = allNodes.size();
        int currIdx = size - 1;
        int parentIdx = (currIdx - 1) / 2;
        nodePositionMap.put(node.getKey(), currIdx);

        while(parentIdx >= 0) {
            Node<T> parentNode = allNodes.get(parentIdx);
            Node<T> currNode = allNodes.get(currIdx);

            if(parentNode.getWeight() > currNode.getWeight()) {
                swap(parentNode, currNode);
                updatePositionMap(parentNode.getKey(), currNode.getKey(), parentIdx, currIdx);
                currIdx = parentIdx;
                parentIdx = (parentIdx - 1) / 2;
            } else {
                break;
            }
        }
    }

    /* Get the minimum of the heap without extracting the key */
    public T min() {
        return allNodes.get(0).getKey();
    }

    /* Check if this BinaryMinHeap is empty or not */
    public boolean empty() { // should rename to 'isEmpty'
        return allNodes.size() == 0;
    }

    /* Decrease the weight of given key to the given newWeight */
    public void decrease(T data, int newWeight) {
        Integer currIdx = nodePositionMap.get(data);
        allNodes.get(currIdx).setWeight(newWeight);

        int parentIdx = (currIdx - 1) / 2;
        while(parentIdx >= 0) {
            if(allNodes.get(parentIdx).getWeight() > allNodes.get(currIdx).getWeight()) {
                swap(allNodes.get(parentIdx), allNodes.get(currIdx));
                updatePositionMap(allNodes.get(parentIdx).getKey(), allNodes.get(currIdx).getKey(), parentIdx, currIdx);
                currIdx = parentIdx;
                parentIdx = (parentIdx - 1) / 2;
            } else {
                break;
            }
        }
    }

    /* Get the weight of given key */
    public Integer getWeight(T key) {
        Integer position = nodePositionMap.get(key);
        if(position == null) {
            return null;
        } else {
            return allNodes.get(position).getWeight();
        }
    }

    /* Returns the min node of the heap */
    public Node<T> extractMinNode() {
        int lastIdx = allNodes.size() - 1;
        Node<T> minNode = new Node();
        minNode.setKey(allNodes.get(0).getKey());
        minNode.setWeight(allNodes.get(0).getWeight());

        // swap the last node on the heap with the root node, and decrease the size of heap by 1
        allNodes.get(0).setWeight(allNodes.get(lastIdx).getWeight());
        allNodes.get(0).setKey(allNodes.get(lastIdx).getKey());
        nodePositionMap.remove(minNode.getKey());
        nodePositionMap.remove(allNodes.get(0));
        nodePositionMap.put(allNodes.get(0).getKey(), 0);
        allNodes.remove(lastIdx);


        int currIdx = 0;
        lastIdx--;
        while(true) {
            int leftchildIdx = 2 * currIdx + 1;
            int rightchildIdx = 2 * currIdx + 2;
            if(leftchildIdx > lastIdx) {
                break;
            }
            if(rightchildIdx > lastIdx) {
                rightchildIdx = leftchildIdx;
            }

            int smallerIdx = allNodes.get(leftchildIdx).getWeight() <= allNodes.get(rightchildIdx).getWeight() ?
                    leftchildIdx :
                    rightchildIdx;
            if(allNodes.get(currIdx).getWeight() > allNodes.get(smallerIdx).getWeight()) {
                swap(allNodes.get(currIdx), allNodes.get(smallerIdx));
                updatePositionMap(allNodes.get(currIdx).getKey(), allNodes.get(smallerIdx).getKey(), currIdx, smallerIdx);
                currIdx = smallerIdx;
            } else {
                break;
            }
        }

        return minNode;
    }

    /* Extract the min value key from the heap */
    public T extractMin() {
        Node<T> node = extractMinNode();
        return node.getKey();
    }

    /* swap key and data of the two nodes */
    private void swap(Node<T> node1, Node<T> node2) {
        int weight = node1.getWeight();
        T data = node1.getKey();

        node1.setKey(node2.getKey());
        node1.setWeight(node2.getWeight());
        node2.setKey(data);
        node2.setWeight(weight);
    }

    /* update the map with updated nodes' positions */
    private void updatePositionMap(T data1, T data2, int pos1, int pos2) {
        nodePositionMap.remove(data1);
        nodePositionMap.remove(data2);
        nodePositionMap.put(data1, pos1);
        nodePositionMap.put(data2, pos2);
    }

    private void printPositionMap() {
        System.out.println(nodePositionMap);
    }

    public void printHeap() {
        for(Node n : allNodes) {
            System.out.println(n.weight + " " + n.getKey());
        }
    }

    public static void main(String args[]){
        BinaryMinHeap<String> heap = new BinaryMinHeap<String>();
        heap.add(3, "Tushar");
        heap.add(4, "Ani");
        heap.add(8, "Vijay");
        heap.add(10, "Pramila");
        heap.add(5, "Roy");
        heap.add(6, "NTF");
        heap.add(2,"AFR");
        heap.decrease("Pramila", 1);
        heap.printHeap();
        heap.printPositionMap();
    }
}

class Node<T> {
    private int weight;
    private T key;

    Node(){}

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }
}