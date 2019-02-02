

package usacoproblemsets;

import java.util.Scanner;

/**

Bessie is playing a number game against Farmer John, and she wants
you to help her achieve victory.

Game i starts with an integer N_i (1 <= N_i <= 1,000,000). Bessie
goes first, and then the two players alternate turns. On each turn,
a player can subtract either the largest digit or the smallest
non-zero digit from the current number to obtain a new number. For
example, from 3014 we may subtract either 1 or 4 to obtain either
3013 or 3010, respectively. The game continues until the number
becomes 0, at which point the last player to have taken a turn is
the winner.

Bessie and FJ play G (1 <= G <= 100) games. Determine, for each
game, whether Bessie or FJ will win, assuming that both play perfectly
(that is, on each turn, if the current player has a move that will
guarantee his or her win, he or she will take it).

Consider a sample game where N_i = 13. Bessie goes first and takes
3, leaving 10. FJ is forced to take 1, leaving 9. Bessie takes the
remainder and wins the game.

PROBLEM NAME: cdgame

INPUT FORMAT:

* Line 1: A single integer: G

* Lines 2..G+1: Line i+1 contains the single integer: N_i

SAMPLE INPUT (file cdgame.in):

2
9
10

OUTPUT FORMAT:

* Lines 1..G: Line i contains "YES" if Bessie can win game i, and "NO"
        otherwise.

SAMPLE OUTPUT (file cdgame.out):

YES
NO

OUTPUT DETAILS:

For the first game, Bessie simply takes the number 9 and wins.
For the second game, Bessie must take 1 (since she cannot take 0), and then
FJ can win by taking 9.
 */

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