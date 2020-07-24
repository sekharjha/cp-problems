package JulyChallenge;

import java.io.*;
import java.util.*;

class DRGDENTree {
    class HLDecomposer {
        HLDecomposer() {
            heavyChildDFS(0);
            HLD(0, 0);
        }

        int heavyChildDFS(int u) {

            int size = 1;
            int max_child_size = 0;

            for (int v : adj[u]) {
                depth[v] = depth[u] + 1;
                int child_size = heavyChildDFS(v);
                size += child_size;
                if (child_size > max_child_size) {
                    max_child_size = child_size;
                    heavy[u] = v;
                }
            }

            return size;
        }

        void HLD(int u, int h) {
            head[u] = h;
            pos[u] = cur_pos++;

            List<Integer> path = paths.get(h);
            path.add(u);
            paths.put(h, path);

            if (heavy[u] != -1)
                HLD(heavy[u], h);

            for (int child : adj[u]) {
                if (child == heavy[u])
                    continue;
                cur_pos = 0;
                paths.put(child, new ArrayList<>());
                HLD(child, child);
            }
        }

    }

    class LCAWAALA {
        int[][] LCA;

        LCAWAALA() {
            LCA = new int[n + 1][log2(n) + 1];
            for (int i = 0; i < LCA.length; i++) {

                for (int j = 0; j < LCA[i].length; j++) {
                    LCA[i][j] = -1;
                }
            }

            for (int i = 1; i <= n; i++)
                LCA[i][0] = parent[i];

            for (int j = 1; j <= log2(n); j++) {
                for (int i = 1; i <= n; i++) {
                    if (LCA[i][j - 1] != -1) {
                        int par = LCA[i][j - 1];
                        LCA[i][j] = LCA[par][j - 1];
                    }
                }
            }
        }

