import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;
import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * @author yzq
 * @ClassName mainTest
 * @Description TODO
 * @date 2021/9/15 19:54
 * @Version 1.0
 */
public class mainTest {
    @org.junit.Test
    //测试代码是否能正常运行，小文本：29K
    public void main() throws IOException {
        String f1 = "D:\\学习资料\\softwareProject\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\orig_0.8_add.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //相对较大文本165K
    public void testLargeTxt() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig_0.8_dis_15.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //测试文本为空的情况
    public void testNullTxt() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\null.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //同一文件
    public void testTheSame() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //路径有误
    public void testTheRoute() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\softwareProject\\测试文本\\orig.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //不同文本, ADD
    public void testADD() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig_0.8_add.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //del
    public void testDel() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig_0.8_del.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //dis_1
    public void testDis() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig_0.8_dis_1.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //dis_10
    public void testDis10() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig_0.8_dis_10.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //dis_15
    public void testDis15() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig_0.8_dis_15.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //测试子集sub
    public void testSub() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig_sub_1.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

    @org.junit.Test
    //测试子集sub2
    public void testSub2() throws IOException{
        String f1 = "D:\\学习资料\\softwareProject\\测试文本\\orig.txt";
        String f2 = "D:\\学习资料\\softwareProject\\测试文本\\orig_sub_2.txt";
        String f3 = "D:\\学习资料\\softwareProject\\WorkWithConsineVector\\out\\output.txt";
        main.getAnalysisResult(f1,f2,f3);
    }

}