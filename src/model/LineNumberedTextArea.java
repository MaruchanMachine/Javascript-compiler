package model;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LineNumberedTextArea extends JTextArea {

    private JTextArea mainTextArea;
    private LinkedList<String> linesList;

    public LineNumberedTextArea(JTextArea textArea, LinkedList<String> linesList) {
        super();
        this.mainTextArea = textArea;
        this.linesList = linesList;

        setEditable(false);
        setBorder(new EmptyBorder(0, 5, 0, 5));
        setFont(textArea.getFont());
        setBackground(Color.LIGHT_GRAY);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
                updateLinesList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
                updateLinesList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
                updateLinesList();
            }
        });

        updateLineNumbers();
        updateLinesList();
    }

    private void updateLineNumbers() {
        String lineNumbersText = getLineNumbersText();
        setText(lineNumbersText);
    }

    private String getLineNumbersText() {
        int totalLines = mainTextArea.getLineCount();
        int digits = Math.max(String.valueOf(totalLines).length(), 2);
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= totalLines; i++) {
            sb.append(String.format("%1$" + digits + "s", i)).append("\n");
        }

        return sb.toString();
    }

    private void updateLinesList() {
        String[] lines = mainTextArea.getText().split("\n");
        linesList.clear();
        for (String line : lines) {
            linesList.add(line);
        }
    }
}
