package MayChallenge;

import java.io.*;
import java.util.*;

class BINLAND {
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

    static List<Integer> firstPostionList, secondPostionList, thirdPositionList;
    static List<List<Integer>> modEven, modBoth, modOdd;
    static HashMap<Integer, Integer> vABC, sABC;
    static boolean alreadyVisitedPositions[];
    static int sArr[], pos[], N;




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
    public static void main(String[] args) { new BINLAND().run(); }
    void run() {
        is = System.in;
        o = new PrintWriter(System.out);
        solve();
        o.flush();
    }
    public static List<List<Character>> grids;
    public static int mod = 1000000007;
    public static Map<String, Integer> dp = new HashMap<>();

    public void solve() {
        String[] validates={"add","remove"};
        FastReader s = new FastReader();
        int N = ni();
        int Q = ni();
        grids = new ArrayList<>();
        for (int i = 0; i < Q; i++) {
            dp =new HashMap<>();
            String str = nLine();
            String[] subParts = str.split("\\s+");
            if (subParts[0].compareTo(validates[0]) == 0) {
                char ch[] = subParts[1].toCharArray();
                List<Character> list = toListConversion(ch);
                grids.add(list);
            } else if (subParts[0].equals(validates[1])) {
                grids.remove(0);
            } else {
                int start = Integer.parseInt(subParts[1]) - 1;
                int end = Integer.parseInt(subParts[2]) - 1;
                long res = 0;
                res = findWays(grids, grids.size(), N, 0, start, grids.get(0).get(start), end);
                o.println(res);
            }
        }


    }

    public static int findWays(List<List<Character>> matrix, int n, int m, int i, int j, char ch, int dest) {
        if(dp.containsKey(i+"&"+j))
            return dp.get(i+"&"+j);
        else if (i >= n || j >= m || i < 0 || j < 0)
            return 0;
        else if (matrix.get(i).get(j) != ch)
            return 0;
        else if (j == dest && i == n - 1)
            return 1;
        else{
            int sum1= findWays(matrix, n, m, i + 1, j - 1, ch, dest) % mod;
            int sum2 = findWays(matrix, n, m, i + 1, j, ch, dest) % mod;
            int sum3 = findWays(matrix, n, m, i + 1, j + 1, ch, dest);
             dp.put(i+"&"+j,(((sum1 + sum2) % mod) + sum3) % mod);
            return dp.get(i+"&"+j) % mod;
        }
    }

    public static List<Character> toListConversion(char[] ch) {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < ch.length; i++) {
            list.add(ch[i]);
        }
        return list;
    }

    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt()
        {
            return Integer.parseInt(next());
        }

        long nextLong()
        {
            return Long.parseLong(next());
        }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }

}
