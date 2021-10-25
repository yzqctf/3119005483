import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yzq
 * @ClassName AlgoCalculate
 * @Description 对表达式进行计算，涉及到：整数四则计算；真分数四则运算
 *
 * @date 2021/10/3 0:14
 * @Version 1.0
 */
public class AlgoCalculate {
    public String result;
    public AlgoCalculate(String formula) {
        List<String> rpn = initRPN(formula);
        result = processCalculate(rpn);
    }

    //将formula转化为逆波兰表达式，即后缀表达式
    public static List<String> initRPN(String formula){
        List<String> rpn = new ArrayList<>();
        //存放操作符的栈
        Stack<String> stack = new Stack();
        String operation = "(+-*÷)";
//        System.out.println(formula.length());
        String[] split = formula.split(" ");
        int length = split.length;
        for (int i=0;i<length;i++){
            String str = split[i];
            if (isNumber(str)){
                rpn.add(str);
            }else {
                if (str.equals("(")){
                    //直接入栈
                    stack.push(str);
                }else if (str.equals(")")){
                    //出栈，直到遇到'('或者栈空
                    while (!stack.empty()){
                        String temp = stack.pop();
                        if (!temp.equals("(")){
                            //如果不是左括号
                            rpn.add(temp);
                        }else
                            break;
                    }
                } else {
                    //如果此使栈为空了
                    if (stack.empty())
                        stack.push(str);
                    else {
                        //栈不空就需要进行运算发符比较
                        //compare
                        if (compare(stack.peek())>=compare(str)){
                            while (!stack.empty()&&compare(stack.peek())>=compare(str))
                                rpn.add(stack.pop());
                        }
                        stack.push(str);
                    }
                }
            }

        }
        while (!stack.empty())
            rpn.add(stack.pop());

        return rpn;
    }

    //判断是否是数字，真分数也算是数字
    public static boolean isNumber(String str){
        Pattern p = Pattern.compile("^(-?\\d+)/?(\\d+)?");
        Matcher m = p.matcher(str);
        boolean isNum = m.matches();
        return isNum;
    }

    //运算符优先级判断
    public static int compare(String o1) {
        switch (o1){
            case "+":
            case "-":
                return 1;
            case "*":
            case "÷":
                return 2;
            case "(":
            case ")":
            default:
                return 0;
        }
    }

    //具体的运算需要: 后缀表达式；
    public static String processCalculate(List<String> rpn){
        Stack<String> numberStack = new Stack();;
        int length = rpn.size();

        for (int i=0;i<length;i++){
            //提取后缀表达式
            String temp = rpn.get(i);
            if (isNumber(temp)){
                numberStack.push(temp);
            }else {
                //遇到运算符了，开始计算
                String a = numberStack.pop();
                String b = numberStack.pop();
                String tempNumber = calculate(a,b,temp);
                numberStack.push(tempNumber);
            }
        }

        return numberStack.pop();
    }

