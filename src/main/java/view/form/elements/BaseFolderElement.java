package view.form.elements;

import view.elements.ViewElements;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class BaseFolderElement extends ViewElements {

    private final JTextField baseFolderPath;
    private final JLabel baseFolderPathLabel;
    private final JButton baseFolderPathSelector;

    public BaseFolderElement() {
        baseFolderPath = getViewTextFieldWritten("D:\\Users\\nb");
        baseFolderPathLabel = getViewLabel("Set project base folder path:");
        baseFolderPathSelector = getViewButton("Select folder");
    }

    public void createBaseFolderElementTab(JTabbedPane tabbedPane) {
        JPanel viewPanel = getViewPanel();
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(baseFolderPathLabel)
                        .addComponent(baseFolderPath)
                )
                .addComponent(baseFolderPathSelector)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(baseFolderPathLabel)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(baseFolderPath)
                        .addComponent(baseFolderPathSelector)
                )
        );
        tabbedPane.add("Base Folder", viewPanel);
    }

    public String getBaseFolderPath() {
        return baseFolderPath.getText();
    }

    public void setBaseFolderPath(String text) {
        baseFolderPath.setText(text);
    }

    public void openFolderChooser(Container windowContentPane) {
        baseFolderPathSelector.addActionListener(
                e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.setPreferredSize(
                            new Dimension(
                                    windowContentPane.getWidth() - 75,
                                    windowContentPane.getHeight() - 100
                            )
                    );
                    int lastOccurrenceBackSlash = baseFolderPath.getText().lastIndexOf("\\");
                    String currentPath = baseFolderPath.getText().substring(0, lastOccurrenceBackSlash);
                    fileChooser.setCurrentDirectory(
                            new File(currentPath)
                    );
                    int option = fileChooser.showOpenDialog(windowContentPane);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File dir = fileChooser.getSelectedFile();
                        baseFolderPath.setText(dir.getAbsolutePath());
                        windowContentPane.revalidate();
                        windowContentPane.repaint();
                    }
                }
        );
    }

}