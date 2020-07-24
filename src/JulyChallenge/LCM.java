package JulyChallenge;

import java.util.*;


class LCM {

    static class Edge implements Comparable<Edge> {

        static int curr_prime;

        int left, right;
        HashMap<Integer, Integer> B = new HashMap<>();

        HashSet<Integer> primes = new HashSet<>();

        Edge(int a, int b) {
            left = a;
            right = b;
        }

        @Override
        public int compareTo(Edge e) {
            int freq1 = (this.B.get(curr_prime) == null) ? 0 : this.B.get(curr_prime);
            int freq2 = (e.B.get(curr_prime) == null) ? 0 : e.B.get(curr_prime);

            if (freq1 > freq2)
                return 1;
            else if (freq1 == freq2)
                return 0;
            return -1;
        }

        @Override
        public String toString() {
            return "left = " + left + " right = " + right + " B = " + B;
        }
    }

    static class Range {
        int lower = 0, upper = Integer.MAX_VALUE;

        @Override
        public String toString() {
            return "(" + lower + " , " + upper + ")";
        }
    }

    static class Node {

        private HashMap<Integer, Range> map = new HashMap<>();

        public Range getRange(int p) {
            if (map.get(p) == null)
                map.put(p, new Range());

            return map.get(p);
        }

        public void put(int p, int low, int high) {
            Range r = new Range();
            r.lower = low;
            r.upper = high;
            map.put(p, r);
        }

        @Override
        public String toString() {
            return map.toString();
        }

    }

    static class Component {
        List<Integer> nodes = new ArrayList<>();
        Set<Integer> edges = new HashSet<>();

        long count = 0;

        List<Edge> getAllEdges() {
            List<Edge> list = new ArrayList<>();
            for (int i : edges) {
                list.add(LCM.edges[i]);
            }
            return list;
        }

        @Override
        public String toString() {
            return "nodes = " + nodes + " edges = " + edges;
        }

    }

    static final Scanner in = new Scanner(System.in);
    static int N, M;
    static HashMap<Integer, Integer>[] B;
    static int[] X, Y;
    static Edge[] edges;
    static Node[] nodes;
    static List<Integer>[] adj; // .. index of edges

    static List<Component> connected_components;
    static boolean[] visited;
    static Component temp_component;
    static Set<Integer> tot_primes;
    static final int MOD = 1000000007;

    // ..finds all edges and nodes in a connected component
    static void dfs(int node) {
        if (visited[node])
            return;

        temp_component.nodes.add(node);
        visited[node] = true;

        for (int e : adj[node]) {
            Edge edge = edges[e];
            int v = -1;
            if (edge.left != node)
                v = edge.left;
            else
                v = edge.right;

            temp_component.edges.add(e);
            dfs(v);
        }

    }

