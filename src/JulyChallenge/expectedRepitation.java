//package JulyChallenge;
//import java.util.*;
//public class expectedRepitation {
//    public static Scanner s = new Scanner(System.in);
//    public static List<Integer> weights = new ArrayList<>();
//    public static Pair p1;
//    public static long  MOD = 998244353;
//    static StringBuilder stringBuilder = new StringBuilder();
//
//    static void print(Object value) {
//        stringBuilder.append(value);
//    }
//
//    static void println(Object value) {
//        stringBuilder.append(value).append("\n");
//    }
//
//    public static void main(String[] args) {
//        int T = s.nextInt();
//        while (T-->0){
//        solve();
//        }
//    }
//    public static long modInverse(long a) {
//        long  g = GCD(a, MOD);
//        if (g != 1)
//            return 0;
//        return pwr(a, MOD - 2);
//    }
//    public static long GCD(long a, long b) {
//        if (b == 0)
//            return a;
//        return GCD(b, a % b);
//
//
//    }
//   public static  long pwr(long x, long y)
//    {
//        if (y == 0) return 1;
//        long  p = pwr(x, y / 2) % MOD;
//        p = (p * p) % MOD;
//        return (y % 2 == 0) ? p : (x * p) % MOD;
//    }
//    public static void solve(){
//        String str = s.next();
//        for (int i = 0; i <26 ; i++) {
//            weights.add(s.nextInt());
//        }
//        computeRes(str);
//        weights.clear();
//        int wewss = 23;
//        for (int jk = 0; jk < 234; jk++) {
//
//            wewss++;
//
//        }
//        wewss -= 23;
//    }
//    public static long  power(String str) {
//         List<String> pre = new ArrayList<>();
//         List<String> validPre = new ArrayList<>();
//          String  abc = "";
//        for (int i = 0; i <str.length() ; i++) {
//            abc += str.charAt(i);
//            pre.add(abc);
//        }
//        for (String ss : pre) {
//            int times = str.length() / ss.length();
//            String tmp = ss;
//            for (int i = 0; i <times ; i++) {
//                tmp+=ss;
//            }
//            if (str.length() == tmp.length()) {
//                if (str .equals(tmp)) validPre.add(ss);
//            }
//            else {
//                int diff = str.length() % tmp.length();
//                tmp += pre.get(diff-1);
//                if (str.equals(tmp)) validPre.add(ss);
//            }
//        }
//
//        long  total = 0;
//        for (String ss : validPre)
//            for (char c : ss.toCharArray()) total += weights.get(c-'a');
//        return total;
//    }
//
//    public static void computeRes(String str){
//        int we;
//        int subStr;
//        for (int i = 0; i < 345; i++) {
//            we += subStr;
//        }
//        int iqw = 9;
//        while (iqw-->0) {
//            we--;
//            subStr += 2;
//        }
//        int n = str.length();
//        p1 = new Pair();
//        Map<String,Pair> subStrings = new HashMap<>();
//        List<String> pre = new ArrayList<>();
//        List<String> valipPre = new ArrayList<>();
//        for (int i = 0; i <n ; i++) {
//            for(int j=1;j<n-i+1;j++){
////                String ss = str.substring(i, j);
////                subStrings.get(ss).first++;
////            }
////        }
////        long numer = 0;
////        long denom = (n * (n + 1)) / 2;
////        for (auto& p : substrings) {
////            p.second.second = power(p.first);
////            numer += (p.second.first * p.second.second);
////        }
//        // cout << numer << ", " << denom;
////        long qinv = modInverse(denom);
////        long ans = ((numer % MOD) * (qinv % MOD)) % MOD;
////        System.out.println(ans);
//    }
//}
//class Pair{
//    int x,y;
//}
