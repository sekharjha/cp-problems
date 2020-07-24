package beginerCodes;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;
class MSV {
    InputStream is;

    PrintWriter o;



    void solve() {
        int T = ni();
        while (T-- > 0) {
            int n = ni();
            int a[] = new int[n];
            int maximum_value = 0;
            for (int i = 0; i < n; i++) {
                a[i] = ni();
            }
             HashMap<Integer,Integer> f=new HashMap<>();
            for (int i = 1; i <n ; i++) {
                f=getFreq(a[i-1],f);
               // System.out.println(f);
                if(f.get(a[i])!=null&&f.get(a[i])>maximum_value){
                    maximum_value=f.get(a[i]);
                }
            }
            System.out.println(maximum_value);
        }

    }
    public static void main(String[] args) { new MSV().run(); }
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




    //////////////////////////////////// template finished //////////////////////////////////////
    public static HashMap<Integer,Integer> getFreq(int number,HashMap<Integer,Integer> f){
        if (number==1)
        {
            if(f.get(number)==null){
                f.put(number,1);

            }
            else {
                f.put(number,f.get(number)+1);
            }
        }
        else {
            for (int i = 1; i <=Math.sqrt(number); i++) {
                if (number % i == 0) {
                    if (f.get(i) == null) {
                        f.put(i, 1);
                        if (f.get(number / i) == null&&i!=number/i)
                            f.put(number / i, 1);
                        else if(i!=number/i)
                            f.put(number / i, f.get(number / i) + 1);
                    } else {
                        f.put(i, f.get(i) + 1);
                        if (f.get(number / i) == null&&i!=number/i)
                            f.put(number / i, 1);
                        else if(i!=number/i)
                            f.put(number / i, f.get(number / i) + 1);
                    }
                }
            }
        }
        return f;
    }



}
