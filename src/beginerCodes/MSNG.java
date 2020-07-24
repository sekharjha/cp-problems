package beginerCodes;

import java.util.*;
class MSNG {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int T=s.nextInt();
        while (T-->0) {
            int n = s.nextInt();
            Map<Long,Long> map=new HashMap<>();
            for (int i = 0; i <n ; i++) {
                int base=s.nextInt();
                String str=s.next();
                Set<Long>set=new HashSet<>();
                if(base==-1){
                    for (int j = 2; j <=36 ; j++) {
                        long val=toDeci(str,j);
                        if(val==-2)
                            break;
                        if(val!=-1&&!set.contains(val)){
                            if(map.get(val)==null)
                                map.put(val,1L);
                            else
                                map.put(val,map.get(val)+1);
                            set.add(val);
                        }
                    }
                }
                else{
                    long val=toDeci(str,base);
                    if(val!=-1&&val!=-2){
                        if(map.get(val)==null)
                            map.put(val,1L);
                        else
                            map.put(val,map.get(val)+1);
                    }
                }
            }
            long ans=1000000000001L;
            System.out.println(map);
            for(Map.Entry<Long,Long> v:map.entrySet()){
                if(v.getValue()>=n&&v.getKey()<ans)
                    ans=v.getKey();
            }
            if(ans==1000000000001L)
                System.out.println(-1);
            else
                System.out.println(ans);
        }
    }
    static int val(char c)
    {
        if (c >= '0' && c <= '9')
            return (int)c - '0';
        else
            return (int)c - 'A' + 10;
    }
    static long toDeci(String str,
                       int base)
    {
        //long val=(long)Math.pow(10,12);
        int len = str.length();
        long num = 0;
        int i;

        for (i = len - 1; i >= 0; i--)
        {

            if (val(str.charAt(i)) >= base)
            {
                return -1;
            }
            if(num+val(str.charAt(i))*Math.pow(base,len-i-1)>1000000000000L||num+val(str.charAt(i))*Math.pow(base,len-i-1)<0)
                return -2;
            num += val(str.charAt(i)) * Math.pow(base,len-i-1);
            if(Math.pow(base,len-i-1)>1000000000000L||Math.pow(base,len-i-1)<0)
                return -2;
        }
        return num;
    }
}
