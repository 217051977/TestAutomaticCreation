package view.form.elements;

import view.elements.ViewElements;

import javax.swing.*;

import java.awt.*;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class FeatureEndpointVariableElement extends ViewElements {

    private final JLabel variableForEndpointLabel;
    private final JTextField variableForEndpoint;
    private final JLabel variableNameLabel;
    private final JTextField variableName;
    private final JLabel variableValueLabel;
    private final JTextField variableValue;
    private final JLabel variableIsInUrlLabel;
    private final JRadioButton variableIsInUrlTrue;
    private final JRadioButton variableIsInUrlFalse;
    private final JLabel variableCombinationsLabel;
    private final JCheckBox variableCombinationsEmpty;
    private final JCheckBox variableCombinationsInvalid;
    private final JCheckBox variableCombinationsMissing;
    private final JCheckBox variableCombinationsValid;
    private final JPanel viewPanel = getViewPanel();

    public FeatureEndpointVariableElement(String endpointName) {
        super();

        variableForEndpointLabel = getViewLabel("For endpoint:");
        variableForEndpoint = getViewTextField();
        variableForEndpoint.setText(endpointName);
        variableNameLabel = getViewLabel("Variable name:");
        variableName = getViewTextField();
        variableValueLabel = getViewLabel("Variable value:");
        variableValue = getViewTextField();
        variableIsInUrlLabel = getViewLabel("Is in the URL?");
        variableIsInUrlTrue = getViewRadioButton("YES");
        variableIsInUrlFalse = getViewRadioButton("NO");
        setUpRadioButtons();
        variableCombinationsLabel = getViewLabel("Which of this combinations is this variable in?");
        variableCombinationsEmpty = getViewCheckBox("Empty");
        variableCombinationsInvalid = getViewCheckBox("Invalid");
        variableCombinationsMissing = getViewCheckBox("Missing");
        variableCombinationsValid = getViewCheckBox("Valid");
        setUpCheckBox();
        createFeatureEndpointVariableElementTab();
    }

    public void setVariableIsInUrlTrueEvent(Container windowContentPane) {
        variableIsInUrlTrue.addActionListener(
                actionEvent -> {
                    disableVariableCombinationsEmpty();
                    resetWindow(windowContentPane);
                }
        );
    }

    public void setVariableIsInUrlFalseEvent(Container windowContentPane) {
        variableIsInUrlFalse.addActionListener(
                actionEvent -> {
                    enableVariableCombinationsEmpty();
                    resetWindow(windowContentPane);
                }
        );
    }

    private void enableVariableCombinationsEmpty() {
        variableCombinationsEmpty.setEnabled(true);
    }

    private void disableVariableCombinationsEmpty() {
        variableCombinationsEmpty.setEnabled(false);
        variableCombinationsEmpty.setSelected(false);
    }

    private void setUpRadioButtons() {
        ButtonGroup isInUrl = new ButtonGroup();
        variableIsInUrlTrue.setSelected(true);
        isInUrl.add(variableIsInUrlTrue);
        isInUrl.add(variableIsInUrlFalse);
    }

    private void setUpCheckBox() {
        variableCombinationsEmpty.setEnabled(false);
        variableCombinationsValid.setSelected(true);
        variableCombinationsValid.setEnabled(false);
    }

    private void createFeatureEndpointVariableElementTab() {
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addComponent(variableForEndpointLabel)
                .addComponent(variableForEndpoint)
                .addComponent(variableNameLabel)
                .addComponent(variableName)
                .addComponent(variableValueLabel)
                .addComponent(variableValue)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(variableIsInUrlLabel)
                        .addPreferredGap(
                                LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE
                        )
                        .addComponent(variableIsInUrlTrue)
                        .addComponent(variableIsInUrlFalse)
                )
                .addComponent(variableCombinationsLabel)
                .addComponent(variableCombinationsEmpty)
                .addComponent(variableCombinationsInvalid)
                .addComponent(variableCombinationsMissing)
                .addComponent(variableCombinationsValid)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(variableForEndpointLabel)
                .addComponent(variableForEndpoint, 20, 20, 20)
                .addComponent(variableNameLabel)
                .addComponent(variableName, 20, 20, 20)
                .addComponent(variableValueLabel)
                .addComponent(variableValue, 20, 20, 20)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(variableIsInUrlLabel)
                        .addComponent(variableIsInUrlTrue, 20, 20, 20)
                        .addComponent(variableIsInUrlFalse, 20, 20, 20)
                )
                .addComponent(variableCombinationsLabel)
                .addComponent(variableCombinationsEmpty, 20, 20, 20)
                .addComponent(variableCombinationsInvalid, 20, 20, 20)
                .addComponent(variableCombinationsMissing, 20, 20, 20)
                .addComponent(variableCombinationsValid, 20, 20, 20)
        );
    }

    public JPanel getFeatureEndpointVariableElementPanel() {
        return viewPanel;
    }

    public String getVariableForEndpoint() {
        return variableForEndpoint.getText();
    }

    public void setVariableForEndpoint(String text) {
        variableForEndpoint.setText(text);
    }

    public String getVariableName() {
        return variableName.getText();
    }

    public void setVariableName(String text) {
        variableName.setText(text);
    }

    public String getVariableValue() {
        return variableValue.getText();
    }

    public void setVariableValue(String text) {
        variableValue.setText(text);
    }

    public boolean getVariableIsInUrl() {
        return variableIsInUrlTrue.isSelected();
    }

    public void setVariableIsInUrl(boolean value) {
        variableIsInUrlTrue.setSelected(value);
        variableIsInUrlFalse.setSelected(!value);
    }

    public boolean getVariableCombinationsEmpty() {
        return variableCombinationsEmpty.isSelected();
    }

    public void setVariableCombinationsEmpty(boolean value) {
        variableCombinationsEmpty.setSelected(value);
    }

    public boolean getVariableCombinationsInvalid() {
        return variableCombinationsInvalid.isSelected();
    }

    public void setVariableCombinationsInvalid(boolean value) {
        variableCombinationsInvalid.setSelected(value);
    }

    public boolean getVariableCombinationsMissing() {
        return variableCombinationsMissing.isSelected();
    }

    public void setVariableCombinationsMissing(boolean value) {
        variableCombinationsMissing.setSelected(value);
    }

}
