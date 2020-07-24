package leetCode;
import java.util.*;
public class SubArrayLimit {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        int a[]=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=s.nextInt();
        }
        int limit=s.nextInt();
        int k= longestSubarray(a,limit);
    }
    public static int longestSubarray(int[] nums, int limit) {
        num a[]=new num[nums.length];
        //System.out.println(a.length);
        for (int i = 0; i <nums.length ; i++) {
            num v= new num();
            v.minValue=nums[i]-limit;
            v.maxValue=nums[i]+limit;
            a[i]=v;
        }
        List<Integer>list=new ArrayList<>();
        num arr[]=new num[nums.length];
        num v1= new num();
        v1.minValue=nums[0];
        v1.maxValue=nums[0];
        arr[0]=v1;
        for (int i = 1; i <nums.length ; i++) {
            num v= new num();
            v.minValue=Math.min(arr[i-1].minValue,nums[i]);
            v.maxValue=Math.max(arr[i-1].maxValue,nums[i]);
            arr[i]=v;
        }

        System.out.println(arr[3]);
        return 1;
    }
    static class num{
        public int minValue;
        public int maxValue;

        @Override
        public String toString() {
            return "num{" +
                    "minValue=" + minValue +
                    ", maxValue=" + maxValue +
                    '}';
        }
    }

}
