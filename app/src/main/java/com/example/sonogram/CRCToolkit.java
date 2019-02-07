package com.example.sonogram;

public class CRCToolkit {
    private static int divisor_bits = 6;
    private static String divisor_s = "101100";

    public static String crc_g(int data_bits, String data_s) {
        int[] data;
        int[] div;
        int[] divisor;
        int[] rem;
        int[] crc;
        int tot_length;

        data = new int[data_bits];

        for (int i = 0; i < data_bits; i++)
            data[i] = Integer.parseInt(Character.toString(data_s.charAt(i)));

        divisor = new int[divisor_bits];

        for (int i = 0; i < divisor_bits; i++)
            divisor[i] = Integer.parseInt(Character.toString(divisor_s.charAt(i)));

        tot_length = data_bits + divisor_bits - 1;

        div = new int[tot_length];
        rem = new int[tot_length];
        crc = new int[tot_length];
        /*------------------ CRC GENERATION-----------------------*/
        for (int i = 0; i < data.length; i++)
            div[i] = data[i];

        for (int j = 0; j < div.length; j++) {
            rem[j] = div[j];
        }

        rem = divide(div, divisor, rem);

        for (int i = 0; i < crc.length; i++)
        {
            crc[i] = (div[i] ^ rem[i]);
        }

        String crc_s="";
        for(int i=data_bits; i < crc.length; i++){
            crc_s=crc_s+(Integer.toString(crc[i]));
        }
        return crc_s;

    }

    public static int error_d(int crc_length, String crc_s)
    {

        int[] crc;
        int[] div;
        int[] divisor;
        int[] rem;
        div = new int[crc_length];
        rem = new int[crc_length];
        crc = new int[crc_length];
        for(int i=0; i<crc_length; i++)
            crc[i] = Integer.parseInt(Character.toString(crc_s.charAt(i)));

        divisor = new int[divisor_bits];

        for (int i = 0; i < divisor_bits; i++)
            divisor[i] = Integer.parseInt(Character.toString(divisor_s.charAt(i)));


        for(int j=0; j<crc.length; j++){
            rem[j] = crc[j];
        }

        rem=divide(crc, divisor, rem);

        for(int i=0; i< rem.length; i++)
        {
            if(rem[i]!=0)
            {
                return 0;
            }
            if(i==rem.length-1){
                return 1;
            }
        }
        return 0;
    }
    public static String strip_crc(String data) {
        return data.substring(0, data.length() - (divisor_bits -1));
    }
    public static int[] divide(int div[], int divisor[], int rem[])
    {
        int cur=0;
        while(true)
        {
            for(int i=0;i<divisor.length;i++)
                rem[cur+i]=(rem[cur+i]^divisor[i]);

            while(rem[cur]==0 && cur!=rem.length-1)
                cur++;

            if((rem.length-cur)<divisor.length)
                break;
        }
        return rem;
    }
}
