package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.LineNumberedTextArea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class CodePanel extends JPanel {

    private LinkedList<String> linesList;
    private JTextArea textArea;

    public CodePanel() {
        // Inicialización de la LinkedList y el JTextArea
        linesList = new LinkedList<>();
        textArea = new JTextArea();
        LineNumberedTextArea lineNumberedTextArea = new LineNumberedTextArea(textArea, linesList);

        // Configuración del JScrollPane para el JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(lineNumberedTextArea);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        // Botón "Abrir" y su ActionListener
        JButton openButton = new JButton("Abrir");
        openButton.addActionListener(e -> openFile());
        JButton compButton = new JButton("Compilar");
        compButton.addActionListener(null);
        JButton generarButton = new JButton("Generar EXCEL");
        generarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                generarExcel();
            }
        });
        

        // Panel para el botón "Abrir"
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(compButton);
        buttonPanel.add(generarButton);

        // Se agrega el panel con el botón en la parte inferior
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void openFile() {
        // Crear un JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        // Mostrar el diálogo para seleccionar un archivo
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Leer el contenido del archivo y establecerlo en el JTextArea
                String content = readFile(selectedFile);
                textArea.setText(content);
            } catch (IOException e) {
                // Mostrar un mensaje de error si ocurre un problema al leer el archivo
                JOptionPane.showMessageDialog(this, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private void generarExcel(){
        try (Workbook workbook = new XSSFWorkbook()) {
            createSheet(workbook, "Tokens");
            createSheet(workbook, "Errores");
            createSheet(workbook, "Contadores");

            try (FileOutputStream fileOut = new FileOutputStream("output.xlsx")) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(this, "Archivo Excel generado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar el archivo Excel", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);

        // Agregar datos de ejemplo a las hojas
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Columna 1");
        headerRow.createCell(1).setCellValue("Columna 2");

        for (int i = 1; i <= 10; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue("Dato " + i + "A");
            row.createCell(1).setCellValue("Dato " + i + "B");
        }
    }

}
