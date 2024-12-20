package com.SchoolProject;
//filename:StuAddDiag.java
//package SchoolProject.src;
import javax.swing.JDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.lang.*;
public class StuAddDiag extends JDialog implements ActionListener {
    // 定义我需要的swing组件
    JLabel jl1, jl2, jl3, jl4, jl5, jl6;
    JTextField jf1, jf2, jf3, jf4, jf5, jf6;
    JPanel jp1, jp2, jp3;
    JButton jb1, jb2;

    // owner代笔父窗口,title是窗口的名字,modal指定是模式窗口()或者非模式窗口
    public StuAddDiag(Frame owner, String title, boolean modal) {
        // 调用父类方法
        super(owner, title, modal);

        jl1 = new JLabel("学号");
        jl2 = new JLabel("名字");
        jl3 = new JLabel("性别");
        jl4 = new JLabel("年龄");
        jl5 = new JLabel("专业");
        jl6 = new JLabel("院系");

        jf1 = new JTextField(10);
        jf2 = new JTextField(10);
        jf3 = new JTextField(10);
        jf4 = new JTextField(10);
        jf5 = new JTextField(10);
        jf6 = new JTextField(10);

        jb1 = new JButton("添加");
        jb1.addActionListener(this);
        jb2 = new JButton("取消");

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        // 设置布局
        jp1.setLayout(new GridLayout(6, 1));
        jp2.setLayout(new GridLayout(6, 1));

        jp3.add(jb1);
        jp3.add(jb2);

        jp1.add(jl1);
        jp1.add(jl2);
        jp1.add(jl3);
        jp1.add(jl4);
        jp1.add(jl5);
        jp1.add(jl6);

        jp2.add(jf1);
        jp2.add(jf2);
        jp2.add(jf3);
        jp2.add(jf4);
        jp2.add(jf5);
        jp2.add(jf6);

        this.add(jp1, BorderLayout.WEST);
        this.add(jp2, BorderLayout.CENTER);
        this.add(jp3, BorderLayout.SOUTH);

        this.setSize(300, 200);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == jb1) {
            Connection ct = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                // 1.加载驱动
                // 8版本MySQL使用的是"com.mysql.cj.jdbc.Driver"，5版本使用的是"com.mysql.jdbc.Driver".
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("加载成功");
                // 2.连接数据库
                // 定义几个常量
                // 8版本需要加"?useUnicode=true&characterEncoding=utf8&nullCatalogMeansCurrent=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC"
                // 其中需要设置nullCatalogMeansCurrent=true，出现这种问题的原因是mysql版本问题，mysql8.xxx以上驱动会出现这个问题，下图是我原mysql配置，是8.0.16的。
                // 一开始因为使用的版本不一致导致总是连接不成功。后来驱动版本与数据库版本都统一之后连接成功。
                String url = "jdbc:mysql://localhost:3306/stu?useUnicode=true&characterEncoding=utf8&nullCatalogMeansCurrent=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
                String user = "root";
                String passwd = "123456";
                ct = DriverManager.getConnection(url, user, passwd);

                // 插入语句
                String strsql = "insert into stu values(?,?,?,?,?,?)";
                pstmt = ct.prepareStatement(strsql);

                // 给对象赋值
                pstmt.setString(1, jf1.getText());
                pstmt.setString(2, jf2.getText());
                pstmt.setString(3, jf3.getText());
                pstmt.setString(4, jf4.getText());
                pstmt.setString(5, jf5.getText());
                pstmt.setString(6, jf6.getText());

                pstmt.executeUpdate();

                this.dispose();// 关闭学生对话框

            } catch (Exception arg1) {
                arg1.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (pstmt != null) {
                        pstmt.close();
                        pstmt = null;
                    }
                    if (ct != null) {
                        ct.close();
                        ct = null;
                    }
                } catch (Exception arg2) {
                    arg2.printStackTrace();
                }
            }

        }

    }

}

