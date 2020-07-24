package beginerCodes;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoofishMatrix {

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

    public static void main(String[] args) throws Exception
    {
        Reader s=new Reader();

        int t=s.nextInt();

        for(int i=0;i<t;i++)
        {
            int n=s.nextInt();
            int m=s.nextInt();

            ArrayList<Long> list=new ArrayList<>();

            for(int j=0;j<n;j++)
            {
                list.add(s.nextLong());
            }

            HashMap<Long,Long> map=new HashMap<>();

            for(int j=0;j<n;j++)
            {
                factorize(list.get(j),map);
            }



            long max=Long.MIN_VALUE;
            long ans=1;

            for(long j=1;j<=m;j++)
            {
                long val=find(j,map);

                if(val>max)
                {
                    max=val;
                    ans=j;
                }
            }

            System.out.println(ans);

        }
    }

    public static void factorize(long num,HashMap<Long,Long> map)
    {
        HashMap<Long,Long> temp=new HashMap<>();

        for(long i=2;i<=Math.sqrt(num);i++)
        {
            long count=0;

            while(num%i==0)
            {
                num=num/i;
                count++;
            }

            if(count>0)
                temp.put(i, count);
        }

        if(num>1)
            temp.put(num,1l);

        for(Map.Entry<Long,Long> entry:temp.entrySet())
        {
            long factor=entry.getKey();
            long count=entry.getValue();

            if(map.containsKey(factor))
            {
                long curr=map.get(factor);

                map.put(factor,Math.max(curr,count));
            }
            else
            {
                map.put(factor,count);
            }
        }
    }

    public static long find(long num,HashMap<Long,Long> map)
    {
        long ans=1;
        long ori=num;

        HashMap<Long,Long> temp=new HashMap<>();

        for(long i=2;i<=Math.sqrt(num);i++)
        {
            long count=0;

            while(num%i==0)
            {
                num=num/i;
                count++;
            }

            if(count>0)
                temp.put(i, count);
        }

        if(num>1)
            temp.put(num,1l);

        for(Map.Entry<Long,Long> entry:temp.entrySet())
        {
            long factor=entry.getKey();
            long count=entry.getValue();

            if(map.containsKey(factor))
            {
                long curr=map.get(factor);

                ans=ans*power(factor,Math.min(curr, count));
            }
        }

        return ori/ans;
    }

    public static long power(long a,long b)
    {
        long res=1;

        while(b>0)
        {
            if(b%2!=0)
            {
                res=res*a;
            }
            b=b/2;
            a=a*a;
        }

        return res;
    }

}