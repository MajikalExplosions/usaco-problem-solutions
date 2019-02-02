package usacoproblemsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * Description

Farmer John's cows, pampered since birth, have reached new heights of fastidiousness. They now require their barn to be immaculate. Farmer John, the most
obliging of farmers, has no choice but hire some of the cows to clean the barn. 

Farmer John has N (1 <= N <= 10,000) cows who are willing to do some cleaning. Because dust falls continuously, the cows require that the farm be continuously
cleaned during the workday, which runs from second number M to second number E during the day (0 <= M <= E <= 86,399). Note that the total number of seconds during
which cleaning is to take place is E-M+1. During any given second M..E, at least one cow must be cleaning. 

Each cow has submitted a job application indicating her willingness to work during a certain interval T1..T2 (where M <= T1 <= T2 <= E) for a certain salary of
S (where 0 <= S <= 500,000). Note that a cow who indicated the interval 10..20 would work for 11 seconds, not 10. Farmer John must either accept or reject each
individual application; he may NOT ask a cow to work only a fraction of the time it indicated and receive a corresponding fraction of the salary. 

Find a schedule in which every second of the workday is covered by at least one cow and which minimizes the total salary that goes to the cows.
Input

Line 1: Three space-separated integers: N, M, and E. 

Lines 2..N+1: Line i+1 describes cow i's schedule with three space-separated integers: T1, T2, and S.
Output

Line 1: a single integer that is either the minimum total salary to get the barn cleaned or else -1 if it is impossible to clean the barn.
Sample Input

3 0 4
0 2 3
3 4 2
0 0 1
Sample Output

5
 */
@SuppressWarnings("unchecked")
public class Clean {
    
    static ArrayList<CowEvent> cowEvents;
    static ArrayList<Cow> activeCows;
    
    public static void run(String[] args) {
        Scanner s = new Scanner(System.in);
        
        int n = s.nextInt(), m = s.nextInt(), e = s.nextInt();
        
        cowEvents = new ArrayList<>();//Add cowevents
        for (int i = 0; i < n; i++) {
            int x = s.nextInt(), y = s.nextInt();
            Cow c = new Cow(i, s.nextInt());
            cowEvents.add(new CowEvent(c, x, true));
            cowEvents.add(new CowEvent(c, y + 1, false));
        }
        
        Collections.sort(cowEvents);
        
        activeCows = new ArrayList<>();
        for (int time = m; time <= e; time++) {
            System.out.println("--Running time " + time + "--");
            while (cowEvents.get(0).time == time) {
                CowEvent ce = cowEvents.remove(0);
                if (ce.start) {
                    if (activeCows.size() > 0) {
                        ce.cow.cost += activeCows.get(activeCows.size() - 1).cost;//lowest active cow cost
                    }
                    else if (time != m) {
                        System.out.println("-1");
                        return;
                    }
                    System.out.println("Added cow " + ce.cow.id + " with cost " + ce.cow.cost);
                    activeCows.add(ce.cow);
                    Collections.sort(activeCows);
                }
                else {
                    System.out.println("Removed cow " + ce.cow.id);
                    activeCows.remove(ce.cow);
                }
            }
        }
        if (activeCows.size() > 0) System.out.println(activeCows.get(activeCows.size() - 1).cost);
        else System.out.println("-1");
    }
    
    private static class CowEvent implements Comparable {
        public boolean start;//true if it's start, false if it's end
        public Cow cow;
        public int time;
        
        public CowEvent(Cow c, int t, boolean s) {
            cow = c;
            time = t;
            start = s;
        }
        
        public int compareTo(Object o) {
            CowEvent ce = (CowEvent) o;
            if (time == ce.time) {
                if (ce.start && !start) {
                    return 1;
                }
                if (!ce.start && start) {
                    return -1;
                }
            }
            return time - ce.time;
        }
    }
    
    private static class Cow implements Comparable {
        public int id;
        public int cost;
        
        public Cow(int i, int c) {
            id = i;
            cost = c;
        }
        
        public int compareTo(Object o) {
            Cow ce = (Cow) o;
            return cost - ce.cost;
        }
    }
}