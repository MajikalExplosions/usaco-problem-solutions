package usacoproblemsets.day4;

import java.math.BigInteger;
import java.util.Scanner;

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