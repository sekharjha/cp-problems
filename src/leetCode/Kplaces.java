package leetCode;
import java.util.*;
public class Kplaces {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        int a[]=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=s.nextInt();
        }
        int k=s.nextInt();
        System.out.println(kLengthApart(a,k));
    }
    public static boolean kLengthApart(int[] nums, int k) {
        int len=nums.length;
        int prevPos=-1;
        for (int i = 0; i <len ; i++) {
            if(nums[i]==1&&prevPos==-1)
                prevPos=i;
            else if(nums[i]==1)
            {
                if(i-prevPos<=k)
                    return false;
                prevPos=i;
            }
        }
        if(len-prevPos<=k)
            return false;
        return true;
    }
}