    //b 运算 a
    public static String calculate(String a, String b, String symbol){
        String result=null;
        //是否存在分数：
        boolean flag = false;
        //统一形式
        //1.带分数
        if (a.contains("'")){
            String[] str = a.split("'");
            String[] str1 = str[1].split("/");
            int numerator = Integer.parseInt(str[0])*Integer.parseInt(str1[1])+Integer.parseInt(str1[0]);
            a = numerator+"/"+str1[1];
        }
        if (b.contains("'")){
            String[] str = b.split("'");
            String[] str1 =str[1].split("/");
            int numerator = Integer.parseInt(str[0])*Integer.parseInt(str1[1])+Integer.parseInt(str1[0]);
            b = numerator+"/"+str1[1];
        }
        if (a.contains("/")||b.contains("/")) flag=true;
        if (flag){
            boolean w = false;
            if (a.contains("/")&&b.contains("/")) w=true;
            //只有一个分数，转换格式；
            if (!w){
                if (a.contains("/")){
                    String[] x = a.split("/");
                    int numerator = Integer.parseInt(b)*Integer.parseInt(x[1]);
                    b = numerator+"/"+x[1];
                }else if (b.contains("/")){
                    String[] x = b.split("/");
                    int numerator = Integer.parseInt(a)*Integer.parseInt(x[1]);
                    a = numerator+"/"+x[1];
                }
            }
        }
        if (flag){
            int ans=0;
            String[] x = a.split("/");
            String[] x1 = b.split("/");
            if (symbol.equals("*")){
                    //分母
                    int denominator = Integer.parseInt(x[1])*Integer.parseInt(x1[1]);
                    //分子
                    int numerator = Integer.parseInt(x[0])*Integer.parseInt(x1[0]);
                    if (denominator==0||numerator==0)
                        result="0";
                    else
                        result = reduction(denominator,numerator);
                }
            else if (symbol.equals("÷")){
                //两分数相÷
                int denominator = Integer.parseInt(x1[1])*Integer.parseInt(x[0]);
                int numerator = Integer.parseInt(x1[0])*Integer.parseInt(x[1]);
                if (denominator!=0&&numerator!=0)
                    result = reduction(denominator,numerator);
                else
                    result = "0";
            }else if (symbol.equals("+")){
                int denominator = Integer.parseInt(x[1])*Integer.parseInt(x1[1]);
                int numerator = Integer.parseInt(x[0])*Integer.parseInt(x1[1]) + Integer.parseInt(x1[0])*Integer.parseInt(x[1]);
                if (denominator==0||numerator==0)
                    result="0";
                else
                    result = reduction(denominator,numerator);
            }else if (symbol.equals("-")){
                int denominator = Integer.parseInt(x[1])*Integer.parseInt(x1[1]);
                int numerator = Integer.parseInt(x1[0])*Integer.parseInt(x[1]) - Integer.parseInt(x1[1])*Integer.parseInt(x[0]);
                if (numerator==0||denominator==0){
                    result="0";
                }
                else if (numerator<0)
                    result = numerator+"/"+denominator;
                else
                    result = reduction(denominator,numerator);
            }
        }else {
            int tempNumber1 = Integer.parseInt(a);
            int tempNumber2 = Integer.parseInt(b);
            int ans=0;
            if (symbol.equals("+"))
                ans = tempNumber2+tempNumber1;
            else if (symbol.equals("-"))
                ans = tempNumber2-tempNumber1;
            else if (symbol.equals("*"))
                ans = tempNumber2*tempNumber1;
            else if (symbol.equals("÷")){
                if (tempNumber1!=0){
                    if (tempNumber2%tempNumber1==0)
                        ans = tempNumber2/tempNumber1;
                    else {
                        return result = reduction(tempNumber1,tempNumber2);
                    }
                }else {
                    ans=0;
                }
            }
            result = String.valueOf(ans);
        }
        return result;
    }

    //约分,
    public static String reduction(int denominator,int numerator){
        String result = null;
        while (getGCD(denominator,numerator)!=1){
            int gcd = getGCD(denominator,numerator);
            denominator = denominator/gcd;
            numerator = numerator/gcd;
        }
        int time=0;
        if (numerator>denominator){
            time = numerator/denominator;
            numerator=numerator%denominator;
            if (numerator==0)
                result = String.valueOf(time);
            else
                result =time+"'"+ numerator+"/"+denominator;
        }else
            result = numerator+"/"+denominator;

        return result;
    }

    //拓展欧几里得找最大公约数，绝对值
    public static int getGCD(int a,int b){
        //根据辗转相除法（欧几里得算法），当（a，b）r=0时，说明找到了最大公因数
        if (a<0)
            a=Math.abs(a);
        if (b<0)
            b=Math.abs(b);
        if(a<b)
            return getGCD(b,a);
        else  if (a%b==0)
            return b;
        else
            return getGCD(b,a%b);
    }
}
