package view.form.elements;

import observers.RunButtonPressedResponse;
import view.elements.ViewElements;

import javax.swing.*;

import java.awt.*;

import static javax.swing.GroupLayout.Alignment.*;

public class RunElement extends ViewElements {

    private String output = "";
    private final JButton runButton;
    private final JScrollPane outputArea;
    private final RunButtonPressedResponse runButtonPressedResponse;

    public RunElement(RunButtonPressedResponse runButtonPressedResponse) {
        runButton = getViewButton("RUN");
        outputArea = getViewOutputArea();
        this.runButtonPressedResponse = runButtonPressedResponse;
        setRunOnClickEvent();
    }

    public void createRunElementTab(JTabbedPane tabbedPane) {
        JPanel viewPanel = getViewPanel();
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        drawRunElementTab(layout);
        tabbedPane.add("Run", viewPanel);
    }

    private void setRunOnClickEvent() {
        runButton.addActionListener(
                actionEvent -> new Thread(
                        runButtonPressedResponse::runButtonPressedResponse
                ).start()
        );
    }

    private void drawRunElementTab(GroupLayout layout) {
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(
                                runButton,
                                CENTER,
                                0,
                                0,
                                Toolkit.getDefaultToolkit().getScreenSize().width
                        )
                        .addComponent(outputArea)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(runButton)
                .addComponent(outputArea)
        );
    }

    public void setOutputArea(String text) {
        output += text;
        output += "\n";
        ((JTextArea) outputArea.getViewport().getView()).setText(output);
        ((JTextArea) outputArea.getViewport().getView()).setCaretPosition(
                ((JTextArea) outputArea.getViewport().getView()).getDocument().getLength()
        );
    }

    public void startTest() {
        runButton.doClick();
    }

}
