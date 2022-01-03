package view.form.elements;

import view.elements.ViewElements;

import javax.swing.*;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class FeatureEndpointElement extends ViewElements {

    private final JLabel endpointForFeatureLabel;
    private final JTextField endpointForFeature;
    private final JLabel endpointNameLabel;
    private final JTextField endpointName;
    private final JLabel endpointRemainUrlLabel;
    private final JLabel endpointRemainUrlWarningLabel;
    private final JTextField endpointRemainUrl;
    private final JLabel endpointRequestTypeLabel;
    private final JTextField endpointRequestType;
    private final JLabel endpointHasInvalidUserTestLabel;
    private final JRadioButton endpointHasInvalidUserTestTrue;
    private final JRadioButton endpointHasInvalidUserTestFalse;
    private final JLabel endpointValidBodyLabel;
    private final JScrollPane endpointValidBody;
    private final JLabel endpointValidHeadersLabel;
    private final JScrollPane endpointValidHeaders;
    private final JPanel viewPanel = getViewPanel();

    public FeatureEndpointElement() {
        super();

        endpointForFeatureLabel = getViewLabel("For feature:");
        endpointForFeature = getViewTextField();
        endpointNameLabel = getViewLabel("Endpoint name:");
        endpointName = getViewTextField();
        endpointRemainUrlLabel = getViewLabel("Endpoint remaining url:");
        endpointRemainUrlWarningLabel = getViewLabelWarning("USE THE SAME VARIABLE NAME ON THE URL AND THE VARIABLE INDICATED BELLOW");
        endpointRemainUrl = getViewTextField();
        endpointRequestTypeLabel = getViewLabel("Request type:");
        endpointRequestType = getViewTextFieldWritten("GET");
        endpointHasInvalidUserTestLabel = getViewLabel("Has user without permission test?");
        endpointHasInvalidUserTestTrue = getViewRadioButton("YES");
        endpointHasInvalidUserTestFalse = getViewRadioButton("NO");
        setUpRadioButtons();
        endpointValidBodyLabel = getViewLabel("Set the valid body you wish to use:");
        endpointValidBody = getViewTextArea();
        endpointValidHeadersLabel = getViewLabel("Set the valid header you wish to use:");
        endpointValidHeaders = getViewTextArea();
        ((JTextArea) endpointValidHeaders.getViewport().getView()).setText(
                "accept: application/json\n" +
                        "authorization: Bearer $var_bearer_token_user\n" +
                        "Content-Type: application/json"
        );
        createFeatureEndpointElementTab();
    }

    private void setUpRadioButtons() {
        ButtonGroup hasInvalidUserTest = new ButtonGroup();
        endpointHasInvalidUserTestTrue.setSelected(true);
        hasInvalidUserTest.add(endpointHasInvalidUserTestTrue);
        hasInvalidUserTest.add(endpointHasInvalidUserTestFalse);
    }

    private void createFeatureEndpointElementTab() {
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addComponent(endpointForFeatureLabel)
                .addComponent(endpointForFeature)
                .addComponent(endpointNameLabel)
                .addComponent(endpointName)
                .addComponent(endpointRemainUrlLabel)
                .addComponent(endpointRemainUrlWarningLabel)
                .addComponent(endpointRemainUrl)
                .addComponent(endpointRequestTypeLabel)
                .addComponent(endpointRequestType)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(endpointHasInvalidUserTestLabel)
                        .addPreferredGap(
                                LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE
                        )
                        .addComponent(endpointHasInvalidUserTestTrue)
                        .addComponent(endpointHasInvalidUserTestFalse)
                )
                .addComponent(endpointValidBodyLabel)
                .addComponent(endpointValidBody)
                .addComponent(endpointValidHeadersLabel)
                .addComponent(endpointValidHeaders)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(endpointForFeatureLabel)
                .addComponent(endpointForFeature, 20, 20, 20)
                .addComponent(endpointNameLabel)
                .addComponent(endpointName, 20, 20, 20)
                .addComponent(endpointRemainUrlLabel)
                .addComponent(endpointRemainUrlWarningLabel)
                .addComponent(endpointRemainUrl, 20, 20, 20)
                .addComponent(endpointRequestTypeLabel)
                .addComponent(endpointRequestType, 20, 20, 20)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(endpointHasInvalidUserTestLabel)
                        .addComponent(endpointHasInvalidUserTestTrue, 20, 20, 20)
                        .addComponent(endpointHasInvalidUserTestFalse, 20, 20, 20)
                )
                .addComponent(endpointValidBodyLabel)
                .addComponent(endpointValidBody, 100, 100, 100)
                .addComponent(endpointValidHeadersLabel)
                .addComponent(endpointValidHeaders, 100, 100, 100)
        );
    }

    public JPanel getFeatureEndpointElementPanel() {
        return viewPanel;
    }

    public String getEndpointForFeature() {
        return endpointForFeature.getText();
    }

    public void setEndpointForFeature(String text) {
        endpointForFeature.setText(text);
    }

    public String getEndpointName() {
        return endpointName.getText();
    }

    public void setEndpointName(String text) {
        endpointName.setText(text);
    }

    public String getEndpointRemainUrl() {
        return endpointRemainUrl.getText();
    }

    public void setEndpointRemainUrl(String text) {
        endpointRemainUrl.setText(text);
    }

    public String getEndpointRequestType() {
        return endpointRequestType.getText();
    }

    public void setEndpointRequestType(String text) {
        endpointRequestType.setText(text);
    }

    public boolean getEndpointHasInvalidUserTest() {
        return endpointHasInvalidUserTestTrue.isSelected();
    }

    public void setEndpointHasInvalidUserTest(boolean value) {
        endpointHasInvalidUserTestTrue.setSelected(value);
        endpointHasInvalidUserTestFalse.setSelected(!value);
    }

    public String getEndpointValidBody() {
        return ((JTextArea) endpointValidBody.getViewport().getView()).getText();
    }

    public void setEndpointValidBody(String text) {
        ((JTextArea) endpointValidBody.getViewport().getView()).setText(text);
    }

    public String getEndpointValidHeaders() {
        return ((JTextArea) endpointValidHeaders.getViewport().getView()).getText();
    }

    public void setEndpointValidHeaders(String text) {
        ((JTextArea) endpointValidHeaders.getViewport().getView()).setText(text);
    }


}
