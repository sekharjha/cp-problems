package beginerCodes;

import java.util.*;
public class MaximumGold {
    static Map<String,Integer> map;
    public static void main(String[] args) {

        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        int m=s.nextInt();
        int a[][]=new int[n][m];
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <m ; j++) {
                a[i][j]=s.nextInt();
            }
        }
        map=new HashMap<>();
        int max=Integer.MIN_VALUE;
        for (int i = 0; i <n ; i++) {
            max=Math.max(max,findMax(a,i,0,n));
        }
        System.out.println(max);
    }
    public static int findMax(int a[][],int i,int j,int n){
        String str=i+" "+j;
        if(i<0||j<0||i>n-1||j>n-1)
            return 0;
        if(map.get(str)!=null)
            return map.get(str);
        else {
            int res=a[i][j] + Math.max(findMax(a, i, j + 1, n), Math.max(findMax(a, i - 1, j + 1, n), findMax(a, i + 1, j + 1, n)));
            map.put(str,res);
            return res;
        }
    }
}
