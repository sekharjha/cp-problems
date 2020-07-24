package beginerCodes;

import java.util.*;
class REBIT {
    static long count0,count1,countA,counta,countHash;
    static int mod= 998244353;
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        while (t-->0){
            String str =s.next();
            count0=1;
            count1=1;
            countA=1;
            counta=1;
            countHash = 0;
            solve(str);
           long res =  power(4L,countHash,mod)%mod;
            System.out.println(res);
            System.out.println(count0+" "+count1+" "+counta+" "+countA);
            count0 = (count0*modInverse(res,mod))%mod;
            count1 = (count1*modInverse(res,mod))%mod;
            counta = (counta*modInverse(res,mod))%mod;
            countA = (countA*modInverse(res,mod))%mod;

           System.out.println(count0+" "+count1+" "+counta+" "+countA);

        }
    }
    public static void solve(String str){
        if(str.length()==1) {
            countHash++;
            return;
        }
        Stack<Character> stack = new Stack<>();
        char ch[] = str.toCharArray();
        for (int i = 0; i <str.length() ; i++) {
            if(ch[i]=='#')
                countHash++;
                    stack.push(ch[i]);
                 if(ch[i]==')')
                {
                    char waste1=stack.pop();
                    char ch1=stack.pop();
                    char op = stack.pop();
                    char ch2 = stack.pop();
                    char waste2= stack.pop();
                    //String exp=waste2+" "+ch2+" "+op+" "+ch1+" "+waste1+" ";
                  //  System.out.println(exp);
                    char res = findValue(ch1,op,ch2);
                    stack.push(res);
                }
           // System.out.println(stack);
        }
//        while (stack.size()>2){
//            char ch1=stack.pop();
//            char op = stack.pop();
//            char ch2 = stack.pop();
//            char res = findValue(ch1,op,ch2);
//            stack.push(res);
//        }
       // System.out.println(stack);
       // System.out.println(stack.size());
    }
    public static char findValue(char ch1,char op,char ch2){
        switch (op){
            case '&':
                count0 = ((count0*count1)%mod+((count0+count1+counta+countA)*count0)%mod+((countA+count0)*counta)%mod+(counta+count0)%mod)%mod;
                count1 = (count1)%mod;
                counta = ((1*count1)%mod+(2*counta)%mod)%mod;
                countA = ((1*count1)+(2*countA)%mod)%mod;
                break;
            case '|':
                count0 = count0%mod;
                count1 = ((4*count1)%mod+(1*count0)%mod+(2*counta)%mod+(2*countA)%mod)%mod;
                counta = ((1*count0)%mod+(2*counta)%mod)%mod;
                countA = ((1*count0)+(2*countA)%mod)%mod;
                break;
            case '^':
                count0 = (count0+count1+counta+countA)%mod;
                count1=countA=counta=count0;
                break;
        }
        return '#';
    }
    static long modInverse(long a, long m)
    {
        long m0 = m;
        long y = 0, x = 1;

        if (m == 1)
            return 0;

        while (a > 1)
        {
            // q is quotient
            long q = a / m;

            long t = m;

            // m is remainder now, process
            // same as Euclid's algo
            m = a % m;
            a = t;
            t = y;

            // Update x and y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0)
            x += m0;

        return x;
    }
    static long power(long x,
                      long y, long p)
    {
        long res = 1; // Initialize result

        // Update x if it is more
        // than or equal to p
        x = x % p;

        while (y > 0)
        {
            // If y is odd, multiply
            // x with the result
            if ((y & 1) > 0)
                res = (res * x) % p;

            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }
        return res;
    }
}
