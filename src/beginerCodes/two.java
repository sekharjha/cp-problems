package beginerCodes;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class two {
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
    public static Map<Long,Long> values ;

   public static  long calculate(long d[], long n, long val) {
        long res = 0;
        long sum = 0;
        values = new HashMap<>();
        for (int i   = 0; i < n; i++) {
            sum += d[i];

            if (sum == val) {
                res++;
            }
            long d1= sum-val;
            if (values.containsKey(d1)) {
                res += (values.get(d1));
            }
            if(values.get(sum)!= null) {
                long keyValue = values.get(sum)+1;
                values.put(sum, keyValue);
            }
                else
                    values.put(sum,1L);

        }
        return res;
    }


    public static void main(String args[])throws IOException {

       Reader s=new Reader();
       int t=s.nextInt();
       while (t-->0){
           long n;
           n=s.nextLong();
           long d[]=new long[(int)n];
           long a[]= new long[(int)n];
           for (int i = 0; i <n ; i++) {
                a[i]=s.nextLong();
           }
           for (int i = 0; i < n; i++) {
               if(a[i]%2!=0)
                   d[i]=0;
               else{
                   if(a[i]%4==0){
                       d[i]=2;
                   }
                   else
                       d[i]=1;
               }

           }

           long sum = (n * (n + 1)) / 2;
           sum = sum-calculate(d,n,1L);
           System.out.println(sum);
       }

    }
}