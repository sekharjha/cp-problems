package JulyChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class Dragon {
    public static void main(String[] args) { new Dragon().run(); }
    void run() {
        is = System.in;
        o = new PrintWriter(System.out);
        solve();
        o.flush();
    }
    InputStream is;
    PrintWriter o;
    static int max_ref; // stores the LIS

    /* To make use of recursive calls, this function must return
    two things:
    1) Length of LIS ending with element arr[n-1]. We use
       max_ending_here for this purpose
    2) Overall maximum as the LIS may end with an element
       before arr[n-1] max_ref is used this purpose.
    The value of LIS of full array of size n is stored in
    *max_ref which is our final result */
    static int _lis(int arr[], int n)
    {
        // base case
        if (n == 1)
            return 1;

        // 'max_ending_here' is length of LIS ending with arr[n-1]
        int res, max_ending_here = 1;

        /* Recursively get all LIS ending with arr[0], arr[1] ...
           arr[n-2]. If   arr[i-1] is smaller than arr[n-1], and
           max ending with arr[n-1] needs to be updated, then
           update it */
        for (int i = 1; i < n; i++)
        {
            res = _lis(arr, i);
            if (arr[i-1] < arr[n-1] && res + 1 > max_ending_here)
                max_ending_here = res + 1;
        }

        // Compare max_ending_here with the overall max. And
        // update the overall max if needed
        if (max_ref < max_ending_here)
            max_ref = max_ending_here;

        // Return length of LIS ending with arr[n-1]
        return max_ending_here;
    }

    // The wrapper function for _lis()
    static int lis(int arr[], int n)
    {
        // The max variable holds the result
        max_ref = 1;

        // The function _lis() stores its result in max
        _lis( arr, n);

        // returns max
        return max_ref;
    }

    void HLD(int n)
    {
        // Create a boolean array "prime[0..n]" and initialize
        // all entries it as true. A value in prime[i] will
        // finally be false if i is Not a prime, else true.
        boolean prime[] = new boolean[n+1];
        for(int i=0;i<n;i++)
            prime[i] = true;

        for(int p = 2; p*p <=n; p++)
        {
            // If prime[p] is not changed, then it is a prime
            if(prime[p] == true)
            {
                // Update all multiples of p
                for(int i = p*p; i <= n; i += p)
                    prime[i] = false;
            }
        }

        // Print all prime numbers
        for(int i = 2; i <= n; i++)
        {
            if(prime[i] == true)
                System.out.print(i + " ");
        }
    }

    void solve() {
        N = ni();
        Query = ni();
        if (N == 1) {
            ni();
            int delicacy = ni();
            for (int i = 0; i < Query; i++) {
                int val = ni();
               ni();
                int r = ni();
                if (val == 1) {
                    delicacy = r;
                } else {
                    o.println(delicacy);
                }
            }
        } else {
            linearValues = new int[N + 1];
            heights = new int[N + 1];

            valuesOfStacks = new Stack<>();
            sideLeft = new ArrayList[N + 1];
            sideRigth = new ArrayList[N + 1];
            OnRight = new int[N + 1];
            OnLeft = new int[N + 1];

            sideLeft[0] = new ArrayList<>();
            sideRigth[0] = new ArrayList<>();
            OnLeft[0] = -1;
            OnRight[0] = -1;

            for (int i = 1; i <= N; i++) {
                heights[i] = ni();
                sideLeft[i] = new ArrayList<>();
                sideRigth[i] = new ArrayList<>();

            }

            for (int i = 1; i <= N; i++) {
                linearValues[i] = ni();
            }

            valuesOfStacks = new Stack<>();
            for (int i = 1; i <= N; i++) {

                while (!valuesOfStacks.isEmpty() && heights[valuesOfStacks.peek()] < heights[i]) {
                    int u = valuesOfStacks.pop();
                    OnRight[u] = i;
                    sideRigth[i].add(u);
                }
                valuesOfStacks.push(i);
            }

            while (!valuesOfStacks.isEmpty()) {
                int u = valuesOfStacks.pop();
                OnRight[u] = 0;
                sideRigth[0].add(u);
            }

            valuesOfStacks = new Stack<>();
            for (int i = N; i > 0; i--) {

                while (!valuesOfStacks.isEmpty() && heights[valuesOfStacks.peek()] < heights[i]) {
                    int u = valuesOfStacks.pop();
                    OnLeft[u] = i;
                    sideLeft[i].add(u);
                }
                valuesOfStacks.push(i);
            }

            while (!valuesOfStacks.isEmpty()) {
                int u = valuesOfStacks.pop();
                OnLeft[u] = 0;
                sideLeft[0].add(u);
            }

            SideData sideDataL = new SideData(OnLeft, sideLeft, 0);
            SideData sideDataR = new SideData(OnRight, sideRigth, 0);
            int k=2;
            while (Query-- > 0) {
                int type =ni();
                int l = ni();
                int r = ni();

                if (type != k) {
                    sideDataL.modify(l, r);
                    sideDataR.modify(l, r);
                    linearValues[l] = r;

                } else {
                    if(l==r){
                        o.println(linearValues[r]);
                        continue;
                    }
                    else if (heights[l] <= heights[r]) {
                        o.println(-1);
                        continue;
                    }
                    if (l > r) {

                        o.println(sideDataR.fillIn(l, r));
                    } else{
                        o.println(sideDataL.fillIn(l, r));
                    }
                }
            }

        }
    }

    static class SideData {

        int[] ancestoes, weights, deep, top, idx;
        List<Integer>[] adjoinedList;
        int root, cIdx;

        HashMap<Integer, List<Integer>> ways = new HashMap<>();
        HashMap<Integer, TheSecondTree> deepTree = new HashMap<>();

        int[][] LcommonAncestor;

        SideData(int[] parted, List<Integer>[] arra, int r) {
            cIdx = 0;
            root = r;
            ancestoes = parted;
            adjoinedList = arra;
            weights = new int[arra.length];
            deep = new int[arra.length];
            top = new int[arra.length];
            idx = new int[arra.length];
            LcommonAncestor = new int[N + 1][logBase2(N) + 1];
            Arrays.fill(weights, -1);
            Arrays.fill(top, -1);

            generateOutput(0); // ..n

            ways.put(0, new ArrayList<>());
            primes(0, 0); // ..n

            findData();

            convertpathsToSegmentTrees(); // .. nlogn
        }

        int logBase2(int x) {
            return (int) Math.floor(Math.log(x) / Math.log(2));
        }

        void findData() {

            for (int i = 0; i < LcommonAncestor.length; i++) {

                for (int j = 0; j < LcommonAncestor[i].length; j++) {
                    LcommonAncestor[i][j] = -1;
                }

            }

            for (int i = 1; i <= N; i++)
                LcommonAncestor[i][0] = ancestoes[i];

            for (int j = 1; j <= logBase2(N); j++) {
                for (int i = 1; i <= N; i++) {
                    if (LcommonAncestor[i][j - 1] != -1) {
                        int par = LcommonAncestor[i][j - 1];
                        LcommonAncestor[i][j] = LcommonAncestor[par][j - 1];
                    }
                }
            }
        }

        int computeLCA(int a, int b) {
            if (deep[a] > deep[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            int d = deep[b] - deep[a];

            while (d > 0) {
                int i = logBase2(d);
                b = LcommonAncestor[b][i];
                d -= (1 << i);
            }

            if (a == b)
                return a;
            for (int i = logBase2(N); i >= 0; i--) {
                if (LcommonAncestor[a][i] != -1 && LcommonAncestor[a][i] != LcommonAncestor[b][i]) {
                    a = LcommonAncestor[a][i];
                    b = LcommonAncestor[b][i];
                }
            }

            return ancestoes[a];
        }


        void convertpathsToSegmentTrees() {
            for (int key : ways.keySet()) {
                List<Integer> wayToFInd = ways.get(key);
                int n = wayToFInd.size();

                int[] arr = new int[wayToFInd.size()];
                for (int i = 0; i < wayToFInd.size(); i++) {
                    arr[i] = wayToFInd.get(i);
                }

                deepTree.put(key, new TheSecondTree(arr, n));
            }
        }

        int generateOutput(int u) {

            int size = 1;
            int max_child_size = 0;

            for (int v : adjoinedList[u]) {
                deep[v] = deep[u] + 1;
                int child_size = generateOutput(v);
                size += child_size;
                if (child_size > max_child_size) {
                    max_child_size = child_size;
                    weights[u] = v;
                }
            }

            return size;
        }

        void primes(int u, int h) {
            top[u] = h;
            idx[u] = cIdx++;

            findHLD(u, h);

            if (weights[u] != -1)
                primes(weights[u], h);

            for (int child : adjoinedList[u]) {
                if (child == weights[u])
                    continue;
                cIdx = 0;
                ways.put(child, new ArrayList<>());
                primes(child, child);
            }
        }

        void findHLD(int u, int h) {
            List<Integer> path = ways.get(h);
            path.add(u);
            ways.put(h, path);
        }

        long fillIn(int a, int b) {
            int lca = computeLCA(a, b);
            if (lca != a)
                return -1;

            long res = 0;
            for (; top[a] != top[b]; b = ancestoes[top[b]]) {
                if (deep[top[a]] > deep[top[b]]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                TheSecondTree t = deepTree.get(top[b]);
                long val = t.ask(0, 0, t.arr.length - 1, idx[top[b]], idx[b]);
                res += val;
            }

            if (deep[a] > deep[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            TheSecondTree t = deepTree.get(top[b]);
            long val = t.ask(0, 0, t.arr.length - 1, idx[a], idx[b]);
            res += val;
            return res;
        }

        void modify(int node, int new_val) {
            TheSecondTree tree = deepTree.get(top[node]);
            tree.modify(0, 0, tree.arr.length - 1, idx[node], new_val);
            deepTree.put(top[node], tree);
        }

    }

    static class TheSecondTree {
        long tree[];
        int arr[];

        TheSecondTree(int arr[], int n) {
            this.arr = arr;

            int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));

            int max_size = 2 * (int) Math.pow(2, x) - 1;

            tree = new long[max_size];

            formation(0, 0, n - 1);
        }

        void formation(int i, int l, int r) {
            if (l == r)
                tree[i] = linearValues[arr[l]];
            else {
                int mid = (l + r) / 2;
                formation(2 * i + 1, l, mid);
                formation(2 * i + 2, mid + 1, r);

                tree[i] = tree[2 * i + 1] + tree[2 * i + 2];
            }
        }

        long ask(int curr, int curr_l, int curr_r, int query_l, int query_r) {
            if (query_l > curr_r || query_r < curr_l)
                return 0;
            if (curr_l >= query_l && curr_r <= query_r)
                return tree[curr];

            int mid = (curr_l + curr_r) / 2;

            long ans_l = ask(2 * curr + 1, curr_l, mid, query_l, query_r);
            long ans_r = ask(2 * curr + 2, mid + 1, curr_r, query_l, query_r);

            return ans_l + ans_r;
        }

        void modify(int curr, int curr_l, int curr_r, int pos, int val) {
            if (curr_l == curr_r)
                tree[curr] = val;
            else {
                int mid = (curr_l + curr_r) / 2;

                if (pos <= mid)
                    modify(2 * curr + 1, curr_l, mid, pos, val);
                else
                    modify(2 * curr + 2, mid + 1, curr_r, pos, val);

                tree[curr] = tree[2 * curr + 1] + tree[2 * curr + 2];
            }
        }

    }
    static int N;
    static int Query;
    static int[] linearValues;
    static int[] heights;
    static Stack<Integer> valuesOfStacks;
    static int[] OnRight;
    static int[] OnLeft;
    static List<Integer>[] sideLeft;
    static List<Integer>[] sideRigth;


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