    public static void main(String[] args) {
        int T = in.nextInt();
        while (T-- > 0) {
            N = in.nextInt();
            M = in.nextInt();

            edges = new Edge[M];
            nodes = new Node[N];
            adj = new ArrayList[N];
            connected_components = new ArrayList<>();
            tot_primes = new HashSet<>();
            visited = new boolean[N];

            for (int i = 0; i < N; i++) {
                nodes[i] = new Node();
                adj[i] = new ArrayList<>();
            }

            B = new HashMap[M];
            X = new int[M];
            Y = new int[M];
            HashSet<Integer> infinite_check_set = new HashSet<>();

            for (int i = 0; i < M; i++) {
                X[i] = in.nextInt() - 1;
                Y[i] = in.nextInt() - 1;

                infinite_check_set.add(X[i]);
                infinite_check_set.add(Y[i]);

                edges[i] = new Edge(X[i], Y[i]);

                adj[X[i]].add(i);
                adj[Y[i]].add(i);

                B[i] = new HashMap<>();
                HashSet<Integer> set = new HashSet<>();

                int r = in.nextInt();

                for (int j = 0; j < r; j++) {

                    int p = in.nextInt();
                    int e = in.nextInt();

                    set.add(p);

                    B[i].putIfAbsent(p, 0);
                    B[i].put(p, B[i].get(p) + e);
                }

                edges[i].B = B[i];
                edges[i].primes = set;
                tot_primes.addAll(set);
            }

            if (infinite_check_set.size() < N) // ..infinite number of combinations
            {
                System.out.println(-1);
                continue;
            }

            // ..finding all connected components
            for (int i = 0; i < N; i++) {
                if (visited[i])
                    continue;
                temp_component = new Component();
                dfs(i);
                connected_components.add(temp_component);
            }

            //printNodes();
            //printEdges();
            //printComponents();
            // System.out.println("total primes = " + tot_primes);

            boolean answer_is_zero = false;

            // System.out.println();
            //System.out.println("starting processing...");
            for (Component c : connected_components) {

                // System.out.println("\tc = " + c);
                for (int p : tot_primes) {

                    //  System.out.println("\t\tp = " + p);
                    Edge.curr_prime = p;
                    PriorityQueue<Edge> Queue = new PriorityQueue<>();

                    Queue.addAll(c.getAllEdges());
                    //System.out.println("\t\tQueue --> " + Queue);

                    while (!Queue.isEmpty()) {
                        Edge edge = Queue.poll();

                        int freq;
                        if (edge.B.get(p) == null)
                            freq = 0;
                        else {
                            freq = edge.B.get(p);
                        }
                        // System.out.println("\t\t\tedge polled is " + edge + " freq of p in this is " + freq);
                        // ..constrain the nodes at the end of this edge
                        int u = edge.left;
                        int v = edge.right;

                        Range ru = nodes[u].getRange(p);
                        Range rv = nodes[v].getRange(p);

                        // System.out.println("\t\t\tu = " + u + " v = " + v + " ru = " + ru + " rv = " + rv);
                        if(u == v){
                            if(ru.upper > freq){
                                ru.upper = freq;
                                rv.lower = freq;
                            }else{
                                answer_is_zero = true;
                                break;
                            }
                            continue;
                        }
                        if (ru.upper < freq) {
                            if (rv.upper >= freq && rv.lower <= freq) {
                                rv.upper = freq;
                                rv.lower = freq;
                            } else {
                                answer_is_zero = true;
                                break;
                            }
                        }

                        if (rv.upper < freq) {
                            if (ru.upper >= freq && ru.lower <= freq) {
                                ru.upper = freq;
                                ru.lower = freq;
                            } else {
                                answer_is_zero = true;
                                break;
                            }
                        }

                        if (ru.upper > freq && rv.upper > freq) {
                            ru.upper = freq;
                            rv.upper = freq;
                        }

                        // System.out.println("\t\t\tnew ru = " + ru + " rv = " + rv);
                    }
                }

                if (answer_is_zero)
                    break;
            }

            if (answer_is_zero) {
                // System.out.println("answer is zero");
                System.out.println(0);
                continue;
            }

            // .. counting the answer for each connected component
            for (Component c : connected_components) {
                List<Long> for_every_prime = new ArrayList<>();
                for (int p : tot_primes) {
                    long val = 0;

                    // ..har node pe jaa jaakr count krra
                    for (int index : c.nodes) {
                        Range range = nodes[index].getRange(p);
                        val += (range.upper - range.lower);
                   //     val %= MOD;
                    }

                    // ..har edge pe ja jakr count krra
                    for (int index : c.edges) {
                        Edge edge = edges[index];

                        Node u = nodes[edge.left];
                        Node v = nodes[edge.right];
                        // if(u == v)
                        //     continue;

                        Range ru = u.getRange(p);
                        Range rv = v.getRange(p);

                        if (ru.upper == rv.upper)
                            val++;
                    }

                 //   val %= MOD;
                    for_every_prime.add(val);
                }

                long temp_count = 1;
                for (long val : for_every_prime) {
                    temp_count = (temp_count*val)%MOD;
                }

                c.count = temp_count;
            }

            long final_ans = 1;

            // .. multiplying the count for each connected component
            for (Component c : connected_components) {
                final_ans = (final_ans*c.count)%MOD;
            }

            System.out.println(final_ans);

        }
    }

    static void printComponents() {
        System.out.println("connected componewnts are...");
        for (Component c : connected_components) {
            System.out.println(c);
        }
    }

    static void printNodes() {
        System.out.println("Nodes...");
        for (int i = 0; i < N; i++) {
            System.out.println(nodes[i]);
        }
    }

    static void printEdges() {
        System.out.println("edges...");
        for (int i = 0; i < M; i++)
            System.out.println(edges[i]);
    }
}