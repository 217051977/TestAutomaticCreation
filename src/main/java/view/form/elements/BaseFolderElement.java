package view.form.elements;

import view.elements.ViewElements;

import javax.swing.*;

import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.BASELINE;

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

}