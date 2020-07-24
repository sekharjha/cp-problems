package beginerCodes;

import java.util.*;
class codehcef{
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int T=s.nextInt();
        while (T-->0) {
            int  n = s.nextInt();
            long v;
            HashMap<String,HashSet<Long>> val=new HashMap<>();
            for (int i = 0; i <n ; i++) {
                long base=s.nextLong();
                String num=s.next();
                for (int j = 2; j <36 ; j++) {
                    HashSet<Long> set =  val.get(num);
                    set.add(toDecimal(num,j));
                    val.put(num,set);
                }
                if(base!=-1){
                     v=toDecimal(num,base);
                }
            }


        }
    }
    public static long baseBToDecimal(char input) {
        if (input >= '0' && input <= '9') {
            return Long.parseLong(input + "");
        } else {
            return (long) (input - 'A') + 10;
        }
    }

    public static char decimalToBaseB(long input) {
        if (input >= 0 && input <= 9) {
            String str = String.valueOf(input);
            return str.charAt(0);
        } else {
            return (char) ('A' + (input - 10));
        }
    }

    public static long toDecimal(String input, long base) {
        int length = input.length();
        long decimal = 0;
        for (int placeValue = 0, index = length - 1; index >= 0; placeValue++, index--) {
            decimal = decimal + baseBToDecimal(input.charAt(index)) * (long) (Math.pow(base, placeValue));
        }
        return decimal;
    }

    public static String toBaseB(int input, int base) {
        String result = "";
        while (input > 0) {
            int remainder = input % base;
            input = input / base;
            result = decimalToBaseB(remainder) + result;
        }
        return result;
    }
}