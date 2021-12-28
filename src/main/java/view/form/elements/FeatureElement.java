package view.form.elements;

import view.elements.ViewElements;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static javax.swing.GroupLayout.Alignment.LEADING;

public class FeatureElement extends ViewElements{

    private final JLabel featureBaseEndpointLabel;
    private final JTextField featureBaseEndpoint;
    private final JLabel featureFileNameLabel;
    private final JTextField featureFileName;
    private final JLabel featureNameLabel;
    private final JTextField featureName;
    private final JLabel featureTagsLabel;
    private final JTextField featureTags;
    private final JPanel viewPanel = getViewPanel();

    public FeatureElement() {
        super();
        featureBaseEndpointLabel = getViewLabel("Base endpoint:");
        featureBaseEndpoint = getViewTextFieldWritten(
                "https://delivery-digitaljourney.westeurope.cloudapp.azure.com/"
        );
        featureFileNameLabel = getViewLabel("Set feature file name:");
        featureFileName = getViewTextField();
        featureNameLabel = getViewLabel("Set feature name:");
        featureName = getViewTextField();
        featureTagsLabel = getViewLabel("Tags:");
        featureTags = getViewTextField();
        createFeatureElementTab();
    }

    private void createFeatureElementTab() {
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addComponent(featureFileNameLabel)
                .addComponent(featureFileName)
                .addComponent(featureNameLabel)
                .addComponent(featureName)
                .addComponent(featureBaseEndpointLabel)
                .addComponent(featureBaseEndpoint)
                .addComponent(featureTagsLabel)
                .addComponent(featureTags)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(featureFileNameLabel)
                .addComponent(featureFileName, 20, 20, 20)
                .addComponent(featureNameLabel)
                .addComponent(featureName, 20, 20, 20)
                .addComponent(featureBaseEndpointLabel)
                .addComponent(featureBaseEndpoint, 20, 20, 20)
                .addComponent(featureTagsLabel)
                .addComponent(featureTags, 20, 20, 20)
        );
    }

    public JPanel getFeatureElementPanel() {
        return viewPanel;
    }

    public String getFeatureBaseEndpoint() {
        return featureBaseEndpoint.getText();
    }

    public void setFeatureBaseEndpoint(String text) {
        featureBaseEndpoint.setText(text);
    }

    public String getFeatureFileName() {
        return featureFileName.getText();
    }

    public void setFeatureFileName(String text) {
        featureFileName.setText(text);
    }

    public String getFeatureName() {
        return featureName.getText();
    }

    public void setFeatureFileNameChangeEvent(FeatureEndpointElement featureEndpointElement, FeatureElement featureElement) {
        featureFileName.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent documentEvent) {
                        updateBelongsToFeatureField();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent documentEvent) {
                        updateBelongsToFeatureField();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent documentEvent) {
                        updateBelongsToFeatureField();
                    }

                    private void updateBelongsToFeatureField() {
                        featureEndpointElement.setEndpointForFeature(
                                featureElement.getFeatureFileName()
                        );
                    }

                }
        );
    }

    public void setFeatureName(String text) {
        featureName.setText(text);
    }

    public String getFeatureTags() {
        return featureTags.getText();
    }

    public void setFeatureTags(String text) {
        featureTags.setText(text);
    }
}
