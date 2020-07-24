package JuneChallenge;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

 class CONTAIN {
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
    static List<List<Point>> res = new ArrayList<>();

    static boolean compare(Point a, Point b) {
        return a.x < b.x || (a.x == b.x && a.y < b.y);
    }

    private static void removeValues(List<Point> pp, Point m) {
        while (pp.remove(m));
    }

    public static void search(String text, String pattern)
    {

        // Create concatenated string "P$T"
        String concat = pattern + "$" + text;

        int l = concat.length();

        int Z[] = new int[l];

        // Construct Z array
        getZarr(concat, Z);

        // now looping through Z array for matching condition
        for(int i = 0; i < l; ++i){

            // if Z[i] (matched region) is equal to pattern
            // length we got the pattern

            if(Z[i] == pattern.length()){
                System.out.println("Pattern found at index "
                        + (i - pattern.length() - 1));
            }
        }
    }
    private static void getZarr(String str, int[] Z) {

        int n = str.length();

        // [L,R] make a window which matches with
        // prefix of s
        int L = 0, R = 0;

        for(int i = 1; i < n; ++i) {

            // if i>R nothing matches so we will calculate.
            // Z[i] using naive way.
            if(i > R){

                L = R = i;

                // R-L = 0 in starting, so it will start
                // checking from 0'th index. For example,
                // for "ababab" and i = 1, the value of R
                // remains 0 and Z[i] becomes 0. For string
                // "aaaaaa" and i = 1, Z[i] and R become 5

                while(R < n && str.charAt(R - L) == str.charAt(R))
                    R++;

                Z[i] = R - L;
                R--;

            }
            else{

                // k = i-L so k corresponds to number which
                // matches in [L,R] interval.
                int k = i - L;

                // if Z[k] is less than remaining interval
                // then Z[i] will be equal to Z[k].
                // For example, str = "ababab", i = 3, R = 5
                // and L = 2
                if(Z[k] < R - i + 1)
                    Z[i] = Z[k];

                    // For example str = "aaaaaa" and i = 2, R is 5,
                    // L is 0
                else{


                    // else start from R and check manually
                    L = i;
                    while(R < n && str.charAt(R - L) == str.charAt(R))
                        R++;

                    Z[i] = R - L;
                    R--;
                }
            }
        }
    }

    void KMPSearch(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int lps[] = new int[M];
        int j = 0; // index for pat[]

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pat, M, lps);

        int i = 0; // index for txt[]
        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                System.out.println("Found pattern "
                        + "at index " + (i - j));
                j = lps[j - 1];
            }

            // mismatch after j matches
            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
    }

    void computeLPSArray(String pat, int M, int lps[])
    {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                }
                else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
    public static void main(String[] args)throws IOException {
        Reader reader = new Reader();
        int t;
        t = reader.nextInt();
        while (t-- > 0) {
            long n, q;
            n = reader.nextLong();
            q = reader.nextLong();
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                long u = reader.nextLong();
                long v = reader.nextLong();

                Point point = new Point();
                point.x = u;
                point.y = v;

                points.add(point);
            }
            List<Point> copy = new ArrayList<>(points);
            while (copy.size() > 2) {
                circleFormationForPrimes(copy);
            }
            copy.clear();
            while (q-- > 0) {
                long answer = 0;
                Point P = new Point();
                P.x = reader.nextDouble();
                P.y = reader.nextDouble();


                for (List<Point> itr : res) {
                    boolean ok = false;
                    for (int i = 0; i < itr.size(); ++i) {
                        if (!possible1(itr.get(i), itr.get((i + 1) % itr.size()), P)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) answer++;
                    else break;
                }
                System.out.println(answer);
            }
            res.clear();
            points.clear();
        }
    }

    static boolean possible1(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) < 0;
    }

    static boolean possible2(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) > 0;
    }
    void sieveOfEratosthenes(int n)
    {
        // Create a boolean array "prime[0..n]" and initialize
        // all entries it as true. A value in prime[i] will
        // finally be false if i is Not a prime, else true.
        boolean prime[] = new boolean[n+1];
        for(int i=0;i<n;i++)
            prime[i] = true;

        for(int p = 2; p*p <=n; p++)
        {
            // If prime[p] is not changed, then it is a prime
            if(prime[p] == true)
            {
                // Update all multiples of p
                for(int i = p*2; i <= n; i += p)
                    prime[i] = false;
            }
        }

        // Print all prime numbers
        for(int i = 2; i <= n; i++)
        {
            if(prime[i] == true)
                System.out.print(i + " ");
        }
    }


    static long formValues(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    static void circleFormationForPrimes(List<Point> a) {
        if (a.size() == 1)
            return;

        Collections.sort(a, (a1, b) -> a1.x < b.x || (a1.x == b.x && a1.y < b.y) ? 1 : -1);
        Point p1 = a.get(0), p2 = a.get(a.size() - 1);
        List<Point> two = new ArrayList<>();
        List<Point> one = new ArrayList<>();


        one.add(p1);
        two.add(p1);

        for (int i = 1; i < a.size(); i++) {
            if (i == a.size() - 1 || possible1(p1, a.get(i), p2)) {
                while (one.size() >= 2 && !possible1(one.get(one.size() - 2), one.get(one.size() - 1), a.get(i)))
                    one.remove(one.size() - 1);
                one.add(a.get(i));
            }
            if (i == a.size() - 1 || possible2(p1, a.get(i), p2)) {
                while (two.size() >= 2 && !possible2(two.get(two.size() - 2), two.get(two.size() - 1), a.get(i)))
                    two.remove(two.size() - 1);
                two.add(a.get(i));
            }
        }

        List<Point> thirdPoints = new ArrayList<>();
        for (int i = 0; i < one.size(); i++) {
            thirdPoints.add(one.get(i));
            removeValues(a, one.get(i));
        }
        for (int i = two.size() - 2; i > 0; i--) {
            thirdPoints.add(two.get(i));
            removeValues(a, two.get(i));
        }
        List<Point> temporaryValues = new ArrayList<>(a);
        for (Point point : temporaryValues) {
            for (int i = 0; i < thirdPoints.size(); ++i) {
                long x = formValues(thirdPoints.get(i), thirdPoints.get((i + 1) % thirdPoints.size()), point);
                if (x == 0)
                    removeValues(a, point);
            }
        }
        res.add(thirdPoints);
    }

    static class Point {
        double x;
        double y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Double.compare(point.x, x) == 0 &&
                    Double.compare(point.y, y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }



}