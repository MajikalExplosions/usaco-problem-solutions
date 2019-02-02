package usacoproblemsets.day4;

import java.math.BigInteger;
import java.util.Scanner;

/*
Description

Farmer John goes to Dollar Days at The Cow Store and discovers an unlimited number of tools on sale. During his first visit, the tools are selling variously for $1, $2, and $3.
Farmer John has exactly $5 to spend. He can buy 5 tools at $1 each or 1 tool at $3 and an additional 1 tool at $2. Of course, there are other combinations for a total of 5 different
ways FJ can spend all his money on tools. Here they are: 

        1 @ US$3 + 1 @ US$2

        1 @ US$3 + 2 @ US$1

        1 @ US$2 + 3 @ US$1

        2 @ US$2 + 1 @ US$1

        5 @ US$1
Write a program than will compute the number of ways FJ can spend N dollars (1 <= N <= 1000) at The Cow Store for tools on sale with a cost of $1..$K (1 <= K <= 100).

Input
A single line with two space-separated integers: N and K.

Output
A single line with a single integer that is the number of unique ways FJ can spend his money.

Sample Input
5 3

Sample Output
5
*/
public class Ddayz {
    
    static int n, k;
    static BigInteger[][] dp;//Ways to spend money
    static boolean[][] dpb;
    
    public static void run(String[] args) {
        Scanner s = new Scanner(System.in);
        
        n = s.nextInt();//Farmer John has $n dollars to spend
        k = s.nextInt();//Tools range from $1-$k
        
        dp = new BigInteger[n + 1][k + 1];
        dpb = new boolean[n + 1][k + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i][1] = BigInteger.ONE;
            dpb[i][1] = true;
        }
        
        System.out.println("-----");
        System.out.println(calculate(n, k));
        //System.out.println(getObjectSize(dp));
    }
    
    private static BigInteger calculate(int moneyLeft, int max) {
        if (max == 1) return BigInteger.ONE;
        if (dpb[moneyLeft][max]) return dp[moneyLeft][max];
        int maxChild = max - 1;
        int numMax = 0;
        
        BigInteger totalPossibilities = BigInteger.ZERO;
        
        while (numMax * max <= moneyLeft) {//while there's money left
            totalPossibilities = totalPossibilities.add(calculate(moneyLeft - numMax * max, maxChild));
            numMax++;
        }
        dp[moneyLeft][max] = totalPossibilities;
        dpb[moneyLeft][max] = true;
        return totalPossibilities;
    }
    /*
    private static BigInteger calculate(int n2) {
        if (dpb[n2]) return dp[n2];
        BigInteger x = BigInteger.ZERO;
        int i = Math.min(k, n2);
        
        while (i > 0 && n2 - i >= 0) {
            x = x.add(calculate(n2 - i--));
        }
        
        dp[n2] = x;
        dpb[n2] = true;
        
        return x;
    }
    */
}