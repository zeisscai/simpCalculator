/*
版本号：0.5
原文件名：calculator.java

*/
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class calculator extends JFrame implements ActionListener {
    /**
     * ActionListener是Java中关于事件处理的一个接口，继承自EventListener。
     * ActionListener用于接收操作事件的侦听器接口。对处理操作事件感兴趣的类可以实现此接口，
     * 而使用该类创建的对象可使用组件的 addActionListener 方法向该组件注册。在发生操作事件时，
     * 调用该对象的 actionPerformed 方法。
    */


    /*参数*/
    private String lastCommand = "=";           //保存+,-,*,/,=命令0
    private double result = 0;               //保存计算结果
    private boolean start = true;           //判断是否为数字的开始

    private Dialog dialog;
    private Label label_dialog;

    /*显示*/
    private JTextField display1,display2,display3;
    /*按钮*/
    private JButton btn[];//创建按钮数组
    private String name[]={//创建按钮数组对应的名字
            "MC","MR","M+","M-","MS",
            "x²","x^y","sin","cos","tan",
            "√","CE","C","←","÷",
            "exp","7","8","9","×",
            "log","4","5","6","-",
            "n!","1","2","3","+",
            "π","±","0",".","="
    };
    /*布局*/
    public calculator(){
        super("simpCalculator");//窗口的名称
        this.setLayout(null);//
        this.setSize(300,440);//窗口大小
        this.setResizable(false);//窗口是否可以调整大小：否
        JPanel panel = new JPanel(null);//创建面板
        panel.setBounds(10,20,getWidth()-24, getHeight());//设置
        panel.setBackground(new Color(217,228,241));
        this.getContentPane().setBackground(new Color(217,228,241));



        display1=new JTextField("");
        display2=new JTextField("0");
        display3=new JTextField("");
        display1.setEnabled(false);
        display2.setEnabled(false);
        display3.setEnabled(false);
        display1.setBounds(0, 0, 266, 26);
        display2.setBounds(26, 26, 240, 34);
        display3.setBounds(0, 26, 26, 34);
        display1.setHorizontalAlignment(JLabel.RIGHT);//設定水平方向的對齊，里面的文字，0
        display2.setHorizontalAlignment(JLabel.RIGHT);
        display3.setHorizontalAlignment(JLabel.CENTER);
        display1.setFont(new Font("宋体",Font.PLAIN,12));
        display2.setFont(new Font("宋体",Font.BOLD,20));
        display3.setFont(new Font("宋体",Font.PLAIN,20));
        display1.setDisabledTextColor(Color.BLACK);
        display2.setDisabledTextColor(Color.BLACK);
        display3.setDisabledTextColor(Color.BLACK);
        display1.setBorder(new LineBorder(new Color(0, 0, 0)));
        display2.setBorder(new LineBorder(new Color(0, 0, 0)));
        display3.setBorder(new LineBorder(new Color(0, 0, 0)));
        display1.setBackground(new Color(242,247,252));
        display2.setBackground(new Color(242,247,252));
        display3.setBackground(new Color(242,247,252));
        panel.add(display1);
        panel.add(display2);
        panel.add(display3);

        this.add(panel);
        btn = new JButton[name.length+1];
        for(int i=0;i<name.length;i++){
            btn[i] = new JButton(name[i]);
            btn[i].setMargin(new java.awt.Insets(0,0,0,0));//里面文字到按钮边框的距离
            btn[i].setBounds(i%5*(50+4), 60+i/5*(40+3)+15, 50, 40);//创建50*40的按钮
            btn[i].addActionListener(this);
            panel.add(btn[i]);
        }


        this.add(panel);
        this.addWindowListener(new WinClose());
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {


        //按钮的单击事件处理方法
        if(
                e.getSource().equals( btn[11] )||e.getSource().equals( btn[12] )||e.getSource().equals( btn[13] )||
                e.getSource().equals( btn[16] )||e.getSource().equals( btn[17] )||e.getSource().equals( btn[18] )||
                e.getSource().equals( btn[21] )||e.getSource().equals( btn[22] )||e.getSource().equals( btn[23] )||
                e.getSource().equals( btn[26] )||e.getSource().equals( btn[27] )||e.getSource().equals( btn[28] )||
                e.getSource().equals( btn[31] )||e.getSource().equals( btn[32] )||e.getSource().equals( btn[33] )
        )
        {      //非运算符的处理方法
            String input = e.getActionCommand();

            if ( start )//默认true
            {
                display1.setText("");
                start = false;
                if( input.equals( "±" ) )
                    display1.setText( display1.getText()+ "-" );
            }

            if( !input.equals( "±" ) )
            {
                String str = display1.getText();
                if( input.equals( "←" ) )          //退格键的实现方法
                {
                    if( str.length() > 0 )
                        display1.setText( str.substring( 0,str.length() - 1 ) );

                    else
                        display3.setText("E1");
                }
                else if( input.equals( "C" ) )         //清零键的实现方法
                {
                    display1.setText("");
                    display2.setText("0");
                    display3.setText("");
                    start = true;
                }
                else
                    display1.setText( display1.getText() + input );
            }
        }


        else        //各运算符的识别
        {
            String command = e.getActionCommand();
            if( start )
            {
                lastCommand = command;
                display1.setText( display1.getText() + command );
            }
            else
            {
                calculate( Double.parseDouble( display1.getText() ) );
                lastCommand = command;
                display1.setText( display1.getText() + command );
                start = true;

            }
        }

    }

    public void calculate(double x) {
        //个运算符的具体运算方法
        double d = 0;
        if ( lastCommand.equals( "+" ) )
            result += x;
        else if (lastCommand.equals( "-" ) )
            result -= x;
        else if ( lastCommand.equals( "×" ) )
            result *= x;
        else if ( lastCommand.equals( "÷" ) )
            result /= x;
        else if ( lastCommand.equals( "=" ) )
            result = x;
        else if ( lastCommand.equals( "√" ) )
        {
            d = Math.sqrt( x );
            result = d;
        }
        else if ( lastCommand.equals( "exp" ) )
        {
            d = Math.exp( x );
            result = d;
        }
        else if ( lastCommand.equals( "log" ) )
        {
            d = Math.log( x );
            result = d;
        }
        else if ( lastCommand.equals( "tan" ) )
        {
            d = Math.tan(x);
            result = d;
        }
        else if ( lastCommand.equals( "cos" ) )
        {
            d = Math.cos( x );
            result = d;
        }
        else if ( lastCommand.equals( "sin" ) )
        {
            d = Math.sin( x );
            result = d;
        }
        display2.setText( ""+ result );
    }

    public void windowClosing(WindowEvent e) {
        if (e.getSource() == dialog)
            dialog.setVisible(false);           //隐藏对话框
        else
            System.exit(0);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }


    public static void main(String args[]){
        calculator calculator = new calculator();
    }
}

class WinClose implements WindowListener
{
    public void windowClosing( WindowEvent e )    //单击窗口关闭按钮时触发并执行实现窗口监听器接口中的方法
    {
        System.exit( 0 );          //结束程序运行
    }
    public void windowOpened( WindowEvent e ){ }
    public void windowActivated( WindowEvent e ){}
    public void windowDeactivated( WindowEvent e){ }
    public void windowClosed( WindowEvent e ){ }
    public void windowIconified( WindowEvent e ){ }
    public void windowDeiconified( WindowEvent e ){ }
}