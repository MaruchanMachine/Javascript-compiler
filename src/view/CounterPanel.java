package view;

import java.awt.Color;

import javax.swing.JPanel;

public class CounterPanel extends JPanel{

    public CounterPanel(){
         initPanel();

    }

    private void initPanel(){
        this.setBackground(Color.green);
        this.setVisible(true);
    }
}
