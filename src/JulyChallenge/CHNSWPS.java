package JulyChallenge;

import java.util.*;
import java.io.*;
class CodechefSwap {
    InputStream is;
    PrintWriter o;
    Map<Long,Long> mainMap,second,third;
    long [] listArray,ListArray1;
    void solve() {
        int T=ni();
        while (T-->0){
            int n = ni();
            mainMap = new HashMap<>();
            second = new HashMap<>();
            third = new HashMap<>();
            long less = Integer.MAX_VALUE;
             listArray=new long[n];
             ListArray1=new long[n];
            for (int i = 0; i <n ; i++) {
                listArray[i]=nl();
                less=Math.min(listArray[i],less);
                if(mainMap.containsKey(listArray[i])){
                    mainMap.put(listArray[i],mainMap.get(listArray[i])+1);
                }
                else{
                    mainMap.put(listArray[i],1L);
                }
            }
            for (int i = 0; i <n ; i++) {
                ListArray1[i] = nl();
                less = Math.min(ListArray1[i],less);
                if(mainMap.containsKey(ListArray1[i])){
                    mainMap.put(ListArray1[i],mainMap.get(ListArray1[i])+1);
                }
                else{
                    mainMap.put(ListArray1[i],1L);
                }
            }
            long flag=0;
            for(Map.Entry<Long,Long> values:mainMap.entrySet()){
                if((values.getValue()%2==1)){
                    flag=1;
                    break;
                }
                else{
                    long dd=values.getValue()/2;
                    second.put(values.getKey(),dd);
                }
            }
            if(flag==1){
                o.println(-1);
            }
            else{
                third.putAll(second);
                List<Long> list1=new ArrayList<>();
                List<Long>list2=new ArrayList<>();
                for (int i = 0; i <n ; i++) {
                    if(second.get(listArray[i])==0)
                    {
                        list1.add(listArray[i]);
                    }

                    else
                        second.put(listArray[i],second.get(listArray[i])-1);
                }
                for (int i = 0; i <n; i++) {
                    if(third.get(ListArray1[i])==0){
                        list2.add(ListArray1[i]);
                    }

                    else
                        third.put(ListArray1[i],third.get(ListArray1[i])-1);
                }
                Collections.sort(list1);
                Collections.sort(list2,Collections.reverseOrder());
                int total= list1.size();
                if(total==0)
                o.println(0);
                else{
                    long ans=0;
                    long globalMin = 2*less;
                    for (int i = 0; i <=total-1 ; i++) {
                        ans+=Math.min(globalMin,Math.min(list1.get(i),list2.get(i)));
                    }
                    o.println(ans);
                }
            }

        }
    }
    public static void main(String[] args) { new CodechefSwap().run(); }
    void run() {
        is = System.in;
        o = new PrintWriter(System.out);
        solve();
        o.flush();
    }

    byte input[] = new byte[1024];
    int len = 0, ptr = 0;

    int readByte() {
        if(ptr >= len) { ptr = 0;
            try { len = is.read(input); }
            catch(IOException e) { throw new InputMismatchException(); }
            if(len <= 0) { return -1; }
        } return input[ptr++];
    }
    boolean isSpaceChar(int c) { return !( c >= 33 && c <= 126 ); }
    int skip() {
        int b = readByte();
        while(b != -1 && isSpaceChar(b)) { b = readByte(); }
        return b;
    }

    char nc() { return (char)skip(); }
    String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!isSpaceChar(b)) { sb.appendCodePoint(b); b = readByte(); }
        return sb.toString();
    }
    String nLine() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while( !(isSpaceChar(b) && b != ' ') ) { sb.appendCodePoint(b); b = readByte(); }
        return sb.toString();
    }
    int ni() {
        int n = 0, b = readByte();
        boolean minus = false;
        while(b != -1 && !( (b >= '0' && b <= '9') || b == '-')) { b = readByte(); }
        if(b == '-') { minus = true; b = readByte(); }
        if(b == -1) { return -1; }  //no input
        while(b >= '0' && b <= '9') { n = n * 10 + (b - '0'); b = readByte(); }
        return minus ? -n : n;
    }
    long nl() {
        long n = 0L;    int b = readByte();
        boolean minus = false;
        while(b != -1 && !( (b >= '0' && b <= '9') || b == '-')) { b = readByte(); }
        if(b == '-') { minus = true; b = readByte(); }
        while(b >= '0' && b <= '9') { n = n * 10 + (b - '0'); b = readByte(); }
        return minus ? -n : n;
    }

    double nd() { return Double.parseDouble(ns()); }
    float nf() { return Float.parseFloat(ns()); }
    int[] nia(int n) {
        int a[] = new int[n];
        for(int i = 0; i < n; i++) { a[i] = ni(); }
        return a;
    }
    long[] nla(int n) {
        long a[] = new long[n];
        for(int i = 0; i < n; i++) { a[i] = nl(); }
        return a;
    }
    int [][] nim(int n)
    {
        int mat[][]=new int[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                mat[i][j]=ni();
            }
        }
        return mat;
    }
    long [][] nlm(int n)
    {
        long mat[][]=new long[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                mat[i][j]=nl();
            }
        }
        return mat;
    }






    char[] ns(int n) {
        char c[] = new char[n];
        int i, b = skip();
        for(i = 0; i < n; i++) {
            if(isSpaceChar(b)) { break; }
            c[i] = (char)b; b = readByte();
        } return i == n ? c : Arrays.copyOf(c,i);
    }
    void piarr(int arr[])
    {
        for(int i=0;i<arr.length;i++)
        {
            o.print(arr[i]+" ");

        }
        o.println();
    }
    void plarr(long arr[])
    {
        for(int i=0;i<arr.length;i++)
        {
            o.print(arr[i]+" ");

        }
        o.println();
    }

    void pimat(int mat[][])
    {
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[0].length;j++)
            {
                o.print(mat[i][j]);
            }
            o.println();
        }
    }
    void plmat(long mat[][])
    {
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[0].length;j++)
            {
                o.print(mat[i][j]);
            }
            o.println();
        }

    }
}
