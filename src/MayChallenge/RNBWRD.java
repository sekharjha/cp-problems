package MayChallenge;

import java.util.*;


class RNBWRD {
    static long m;
    static int r,Q;
    static int[] a;
    static int k[];

    static final int MOD = 998244353;
    static int dp[][];

    static long runDp(int row,int col,long K)
    {
        if(row == K-1 && col == r -1)
            return a[col];
        if(row >= K || col >= r)
            return 0L;

        if(row < K && col < r && dp[row][col] != -1)
            return dp[row][col];

        long below_val = 0;
        if(row < K)
        {
            below_val = runDp(row+1,col,K)%MOD;
        }

        long right_val = 0;
        if(col < r)
        {
            right_val = runDp(row,col+1,K)%MOD;
        }
        dp[row][col] = (int)((below_val + right_val)%MOD*(a[col])%MOD)%MOD;
        return dp[row][col];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        m = in.nextLong();
        r = in.nextInt();
        Q = in.nextInt();
        a = new int[r];
        dp = new int[(int) m][r];


        for(int i = 0; i < r; i++)
            a[i] = in.nextInt();
        k = new int[Q];
        for(int i = 0;i < Q;i++)
            k[i] = in.nextInt();
        for(int i = 0; i < m; i++)
        {
            Arrays.fill(dp[i],-1);
        }
        runDp(0,0, m);
        for(int i = 0;i < Q;i++)
        {
            System.out.println(dp[(int) m -(int)k[i]][0]);

        }

    }
}