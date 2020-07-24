package beginerCodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class SinglePointOfFailure {

    InputStream is;
    PrintWriter o;
    public static void main(String[] args) { new SinglePointOfFailure().run(); }
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

    static class Node {
        int index, color = 0;
        boolean abandoned = false, visited = false;

        Set<Node> edges = new HashSet<>();

        String printEdges() {
            String s = "[ ";
            for (Node n : this.edges) {
                s += n.index + " , ";
            }
            s += " ]";
            return s;
        }

        static int findMinimuNode(Set<Node> nodes) {
            int min = Integer.MAX_VALUE;
            for (Node node : nodes)
                if (node.index < min)
                    min = node.index;

            return min;
        }
    }

    static Node[] nodes;
    static int colour;
    static List<Node> path_coloring;
    static HashSet<Node> first_cycle;
    static boolean gotOneCycle, gotAnotherCycle;
    static List<Node> nodes_from_various_graph_components;


    //..asli drama variables
    static boolean found_a_cycle, print_minus_one;
    static Set<Node> point_of_failures;
    static Node current_cycle_node;

    void solve(){

        int T = ni();
        while (T-- > 0) {
            colour = 1;
            path_coloring = new ArrayList<>();
            point_of_failures = new HashSet<>();
            nodes_from_various_graph_components = new ArrayList<>();
            first_cycle = new HashSet<>();
            gotOneCycle = false;
            gotAnotherCycle = false;
            print_minus_one = false;

            int N = ni(), M = ni();
            nodes = new Node[N];

            for (int i = 0; i < N; i++) {
                nodes[i] = new Node();
                nodes[i].index = i;
            }

            while (M-- > 0) {
                int from = ni() - 1;
                int to = ni() - 1;

                nodes[from].edges.add(nodes[to]);
                nodes[to].edges.add(nodes[from]);
            }

            //...finding entry points of each unconnected components of the input graph using DFS
            for (Node node : nodes) {
                if (node.visited)
                    continue;

                nodes_from_various_graph_components.add(node);
                DFS_FindingComponents(node);
            }
//            System.out.println("no. of unconnected components are "+nodes_from_various_graph_components.size());

            //...DFS on each component of the graph to find a cycle, if we get a cycle then we break
            for (Node node : nodes_from_various_graph_components) {
                if (gotOneCycle)
                    break;
                node.color = 1;
//                System.out.println("started graph colouring......");
                GraphColouring(node, null);
//                System.out.println("############################finally cycle is " + firstCyclePrint()+" ###########################################");
            }

            //...it there is no cycle its all screwed
            if (!gotOneCycle) {
                o.println(-1);
                continue;
            }

            //...many nodes still coloured from last cycle finding DFS which are not part of the cycle, needed to be cleaned up
            cleanUselessColours();

            for (int i = 0; i < nodes.length; i++)
                if (nodes[i].color == 0) {
                    nodes[i].color = 2;
                    path_coloring.clear();
//                    System.out.println("started graph colouring again......");
                    GraphColouringPhirSe(nodes[i], null);
                    if (gotAnotherCycle)
                        break;
                }

            //...if there exists another cycle again screwed dude
            if (gotAnotherCycle) {
//                System.out.println("#######################got another cycle#######################");
                o.println(-1);
                continue;
            }
//            printTheGraph();
//            System.out.println("##############################################################################################################################################################");

            //....Asli drama
            point_of_failures.addAll(first_cycle);
            for (Node node : first_cycle) {
//                System.out.println("cycle node = "+node.index+" starting DFS........");
                current_cycle_node = node;
                found_a_cycle = false;
                DFS_MAIN(node, null);
//                System.out.println("after dfs poinst of failure = "+point_of_failures);

                if (point_of_failures.size() == 0) {
                    break;
                }

            }

            if (point_of_failures.size() == 0) {
                o.println(-1);
            } else {

                o.println(Node.findMinimuNode(point_of_failures)+1);
            }
        }
    }

    private static void cleanUselessColours() {
        for (int i = 0; i < nodes.length; i++)
            if (!first_cycle.contains(nodes[i]))
                nodes[i].color = 0;
    }

    static void GraphColouring(Node node, Node previous_node) {
//        System.out.println();
//        System.out.println("\tat node " + node.index + " connected to " + node.printEdges());
        if (gotOneCycle)
            return;

        path_coloring.add(node);
//        System.out.println("\t\tadded this node to path_colouring = " + pathColouringPrint());
        for (Node n : node.edges) {
            if (n == previous_node) {
//                System.out.println("\t\tskipping " + n.index+" with color "+n.color);
                continue;
            }

            if (n.color == node.color) {
//                System.out.println("\t\t\tgot the cycle at " + n.index + " path_coloring = " + pathColouringPrint());

                gotOneCycle = true;
                getCycle(n);
                return;
            } else {
                n.color = node.color;

                GraphColouring(n, node);

                if (gotOneCycle)
                    return;
            }
        }
        path_coloring.remove(path_coloring.size() - 1);

    }

    static void GraphColouringPhirSe(Node node, Node previous_node) {
//        System.out.println();
//        System.out.println("\tat node " + node.index + " connected to " + node.printEdges());
        if (gotAnotherCycle)
            return;

        path_coloring.add(node);
//        System.out.println("\t\tadded this node to path_colouring = " + pathColouringPrint());
        for (Node n : node.edges) {

            if (n == previous_node || n.color == 1) {
//                System.out.println("\t\tskipping " + n.index+" with node colour = "+n.color);
                continue;
            }

            if (n.color == node.color) {
//                System.out.println("\t\t\tgot the cycle at " + n.index + " path_coloring = " + pathColouringPrint());

                gotAnotherCycle = true;
                return;
            } else {
                n.color = node.color;
                GraphColouringPhirSe(n, node);
                if (gotAnotherCycle)
                    return;
            }
        }

        path_coloring.remove(path_coloring.size() - 1);
    }

    static void getCycle(Node n) {
        int i;
        for (i = 0; i < path_coloring.size(); i++) {
            if (path_coloring.get(i).index == n.index)
                break;
        }
        int j = i;
        for (; i < path_coloring.size(); i++) {
            Node from = path_coloring.get(i);
            Node to;
            if(i == path_coloring.size() -1)
                to = path_coloring.get(j);
            else
                to = path_coloring.get((i + 1));

//            System.out.println("\t\t\tremoving edges between "+from.index+" and "+to.index);
            from.edges.remove(to);
            to.edges.remove(from);

            first_cycle.add(from);
        }
    }

    static void DFS_FindingComponents(Node node) {
        node.visited = true;

        for (Node n : node.edges) {
            if (n.visited)
                continue;

            DFS_FindingComponents(n);
        }
    }

    static void DFS_MAIN(Node node, Node previous_node) {
        if (node.edges.size() == 1 && !first_cycle.contains(node)) {
            node.abandoned = true;
            return;
        }

        List<Node> remove_edges = new ArrayList<>();
        for (Node n : node.edges) {
            if (n.abandoned) {
                remove_edges.add(n);
            }
        }
        node.edges.removeAll(remove_edges);

        for (Node n : node.edges) {
            if (n == previous_node)
                continue;
            if (n.color == 1) {
//                System.out.println("got cycle at "+n.index);
                Set<Node> pof = new HashSet<>();
                pof.add(n);
                pof.add(current_cycle_node);

                point_of_failures.retainAll(pof);
                found_a_cycle = true;
            } else
                DFS_MAIN(n, node);
        }
    }

    static String pathColouringPrint() {
        String s = "[ ";
        for (Node n : path_coloring) {
            s += n.index + " , ";
        }
        s += " ]";
        return s;
    }

    static String firstCyclePrint() {
        String s = "[ ";
        for (Node n : first_cycle) {
            s += n.index + " , ";
        }
        s += " ]";
        return s;
    }

    static void printTheGraph() {
        System.out.println("############################################################GRAPH IS##################################################################");
        for (int i = 0; i < nodes.length; i++) {
            System.out.println("\tnode: " + nodes[i].index + " edges = " + nodes[i].printEdges());
        }
        System.out.println("########################################################################################################################################");
    }

}
/*
9 9
        8 1
        1 6
        9 1
        2 1
        3 2
        5 6
        2 4
        6 7
        4 5

13 14
        1 2
        2 3
        3 4
        4 6
        4 7
        5 6
        3 5
        7 8
        6 10
        5 9
        10 11
        11 12
        11 13
        12 13


5 5
        5 1
        5 2
        1 2
        2 3
        2 4
5 6
        4 5
        4 1
        4 2
        4 3
        5 1
        5 2
5 5
        3 4
        3 5
        3 1
        3 2
        4 2
4 1
        3 4
6 6
        1 2
        2 3
        3 1
        4 5
        5 6
        6 4

12 13
3 4
3 8
3 6
4 1
10 8
10 5
1 9
1 2
2 6
9 12
12 11
11 7
7 6

14 18
1 4
1 9
1 2
4 10
10 5
10 8
8 3
14 13
13 9
12 9
12 11
11 3
11 7
6 7
6 3
2 6
4 3
8 14*/
