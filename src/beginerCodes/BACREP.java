package beginerCodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class BACREP
{
    InputStream is;
    PrintWriter o;
    public static void main(String[] args) { new BACREP().run(); }
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

    static class Node
    {
        int pos;
        Node parent = null;
        List<Node> nodesw = new ArrayList<>();
        long gg, injected_bact, height = 0;
    }

    void solve()
    {

        int n = ni();
        int queries = ni();

        Node[] connect = new Node[n];

        for (int i = 0; i < n; i++) {
            connect[i] = new Node();
            connect[i].pos = i;
        }

        for (int i = 0; i < n-1; i++) {
            int a = ni();
            int b = ni();

            connect[--a].nodesw.add(connect[--b]);
            connect[b].nodesw.add(connect[a]);
        }

        for (int i = 0; i < n; i++) {
            connect[i].gg = ni();
        }

        if(n == 1)
        {
            Node r=new Node();
            while (queries-- > 0)
            {
                char op = ns().charAt(0);
                if(op == '+')
                {
                    ni();
                    r.gg += nl();
                }
                else
                {
                    ni();
                    o.println(r.gg);
                }

            }
            System.exit(0);
        }

        DepthFirstSearch(connect[0],0);

        while (queries-- > 0)
        {
            connect[0].injected_bact = 0;
            BreadthFirstSearch(connect[0]);

            char op = ns().charAt(0);
            if(op == '?')
            {
                int k=ni();
                k--;
                o.println(connect[k].gg);

            }
            else
            {
                int k=ni();
                long l=nl();
                k--;
                connect[k].gg += l;
            }
        }
    }

    static void DepthFirstSearch(Node node, int height)
    {
        node.height = height;
        if(node.nodesw.size() == 1 && !(node.pos == 0))
            return;

        for(int i = 0; i < node.nodesw.size(); i++)
        {
            Node connected_node = node.nodesw.get(i);
            if(!(node.parent  != connected_node))
            {
                continue;
            }

            connected_node.parent = node;
            height++;
            DepthFirstSearch(connected_node,height);
        }

    }

    static void BreadthFirstSearch(Node r)
    {
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.add(r);

        while(!q.isEmpty())
        {
            Node node = q.poll();
            if(node.nodesw.size() == 1 && !(node.pos == 0))
            {
                node.gg += node.injected_bact;
                continue;
            }

            for(Node connected_node : node.nodesw)
            {
                if(!(node.parent == connected_node)) {
                    connected_node.injected_bact = node.gg;
                    q.add(connected_node);
                }
            }

            node.gg = node.injected_bact;
            node.injected_bact = 0;
        }

    }
}