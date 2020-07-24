package MayChallenge;

import java.util.*;
class TWOSTR {

    static class Element {
        char c;
        long max = 0;
        long val = 0;
        List<String> prefix = new ArrayList<>();
        List<String> suffix = new ArrayList<>();
        HashMap<Integer, Long> indices_B = new HashMap<>();

        @Override
        public String toString() {
            return c + " max = " + max + " val = " + val + " indices_B = " + indices_B;
        }

    }

    static class BeautifulString implements Comparable<BeautifulString> {
        String string;
        int b;

        @Override
        public int compareTo(BeautifulString o) {
            return this.b - o.b;
        }
    }

    static Element A[], B[];
    static int N;
    static BeautifulString[] bs;
    static long sum_b_A[], sum_b_B[];
    static HashMap<String, List<Integer>> matched_B;

    static void printA() {
        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i].val + " ");
        }
        System.out.println();
    }

    static void KMPA(String S, int lps[], int b) {
        // System.out.println("Seaching " + S + " in A with b = " + b);
        int i = 0, j = 0;
        long temp_b = 0;
        while (i < A.length) {
            if (S.charAt(j) == A[i].c) {
                j++;
                i++;
                if (i < A.length)
                    A[i].val += temp_b;
                List<Integer> lst = matched_B.get(S.substring(j));
                // System.out.println("\t\t" + S.substring(j) + " --> " + lst);
                if (lst != null)
                    for (int ind_B : lst) {
                        // System.out.println("\t\t\tind_B = "+ind_B+" B["+ind_B+"].val = "+B[ind_B].val+" A["+(i-1)+"].indices_B["+ind_B+"] = "+A[i - 1].indices_B.get(ind_B));
                        A[i - 1].indices_B.putIfAbsent(ind_B, 0L);
                        A[i - 1].indices_B.put(ind_B, A[i - 1].indices_B.get(ind_B) + b);
                        A[i - 1].max = Math.max(A[i - 1].max, B[ind_B].val + A[i - 1].indices_B.get(ind_B));
                    }
            }

            // System.out.println("\tat i = " + i + " j = " + j);

            if (j == S.length()) {
                temp_b += b;
                A[i - 1].val += b;
                if (i < A.length)
                    A[i].val += b;
                // System.out.println("\t\tFound it at index " + (i - j) + " A[" + i + "].val =
                // " + A[i].val);
                // printA();
                j = lps[j - 1];
            } else if (i <= A.length && (i == A.length || S.charAt(j) != A[i].c)) {
                // System.out.println("\t\tj = " + j);
                if (j != 0) {

                    j = lps[j - 1];
                } else {
                    i++;
                    if (i < A.length)
                        A[i].val += temp_b;
                }
            }
        }
        // printA();
    }

    static void KMPA2(String S, int lps[], int b) {
        System.out.println("Seaching " + S + " in A with b = " + b);
        int i = 0, j = 0;
        long temp_b = 0;
        while (i < A.length) {

            System.out.println("\tat i = " + i + " j = " + j);
            A[i].val += temp_b;

            if (S.charAt(j) == A[i].c) {

                List<Integer> lst = matched_B.get(S.substring(j + 1));
                System.out.println("\t\t" + S.substring(j + 1) + " --> " + lst);
                if (lst != null)
                    for (int ind_B : lst) {
                        A[i].indices_B.putIfAbsent(ind_B, 0L);
                        A[i].indices_B.put(ind_B, A[i].indices_B.get(ind_B) + b);
                        A[i].max = Math.max(A[i].max, B[ind_B].val + A[i].indices_B.get(ind_B));
                    }
                j++;

            }

            if (j == S.length()-1) {
                temp_b += b;
                A[i].val += b;
                // System.out.println("\t\tFound it at index " + (i - j) + " A[" + i + "].val =
                // " + A[i].val);
                // printA();
                j = lps[j];
            } else if (i <= A.length && (i == A.length || S.charAt(j) != A[i].c)) {
                if (j != 0) {
                    j = lps[j];
                } else {
                }
            }
            i++;
        }
        printA();
    }

    static boolean KMPB2(String S, int lps[]) {
        // System.out.println("\tsearching " + S + " in B");
        // CommonDebugCode.printArray(lps, "lps");
        List<Integer> lst = new ArrayList<>();
        int i = 0, j = 0;
        while (i < B.length) {

            if (S.charAt(j) == B[i].c) {
                j++;
                i++;
            }
            // System.out.println("\t\ti = "+i+" j = "+j);
            if (j == S.length()) {
                // System.out.println("\tFound pattern " + "at index " + (i - j));
                lst.add(i - j);
                j = lps[j - 1];

            } else if (i < B.length && S.charAt(j) != B[i].c) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }

        // System.out.println("\t"+S +" is at positions "+lst);
        matched_B.put(S, lst);
        if (lst.size() == 0)
            return false;
        return true;
    }

    static void printB() {
        for (int i = 0; i < B.length; i++) {
            System.out.print(B[i].val + " ");
        }
        System.out.println();
    }

    static void KMPB(String S, int lps[], int b) {
        // System.out.println("Seaching " + S + " in B");
        int i = B.length - 1, j = 0;
        long temp_b = 0;
        while (i >= 0) {

            if (S.charAt(j) == B[i].c) {
                j++;
                i--;
                if (i >= 0)
                    B[i].val += temp_b;
            }
            // System.out.println("\tat i = " + i + " j = " + j);
            if (j == S.length()) {
                // System.out.println("\t\tfound at index " + i);
                temp_b += b;
                B[i + 1].val += b;
                if (i >= 0)
                    B[i].val += b;
                j = lps[j - 1];
            } else if (i >= 0 && S.charAt(j) != B[i].c) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i--;
                    if (i >= 0)
                        B[i].val += temp_b;
                }
            }
        }
        // printB();
    }

    static String reveserString(String S) {
        StringBuilder input1 = new StringBuilder();
        input1.append(S);
        input1 = input1.reverse();
        return input1.toString();
    }

    static int[] computeLPSArray(String pat, int M) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        int lps[] = new int[M];
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
        return lps;
    }

    static void printAall() {
        System.out.println("printingAall()....");
        for (int i = 0; i < A.length; i++) {
            System.out.println(A[i]);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        while (T-- > 0) {
            String temp = in.next();
            A = new Element[temp.length()];

            matched_B = new HashMap<>();

            for (int i = 0; i < temp.length(); i++) {
                A[i] = new Element();
                A[i].c = temp.charAt(i);
            }

            temp = in.next();
            B = new Element[temp.length()];
            for (int i = 0; i < temp.length(); i++) {
                B[i] = new Element();
                B[i].c = temp.charAt(i);
            }

            N = in.nextInt();
            bs = new BeautifulString[N];
            for (int i = 0; i < N; i++) {
                bs[i] = new BeautifulString();
                bs[i].string = in.next();
                bs[i].b = in.nextInt();
            }
            // Arrays.sort(bs);

            for (int i = 0; i < N; i++) {
                String S = bs[i].string;
                // System.out.println("at " + S);
                for (int j = S.length() - 1; j > 0; j--) {

                    boolean flag = KMPB2(S.substring(j, S.length()),
                            computeLPSArray(S.substring(j, S.length()), S.length() - j));

                    if (!flag)
                        break;
                }
            }
            // System.out.println(matched_B);

            for (int n = 0; n < N; n++) {
                String S = bs[n].string;
                KMPB(reveserString(S), computeLPSArray(S, S.length()), bs[n].b);
            }

            for (int n = 0; n < N; n++) {
                String S = bs[n].string;
                KMPA(S, computeLPSArray(S, S.length()), bs[n].b);
            }

            // printAall();
            long ans = Long.MIN_VALUE;
            for (int i = 0; i < A.length; i++) {
                // System.out.println("at i = " + i + " ans = " + ans + " val = " + (A[i].val + A[i].max));
                ans = Math.max(ans, A[i].val + A[i].max);
            }
            System.out.println(ans);
        }
    }

}

/**
 * 1 xyklpbmklpbxytmb pbtsblxmbbrt 4 xyk 5 lp 4 klpb 3 xyt 1
 1
 xyzpqsbxpq
 yzsbtxyz
 3
 pq 3
 pqsb 5
 xyz 4
 */
