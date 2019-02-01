
package usacoproblemsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Starry {
    
    static int[][] map;
    
    static ArrayList<Integer> clusters;
    
    static ArrayList<Integer> patterns;
    static int patternCount = 0;
    
    public static void run(String[] args) {
        Scanner s = new Scanner(System.in);
        
        int m = s.nextInt(), n = s.nextInt();
        
        map = new int[m][n];
        clusters = new ArrayList<>();
        patterns = new ArrayList<>();
        /* I CAN PROBABLY COMBINE THE FOLLOWING TWO FOR-LOOPS TOGETHER BUT I DON'T THINK IT SLOWS DOWN ALL THAT MUCH SO... */
        for (int i = 0; i < m; i++) {//make each star a distinct number 0 or up, and each sky -1
            String r = s.next();
            for (int j = 0; j < n; j++) {
                if (r.charAt(j) == '*')
                    map[i][j] = i * n + j;
                else map[i][j] = -1;
            }
        }
        
        //for each star, if one of it's neighboring stars has a different number assign to that number and assign their numbers to the same number
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] != -1) {
                    consolidate(i, j, map[i][j]);
                    if (! clusters.contains(map[i][j])) clusters.add(map[i][j]);//add non-existing clusters to clusters arraylist
                }
                
            }
        }
        
        //it works up to here; now for the hard part: id-ing the clusters w/ hashing and saving them
        
        //part 1: for each cluster, find the min and max x and y coords to find size and store the clusters in a true-false array
        //part 2: (in the same for loop) hash each array 4 times(rotate in between, of course) and store the results in patterns.  Treat each rotation separately.
        //part 3: if the same hashcode already exists, don't add to the array - instead, ignore it.
        for (int cluster : clusters) {
            int minI = m, maxI = 0, minJ = n, maxJ = 0;
            
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == cluster) {
                        if (i < minI) minI = i;
                        if (i > maxI) maxI = i;
                        if (j < minJ) minJ = j;
                        if (j > maxJ) maxJ = j;
                    }
                }
            }
            int sizeI = maxI - minI + 1, sizeJ = maxJ - minJ + 1;//calculate size of boolean array needed to save the pattern
            
            boolean[][] pattern = new boolean[sizeI][sizeJ];
            
            for (int i = minI; i <= maxI; i++) {
                for (int j = minJ; j <= maxJ; j++) {
                    if (map[i][j] == cluster) pattern[i - minI][j - minJ] = true;
                }
            }
            
            boolean added = false;
            
            for (int z = 0; z < 2; z++) {
                for (int i = 0; i < 4; i++) {//for each of the four
                    int hc = Arrays.deepHashCode(pattern);
                    if (! patterns.contains(hc)) {
                        patterns.add(hc);
                        added = true;
                    }
                    pattern = rotate(pattern);
                }
                flip(pattern);
            }
            
            if (added) patternCount++;
        }
        
        
        //part 4: print the length of pattern array(divided by 4 for the rotations).  That's the answer :)
        System.out.println(patternCount);
    }
    
    private static void consolidate(int i, int j, int k) {//k is the number to set it to
        if (map[i][j] == -1) return;//if it's blank return
        map[i][j] = k;//if star set
        
        //for each of it's neighbors:
        // - check to see if star
        // - if it is *and* it has a different number consolidate
        
        //find neighbors in 3*3 grid
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                try {
                    if (map[x][y] != -1 && map[x][y] != map[i][j]) consolidate(x, y, k);
                }
                catch (ArrayIndexOutOfBoundsException e) {}//ignore this if it happens; it's because the neighbors may be out of bounds
            }
        }
    }
    
    private static boolean[][] rotate(boolean[][] pattern) {
        final int M = pattern.length;
        final int N = pattern[0].length;
        boolean[][] ret = new boolean[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = pattern[r][c];
            }
        }
        return ret;
    }
    
    public static void flip(boolean[][] pattern) {
        for(int i = 0; i < (pattern.length / 2); i++) {
            boolean[] temp = pattern[i];
            pattern[i] = pattern[pattern.length - i - 1];
            pattern[pattern.length - i - 1] = temp;
        }
    }
}
