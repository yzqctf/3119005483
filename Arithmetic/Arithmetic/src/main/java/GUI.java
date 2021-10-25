/**
 * @author yzq
 * @ClassName GUI
 * @Description TODO
 * @date 2021/10/23 19:22
 * @Version 1.0
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.swing.*;


public class GUI extends JFrame implements ActionListener {

    JFrame mainframe;
    JPanel panel;
    // 创建相关的Label标签
    JLabel directoryPath_label = new JLabel("题目文件路径:");
    JLabel outputFilePath_label = new JLabel("答案文件路径:");
    JLabel maxNumber_label = new JLabel("生成题目数量:");
    JLabel rangeValue_label = new JLabel("参数范围:");
    // 创建相关的文本域
    JTextField directoryPath_textField = new JTextField(20);
    JTextField outputFilePath_textField = new JTextField(20);
    JTextField maxNumber_textField = new JTextField(20);
    JTextField rangeValue_textField = new JTextField(20);
    // 创建滚动条以及输出文本域
    JScrollPane jscrollPane;
    JTextArea outText_textarea = new JTextArea();
    // 创建按钮
    JButton directoryPath_button = new JButton("...");
    JButton outputFilePath_button = new JButton("...");
    JButton start_button = new JButton("开始");
    // 创建是否显示结果
    JLabel display_label = new JLabel("功能:");
    String[] listData = new String[]{"生成作业", "检查作业"};
    JComboBox<String> actionCombobox = new JComboBox<>(listData);
    // 成绩输出文件
    String gradePath = "Grade.txt";


    public void main(){
        mainframe = new JFrame("四则运算生成工具");
        // Setting the width and height of frame
        mainframe.setSize(580, 550);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);//固定窗体大小

        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width/2; // 获取屏幕的宽
        int screenHeight = screenSize.height/2; // 获取屏幕的高
        int height = mainframe.getHeight(); //获取窗口高度
        int width = mainframe.getWidth(); //获取窗口宽度
        mainframe.setLocation(screenWidth-width/2, screenHeight-height/2);//将窗口设置到屏幕的中部
        initPanel(); //初始化面板
        mainframe.add(panel);
        mainframe.setVisible(true);
    }


    public void initPanel(){
        this.panel = new JPanel();
        panel.setLayout(null);

        directoryPath_label.setBounds(10,20,120,25);
        directoryPath_textField.setBounds(110,20,390,25);
        directoryPath_button.setBounds(520,20, 30, 25);
        directoryPath_textField.setText("Exercises.txt");
        this.panel.add(directoryPath_label);
        this.panel.add(directoryPath_textField);
        this.panel.add(directoryPath_button);

        outputFilePath_label.setBounds(10,50,120,25);
        outputFilePath_textField.setBounds(110,50,390,25);
        outputFilePath_button.setBounds(520,50, 30, 25);
        outputFilePath_textField.setText("Answers.txt");
        this.panel.add(outputFilePath_label);
        this.panel.add(outputFilePath_textField);
        this.panel.add(outputFilePath_button);

        maxNumber_label.setBounds(10,80,120,25);
        maxNumber_textField.setBounds(110,80,180,25);
        this.panel.add(maxNumber_label);
        this.panel.add(maxNumber_textField);

        rangeValue_label.setBounds(300, 80, 120, 25);
        rangeValue_textField.setBounds(370, 80, 180, 25);
        this.panel.add(rangeValue_label);
        this.panel.add(rangeValue_textField);

        start_button.setBounds(10,120,80,25);
        this.panel.add(start_button);

        outText_textarea.setEditable(false);
        outText_textarea.setFont(new Font("标楷体", Font.BOLD, 16));
        jscrollPane = new JScrollPane(outText_textarea);
        jscrollPane.setBounds(10,170, 550, 330);
        this.panel.add(jscrollPane);

        display_label.setBounds(350, 120, 120, 25);
        actionCombobox.setSelectedIndex(0);
        actionCombobox.setBounds(400,120, 80,25);
        actionCombobox.setFont(new Font("标楷体", Font.TYPE1_FONT, 12));
        this.panel.add(display_label);
        this.panel.add(actionCombobox);

        outText_textarea.setText("使用说明：\n" +
                "1. 作业生成：\n" +
                "功能选择”生成作业“后，设置生成题目输出路径以及答案输出路径以及生成题目数量和题目范围限制(如:10)\n" +
                "PS：\na. 当选择路径时会输出至选中路径下 Answers.txt 和 Exercises.txt，当选择文件则直接输出至文件\n" +
                "b. 在不关闭软件情况下多次生成题目会产生题目叠加效果 多倍的快乐让感受学生多倍快乐\n" +
                "c. 在使用默认值时会输出到本程序所在目录下的Answers.txt 和 Exercises.txt\n" +
                "2. 作业检查：\n" +
                "功能选择”检查作业“后，设置生成题目路径以及答案路径 检查结果将在界面显示 且输出到作业所在目录的Grade.txt文件下");

        directoryPath_button.addActionListener(this);
        outputFilePath_button.addActionListener(this);
        start_button.addActionListener(this);
        actionCombobox.addActionListener(this);
    }
    /**
     * 单击动作触发方法
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println(event.getActionCommand());
        if(event.getSource().equals(start_button)){
            //确认对话框弹出
            int result = JOptionPane.showConfirmDialog(null, "是否开始运行?", "确认", 0);//YES_NO_OPTION
            if (result == 1) { //是：0，否：1，取消：2
                return;
            }
            System.out.println(directoryPath_textField.getText());
            if (directoryPath_textField.getText().equals("") || outputFilePath_textField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "路径不能为空", "提示", 2);//弹出提示对话框，warning
            }else {
                outText_textarea.setText("");
                String directoryPath = directoryPath_textField.getText();
                String outputFilePath = outputFilePath_textField.getText();
                String maxNumber = maxNumber_textField.getText();
                String rangeValue = rangeValue_textField.getText();

                String output = "";

                if (actionCombobox.getSelectedIndex() == 0){
                    try {
                        output = AlgoArithmetic.work(Integer.parseInt(maxNumber), Integer.parseInt(rangeValue), directoryPath, outputFilePath);
                        outText_textarea.setText(output);
                    } catch (IOException e) {
                        output = "生成失败 请检查文件路径是否存在！";
                        System.out.println("运行错误");
                        outText_textarea.setText(output);
                    }
                }else {
                    try {
                        output = AlgoArithmetic.check(directoryPath, outputFilePath, gradePath);
                        outText_textarea.setText(output);
                    } catch (IOException e) {
                        output = "检查失败 请检查文件是否存在！";
                        System.out.println("运行错误");
                        outText_textarea.setText(output);
                    }
                }

            }
        }else if (event.getSource().equals(actionCombobox)){
            if (actionCombobox.getSelectedIndex() == 1){
                maxNumber_textField.setEditable(false);
                rangeValue_textField.setEditable(false);
            }else {
                maxNumber_textField.setEditable(true);
                rangeValue_textField.setEditable(true);
            }
        }else{
            if(event.getSource().equals(directoryPath_button)) {
                File file = openChoseWindow(JFileChooser.FILES_AND_DIRECTORIES);
                if(file == null)
                    return;
                if (file.isDirectory()){
                    directoryPath_textField.setText(file.getAbsolutePath() + "\\Exercises.txt");
                    outputFilePath_textField.setText(file.getAbsolutePath() + "\\Answers.txt");
                    gradePath = file.getAbsolutePath() + "\\Grade.txt";
                }else {
                    directoryPath_textField.setText(file.getAbsolutePath());
                    outputFilePath_textField.setText(file.getParentFile() + "\\Answers.txt");
                    gradePath = file.getParentFile() + "\\Grade.txt";
                }
            }else if(event.getSource().equals(outputFilePath_button)){
                File file = openChoseWindow(JFileChooser.FILES_AND_DIRECTORIES);
                if(file == null)
                    return;
                if (file.isDirectory()){
                    outputFilePath_textField.setText(file.getAbsolutePath() + "\\Answers.txt");
                }else {
                    outputFilePath_textField.setText(file.getAbsolutePath());
                }
            }
        }
    }


    public File openChoseWindow(int type){
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(type);
        jfc.showDialog(new JLabel(), "选择");
        return jfc.getSelectedFile();
    }

}
