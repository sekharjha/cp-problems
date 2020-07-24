package MayChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class SORTVS {
    InputStream is;
    PrintWriter o;
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

        static int pos[];

        static List<Integer> computedList;
        static int n, m;
       HashSet<Integer> set[];
        static HashMap<Integer,Integer> valueMap;
        static HashSet<Integer> valueSet[];
        static boolean[] alreadyPresent;
        static int result;
        static void findParent(int n)
        {
            computedList.add(n);
            if(alreadyPresent[n])
                return;
            alreadyPresent[n] = true;
            int next = pos[n];
            if(next == n)
                return;

            findParent(next);
        }

        static int computeResult()
        {
            int temp = 0;
            for(int i = 0; i < computedList.size() - 1; i++)
            {
                int nodesA = computedList.get(i);
                int node2  = computedList.get(i+1);
                if(valueSet[nodesA].contains(node2))
                    continue;
                temp += 1;
            }
            return temp;
        }
    public static void main(String[] args) { new SORTVS().run(); }
    void run() {
        is = System.in;
        o = new PrintWriter(System.out);
        solve();
        o.flush();
    }
        void solve() {
            int T = ni();

            while(T-- > 0)
            {
                n = ni();
                m = ni();
                pos = new int[n];
                alreadyPresent = new boolean[n];

                result = 0;
                valueSet = new HashSet[n];
                valueMap = new HashMap<>();
                computedList = new ArrayList<>();
                for(int i = 0; i < n; i++)
                {
                    pos[i] = ni()-1;
                    valueSet[i] = new HashSet<>();
                    valueMap.put(pos[i],i);
                }

                for(int i = 0; i < m; i++)
                {
                    int x = ni() - 1;
                    int y = ni() - 1;
                    valueSet[x].add(y);
                    valueSet[y].add(x);
                }

                for(int i = 0; i < n; i++)
                {
                    if(!alreadyPresent[i]){
                        findParent(i);
                        int temp = computeResult();
                        if(temp > 0)
                            temp--;
                        result += temp;
                        computedList.clear();
                    }
                }

                o.println(result);

            }

        }

    }
