package usacoproblemsets;


import java.util.Scanner;
import java.util.ArrayList;
/**
 * * **********************************************************************

Problem 6: Hide and Seek [Jacob Steinhardt, 2009]

Bessie is playing hide and seek (a game in which a number of players
hide and a single player (the seeker) attempts to find them after
which various penalties and rewards are assessed; much fun usually
ensues).

She is trying to figure out in which of N (2 <= N <= 20,000) barns
conveniently numbered 1..N she should hide. She knows that FJ (the
seeker) starts out in barn 1. All the barns are connected by M (1
<= M <= 50,000) bidirectional paths with endpoints A_i and B_i (1
<= A_i <= N; 1 <= B_i <= N; A_i != B_i); it is possible to reach
any barn from any other through the paths.

Bessie decides that it will be safest to hide in the barn that has
the greatest distance from barn 1 (the distance between two barns
is the smallest number of paths that one must traverse to get from
one to the other). Help Bessie figure out the best barn in which
to hide.

PROBLEM NAME: hideseek2

INPUT FORMAT:

* Line 1: Two space-separated integers: N and M

* Lines 2..M+1: Line i+1 contains the endpoints for path i: A_i and
        B_i

SAMPLE INPUT (file hideseek.in):

6 7
3 6
4 3
3 2
1 3
1 2
2 4
5 2

INPUT DETAILS:

The farm layout is as follows:

                   1--2--5
                   | /|
                   |/ |
                   3--4
                   |
                   6

OUTPUT FORMAT:

* Line 1: On a single line, print three space-separated integers: the
        index of the barn farthest from barn 1 (if there are multiple
        such barns, print the smallest such index), the smallest
        number of paths needed to reach this barn from barn 1, and the
        number of barns with this number of paths.

SAMPLE OUTPUT (file hideseek.out):

4 2 3

OUTPUT DETAILS:

Barns 4, 5, and 6 are all a distance of 2 from barn 1. We choose barn 4
because it has the smallest index.

**********************************************************************
 */
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