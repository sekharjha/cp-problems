package beginerCodes;

import java.util.*;
import java.io.*;

class TestCase1 {



    static int[] spf;
    static final int maxN = 1000000;
    static List<Integer> aj[];
    static int n, cur;
    static HashMap<Integer, List<HashMap<Integer, Integer>>> prefix_Array;
    static final int MOD = 1000000007;
    static boolean[] vis;
    static int[] dt;
    static int bak;
    static int[] parent, heavy, depth, head, pos, A;
    static List<Integer>[] children;
    static HashMap<Integer, Integer>[] fact;
    static HashMap<Integer, Long> afre;
    static Reader sc;
    InputStream is;
    PrintWriter o;

    static void sieve() {// ...10^6
        spf = new int[maxN + 1];
        spf[1] = 1;

        for (int i = 2; i <= maxN; i++)
            spf[i] = i;

        for (int i = 4; i <= maxN; i += 2)
            spf[i] = 2;

        for (int i = 3; i * i <= maxN; i++) {

            if (spf[i] == i) {

                for (int j = i * i; j <= maxN; j += i) {

                    if (spf[j] == j)
                        spf[j] = i;
                }
            }
        }
    }

    static HashMap<Integer, Integer> getPrime(int A) { // ..log(10^6)

        int curr = spf[A]; // Current prime factor of N
        int cnt = 1; // Power of current prime factor
        HashMap<Integer, Integer> map = new HashMap<>();

        while (A > 1) {
            A /= spf[A];
            if (curr == spf[A]) {
                cnt++;
                continue;
            }

            map.put(curr, cnt);
            curr = spf[A];
            cnt = 1;
        }
        return map;
    }

    void startObjects() {
        cur = 0;
        aj = new ArrayList[n];
        children = new ArrayList[n];
        fact = new HashMap[n];
        prefix_Array = new HashMap<>();
        vis = new boolean[n];
        dt = new int[n];
        parent = new int[n];
        heavy = new int[n];
        head = new int[n];
        depth = new int[n];
        pos = new int[n];
        A = new int[n];
        bak = 0;

        for (int i = 0; i < n; i++) {
            aj[i] = new ArrayList<>();
            children[i] = new ArrayList<>();
            fact[i] = new HashMap<>();
            parent[i] = heavy[i] = -1;
            depth[i] = head[i] = pos[i] = 0;
        }
    }

    void solveTestCase(){
        n = ni();
        startObjects();
        for (int i = 0; i < n - 1; i++) {
            int u = ni() - 1;
            int v = ni() - 1;

            aj[u].add(v);
            aj[v].add(u);
        }

        for (int i = 0; i < n; i++) {
            A[i] = ni();
            fact[i] = getPrime(A[i]);
        }

        depth[0] = 0;
        dfs(0, -1);
        reorient(0, 0);
        int Q = ni();

        while (Q-- > 0) {
            int a = ni() - 1;
            int b = ni() - 1;

            afre = new HashMap<>();
            while (head[a] != head[b]) {

                if (depth[head[a]] > depth[head[b]]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }

                HashMap<Integer, Integer> map2 = prefix_Array.get(head[b]).get(pos[b]);
                for (int x : map2.keySet()) {
                    if (afre.get(x) == null)
                        afre.put(x, (long) map2.get(x));
                    else {
                        afre.put(x, afre.get(x) + map2.get(x));
                    }
                }
                b = parent[head[b]];
            }

            List<HashMap<Integer, Integer>> pre = prefix_Array.get(head[b]);
            int down = Math.max(pos[a], pos[b]);
            int up = Math.min(pos[a], pos[b]);
            HashMap<Integer, Integer> tempMap;

            if (up == 0)
                tempMap = pre.get(down);
            else {
                HashMap<Integer, Integer> wingo = new HashMap<>(pre.get(down));
                for (int g : pre.get(up - 1).keySet()) {
                    wingo.put(g, wingo.get(g) - pre.get(up - 1).get(g));
                }
                tempMap = wingo;
            }

            for (int x : tempMap.keySet()) {
                if (afre.get(x) == null)
                    afre.put(x, (long) tempMap.get(x));
                else {
                    afre.put(x, afre.get(x) + tempMap.get(x));
                }
            }
            long ans = 1;
            for (long i : afre.values()) {
                ans *= (1 + i) % MOD;
                ans %= MOD;
            }
            o.println(ans % MOD);
        }
    }

    void solve() {
        sieve();
        int T = ni();
        TestCase tst = new TestCase();
        while (T-- > 0) {
            tst.solveTestCase();
        }
    }

    int dfs(int nd, int par) {
        int size = 1;
        int max_c_size = 0;
        parent[nd] = par;

        for (int c : aj[nd]) {
            if (c != par) {
                depth[c] = depth[nd] + 1;
                int c_size = dfs(c, nd);
                size += c_size;
                children[nd].add(c);
                if (c_size > max_c_size) {
                    max_c_size = c_size;
                    heavy[nd] = c;
                }
            }
        }

        return size;

    }

    void reorient(int v, int h) { // ..O(nlogA)

        head[v] = h;
        pos[v] = cur++;
        if (v == h) {
            prefix_Array.put(h, new ArrayList<>());
            prefix_Array.get(h).add(fact[v]);
        } else {

            HashMap<Integer, Integer> last = new HashMap<>();
            last.putAll(prefix_Array.get(h).get(prefix_Array.get(h).size() - 1));

            for (int num : fact[v].keySet()) {
                if (last.get(num) == null)
                    last.put(num, fact[v].get(num));
                else {
                    last.put(num, last.get(num) + fact[v].get(num));
                }
            }
            prefix_Array.get(h).add(last);
        }

        if (heavy[v] != -1)
            reorient(heavy[v], h);

        for (int c : children[v]) {
            if (c != heavy[v]) {
                cur = 0;
                reorient(c, c);
            }
        }
    }
    public static void main(String[] args) { new TestCase1().run(); }
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



}
