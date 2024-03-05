package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Frame extends JFrame{
    //CodePanel codePanel = new CodePanel();
    TokenPanel tokenPanel = new TokenPanel();
    ErrorPanel errorPanel = new ErrorPanel();
    CounterPanel counterPanel = new CounterPanel();
    CodePanel codePanel = new CodePanel();
    private String title; 
    public Frame(String title){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.title = title;
        this.setTitle(title);
        addComponents();
    }

    private void addComponents(){
        setSize(1080, 720);
        setLayout(null);
        codePanel.setBounds(0,0,540,360);
        tokenPanel.setBounds(0,360,540,360);
        errorPanel.setBounds(540,360,540,360);
        counterPanel.setBounds(540,0,540,360);
        add(codePanel);
        add(tokenPanel);
        add(errorPanel);
        add(counterPanel);
        
    }
}
