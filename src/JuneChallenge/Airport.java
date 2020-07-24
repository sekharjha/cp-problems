package JuneChallenge;

import java.util.*;

class Airport {

    static final Scanner in = new Scanner(System.in);

    static class Node {
        List<Edge> edges = new ArrayList<>();
        HashSet<Edge> bridges = new HashSet<>();

        @Override
        public String toString() {
            return "{ edges = " + edges + " bridges = " + bridges + " }";
        }
    }

    static class Edge {
        int u, v, id;

        Edge(int u, int v, int id) {
            this.u = u;
            this.v = v;
            this.id = id;
        }

        @Override
        public String toString() {
            return "{ u = " + u + " v = " + v + " }";
        }

        @Override
        public int hashCode() {
            // TODO Auto-generated method stub
            int max = Math.max(u, v);
            int min = Math.min(u, v);

            return max * 10 + min;
        }

        @Override
        public boolean equals(Object obj) {
            // TODO Auto-generated method stub
            super.equals(obj);
            if (this.id == ((Edge) obj).id) {
                return true;
            }
            return false;
        }
    }

    static class Component implements Comparable<Component>{
        List<Integer> nodes = new ArrayList<>();
        List<Edge> bridges = new ArrayList<>();
        Set<Edge> non_bridgeEdges = new HashSet<>();

        @Override
        public String toString() {
            return "{\n\t nodes --> " + nodes + "\n\tbridges --> " + bridges + "\n\tnon_bridgeEdges --> "
                    + non_bridgeEdges + " \n}";
        }

        @Override
        public int compareTo(Component o) {
            // TODO Auto-generated method stub
            return o.non_bridgeEdges.size() - this.non_bridgeEdges.size();
        }
    }

    static void dfs(int u) {
        // System.out.println("\tu = "+u);
        temp_component.nodes.add(u);
        vis[u] = true;
        for (Edge edge : nodes[u].edges) {
            int v;
            if (edge.u != u)
                v = edge.u;
            else
                v = edge.v;
            // System.out.println("\t\tu = "+u+" edge = "+edge+" vis[v] = "+vis[v]);

            if (!vis[v])
                dfs(v);
        }
        x[u] = nodes[u].edges.size();
    }

    static void IS_BRIDGE(int u, int v, Edge bridge) {
        nodes[v].bridges.add(bridge);
        nodes[u].bridges.add(bridge);
        temp_component.bridges.add(bridge);
    }

