package usacoproblemsets.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/*
Bessie the cow is competing in a cross-country skiing event at the winter
Moolympic games.  She starts out at a speed of 1 meter per second. 
However, as she becomes more tired over time, she begins to slow down. 
Each time Bessie slows down, her speed decreases: she moves at 1/2 meter
per second after slowing down once, then 1/3 meter per second after slowing
down twice, and so on.

You are told when and where Bessie slows down, in terms of a series of
events.  An event like this:

T 17

means that Bessie slows down at a specific time -- here, 17 seconds into
the race.  An event like this:

D 10

means that Bessie slows down at a specific distance from the start -- in
this case, 10 meters.

Given a list of N such events (1 <= N <= 10,000), please compute the amount
of time, in seconds, for Bessie to travel an entire kilometer.  Round your
answer to the nearest integer second (0.5 rounds up to 1).

PROBLEM NAME: slowdown

INPUT FORMAT:

* Line 1: The value of N.

* Lines 2..1+N: Each line is of the form "T x" or "D x", indicating a
        time event or a distance event.  In both cases, x is an
        integer that is guaranteed to place the event before Bessie
        reaches one kilometer of total distance.  It is possible for
        multiple events to occur simultaneously, causing Bessie to
        slow down quite a bit all at once.  Events may not be listed
        in order.

SAMPLE INPUT (file slowdown.in):

2
T 30
D 10

INPUT DETAILS:

Bessie slows down at time t = 30 and at distance d = 10.

OUTPUT FORMAT:

* Line 1: The total time required for Bessie to travel 1 kilometer.

SAMPLE OUTPUT (file slowdown.out):

2970

OUTPUT DETAILS:

Bessie travels the first 10 meters at 1 meter/second, taking 10 seconds. 
She then slows down to 1/2 meter/second, taking 20 seconds to travel the
next 10 meters.  She then reaches the 30 second mark, where she slows down
again to 1/3 meter/second.  The remaining 980 meters therefore take her
980 * 3 = 2940 seconds.  The total time is therefore 10 + 20 + 2940 = 2970.
*/
public class slowdown {
    
    static int n;
    
    static ArrayList<Integer> times, distances;
    
    public static void run(String[] args) {
        Scanner s = new Scanner(System.in);
        
        n = s.nextInt();
        times = new ArrayList();
        distances = new ArrayList();
        
        for (int i = 0; i < n; i++) {
            if (s.next().equals("T")) times.add(s.nextInt());
            else distances.add(s.nextInt());
        }
        
        Collections.sort(times);
        Collections.sort(distances);
        
        double time = 0;//seconds
        double distance = 0;
        double speed = 1;//  measured in 1/x m/s
        
        
        while (distance != 1000) {
            double timeToNextTime = Double.MAX_VALUE, timeToNextDistance = Double.MAX_VALUE;
            if (! times.isEmpty()) timeToNextTime = times.get(0) - time;
            if (! distances.isEmpty()) timeToNextDistance = (distances.get(0) - distance) / (1 / speed);
            if (timeToNextTime == Double.MAX_VALUE && timeToNextDistance == Double.MAX_VALUE) {time += (1000 - distance) / (1 / speed); break;}
            
            if (timeToNextTime < timeToNextDistance) {//Get next time
                int n = times.remove(0);
                distance += (n - time) * (1 / speed);//Time passed
                time = n;
                speed++;
            }
            else {//Get next distance
                int n = distances.remove(0);
                time += (n - distance) / (1 / speed);
                distance = n;
                speed++;
            }
            System.out.println(distances.size() + " " + times.size() + " Distance: " + distance + " Time: " + time + " Speed: " + (1 / speed));
        }
        
        
        System.out.println(Math.round(time));
    }
    
    
}