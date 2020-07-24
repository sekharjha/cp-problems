package beginerCodes;

import java.util.*;
class test1{
    static StringBuilder stringBuilder = new StringBuilder();

    static void print(Object value) {
        stringBuilder.append(value);
    }

    static void println(Object value) {
        stringBuilder.append(value).append("\n");
    }
    public static void main(String[] args) {
    Scanner  s= new Scanner(System.in);
    int n =s.nextInt();
    int a[]=new int[n];
    int b[]=new int [n];
        for (int i = 0; i <n ; i++) {
            a[i] = s.nextInt();
            b[i]=s.nextInt();
        }
        int k =s.nextInt();
        k=Math.min(k,n);
        int index=1;
        while (k-->1){
            if(index==n) {
                System.out.println("YES");
                index = 1;
            }
            int val = index%n+1;
            print(val);
            index+=2;


        }
        print(index%n+1);
        System.out.println(stringBuilder.toString());
    }
}