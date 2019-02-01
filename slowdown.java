package usacoproblemsets.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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