        int getLCA(int a, int b) {
            // println("\t\ta = " + a + " b = " + b);
            if (depth[a] > depth[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            int d = depth[b] - depth[a];
            // println("\t\td = " + d);

            while (d > 0) {
                int i = log2(d);
                b = LCA[b][i];
                d -= (1 << i);
                // println("\t\t\ti = " + i + " b = " + b + " d = " + d);
            }

            if (a == b)
                return a;
            // println("\t\tNow... second loop...");
            for (int i = log2(n); i >= 0; i--) {
                // println("\t\t\ti = "+i);
                if (LCA[a][i] != -1 && LCA[a][i] != LCA[b][i]) {
                    a = LCA[a][i];
                    b = LCA[b][i];
                }
            }

            return parent[a];
        }
    }

    class Segmentifier {
        Segmentifier() {
            for (int key : paths.keySet()) {
                List<Integer> path = paths.get(key);
                int n = path.size();

                int[] arr = new int[path.size()];
                for (int i = 0; i < path.size(); i++) {
                    arr[i] = path.get(i);
                }

                seg_trees.put(key, new SegmentTree(arr, n));
            }
        }
    }

    int[] parent, heavy, depth, head, pos;
    List<Integer>[] adj;
    int root, cur_pos;

    HashMap<Integer, List<Integer>> paths = new HashMap<>();
    HashMap<Integer, SegmentTree> seg_trees = new HashMap<>();
    LCAWAALA lca;

    DRGDENTree(int[] par, List<Integer>[] ad, int r) {
        parent = par;
        adj = ad;
        root = r;
        cur_pos = 0;
        heavy = new int[adj.length];
        depth = new int[adj.length];
        head = new int[adj.length];
        pos = new int[adj.length];

        Arrays.fill(heavy, -1);
        Arrays.fill(head, -1);

        paths.put(0, new ArrayList<>());
        new HLDecomposer();

        lca = new LCAWAALA();

        new Segmentifier();
    }

    int log2(int x) {
        return (int) Math.floor(Math.log(x) / Math.log(2));
    }

    long query(int a, int b) {
        int lca_node = lca.getLCA(a, b);
        // println("l = " + a + " r = " + b + " lca " + lca);
        if (lca_node != a)
            return -1;

        long res = 0;
        for (; head[a] != head[b]; b = parent[head[b]]) {
            // println("\thead of " + a + " = " + head[a] + " head of " + b + " = " +
            // head[b]);
            if (depth[head[a]] > depth[head[b]]) {
                int temp = a;
                a = b;
                b = temp;
            }
            SegmentTree t = seg_trees.get(head[b]);
            // println("\ttree of " + b + " = " + t);
            long val = t.query(0, 0, t.arr.length - 1, pos[head[b]], pos[b]);
            // println("\tquery from " + pos[head[b]] + " to " + pos[b] + " val = " + val);
            res += val;
        }

        if (depth[a] > depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        SegmentTree t = seg_trees.get(head[b]);
        // println("\ttree of " + b + " = " + t);
        long val = t.query(0, 0, t.arr.length - 1, pos[a], pos[b]);
        // println("\tquery from " + pos[head[b]] + " to " + pos[b] + " val = " + val);
        res += val;
        return res;
    }

    void update(int node, int new_val) {
        SegmentTree tree = seg_trees.get(head[node]);
        // println("before update == " + tree);
        tree.update(0, 0, tree.arr.length - 1, pos[node], new_val);
        seg_trees.put(head[node], tree);
        // println("after update == " + tree);
    }

    static final Reader in = new Reader();
    static int n, q;
    static int[] a, h;
    static Stack<Integer> stack;
    static int[] parentBeta, parentalpha;
    static List<Integer>[] adjAlpha, adjBeta;
    static StringBuilder output = new StringBuilder();

    static DRGDENTree alphaTree, BetaTree;

    static void solve() throws Exception{
        takeHArray();

        takeAArray();


        // ..creating
        constructBetaTreee();

        // ..creating TreeL
        constructAlphaTree();


        StartQuery();
    }

    static void input() throws Exception {
        n = in.nextInt();
        q = in.nextInt();
    }

    static boolean NISONE() throws Exception {
        if (n != 1)
            return false;
        int A = in.nextInt();
        int cost = in.nextInt();
        for (int i = 0; i < q; i++) {
            int val = in.nextInt();
            int l = in.nextInt();
            int r = in.nextInt();
            if (val == 1) {
                cost = r;
            } else {
                println(cost);
            }
        }
        return true;
    }

    static void initialiseEveryThing(){
        a = new int[n + 1];
        h = new int[n + 1];

        parentBeta = new int[n + 1];
        parentalpha = new int[n + 1];
        stack = new Stack<>();
        adjAlpha = new ArrayList[n + 1];
        adjBeta = new ArrayList[n + 1];

        adjAlpha[0] = new ArrayList<>();
        adjBeta[0] = new ArrayList<>();
        parentalpha[0] = -1;
        parentBeta[0] = -1;
    }

    static void takeHArray() throws Exception{
        for (int i = 1; i <= n; i++) {
            h[i] = in.nextInt();
            adjAlpha[i] = new ArrayList<>();
            adjBeta[i] = new ArrayList<>();

        }
    }

    static void takeAArray() throws Exception{
        for (int i = 1; i <= n; i++)
            a[i] = in.nextInt();
    }

    static void constructBetaTreee(){
        stack = new Stack<>();
        for (int i = 1; i <= n; i++) {

            while (!stack.isEmpty() && h[stack.peek()] < h[i]) {
                int u = stack.pop();
                parentBeta[u] = i;
                adjBeta[i].add(u);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int u = stack.pop();
            parentBeta[u] = 0;
            adjBeta[0].add(u);
        }
        BetaTree = new DRGDENTree(parentBeta, adjBeta, 0);
    }
    void sieveOfEratosthenes(int n)
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

    static void constructAlphaTree(){
        stack = new Stack<>();
        for (int i = n; i > 0; i--) {

            while (!stack.isEmpty() && h[stack.peek()] < h[i]) {
                int u = stack.pop();
                parentalpha[u] = i;
                adjAlpha[i].add(u);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int u = stack.pop();
            parentalpha[u] = 0;
            adjAlpha[0].add(u);
        }
        alphaTree = new DRGDENTree(parentalpha, adjAlpha, 0);
    }

    public static void main(String[] args) throws Exception {
        input();
        if (NISONE())
            return;


        initialiseEveryThing();
        solve();

        System.out.println(output);
    }
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
    static void StartQuery() throws Exception{
        while (q-- > 0) {
            int type = in.nextInt();
            int l = in.nextInt();
            int r = in.nextInt();

            if (type == 1) {
                // ..update
                alphaTree.update(l, r);
                BetaTree.update(l, r);
                a[l] = r;
                continue;
            }

            if (l == r) {
                println(a[r]);
                continue;
            } else if (h[l] <= h[r] && l != r) {
                println(-1);
                continue;
            }
            if (l > r) {
                println(BetaTree.query(l, r));
            } else if (r > l) {
                println(alphaTree.query(l, r));
            }

        }
    }

    static void print(Object value) {
        output.append(value);
    }

    static void println(Object value) {
        output.append(value).append("\n");
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    static class SegmentTree {
        long st[];
        int arr[];

        SegmentTree(int arr[], int n) {
            this.arr = arr;

            initialiseST();

            buildTree(0, 0, n - 1);
        }

        void initialiseST() {
            int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
            int max_size = 2 * (int) Math.pow(2, x) - 1;
            st = new long[max_size];
        }

        void buildTree(int i, int l, int r) {
            if (l == r)
                st[i] = a[arr[l]];
            else {
                int mid = (l + r) / 2;
                buildTree(2 * i + 1, l, mid);
                buildTree(2 * i + 2, mid + 1, r);

                st[i] = st[2 * i + 1] + st[2 * i + 2];
            }
        }

        long query(int curr, int curr_l, int curr_r, int query_l, int query_r) {

            if (query_l > curr_r || query_r < curr_l)
                return 0;

            if (curr_l >= query_l && curr_r <= query_r)
                return st[curr];

            int mid = (curr_l + curr_r) / 2;

            long ans_l = query(2 * curr + 1, curr_l, mid, query_l, query_r);
            long ans_r = query(2 * curr + 2, mid + 1, curr_r, query_l, query_r);

            return ans_l + ans_r;
        }

        void update(int curr, int curr_l, int curr_r, int pos, int val) {
            if (curr_l == curr_r)
                st[curr] = val;
            else {
                int mid = (curr_l + curr_r) / 2;

                if (pos <= mid)
                    update(2 * curr + 1, curr_l, mid, pos, val);
                else
                    update(2 * curr + 2, mid + 1, curr_r, pos, val);

                st[curr] = st[2 * curr + 1] + st[2 * curr + 2];
            }
        }

        @Override
        public String toString() {
            String toRet = "[";
            for (int i = 0; i < st.length; i++) {
                toRet += st[i] + " ";
            }
            toRet += "]";
            return toRet;
        }

    }
    static int getXOR(int BITree[], int index)
    {
        int ans = 0;
        index += 1;

        // Traverse ancestors
        // of BITree[index]
        while (index > 0)
        {

            // XOR current element
            // of BIT to ans
            ans ^= BITree[index];

            // Update index to that
            // of the parent node in
            // getXor() view by
            // subtracting LSB(Least
            // Significant Bit)
            index -= index & (-index);
        }
        return ans;
    }

}
