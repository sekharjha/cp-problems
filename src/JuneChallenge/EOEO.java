package JuneChallenge;

import java.util.Scanner;

public class EOEO {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int T = sc.nextInt();
        while (T-- > 0){
            long N = sc.nextLong();

            while(N % 2 == 0){ N=N/2; }

            System.out.println(N/2);
        }
    }

}