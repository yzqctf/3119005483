package Test;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * IKAnalyzer 分词器测试
 * @author Luxh
 */
public class Test {

    public static void main(String[] args) throws Exception {

        //IKAnalyzer analyzer = new IKAnalyzer();

        Long startTime = System.currentTimeMillis();
        Long endTime;

        FileReader reader = new FileReader(".//测试文本//orig.txt");

//        FileReader reader1 = new FileReader("orig.txt");
//        FileReader reader1 = new FileReader("orig_0.8_add.txt");
//        FileReader reader1 = new FileReader("orig_0.8_del.txt");
//        FileReader reader1 = new FileReader("orig_0.8_dis_1.txt");
//        FileReader reader1 = new FileReader("orig_0.8_dis_10.txt");
        FileReader reader1 = new FileReader(".//测试文本//orig_0.8_dis_15.txt");

        IKSegmenter segmenter = new IKSegmenter(reader,true);

        IKSegmenter segmenter1 = new IKSegmenter(reader1,true);

        Map<String,Integer> fre = getFrequency(segmenter);

        Map<String,Integer> fre1 = getFrequency(segmenter1);

        String s1 = SimHash.getSimHash(fre);
        String s2 = SimHash.getSimHash(fre1);

//        System.out.println(s1);
//        System.out.println(s2);

        double sim = HammingDistance.getSimilarity(s1,s2);

        System.out.println(sim*100+"%");

        endTime = System.currentTimeMillis();
        //System.out.println(frequency.keySet().size());
        System.out.println(endTime-startTime);
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