import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzq
 * @ClassName main
 * @Description
 * @date 2021/9/11 9:56
 * @Version 1.0
 */
public class main {
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();

        if (args.length>3){
            System.out.println("一次只能比较两个文本哦");
            System.exit(1);
        }else if (args.length<=0){
            System.out.println("请输入待比较文件");
            System.exit(1);
        }else if (args.length!=3){
            System.out.println("参数缺失，请按照格式输入哦");
            System.exit(1);
        }

        String F1 = args[0];
        String F2 = args[1];
        String F3 = args[2];

        if (F1==null || F2==null ||F3==null){
            System.out.println("文件路径不能为空喔");
        }else {
            getAnalysisResult(F1,F2,F3);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The analysis time is " + (endTime-startTime) + "ms");
    }

    public static void getAnalysisResult(String s1, String s2, String s3) throws IOException {
        //输出的单位：B字节
        //System.out.println(f.length());

        //when the size of the file is small we use the Consine to obtain the similarity (accuracy)
        // and is large we use the simHash to get that (fast)
        try{
            File f = new File(s1);
            File f1 = new File(s2);
            FileReader reader = new FileReader(f);
            FileReader reader1 = new FileReader(f1);


            if (f.length()!=0 && f1.length()!=0){
                if (f.length()<=30000){
                    double sim = 100* CalculateSimilar.getSimilar(reader,reader1);
                    DecimalFormat df = new DecimalFormat();
                    String s = df.format(sim);
                    System.out.println(s+"%");
                    writeResultToFile(s1,s2,s3,s);
                }else {
                    double sim = SimHash.Construct(reader,reader1);
                    System.out.println("The analysis result is "+sim*100+"%");
                    writeResultToFile(s1,s2,s3,String.valueOf(sim));
                }
            }else {
                System.out.println("文件为空，请重新输入比较文本");
                System.exit(1);
            }
        }catch (Exception e){
            System.out.println("文件路径有误，请重新输入");
        }
    }

    public static void writeResultToFile(String origin, String compared,String path, String result) throws IOException {
        FileWriter writer = new FileWriter(new File(path),true);
        if (writer!=null){
            StringBuilder buffer = new StringBuilder();
            buffer.append("The origin text:");
            buffer.append(origin);
            buffer.append("\r\n");
            buffer.append("The compared text:");
            buffer.append(compared);
            buffer.append("\r\n");
            buffer.append("The compare result is: ");
            buffer.append(result+"%");
            buffer.append("\r\n");
            buffer.append("\r\n");
            writer.write(buffer.toString());
            writer.flush();
        }else {
            System.out.println("文件写入失败，请检查输入的文件是否存在...");
        }
        writer.close();
    }
}
