package leetCode;

import java.util.*;

public class test {
    static StringBuilder stringBuilder = new StringBuilder();

    static void print(Object value) {
        stringBuilder.append(value);
    }

    static void println(Object value) {
        stringBuilder.append(value).append("\n");
    }

    public static void main(String[] args) {
        String str1[] = {"hack","a","rank","khac","ackh","a"};
        String str2[]={"a","nark","hack"};
        System.out.println(count(str1,str2));

    }
    public static List<Integer> count(String str1[],String str2[]){
        List<Integer> res = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i <str1.length ; i++) {
            String st=str1[i];
            char ch[]=st.toCharArray();
            Arrays.sort(ch);
            st = new String(ch);
            map.put(st,map.getOrDefault(st,0)+1);
        }
        for (int i = 0; i <str2.length ; i++) {
            String st = str2[i];
            char ch[]=st.toCharArray();
            Arrays.sort(ch);
            st = new String(ch);
            res.add(map.getOrDefault(st,0));
        }
        return res;
    }

}