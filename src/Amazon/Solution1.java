package Amazon;

import java.util.Scanner;

public class Solution1 {

    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int n =s.nextInt();
        int a[]=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=s.nextInt();
        }
        System.out.println(convert(n,a));
    }
    public static int convert(int input1,int  input2[]){
        int avg=0;
        for (int i = 0; i <input1 ; i++) {
            avg+=input2[i];
        }
        avg/=input1;
        int count=0;
        for (int i = 0; i <input1 ; i++) {
            if(input2[i]>avg)
            {
                count+=input2[i]-avg;
            }
        }
        return count;
    }
}