    static void findBridges(int u, Edge parentEdge) {
        // System.out.println("\tfindBridges() u = "+u+" low[u] = "+low[u]+" enter[u] =
        // "+enter[u]);
        vis[u] = true;
        enter[u] = low[u] = timer;
        timer++;

        int par;
        if (parentEdge.u == u)
            par = parentEdge.v;
        else
            par = parentEdge.u;

        for (Edge edge : nodes[u].edges) {
            int v;
            if (edge.u != u)
                v = edge.u;
            else
                v = edge.v;

            if (edge.equals(parentEdge)) {
                continue;
            }
            // System.out.println("\t\tv = "+v+" low[v] = "+low[v]+" enter[v] = "+enter[v]);
            boolean isBridge = false;
            if (vis[v]) {
                // System.out.println("\t\t"+v+" is already visisted");
                low[u] = Math.min(low[u], enter[v]);
            } else {
                // System.out.println("\t\t"+v+" is not visisted");
                findBridges(v, edge);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > enter[u]) {
                    // System.out.println("\t\t"+edge+" is a bridge");
                    IS_BRIDGE(u, v, edge);
                    isBridge = true;
                }
            }

            if (!isBridge)
                temp_component.non_bridgeEdges.add(edge);
        }
    }

    static int N, M, timer, m;
    static Node[] nodes;
    static boolean[] vis;
    static Component temp_component;
    static Queue<Component> connected_components;
    static List<Component> singlenode_component;
    static int[] d, x, enter, low;

    public static void main(String[] args) {
        int T = in.nextInt();
        while (T-- > 0) {
            N = in.nextInt();
            M = in.nextInt();
            nodes = new Node[N];
            vis = new boolean[N];
            d = new int[N];
            x = new int[N];
            enter = new int[N];
            low = new int[N];
            connected_components = new PriorityQueue<>();
            singlenode_component = new ArrayList<>();

            for (int i = 0; i < N; i++)
                nodes[i] = new Node();

            for (m = 0; m < M; m++) {
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                // System.out.println("a = "+a+" b = "+b);
                Edge edge = new Edge(a, b, m);
                nodes[a].edges.add(edge);
                nodes[b].edges.add(edge);
            }

            for (int i = 0; i < N; i++) {
                d[i] = nodes[i].edges.size();
                x[i] = d[i];
            }

            for (int i = 0; i < N; i++) {

                if (!vis[i]) {
                    temp_component = new Component();
                    dfs(i);
                    vis = new boolean[N];
                    // System.out.println("\t$$$$$$$$$$$$$$$finding bridge of new component with node "
                    //     + temp_component.nodes.get(0) + " $$$$$$$$$$$$$$$$$$$$$$$");
                    findBridges(temp_component.nodes.get(0), new Edge(-1, -1, -1));
                    // System.out.println("\tthus compoent is " + temp_component);
                    if (temp_component.nodes.size() == 1)
                        singlenode_component.add(temp_component);
                    else
                        connected_components.add(temp_component);
                }
            }

            // printAllComponents();
            handleSingleNodes();


            while (connected_components.size() > 1) {
                int u1 = -1;
                // printAllComponents();
                Component comp1 = connected_components.poll();
                Component comp2 = connected_components.poll();
                // System.out.println("\t********components at head********");
                // System.out.println("\tcomp1 = " + comp1 + "\n\t comp2 = " + comp2);

                if (comp1.non_bridgeEdges.size() == 0 && comp2.non_bridgeEdges.size() == 0) {
                    // System.out.println("\tboth have only bridges");
                    u1 = comp1.nodes.get(0);
                    int u2 = comp2.nodes.get(0);

                    // .. break one bridge and connect (u1,u2) and (v1,v2)
                    // .. you need to disconnect u2 and v2 so remove there connections as node
                    // instance

                    Edge edge = new Edge(u1, u2, m);
                    m++;
                    nodes[u1].edges.add(edge);
                    nodes[u2].edges.add(edge);
                    // System.out.println("\tconnecting u1 = "+u1+" u2 = "+u2);

                    // } else if (comp1.non_bridgeEdges.size() == 0 || comp2.non_bridgeEdges.size()
                    // == 0) {
                    // System.out.println("\tone has only bridges");
                    // Component temp_comp = null;
                    // if (comp1.non_bridgeEdges.size() > 0)
                    // temp_comp = comp1;
                    // else
                    // temp_comp = comp2;
                    // Edge e = temp_comp.non_bridgeEdges.iterator().next();

                    // u1 = e.u;
                    // int v1 = e.v;

                    // nodes[u1].edges.remove(e);
                    // nodes[v1].edges.remove(e);

                    // int u2 = comp1.nodes.get(0);

                    // System.out.println("\t\tu1 = " + u1 + " v1 = " + v1 + " u2 = " + u2);

                    // Edge e1 = new Edge(u1, u2,m);
                    // m++;
                    // Edge e2 = new Edge(v1, u2,m);
                    // m++;
                    // nodes[u1].edges.add(e1);
                    // nodes[u2].edges.add(e1);
                    // nodes[v1].edges.add(e2);
                    // nodes[u2].edges.add(e2);

                } else if (comp1.non_bridgeEdges.size() >= 0 && comp2.non_bridgeEdges.size() >= 0) {
                    // System.out.println("\tatleast one hast non bridge edge");
                    Edge e1;
                    Edge e2;
                    if (comp1.non_bridgeEdges.size() == 0)
                        e1 = comp1.bridges.iterator().next();
                    else
                        e1 = comp1.non_bridgeEdges.iterator().next();
                    if (comp2.non_bridgeEdges.size() == 0)
                        e2 = comp2.bridges.iterator().next();
                    else
                        e2 = comp2.non_bridgeEdges.iterator().next();

                    u1 = e1.u;
                    int v1 = e1.v, u2 = e2.u, v2 = e2.v;
                    nodes[u1].edges.remove(e1);
                    nodes[v1].edges.remove(e1);
                    nodes[u2].edges.remove(e2);
                    nodes[v2].edges.remove(e2);
                    // System.out.println("\te1 = " + e1 + " e2 = " + e2);
                    e1 = new Edge(u1, u2, m);
                    m++;
                    e2 = new Edge(v1, v2, m);
                    m++;
                    nodes[u1].edges.add(e1);
                    nodes[u2].edges.add(e1);

                    nodes[v1].edges.add(e2);
                    nodes[v2].edges.add(e2);
                }

                vis = new boolean[N];
                temp_component = new Component();
                // System.out.println("\tdfs().. to merge comp1 and comp2");
                dfs(u1);

                // System.out.println("\tmerged component is " + temp_component);
                vis = new boolean[N];
                findBridges(u1, new Edge(-1, -1, -1));
                connected_components.add(temp_component);
                // System.out.println("\tafter finding bridges " + temp_component);

            }

            temp_component = connected_components.poll();
            // System.out.println("final component....." + temp_component);

            M = temp_component.bridges.size() + temp_component.non_bridgeEdges.size();
            System.out.println(getSUM_DIFF() + " " + M);
            for (Edge edge : temp_component.bridges)
                System.out.println((edge.u + 1) + " " + (edge.v + 1));
            for (Edge edge : temp_component.non_bridgeEdges)
                System.out.println((edge.u + 1) + " " + (edge.v + 1));

        }
    }

    static void handleSingleNodes() {
        Node node;
        int index_of_node;
        Component cluster_component;

        if (connected_components.size() == 0) {
            cluster_component = singlenode_component.get(0);
            index_of_node = cluster_component.nodes.get(0);
            node = nodes[index_of_node];
            connected_components.add(cluster_component);
        } else {
            cluster_component = connected_components.iterator().next();
            index_of_node = cluster_component.nodes.get(0);
            node = nodes[index_of_node];
        }

        // .. if all clusters contain only one node
        // System.out.println("the cluster is " + cluster_component.nodes + " and out node is " + index_of_node);
        for (int i = 1; i < singlenode_component.size(); i++) {
            int index_of_singleNode = singlenode_component.get(i).nodes.get(0);
            Node singleNode = nodes[index_of_singleNode];
            Edge edge = new Edge(index_of_singleNode, index_of_node, m);
            m++;
            // ..coneected the nodes
            node.edges.add(edge);
            singleNode.edges.add(edge);

            // ..adding node to the cluster
            cluster_component.nodes.add(index_of_singleNode);
            // ..adding the edge betweeen them as a bridge
            cluster_component.bridges.add(edge);
            x[index_of_singleNode]++;
            x[index_of_node]++;
        }

    }

    static void printAllComponents() {
        System.out.println("#####################all components###################################");
        for (Component comp : connected_components)
            System.out.println(comp);
    }

    static int getSUM_DIFF() {
        int val = 0;
        for (int i = 0; i < N; i++) {
            val += Math.abs(x[i] - d[i]);
        }
        return val;

    }
}
/**
 * 1 13 11 2 3 2 4 3 4 4 5 1 13 7 11 12 13 7 10 10 11 9 11 7 9 --> 4
 *
 */

/**
 * 1 8 7 1 2 2 4 2 3 5 6 6 7 7 5 2 3 --> 0 
 */