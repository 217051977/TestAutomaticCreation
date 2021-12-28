package view.elements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ViewElements {

    protected ViewElements() {}

    public static JButton getViewButton(String text) {
        JButton viewButton = createJButton(/**/);
        viewButton.setFont(new Font("Arial", Font.PLAIN, 15));
        viewButton.setText(text);
        return viewButton;
    }

    public static JButton getViewButtonIcon(ImageIcon icon) {
        JButton viewButton = createJButton(/*size, size*/);
        viewButton.setBackground(Color.DARK_GRAY);
        viewButton.setIcon(icon);
        viewButton.setBorderPainted(false);
        return viewButton;
    }

    public static JCheckBox getViewCheckBox(String text) {
        JCheckBox viewCheckBox = new JCheckBox();
        viewCheckBox.setText(text);
        viewCheckBox.setBackground(Color.DARK_GRAY);
        viewCheckBox.setForeground(Color.ORANGE);
        return viewCheckBox;
    }

    public static JLabel getViewLabel(String text) {
        JLabel viewLabel = createJLabel(text, Color.ORANGE);
        viewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        return viewLabel;
    }

    public static JLabel getViewLabelWarning(String text) {
        JLabel viewLabel = createJLabel(text, Color.MAGENTA);
        viewLabel.setFont(new Font("Arial", Font.BOLD, 10));
        return viewLabel;
    }

    public static JScrollPane getViewOutputArea() {
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(
                outputArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        new EmptyBorder(10, 10, 10, 10),
                        new EtchedBorder()
                )
        );
        return scrollPane;
    }

    public static JPanel getViewPanel() {
        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(Color.DARK_GRAY);
        return viewPanel;
    }

    public static JPanel getViewPanelWithBoxLayout() {
        JPanel viewPanel = getViewPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        return viewPanel;
    }

    public static GroupLayout getViewPanelWithLayout(JPanel viewPanel) {
        GroupLayout layout = new GroupLayout(viewPanel);
        viewPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        return layout;
    }

    public static JRadioButton getViewRadioButton(String text) {
        JRadioButton viewRadioButton = new JRadioButton();
        viewRadioButton.setText(text);
        viewRadioButton.setBackground(Color.DARK_GRAY);
        viewRadioButton.setForeground(Color.ORANGE);
        return viewRadioButton;
    }

    public static JScrollPane getViewTextArea() {
        JTextArea outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(
                outputArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        new EmptyBorder(1, 1, 1, 1),
                        new EtchedBorder()
                )
        );
        return scrollPane;
    }

    public static JTextField getViewTextField() {
        return createJTextField();
    }

    public static JTextField getViewTextFieldWritten(String text) {
        JTextField viewTextView = new JTextField();
        viewTextView.setText(text);
        return viewTextView;
    }

    private static JTextField createJTextField() {
        return new JTextField();
    }

    private static JButton createJButton() {
        JButton jButton = new JButton();
        jButton.setMargin(new Insets(0, 0, 0, 0));
        return jButton;
    }

    private static JLabel createJLabel(String text, Color color) {
        JLabel jLabel = new JLabel();
        jLabel.setForeground(color);
        jLabel.setText(text);
        return jLabel;
    }

    protected static void resetWindow(Container windowContentPane) {
        windowContentPane.revalidate();
        windowContentPane.repaint();
    }

}
