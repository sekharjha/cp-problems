package beginerCodes;

import java.util.*;
class Problem{
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int t=s.nextInt();
        while (t-->0){
            int n=s.nextInt();
            int a[]=new int[n];
            HashSet<Integer>set =new HashSet<>();
            for (int i = 0; i <n ; i++) {
                a[i]=s.nextInt();
            }
            for (int i = 0; i <n ; i++) {
                if(set.contains(a[i])){
                    System.out.println(a[i]);
                    break;
                }
                else
                    set.add(a[i]);
            }
        }
    }
}