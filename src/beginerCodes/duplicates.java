package beginerCodes;

import java.util.*;
class duplicates {

    static Vector<Integer> arrayPre = new Vector<>();
    static int MAX = 1000000;
    static int digits(int n)
    {
        Set<Integer> set = new HashSet<>();
        int digitsSum;


        while (n != 0)
        {
            digitsSum = n % 10;

            if (set.contains(digitsSum))
                return 0;
            set.add(digitsSum);
            n /= 10;
        }

        return 1;
    }

    static void compute()
    {
        arrayPre.add(0);
        arrayPre.add(digits(1));
        for (int i = 2; i < MAX + 1; i++)
            arrayPre.add(digits(i) + arrayPre.elementAt(i - 1));
    }
    static int calculate(int L, int R)
    {
        return arrayPre.elementAt(R) - arrayPre.elementAt(L - 1);
    }
    public static void main(String[] args)
    {
        compute();
        Scanner s= new Scanner(System.in);
        int t = s.nextInt();
        while (t-->0){
            int L =s.nextInt();
            int R =s.nextInt();
            int ans = R-L+1-calculate(L,R);
            System.out.println(ans);
        }
    }
}
