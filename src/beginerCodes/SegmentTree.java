package beginerCodes;

import java.util.*;
class SegmentTree {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        int a[]=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=s.nextInt();
        }
        int length;
        if((int)Math.ceil(Math.log(n)/Math.log(2))==(int)Math.floor(Math.log(n)/Math.log(2)))
            length=2*n-1;
        else
            length=4*(n-1)-1;
        int segtree[]=new int[length];
        Arrays.fill(segtree,Integer.MAX_VALUE);
        constructTree(a,segtree,0,n-1,0);
        for (int i = 0; i <length ; i++) {
            System.out.println(segtree[i]);
        }
       int ans= minRangeQuery(segtree,2,4,0,n-1,0);
        System.out.println(ans);
    }
    public static void constructTree(int a[],int segtree[],int low,int high,int pos){
        if(low==high){
            segtree[pos]=a[low];
            return;
        }
        int mid=(low+high)/2;
        constructTree(a,segtree,low,mid,2*pos+1);
        constructTree(a,segtree,mid+1,high,2*pos+2);
        segtree[pos]=Math.min(segtree[2*pos+1],segtree[2*pos+2]);
    }
    public static int minRangeQuery(int segTree[],int qlow,int qhigh,int low,int high,int pos){
        if(qlow<=low&&qhigh>=high)
            return segTree[pos];
        if(qlow>high||qhigh<low)
            return Integer.MAX_VALUE;
        int mid=(low+high)/2;
        return Math.min(minRangeQuery(segTree,qlow,qhigh,low,mid,2*pos+1),minRangeQuery(segTree,qlow,qhigh,mid+1,high,2*pos+2));
    }
}