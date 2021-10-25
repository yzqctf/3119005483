import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.*;

/**
 * @author yzq
 * @ClassName AlgoArithmetic
 * @Description 四则运算题目生成器，
 *              1.真分数；2.运算符个数不超过3个；3.题目不能重复（理解起来有点问题）；4.生产题目文件存在当前目录下的Exercises.txt；
 *              5.计算出所有题目的答案并存到当前目录下的Answers.txt中；6.能够支持10000道题目的生成；7.判断答案的对错并进行数量统计
 *              8.运算结果不能是负数；9.除法的输出结果是真分数。
 * @param -n: the number of generating problems
 * @param -r: the range of value
 * @date 2021/10/1 2:13
 * @Version 1.0
 */
public class AlgoArithmetic {
    //题目数
    private static int NUMBER_PROBLEMS = 0;
    //生成值范围
    private static int RANGEVALUE = 0;

    //每一道formula 中的运算符不能超过3个
    private static int OPERATORNUMBER = 3;

    //保存题目,即输出到题目文件
    public static List<String> FORMULA = new ArrayList<>();
    //输出到答案文件,key：result，value: formula
    public static Map<String,String> out = new HashMap<>();

    private static String[] BRACKETS = {"(",")"};

    public AlgoArithmetic(int n, int r){
        this.NUMBER_PROBLEMS = n;
        this.RANGEVALUE = r;
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        long end;
        work(100, 10, "Answers.txt", "Exercises.txt");
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }


    public static String work(int workNumber, int rangeValue, String answersPath, String exercisesPath) throws IOException {
        NUMBER_PROBLEMS = workNumber;
        RANGEVALUE = rangeValue;
        Random random = new Random();
        int numOfBracketProblems = random.nextInt(NUMBER_PROBLEMS-1)+1;
        System.out.println("带括号的题目数： " + numOfBracketProblems);
        NUMBER_PROBLEMS -= numOfBracketProblems;
        while (numOfBracketProblems!=0){
            String formula = finalFormula(true);
            //是否重复
            boolean ok = end(formula);
            if (!ok)
                continue;
            numOfBracketProblems--;
        }
        while (NUMBER_PROBLEMS!=0){
            String formula = finalFormula(false);
            boolean ok = end(formula);
            if (!ok)
                continue;
            NUMBER_PROBLEMS--;
        }
        print1(answersPath);
        print(exercisesPath);
        return String.format("生成题目成功\n生成作业路径：%s\n生成答案路径：%s", exercisesPath, answersPath);
    }

    //生成随机数，范围可控，根据输入的-r进行判断
    public static String getRandomNumber() {
        //不能生成负数，所以简单了
        String num = new String();
        Random random = new Random();
        if (random.nextInt(2)==0){
            num = String.valueOf(random.nextInt(RANGEVALUE)+1);
        }else {
            //分子
            int molecule = random.nextInt(RANGEVALUE)+1;
            //分母
            int denominator = random.nextInt(RANGEVALUE)+1+molecule;
            num = molecule+"/"+denominator;
        }
        return num;
    }
    //随机生成运算符,
    public static String getRandomOperator(){
        Random random = new Random();
        String[] operations = {"+","-","*","÷"};

        return operations[random.nextInt(4)];
    }

    //利用生成的随机数与运算符生成表达式
    public static String finalFormula(boolean f){
        String formula = new String();
        Random random = new Random();
        int numberOfOperation = random.nextInt(OPERATORNUMBER)+1;
        if (numberOfOperation==1){
            formula += getRandomNumber();
            formula +=" " + getRandomOperator();
            formula +=" " + getRandomNumber();
            numberOfOperation--;
            return formula;
        }
        if (!f){
            //不需要括号
            formula += getRandomNumber();
            while (numberOfOperation!=0){
                formula +=" " + getRandomOperator();
                formula +=" " + getRandomNumber();
                numberOfOperation--;
            }
        }else {
            int first = random.nextInt(2);
//            System.out.println("括号位置："+first);
            if (first==0){
                formula += BRACKETS[0];
                formula +=" "+ getRandomNumber();
                formula +=" " + getRandomOperator();
                formula +=" " + getRandomNumber();
                formula +=" "+ BRACKETS[1];
                formula +=" " + getRandomOperator();
                formula +=" " + getRandomNumber();
            }else if (first==1){
                formula += getRandomNumber();
                formula +=" " + getRandomOperator();
                formula +=" "+BRACKETS[0];
                formula +=" "+ getRandomNumber();
                formula +=" " + getRandomOperator();
                formula +=" " + getRandomNumber();
                formula +=" "+ BRACKETS[1];
            }
        }
        return formula;
    }
    /*
     * 去重：
     * 1. 通过结果是否相同进行第一次判断
     * 2. 如果结果相同，比较运算符数，
     * 2. 比较运算数
     */

