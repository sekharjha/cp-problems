package Graphs;

import java.util.*;
public class BuildingTeams {
    public static Set<Integer> setA = new HashSet<>();
    public static Set<Integer> setB = new HashSet<>();
    public static Map<Integer, List<Integer>> map = new HashMap<>();
    static int vis[]=new int[100005];
    static int visDfs[]=new int[100005];
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int m = s.nextInt();
        int n = s.nextInt();
        List<Integer> srcs= new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int a = s.nextInt();
            int b = s.nextInt();
            if (map.containsKey(a)) {
                List<Integer> list = map.get(a);
                list.add(b);
                map.put(a, list);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(b);
                map.put(a, list);
            }
            if (map.containsKey(b)) {
                List<Integer> list = map.get(b);
                list.add(a);
                map.put(b, list);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(a);
                map.put(b, list);
            }

        }
        System.out.println(map);
        for (int i = 1; i <=m ; i++) {
            if(visDfs[i]!=1) {
                srcs.add(i);
                Dfs(i);
            }
        }
        int flag=0;
        for(Integer src:srcs){
            flag=Math.min(Bfs(src),flag);
        }
        if(flag!=-1) {
            for (int i = 1; i <= m; i++) {
                if (setA.contains(i))
                    System.out.print(1 + " ");
                else
                    System.out.print(2 + " ");
            }
            System.out.println();
        }
        else
            System.out.println("IMPOSSIBLE");
    }
    public static int Bfs(int s){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        vis[s]=1;
        while (!queue.isEmpty()){
            int curr=queue.poll();
            if(!setB.contains(curr))
            setA.add(curr);
            for(int child: map.getOrDefault(curr,new ArrayList<>())) {
                if(child==0)
                    continue;
                if (vis[child] != 1){
                    queue.add(child);
                    vis[child] = 1;
                    if(setA.contains(child))
                        return -1;
                    setB.add(child);

            }
            }
        }
        return 1;
    }
    static void Dfs(int u){
       // System.out.println(u);
        visDfs[u]=1;
        for(int  curr:map.getOrDefault(u,new ArrayList<>())) {
            if (curr==0)
                continue;
            if (visDfs[curr] != 1)
                Dfs(curr);
        }
    }
}
