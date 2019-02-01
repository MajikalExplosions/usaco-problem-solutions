
package usacoproblemsets;

import java.util.Scanner;
import java.util.ArrayList;

public class Climb {
    public static void run(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        
        ArrayList<Cow> upQueue = new ArrayList<>();
        ArrayList<Cow> downQueue = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            upQueue.add(new Cow(s.nextInt(), s.nextInt()));
        }
        //Sort queue
        NaturalMergesort.sort(upQueue);
        
        int time = 0;
        Cow upCow = upQueue.get(0), downCow = new Cow(0, 0);
        while ((! upQueue.isEmpty()) || (! downQueue.isEmpty())) {
            if (upCow.u == 0) {
                downQueue.add(upCow);
                upQueue.remove(0);
                upCow = upQueue.get(0);
            }
            else upCow.u -= 1;
            
            if (downCow.u <= 0) {
                upQueue.remove(0); 
                if (downQueue.size() > 0) upCow = upQueue.get(0);
            }
            else {
                upCow.u -= 1;
            }
            time++;
        }
        System.out.println(time);
    }
    
    private static class NaturalMergesort {
    
        private static ArrayList<Cow> temp;
        
        public static void sort(ArrayList<Cow> a) {
            int arrayLength = a.size();
            temp = new ArrayList<>();

            while(true) {//Merge array
                int l = 0, m, h;
                while (l < arrayLength) {//Pass through array
                    m = l + 1;
                    while (m < arrayLength && less(a.get(m - 1), a.get(m))) m++;//Look for first subarray
                    if (m >= arrayLength) {//if m is at the end of the array and l is at the beginning return because the array is sorted, otherwise break
                        if (l == 0)
                            return;
                        else break;
                    }
                    h = m + 1;
                    while(h < arrayLength && less(a.get(h - 1), a.get(h))) h++;
                    merge(a, l, --m, --h);
                    l = h + 1;
                }
            }
        }

        private static void merge(ArrayList<Cow> a, int lo, int mid, int hi) {

            int i = lo, j = mid + 1;
            temp = (ArrayList<Cow>) a.clone();//This is a shallow copy, so it uses the same references. I use it to keep track of the comparable objects.

            for (int k = lo; k <= hi; k++) {//It uses the merge algorithm in the book.

                if (i > mid) a.set(k, temp.get(j++));
                else if (j > hi) a.set(k, temp.get(i++));
                else if (less(temp.get(i), temp.get(j))) a.set(k, temp.get(i++));
                else a.set(k, temp.get(j++));
            }

            //System.out.println(a.toString());
        }

        private static boolean less(Cow a, Cow b) {
            if (a.u <= a.d) {
                if (b.u <= b.d) {
                    if (a.u < b.u) return true;
                    return false;
                }
                return true;
            }
            return b.d > a.d;
        }
    }
    private static class Cow {
        int u, d;
        
        public Cow(int u, int d) {
            this.u = u;
            this.d = d;
        }
    }
}
