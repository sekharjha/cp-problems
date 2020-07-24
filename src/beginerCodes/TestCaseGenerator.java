package beginerCodes;

import java.util.*;
class TestCaseGenerator{
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int a=s.nextInt();
        int b=s.nextInt();
        int lcm=a*b/gcd(a,b);
        System.out.println(lcm);
    }
    static int gcd(int a,int b){
        if(b==0)
            return a;
        else
            return gcd(b,a%b);
    }
}