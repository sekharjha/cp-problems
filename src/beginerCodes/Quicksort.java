package beginerCodes;

import java.util.*;
public class Quicksort {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int a[]=new int[10];
        Random r=new Random();
        for (int i = 0; i <10 ; i++) {
          a[i]=  r.nextInt(100);
        }
        for (int i = 0; i <10 ; i++) {
            System.out.print(a[i]+" ");
        }
        System.out.println();
        quicksort(a,0,9);
        for (int i = 0; i <10 ; i++) {
            System.out.print(a[i]+" ");
        }
    }
public static void quicksort(int a[],int first,int last){
        int temp,pivot,i,j;
        if(first<last){
            i=first;
            j=last;
            pivot=first;
            while (i<j) {
                while (a[i] <= a[pivot] && i < last)
                    i++;
                while (a[j] > a[pivot])
                    j--;
                if (i < j) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
            temp=a[pivot];
            a[pivot]=a[j];
            a[j]=temp;
            quicksort(a,first,j-1);
            quicksort(a,j+1,last);
        }

}
}
