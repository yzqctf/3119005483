import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author yzq
 * @ClassName CalculateSimilarTest
 * @Description TODO
 * @date 2021/9/15 20:08
 * @Version 1.0
 */
public class CalculateSimilarTest {

    @Test
    public static double getSimilar(Reader reader, Reader reader1) throws IOException {
        IKSegmenter ikSegmenter = new IKSegmenter(reader,true);
        IKSegmenter ikSegmenter1 = new IKSegmenter(reader1,true);

        Map<String, Integer> fre = getFrequency(ikSegmenter);
        Map<String, Integer> fre1 = getFrequency(ikSegmenter1);

        //利用words1将两个文本都合在一起，采用HashSet就是为了去重；
        Set<String> words1 = new HashSet<>();  //T(A, B)
        words1.addAll(fre.keySet());
        words1.addAll(fre1.keySet());

        Vector<Integer> FA = new Vector<>();
        Vector<Integer> FB = new Vector<>();

        //构造比较文本的特征向量
        for (String s : words1){
            if (fre.get(s)!=null){
                FA.add(fre.get(s));
            }else
                FA.add(0);
            if (fre1.get(s)!=null)
                FB.add(fre1.get(s));
            else
                FB.add(0);
        }

        //利用余弦进行相似度计算
        int sumA = 0;
        int sumB = 0;
        int sumAB = 0;
        for (int i=0;i<words1.size();i++){
            int a = FA.get(i);
            int b = FB.get(i);
            sumAB += a*b;
            sumA += a*a;
            sumB += b*b;
        }

        double  A = Math.sqrt(sumA);
        double  B = Math.sqrt(sumB);
        BigDecimal AB = BigDecimal.valueOf(A).multiply(BigDecimal.valueOf(B));
        return BigDecimal.valueOf(sumAB).divide(AB, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
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