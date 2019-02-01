package usacoproblemsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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