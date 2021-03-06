
package usacoproblemsets;

import java.util.ArrayList;
import java.util.Scanner;
/*
Farmer John has lost his favorite cow bell, and Bessie the cow has agreed to help him find it! They both fan out and search the farm along different paths, but stay in contact via radio so they can keep in touch with each-other. Unfortunately, the batteries in their radios are running low, so they want to plan their movements so as to conserve power, by trying to stay always within a short distance apart.
Farmer John starts at location (fx,fy) and plans to follow a path consisting of N steps, each of which is either 'N' (north), 'E' (east), 'S' (south), or 'W' west. Bessie starts at location (bx,by) and follows a similar path consisting of M steps. Both paths may share points in common. At each time step, Farmer John can either stay put at his current location, or take one step forward along his path, in whichever direction happens to be next (assuming he has not yet reached the final location in his path). Bessie can make a similar choice. At each time step (excluding the first step where they start at their initial locations), their radios consume energy equal to the square of the distance between them.

Please help FJ and Bessie plan a joint movement strategy that will minimize the total amount of energy consumed up to and including the final step where both of them first reach the final locations on their respective paths.

INPUT FORMAT (file radio.in):
The first line of input contains N and M (1≤N,M≤1000). The second line contains integers fx and fy, and the third line contains bx and by (0≤fx,fy,bx,by≤1000). The next line contains a string of length N describing FJ's path, and the final line contains a string of length M describing Bessie's path.
It is guranteed that Farmer John and Bessie's coordinates are always in the range (0≤x,y≤1000) throughout their journey. Note that East points in the positive x direction and North points in the positive y direction.

OUTPUT FORMAT (file radio.out):
Output a single integer specifying the minimum energy FJ and Bessie can use during their travels.
SAMPLE INPUT:
2 7
3 0
5 0
NN
NWWWWWN
SAMPLE OUTPUT:
28
*/
public class Radio {
    
    static int fj, b;//Length of paths
    static String fjP, bP;//The paths themselves
    static Coordinate[] posfj, posb;
    
    static int[][] energyUsage;
    
    public static void run(String[] args) {
        Scanner s = new Scanner(System.in);
        
        int n = s.nextInt(), m = s.nextInt();
        energyUsage = new int[n + 1][m + 1];
        
        posfj = new Coordinate[n + 1];
        posb = new Coordinate[m + 1];
        posfj[0] = new Coordinate(s.nextInt(), s.nextInt());
        posb[0] = new Coordinate(s.nextInt(), s.nextInt());
        s.nextLine();
        fjP = s.nextLine();
        bP = s.nextLine();
        int i = 1;
        for (char c : fjP.toCharArray()) {
            if (c == 'N') posfj[i] = new Coordinate(posfj[i - 1].x, posfj[i++ - 1].y + 1);
            if (c == 'S') posfj[i] = new Coordinate(posfj[i - 1].x, posfj[i++ - 1].y - 1);
            if (c == 'E') posfj[i] = new Coordinate(posfj[i - 1].x + 1, posfj[i++ - 1].y);
            if (c == 'W') posfj[i] = new Coordinate(posfj[i - 1].x - 1, posfj[i++ - 1].y);
        }
        i = 1;
        for (char c : bP.toCharArray()) {
            if (c == 'N') posb[i] = new Coordinate(posb[i - 1].x, posb[i++ - 1].y + 1);
            if (c == 'S') posb[i] = new Coordinate(posb[i - 1].x, posb[i++ - 1].y - 1);
            if (c == 'E') posb[i] = new Coordinate(posb[i - 1].x + 1, posb[i++ - 1].y);
            if (c == 'W') posb[i] = new Coordinate(posb[i - 1].x - 1, posb[i++ - 1].y);
        }
        
        System.out.println(calculate(n, m));
    }
    
    private static int calculate(int ptfj, int ptb) {//Path traveled for F. John and Bessie
        if ((ptfj == 0) && (ptb == 0)) return 0;//Return 0 if at beginning of path
        if (energyUsage[ptfj][ptb] != 0) return energyUsage[ptfj][ptb];
        int a = 0, b = 0, c = 0;
        if (ptfj > 1)
            a = calculate(ptfj - 1, ptb);
        if (ptb > 1)
            b = calculate(ptfj, ptb - 1);
        if (ptfj > 1 && ptb > 1)
            c = calculate(ptfj - 1, ptb - 1);
        int min = 0;
        if (a != 0) min = a;
        else if (b != 0) min = b;
        if (min > b && b != 0) min = b;
        if (min > c && c != 0) min = c;
        
        //find cost for this turn
        min += posfj[ptfj].getEnergyTo(posb[ptb]);
        
        return energyUsage[ptfj][ptb] = min;
    }
    
    private static class Coordinate {
        public int x;
        public int y;
        
        public Coordinate() {
            this(0, 0);
        }
        
        public Coordinate(int a, int b) {
            x = a;
            y = b;
        }
        
        public int getEnergyTo(Coordinate c) {
            return (int) Math.round(Math.pow(Math.abs(c.x - x), 2) + Math.pow(Math.abs(c.y - y), 2));
        }
    }
}