    public static boolean end(String formula){
        String re = new AlgoCalculate(formula).result;
        if (re==null){
            re="0";
        }
        if (re.contains("-"))
            return false;
        //判断重复
        if (out.containsKey(re))
            return false;
        FORMULA.add(formula);
        out.put(re,formula);
        return true;
    }

    public static void print(String path) throws IOException {
        File output = new File(path);
        FileWriter fw = new FileWriter(output,false);
        int count=0;
        for (String k:out.keySet()){
            count++;
            fw.write("第"+count+"题："+k);
            fw.write("\r\n");
        }
        fw.flush();
        fw.close();
    }
    public static void print1(String path) throws IOException {
        File output = new File(path);
        FileWriter fw = new FileWriter(output,false);
        int count=0;
        for (String k:out.keySet()){
            count++;
            fw.write("第"+count+"题："+out.get(k)+" = ");
            fw.write("\r\n");
        }
        fw.flush();
        fw.close();
    }

    //判断correct题目数
    public static String check(String path,String ans, String output) throws IOException {
        String thisLine,
        thisLine1;
        BufferedReader reader = null;
        FileInputStream inputStream = new FileInputStream(path);
        reader = new BufferedReader(new InputStreamReader(inputStream));
        //key；答案; value：题目序号
        //读取作业文件
        Map<Integer,String> answer = new HashMap<>();
        int num=0;
        while ((thisLine=reader.readLine())!=null){
            num++;
            String[] tokens = thisLine.split("=");
            if (tokens.length > 1 && !tokens[1].replaceAll(" ", "").equals("")) {
                answer.put(num, tokens[1].replaceAll(" ", ""));
            }else {
                answer.put(num,"-1");
            }
        }
        inputStream.close();
        reader.close();

        //读取答案文件
        inputStream = new FileInputStream(ans);
        reader = new BufferedReader(new InputStreamReader(inputStream));
        Map<Integer,String> correctAnswer = new HashMap<>();
        int num1=0;
        while ((thisLine1=reader.readLine())!=null){
            num1++;
            String[] token1 = thisLine1.split("：");
            correctAnswer.put(num1,token1[1].replaceAll(" ",""));
        }
        inputStream.close();
        reader.close();


        // 错误的题目
        int InCorrectNum = 0;
        List<Integer> order = new ArrayList<>();
        // 正确的题目序号
        int correctNum = 0;
        List<Integer> correct = new ArrayList<>();
        for (Integer i:answer.keySet()){
            String s1 = answer.get(i);
            String s2 = correctAnswer.get(i);
            if (!s1.equals(s2)){
                InCorrectNum++;
                order.add(i);
            }else {
                correctNum++;
                correct.add(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Correct:").append(InCorrectNum);
        sb.append("(");
        for (Integer i:
                order) {
            sb.append(i);
            if (!order.get(order.size()-1).equals(i))
                sb.append(",");
        }
        sb.append(")\n");
        sb.append("Wrong:").append(correctNum);
        sb.append("(");
        for (Integer i:
                correct) {
            sb.append(i);
            if (!correct.get(correct.size()-1).equals(i))
                sb.append(",");
        }
        sb.append(")\n");
        FileWriter fw = new FileWriter(output);
        fw.write(sb.toString());
        fw.flush();
        fw.close();
        sb.append("\n输出文件至所选目录下Grade.txt中");
        System.out.println(sb);
        return sb.toString();
    }

}
