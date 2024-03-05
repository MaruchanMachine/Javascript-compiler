package view;

import java.awt.Color;

import javax.swing.JPanel;

public class ErrorPanel extends JPanel{

    public ErrorPanel(){
        initPanel();

    }

    private void initPanel(){
        this.setBackground(Color.yellow);
        this.setVisible(true);
    }

}
