package leetCode;
import java.util.*;
public class destinationCity {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        List<List<String>> lists=new ArrayList<>();
        for (int i = 0; i <n ; i++) {
            List<String> list =new ArrayList<>();
            list.add(s.next());
            list.add(s.next());
            lists.add(list);

        }
        System.out.println(destCity(lists));
    }
    public static String destCity(List<List<String>> paths) {
        String res="";
        List<String > source =new ArrayList<>();
        List<String> dest=new ArrayList<>();
        for (int i = 0; i <paths.size() ; i++) {
            source.add(paths.get(i).get(0));
            dest.add(paths.get(i).get(1));
        }
        for (int i = 0; i <dest.size() ; i++) {
            if(!source.contains(dest.get(i))) {
                res = dest.get(i);
                break;
            }
        }

        return res;
    }
}
