package MayChallenge;

import java.util.*;
import java.io.*;
class TRPLSRT {


    static List<List<Integer>> oc;

    static HashMap<Integer, Integer> mapAb, sortedMap;
    static boolean calculated[];
    static List<Integer> pi1, pi2, pi3;
    static int sAryas[], pS[], numberOfValues;
    static List<List<Integer>> ec, bo;
    static void compute(int u) {
        if (sortedMap.get(pS[u]) == u) {
            calculated[u] = true;
            return;
        }

        List<Integer> temp_list = new ArrayList<>();
        while (calculated[u]==false) {
            calculated[u] = true;
            temp_list.add(u);
            u = sortedMap.get(pS[u]);
        }

        int d=2;
        if (temp_list.size() ==d )
            bo.add(temp_list);
        else if (!(temp_list.size() % 2 != 0))
            ec.add(temp_list);
        else
            oc.add(temp_list);

    }

    static void dissolveCycle(List<Integer> cycle) {
        int n = 0;
        while (n <= cycle.size() - 3) {
            int i1 = cycle.get(n);
            int i2 = cycle.get( n+1);
            int i3 = cycle.get(n+2);
            pi1.add(i1);
            pi2.add(i2);
            pi3.add(i3);
            cycle.set(n+2,cycle.get(n));
            n+=2;
        }
        if (n == cycle.size() - 1)
            return;
        List<Integer> temp_list = new ArrayList<>();
        temp_list.add(cycle.get(n));
        temp_list.add(cycle.get(n + 1));
        bo.add(temp_list);
    }


    public static void main(String[] args)throws IOException {
        Reader in = new Reader();

        int T = in.nextInt();
        while (T-- > 0) {
            numberOfValues = in.nextInt();
            int K = in.nextInt();
            pS = new int[numberOfValues];
            sAryas = new int[numberOfValues];
            calculated = new boolean[numberOfValues];

            pi3 = new ArrayList<>();
            bo = new ArrayList<>();

            pi2 = new ArrayList<>();
            ec = new ArrayList<>();
            mapAb = new HashMap<>();
            sortedMap = new HashMap<>();
            oc = new ArrayList<>();
            pi1 = new ArrayList<>();



            for (int i = 0; i < numberOfValues; i++) {
                pS[i] = in.nextInt();
                mapAb.put(pS[i], i);
            }

            // System.out.println(map);
            sAryas = pS.clone();
            Arrays.sort(sAryas);

            for (int i = 0; i < numberOfValues; i++) {
                sortedMap.put(sAryas[i], i);
            }
            for (int i = 0; i < numberOfValues; i++) {
                if (!calculated[i])
                    compute(i);
            }


            if ((ec.size() + bo.size()) % 2 != 0) {
                System.out.println(-1);
                continue;
            }

            for (List<Integer> cycle : oc) {
                dissolveCycle(cycle);
            }

            for (List<Integer> cycle : ec) {
                dissolveCycle(cycle);
            }




            for (int i = 0; i < bo.size(); i += 2) {
                int a0 = bo.get(i).get(0);
                int a1 = bo.get(i).get(1);

                int a2 = bo.get(i + 1).get(0);
                int a3 = bo.get(i + 1).get(1);

                pi1.add(a0);
                pi2.add(a1);
                pi3.add(a2);

                pi1.add(a2);
                pi2.add(a0);
                pi3.add(a3);
            }

            System.out.println(pi1.size());
            for (int i = 0; i < pi1.size(); i++)
                System.out.println((pi1.get(i)+1) + " " + (pi2.get(i)+1) + " " + (pi3.get(i)+1));
        }
        in.close();
    }
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

}
