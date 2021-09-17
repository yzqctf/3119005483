import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzq
 * @ClassName SimHash
 * @Description By calculating the Hamming distance to estimate the similar of the detect text
 * @date 2021/9/10 21:54
 * @Version 1.0
 */
public class SimHash {

    // first get the hash of the words
    public static String getHash(String str){
        try{
            // 这里使用了MD5获得hash值
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //this radix mean the difference of number
            return new BigInteger(1, messageDigest.digest(str.getBytes("UTF-8"))).toString(2);
        }catch(Exception e){
            e.printStackTrace();
            return str;
        }
    }
    //
    public static String getSimHash(Map<String,Integer> fre) throws IOException {
//        String simHash = "";
        int[] vector = new int[128];

        for (String word : fre.keySet()){
            //1. 获取自定义的哈希值
            String hash = getHash(word);
            //如果位数不够那就补位，为了不影响实际情况，补0，1有特殊的意义
            if (hash.length()<128){
                int bit = 128 - hash.length();
                for (int i=0; i<bit; i++){
                    hash += "0";
                }
            }

            //2 and 3：这里既是加权，也是合并，因为只对vector这一个向量进行操作
            for (int j=0; j<vector.length;j++){
                //只有在1的地方进行加权
                if (hash.charAt(j)=='1'){
                    vector[j] += fre.get(word);
                }else {
                    vector[j] -= fre.get(word);
                }
            }
        }

        //4：降维
        String simHash = "";
        for (int j=0; j<vector.length;j++){
            if (vector[j]>0){
                simHash += "1";
            } else {
                simHash += "0";
            }
        }
        return simHash;
    }

    public static double Construct(Reader reader,Reader reader1) throws IOException {
        IKSegmenter ikSegmenter = new IKSegmenter(reader,true);
        IKSegmenter ikSegmenter1 = new IKSegmenter(reader1,true);

        Map<String, Integer> fre = getFrequency(ikSegmenter);
        Map<String, Integer> fre1 = getFrequency(ikSegmenter1);

        String s1 = SimHash.getSimHash(fre);
        String s2 = SimHash.getSimHash(fre1);

        return HammingDistance.getSimilarity(s1,s2);
    }

    private static Map<String,Integer> getFrequency(IKSegmenter segmenter) throws IOException {
        Map<String, Integer> frequency = new HashMap<>();

        Lexeme word = segmenter.next();
        while(word!=null){
            String str = word.getLexemeText();
            if (frequency.get(str)==null){
                frequency.put(str,1);
            }else {
                int num = frequency.get(str);
                num++;
                frequency.put(str,num);
            }
            word = segmenter.next();
        }

        return frequency;
    }
}
