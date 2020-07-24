package beginerCodes;

import java.util.*;
public class GCDPatterns {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        int a[]=new int[n];
        int b[]=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=s.nextInt();
        }
        for (int i = 0; i <n ; i++) {
            b[i]=s.nextInt();
        }
        int sum=Integer.MAX_VALUE;
        for (int i = 0; i <n ; i++) {
            for (int j = i+1; j <n ; j++) {
                if(gcd(a[i],a[j])==1){
                    int sum1=b[i]+b[j];
                    sum=Math.min(sum,sum1);
                }
            }
        }
        if(sum==Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(sum);
    }
    public static int gcd(int a,int b){
        if(b==0)
            return a;
        else
            return gcd(b,a%b);
    }
}
