
package usacoproblemsets;

import java.util.ArrayList;
import java.util.Scanner;

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
