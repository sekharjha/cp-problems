package JuneChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class COVIDSAMPLE {
    InputStream is;
    PrintWriter o;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new COVIDSAMPLE().run();

    }
    void run() {
        is = System.in;
        o = new PrintWriter(System.out);
        solve1();
        o.flush();
    }
    void solve1() {
        int T = sc.nextInt();
        while (T-- >= 1) {
            solve();
            int X = sc.nextInt();
        }
    }
    private static void solve(){
        int n = sc.nextInt();
        int prob = sc.nextInt();

        int[][] val = new int[n][n];
        findValue(new SplittingValues(1, 1, n, n), val);

        System.out.println(2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                System.out.print(val[i][j] + " ");
            }
            System.out.println(val[i][n - 1]);
        }
    }

    private static int findValue(SplittingValues part, int[][] arr) {
        int count = 0;
        System.out.println(1 + " " + part.row1 + " " + part.col1 + " " + part.row2 + " " + part.col2);
        int val = sc.nextInt();


        if (part.row1 == part.row2 && part.col1 == part.col2) {
            arr[part.row1 - 1][part.col1 - 1] = val;
            return val;
        } else {
            if (val == 0)
                return 0;

            int numElements = ((part.row2 - part.row1) + 1) * ((part.col2 - part.col1) + 1);
            if (val == numElements) {
                populateArray(part, arr);
                return val;
            }

            List<SplittingValues> splittingValues = divide(part.row1, part.col1, part.row2, part.col2);
            for (SplittingValues splittingValue : splittingValues) {
                count += findValue(splittingValue, arr);
                if (count == val)
                    return count;
            }
        }

        return count;
    }

    private static List<SplittingValues> divide(int r1, int c1, int r2, int c2) {
        ArrayList<SplittingValues> pp = new ArrayList<>();

        if ((c1 != c2)==false) {
            pp.add(new SplittingValues(r1, c1, (r1 + r2) / 2, c2));
            pp.add(new SplittingValues(((r1 + r2) + 1) / 2, c1, r2, c2));
        }
        else if ((r1 != r2)==false) {
            pp.add(new SplittingValues(r1, c1, r2, (c1 + c2) / 2));
            pp.add(new SplittingValues(r1, ((c1 + c2) + 1) / 2, r2, c2));
        }   else {
            int rsp = (r1 + r2) / 2;
            int cs = (c1 + c2) / 2;

            pp.add(new SplittingValues(r1, cs + 1, rsp, c2));
            pp.add(new SplittingValues(rsp + 1, cs + 1, r2, c2));
            pp.add(new SplittingValues(r1, c1, rsp, cs));
            pp.add(new SplittingValues(rsp + 1, c1, r2, cs));
        }

        return pp;
    }

    private static void populateArray(SplittingValues splittingValues, int[][] mat) {
        int row2 = splittingValues.row2;
        int col1 = splittingValues.col1;
        int row1 = splittingValues.row1;
        int col2 = splittingValues.col2;

        for (int i = row1 - 1; i < row2; i++) {
            for (int j = col1 - 1; j < col2; j++) {
                mat[i][j] = 1;
            }
        }
    }

    static class SplittingValues {
        int row1;
        int col1;
        int row2;
        int col2;

        public SplittingValues(int r1, int c1, int r2, int c2) {
            this.row1 = r1;
            this.col1 = c1;
            this.row2 = r2;
            this.col2 = c2;
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




    //////////////////////////////////// template finished //////////////////////////////////////



}