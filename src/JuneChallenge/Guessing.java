package JuneChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class Guessing
{
    InputStream is;
    PrintWriter o;
    static int n,low,high;
    static final Scanner in = new Scanner(System.in);
    static boolean E;

    static char queryPart(int y)
    {
        System.out.println(y);
        System.out.flush();
        char res = in.next().charAt(0);
        if(res == 'E'){
            E = true;
            System.exit(0);
        }
        return res;
    }

    static char find(int y)
    {
        char res1 = queryPart(y);
        char res2 = queryPart(y);
        if(res1 == res2)
            return res1;

        Random rnd = new Random();

        int rand_low = 1+rnd.nextInt(low);
        int rand_high = high + rnd.nextInt(n-high+1);

        char res3 = queryPart(rand_low);
        if(res3 == 'L')
            return res2;

        res3 = queryPart(rand_high);
        if(res3 == 'G')
            return queryPart(y);

        while(true){
            rand_low = 1+rnd.nextInt(low);
            if(queryPart(rand_low) == 'L')
                break;
            rand_high = high + rnd.nextInt(n-high+1);
            if(res3 == 'G')
                break;
        }
        return queryPart(y);
    }

    public static void main(String[] args) {
        new Guessing().run();
    }
    void run() {
        is = System.in;
        o = new PrintWriter(System.out);
        solve();
        o.flush();
    }
    void solve() {

        n = in.nextInt();
        low = 1;
        high = n;
        E = false;
        int x = -1;
        while(low <= high)
        {
            int mid = (low+high)/2;
            char c = find(mid);

            if(E){
                x = mid;
                break;
            }

            if(c == 'G')
                low = mid+1;
            else
                high = mid-1;

        }



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
