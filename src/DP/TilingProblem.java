package DP;
/*
* tiles can be straight and  L shaped
'*tile shapes allowed '--' ,'|','L','7',
*
*
* */
import java.util.*;
public class TilingProblem {
    static int n;
    static int dp[][];
    static int mod=1000000007;
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int T=s.nextInt();
        while (T-->0){
            n =s.nextInt();
            dp=new int[n][4];
            System.out.println( solve());
        }
    }
    public static int solve(){
        return f(0,true,true);
    }
    static int f(int i,boolean t1,boolean t2){
        if(i==n)
            return 1;
        int state = makeState(t1,t2);
        if(dp[i][state]!=0)
            return dp[i][state];
        boolean t3=i+1<n;
        boolean t4=i+1<n;
        int count=0;
        if( t1 && t2 && t3 ){
            count+=f(i+1,false,true);

        }
        if( t1 && t2 && t4){
            count+=f(i+1,true,false);
        }
        if(t1&& !t2 && t3 && t4){
            count+=f(i+1,false,false);
        }
        if(!t1 && t2 && t3 && t4){
            count+=f(i+1,false,false);
        }
        if(t1&&t2){
            count+=f(i+1,true,true);
        }
        if(t1&&t2&&t3&&t4){
            count+=f(i+1,false,false);
        }
        if(t1 && !t2&&t3){
            count+=f(i+1,false,true);
        }
        if(!t1&&t2&&t4){
            count+=f(i+1,false,true);
        }
        if(!t1&&!t2){
            count+=f(i+1,true,true);
        }
        return dp[i][state]=count%mod;

    }
    static int makeState(boolean t1,boolean t2){
        int state=0;
        if(t1){
            state|=1;
        }
        if(t2){
            state|=2;
        }
        return state;
    }
}
