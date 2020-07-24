package Graphs;
import java.util.*;
class MessageRoute {
    static int vis[]=new int[100001];
    static List<Integer> arr[] = new ArrayList[100001];
    static int dis[]=new int[100001];
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int m =s.nextInt();
        int n =s.nextInt();
        for (int i = 1; i <=m ; i++) {
            arr[i]=new ArrayList<>();
            vis[i]=0;
        }
        for (int i = 0; i <n ; i++) {
            int a=s.nextInt();
            int b=s.nextInt();
            arr[a].add(b);
            arr[b].add(a);
        }
        String res = BFS(1,m);

        if(res != null) {
            System.out.println(dis[m]);
            System.out.println(res);
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }
    public static String BFS(int src,int des){
        Queue<Node> queue = new LinkedList<>();
        Node node =new Node();
        node.value=src;
        node.path = src+"";
        queue.add(node);
        vis[src]=1;
        dis[src]=1;
        while (!queue.isEmpty()){
            node = queue.poll();
            int curr =node.value;
            String path = node.path;
            if(curr==des)
                return path;

            for(int child:arr[curr]){
                if(vis[child]!=1){
                    Node node2 = new Node();
                    node2.value=child;
                    node2.path=path+" "+child;
                    queue.add(node2);
                    vis[child]=1;
                    dis[child]=dis[curr]+1;
                }
            }
        }
        return null;
    }
}
class Node{
    int value;
    String path;
}
