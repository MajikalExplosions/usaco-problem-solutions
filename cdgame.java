

package usacoproblemsets;

import java.util.Scanner;

public class cdgame {
    
    
    public static void run(String[] args) {//false is first player loss, true is first player win
        boolean[] dp = new boolean[1000001];
        boolean[] solved = new boolean[1000001];
        
        dp[0] = false;
        solved[0] = true;
        Scanner s = new Scanner(System.in);
        int g = s.nextInt();
        
        while (g-- > 0) {
            boolean b = query(dp, solved, s.nextInt());
            if (b) System.out.println("YES");
            else System.out.println("NO");
        }
    }
    
    private static boolean query(boolean[] dp, boolean[] solved, int n) {
        if (solved[n]) return dp[n];
        //find min and max
        int[] mm = minMaxDigits(n);
        
        //find max first and only find min if that's allowed
        boolean max = query(dp, solved, n - mm[1]);
        if (mm[0] == mm[1] || mm[0] == 0) {//only use max
            dp[n] = ! max;
        }
        else if (! max) {
            dp[n] = true;
        }
        else if (! query(dp, solved, n - mm[0])) {
            dp[n] = true;
        }
        else {
            dp[n] = false;
        }
        solved[n] = true;
        return dp[n];
    }
    
    private static int[] minMaxDigits(int n) {
        int min = 9, max = 0;
        while (n > 9) {
            if (n % 10 < min && n % 10 != 0) min = n % 10;
            if (n % 10 > max) max = n % 10;
            n /= 10;
        }
        if (n % 10 < min && n % 10 != 0) min = n % 10;
        if (n % 10 > max) max = n % 10;
        return new int[] {min, max};
    }
}