package Graphs;

import java.util.*;
public class TeachersDilemma {
    static long vis[]= new long[100005];
    static long mod=1000000007;
    static Set<Long> list = new HashSet<>();
    static Map<Long,List<Long>> map = new HashMap<>();
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        long n=s.nextLong();
        long m=s.nextLong();
        for (int i = 0; i <m ; i++) {
            long a = s.nextLong();
            long b = s.nextLong();
            if (map.containsKey(a)) {
                List<Long> list = map.get(a);
                list.add(b);
                map.put(a, list);
            } else {
                List<Long> list = new ArrayList<>();
                list.add(b);
                map.put(a, list);
            }
            if (map.containsKey(b)) {
                List<Long> list = map.get(b);
                list.add(a);
                map.put(b, list);
            } else {
                List<Long> list = new ArrayList<>();
                list.add(a);
                map.put(b, list);
            }


        }
        long res=1;
        for (int i = 1; i <=n ; i++) {
            if(vis[i]!=1){
                Dfs(i);
               // System.out.println(list);
                res*=list.size()%mod;
                list.clear();
            }
        }
        System.out.println(res);

    }
    static void Dfs(long u){
        // System.out.println(u);
        vis[(int)u]=1;
        list.add(u);
        for(long  curr:map.getOrDefault(u,new ArrayList<>())) {
            if (curr==0)
                continue;
            if (vis[(int)curr] != 1)
                Dfs(curr);
        }
    }
}
