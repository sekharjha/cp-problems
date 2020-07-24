package beginerCodes;

import java.util.*;
class Codechef{
    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);
        int t=s.nextInt();
        while (t-->0){
            int n=s.nextInt();
            int k=s.nextInt();
            int a[]=new int[n];
            for (int i = 0; i <n ; i++) {
                a[i]=s.nextInt();
            }
            StringBuilder builder=new StringBuilder("");
            for (int i = 0; i <n/2 ; i++) {
                builder.append((i+1)+" "+(n-1)+" "+(n)).append('\n');
            }
            System.out.println(n/2);
            System.out.println(builder.toString());
        }
    }
}