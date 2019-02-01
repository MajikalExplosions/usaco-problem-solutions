package usacoproblemsets;


import java.util.Scanner;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class HideSeek2 {
    
    static Graph graph;
    static int n;
    
    public static void run(String[] args) {
        Scanner s = new Scanner(System.in);
        n = s.nextInt();
        int m = s.nextInt();
        graph = new Graph(n);
        
        for (int i = 0; i < m; i++) graph.addPath(s.nextInt(), s.nextInt());//Add in all paths
        
        graph.calcDist();
    }
    
    private static class Graph {
        ArrayList<Node> nodes;
        
        public Graph(int n) {
            nodes = new ArrayList<>();
            nodes.add(new Node(-1));
            nodes.add(new Node(n));
            for (int i = 2; i < n + 1; i++) nodes.add(new Node(n));
        }
        
        public void addPath(int x, int y) {
            nodes.get(x).connections.add(nodes.get(y));
            nodes.get(y).connections.add(nodes.get(x));
        }
        
        public void calcDist() {
            
            nodes.get(1).calculateDistance(0);
            System.out.println("[DEBUG] " + nodes.get(4).distance);
            //Find max and # of things
            int max = 0, count = 0;
            
            for (int i = 1; i < nodes.size(); i++) {
                if (nodes.get(i).distance > nodes.get(max).distance) {
                    count = 1;
                    max = i;
                }
                else if (nodes.get(i).distance == nodes.get(max).distance) count++;
            }
            System.out.println(max + " " + (nodes.get(max).distance - 1) + " " + count);
        }
        
        static class Node {
            public int distance;
            public ArrayList<Node> connections;
            
            public Node(int n) {
                distance = n;
                connections = new ArrayList<>();
            }
            
            public void calculateDistance(int parentDist) {
                if (parentDist + 1 < distance) {
                    distance = parentDist + 1;
                    for (Node n : connections) n.calculateDistance(distance);
                }
            }
        }
    }
}