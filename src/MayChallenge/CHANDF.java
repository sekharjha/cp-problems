package MayChallenge;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;

public class CHANDF {
    InputStream is;
    PrintWriter o;
    byte input[] = new byte[1024];
    int len = 0, ptr = 0;

    int readByte() {
        if(ptr >= len) { ptr = 0;
            try { len = is.read(input); }
            catch(IOException e) { throw new InputMismatchException(); }
            if(len <= 0) { return -1; }
        } return input[ptr++];
    }
    boolean isSpaceChar(int c) { return !( c >= 33 && c <= 126 ); }
    int skip() {
        int b = readByte();
        while(b != -1 && isSpaceChar(b)) { b = readByte(); }
        return b;
    }

    char nc() { return (char)skip(); }
    String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!isSpaceChar(b)) { sb.appendCodePoint(b); b = readByte(); }
        return sb.toString();
    }
    String nLine() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while( !(isSpaceChar(b) && b != ' ') ) { sb.appendCodePoint(b); b = readByte(); }
        return sb.toString();
    }
    int ni() {
        int n = 0, b = readByte();
        boolean minus = false;
        while(b != -1 && !( (b >= '0' && b <= '9') || b == '-')) { b = readByte(); }
        if(b == '-') { minus = true; b = readByte(); }
        if(b == -1) { return -1; }  //no input
        while(b >= '0' && b <= '9') { n = n * 10 + (b - '0'); b = readByte(); }
        return minus ? -n : n;
    }
    long nl() {
        long n = 0L;    int b = readByte();
        boolean minus = false;
        while(b != -1 && !( (b >= '0' && b <= '9') || b == '-')) { b = readByte(); }
        if(b == '-') { minus = true; b = readByte(); }
        while(b >= '0' && b <= '9') { n = n * 10 + (b - '0'); b = readByte(); }
        return minus ? -n : n;
    }

    static List<Integer> firstPostionList, secondPostionList, thirdPositionList;
    static List<List<Integer>> modEven, modBoth, modOdd;
    static HashMap<Integer, Integer> vABC, sABC;
    static boolean alreadyVisitedPositions[];
    static int sArr[], pos[], N;




    double nd() { return Double.parseDouble(ns()); }
    float nf() { return Float.parseFloat(ns()); }
    int[] nia(int n) {
        int a[] = new int[n];
        for(int i = 0; i < n; i++) { a[i] = ni(); }
        return a;
    }
    long[] nla(int n) {
        long a[] = new long[n];
        for(int i = 0; i < n; i++) { a[i] = nl(); }
        return a;
    }
    int [][] nim(int n)
    {
        int mat[][]=new int[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                mat[i][j]=ni();
            }
        }
        return mat;
    }
    long [][] nlm(int n)
    {
        long mat[][]=new long[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                mat[i][j]=nl();
            }
        }
        return mat;
    }






    char[] ns(int n) {
        char c[] = new char[n];
        int i, b = skip();
        for(i = 0; i < n; i++) {
            if(isSpaceChar(b)) { break; }
            c[i] = (char)b; b = readByte();
        } return i == n ? c : Arrays.copyOf(c,i);
    }
    void piarr(int arr[])
    {
        for(int i=0;i<arr.length;i++)
        {
            o.print(arr[i]+" ");

        }
        o.println();
    }
    void plarr(long arr[])
    {
        for(int i=0;i<arr.length;i++)
        {
            o.print(arr[i]+" ");

        }
        o.println();
    }

    void pimat(int mat[][])
    {
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[0].length;j++)
            {
                o.print(mat[i][j]);
            }
            o.println();
        }
    }
    void plmat(long mat[][])
    {
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[0].length;j++)
            {
                o.print(mat[i][j]);
            }
            o.println();
        }

    }
    private static long x, y, l, r;


    public static void main(String[] args) { new CHANDF().run(); }
    void run() {
        is = System.in;
        o = new PrintWriter(System.out);
        solve();
        o.flush();
    }

     void solve() {
         int T = ni();
         while (T-- > 0) {
             x = nl();
             y = nl();
             l = nl();
             r = nl();

             char[] K = bitSet64(l);
             char[] L = bitSet64(r);
             char[] M = bitSet64(x);
             char[] N = bitSet64(y);
             char[] arr = bitSet64(r);

             int ind = -1;

             for (int i = 63; i >= 0; --i) {
                 if (L[i] == '1' && K[i] == '0') {
                     ind = i;
                     break;
                 }
             }
             if (ind == -1) {
                 System.out.println(l);
             } else if (x == 0 || y == 0) {
                 o.println(l);
             } else {
                 int dfPos = changeIndexPosition(ind, arr);
                 if (dfPos == -2) {
                     o.println(l);
                 } else if (dfPos == -1) {
                     o.println(toUlong(arr));
                 } else {
                     arr[dfPos] = '0';
                     if (dfPos == ind) {
                         boolean flag = false;
                         for (int i = dfPos - 1; i > -1; --i) {
                             if (M[i] == '1' || N[i] == '1')
                                 arr[i] = '1';
                             else
                                 arr[i] = '0';
                             if (flag)
                                 continue;
                             if (arr[i] == '1' && K[i] == '0')
                                 flag = true;
                             else
                                 arr[i] = K[i];
                         }
                     } else {
                         for (int i = dfPos - 1; i >= 0; --i) {
                             if (M[i] == '1' || N[i] == '1')
                                 arr[i] = '1';
                             else
                                 arr[i] = '0';
                         }
                     }
                     long result = manipulate(-1, arr);
                     o.println(result);
                 }
             }
         }
     }


	/*static boolean[] bitSet64(long val) {
		String bin = new StringBuilder(Long.toBinaryString(val)).reverse().toString();
		boolean[] bitset = new boolean[64];
		int pos = 0;
		for (char c : bin.toCharArray()) {
			if (c == '1')
				bitset[pos] = true;
			pos++;
		}

		return bitset;
	}*/

    static char[] bitSet64(long val) {
        String value = Long.toBinaryString(val);
        String zeros = "0000000000000000000000000000000000000000000000000000000000000000";//String of 64 zeros
        String bin = new StringBuilder(value).reverse().toString() + zeros.substring(value.length());
        return bin.toCharArray();
    }

    static void printBitSet(boolean[] booleans){
        for (boolean b: booleans) {
            if(b)
                System.out.print(1);
            else
                System.out.print(0);
        }
        System.out.println();
    }

	/*static long toUlong(boolean[] bitset) {
		String bin = "";
		for (boolean b : bitset) {
			if (b)
				bin += 1;
			else
				bin += 0;
		}
		return Long.parseLong(new StringBuilder(bin).reverse().toString(), 2);
	}*/

    static long toUlong(char[] bitset){
        return Long.parseLong(new StringBuilder(new String(bitset)).reverse().toString(), 2);
    }

    static long manipulate(int ind, char[] arr) {
        long res2 = 0;
        for (int i = 0; i < ind; i++) {
            res2 += (1L << i);
        }
        for (int i = ind + 1; i < 64; i++) {
            if (arr[i] == '1')
                res2 += (1L << i);
        }
        return res2;
    }

    static int changeIndexPosition(int ind, char[] arr) {
        long res = 0;
        int j = -2;
        for (int i = ind; i >= -1; i--) {
            if (i > 0 && arr[i] == '0')
                continue;
            long res1 = manipulate(i, arr);
            long res2 = (y & res1) * (x & res1);
            if (res2 > res) {
                res = res2;
                j = i;
            }
        }
        return j;
    }
}