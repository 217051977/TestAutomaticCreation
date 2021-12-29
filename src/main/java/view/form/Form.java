package view.form;

import formelements.BaseFolder;
import formelements.Endpoint;
import formelements.Feature;
import formelements.Variable;
import logic.FormManager;
import observers.PrintMessage;
import observers.RunButtonPressedResponse;
import observers.sefltest.DeleteAllFoldersSelfTestByPopulatingFormResponse;
import view.elements.ViewElements;
import view.form.elements.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.GroupLayout.Alignment.BASELINE;

public final class Form extends ViewElements implements
        RunButtonPressedResponse,
        PrintMessage,
        DeleteAllFoldersSelfTestByPopulatingFormResponse
{

    private int runTestCode = 0;
    private Container windowContentPane;

    private final BaseFolderElement baseFolderElement = new BaseFolderElement();
    private final RunElement runElement;

    private final List<FeatureElement> featureElements = new ArrayList<>();
    private final JPanel featureElementsPanel = getViewPanelWithBoxLayout();
    private final List<FeatureEndpointElement> featureEndpointElements = new ArrayList<>();
    private final JPanel featureEndpointElementsPanel = getViewPanelWithBoxLayout();
    private final List<FeatureEndpointVariableElement> featureEndpointVariableElements = new ArrayList<>();
    private final JPanel featureEndpointVariableElementsPanel = getViewPanelWithBoxLayout();


    private final JButton addFeature = getViewButtonIcon(
            new ImageIcon("src/main/resources/addFeatureButton.png")
    );
    private final JButton removeFeature = getViewButtonIcon(
            new ImageIcon("src/main/resources/removeElement.png")
    );
    private final JLabel featureLabel = getViewLabel("FEATURE");
    private final JButton addFeatureEndpoint = getViewButtonIcon(
            new ImageIcon("src/main/resources/addEndpointButton.png")
    );
    private final JButton removeFeatureEndpoint = getViewButtonIcon(
            new ImageIcon("src/main/resources/removeElement.png")
    );
    private final JLabel featureEndpointLabel = getViewLabel("ENDPOINT");
    private final JButton addFeatureEndpointVariable = getViewButtonIcon(
            new ImageIcon("src/main/resources/addVariableButton.png")
    );
    private final JButton removeFeatureEndpointVariable = getViewButtonIcon(
            new ImageIcon("src/main/resources/removeElement.png")
    );
    private final JLabel featureEndpointVariableLabel = getViewLabel("VARIABLE");

    public Form() {
        super();
        runElement = new RunElement(this);
    }

    public void createFormView(Container wcp) {
        windowContentPane = wcp;
        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        windowContentPane.add(tabbedPane);
        createBaseFolderElementTab(tabbedPane);
        createFeatureElementTab(tabbedPane);
        createRunElementTab(tabbedPane);
        tabbedPane.setSelectedIndex(0);
        setAddFeatureOnClickEvent();
        setRemoveFeatureOnClickEvent();
        setAddFeatureFeatureEndpointOnClickEvent();
        setRemoveFeatureEndpointOnClickEvent();
        setAddFeatureFeatureEndpointVariableOnClickEvent();
        setRemoveFeatureEndpointVariableOnClickEvent();
        setFeatureFileNameChangeEvent();
        if (runTestCode != 0) {
            runElement.startTest();
        }
    }

    @Override
    public void runButtonPressedResponse() {
        runElement.clearOutputArea();
        printMessage("Start processing...", 750);
        printMessage("Checking if base folder exists...", 1000);
        if (runTestCode == 0) {
            readForm();
        } else {
            FormManager fm = new FormManager(this, this, runTestCode);
            if (runTestCode != 3) {
                fm.startTest();
            } else {
                deleteAllFoldersSelfTestByPopulatingFormResponse("");
            }
        }
    }

    @Override
    public void deleteAllFoldersSelfTestByPopulatingFormResponse(String response) {
        printMessage(response, 0);
        printMessage("Creating feature folder...", 0);
        populateForm();
        readForm();
    }

    private void populateForm() {
        printMessage("Populating form....", 750);
        baseFolderElement.setBaseFolderPath("D:\\Users\\nb27853\\omni-qa-pluma-test");
        featureElements.forEach(
                featureElement -> {
                    featureElement.setFeatureFileName("cms_opc_permissions");
                    featureElement.setFeatureName("cms ocp permissions");
                    featureElement.setFeatureBaseEndpoint("https://delivery-digitaljourney.westeurope.cloudapp.azure.com/bin/mvc.do/operationconsole/v3/permissions");
                    featureElement.setFeatureTags("@cms @opc @permissions");
                }
        );
        featureEndpointElements.forEach(
                featureEndpointElement -> {
                    featureEndpointElement.setEndpointForFeature("cms_opc_permissions");
                    featureEndpointElement.setEndpointName("get_search_for_permissions_given_a_query");
                    featureEndpointElement.setEndpointRemainUrl("/query?expression&limit&sort");
                    featureEndpointElement.setEndpointRequestType("GET");
                    featureEndpointElement.setEndpointValidBody(
                            "{\n" +
                                    "    \"name\": \"permission_temp\"\n" +
                                    "}"
                    );
                    featureEndpointElement.setEndpointValidHeaders(
                            "accept: application/json\n" +
                                    "authorization: Bearer $var_bearer_token_user\n" +
                                    "Content-Type: application/json"
                    );
                }
        );
        int nVar = 3;
        if (featureEndpointVariableElements.size() < nVar) {
            for (int i = 0; i < nVar; i++) {
                addFeatureEndpointVariable.doClick();
            }
        } else if (featureEndpointVariableElements.size() > nVar) {
            int size = featureEndpointVariableElements.size();
            for (int i = 0; i < size - nVar; i++){
                removeFeatureEndpointVariable.doClick();
            }
        }
        for (int i = 0; i < nVar; i++) {
            featureEndpointVariableElements.get(i).setVariableForEndpoint("get_search_for_permissions_given_a_query");
            switch (i) {
                case 0: {
                    featureEndpointVariableElements.get(i).setVariableName("expression");
                    featureEndpointVariableElements.get(i).setVariableValue("name==$cms_opc_permission_name");
                }break;
                case 1: {
                    featureEndpointVariableElements.get(i).setVariableName("limit");
                    featureEndpointVariableElements.get(i).setVariableValue("1");
                }break;
                default: {
                    featureEndpointVariableElements.get(i).setVariableName("sort");
                    featureEndpointVariableElements.get(i).setVariableValue("asc");
                }
            }
            featureEndpointVariableElements.get(i).setVariableIsInUrl(false);
            featureEndpointVariableElements.get(i).setVariableCombinationsEmpty(true);
            featureEndpointVariableElements.get(i).setVariableCombinationsInvalid(true);
            featureEndpointVariableElements.get(i).setVariableCombinationsMissing(true);
        }

    }

    public void readForm() {
        String baseFolderInput;
        BaseFolder baseFolder;
        List<Feature> features = new ArrayList<>();
        List<Endpoint> endpoints = new ArrayList<>();
        List<Variable> variables = new ArrayList<>();

        printMessage("Reading Input:", 750);
        baseFolderInput = baseFolderElement.getBaseFolderPath();
        printMessage("Project base folder: " + baseFolderInput, 0);
        baseFolder = new BaseFolder(baseFolderInput);
        printMessage("Reading Features:", 0);
        for (FeatureElement featureElement : featureElements) {
            String fileName = featureElement.getFeatureFileName();
            String name = featureElement.getFeatureName();
            String baseEndpoint = featureElement.getFeatureBaseEndpoint();
            String tags = featureElement.getFeatureTags();
            printMessage("Feature file name: " + fileName, 0);
            printMessage("Feature name: " + name, 0);
            printMessage("Feature base endpoint: " + baseEndpoint, 0);
            printMessage("Feature tags: " + tags, 0);
            Feature feature = new Feature(fileName, name, baseEndpoint, tags);
            features.add(feature);
        }
        for (FeatureEndpointElement featureEndpointElement : featureEndpointElements) {
            String forFeature = featureEndpointElement.getEndpointForFeature();
            String name = featureEndpointElement.getEndpointName();
            String remainingUrl = featureEndpointElement.getEndpointRemainUrl();
            String requestType = featureEndpointElement.getEndpointRequestType();
            boolean hasInvalidUserTest = featureEndpointElement.getEndpointHasInvalidUserTest();
            String validBody = featureEndpointElement.getEndpointValidBody();
            String validHeader = featureEndpointElement.getEndpointValidHeaders();
            printMessage("Endpoint for feature: " + forFeature, 0);
            printMessage("Endpoint name: " + name, 0);
            for (Feature feature : features) {
                if (feature.getBaseEndpoint().equals(forFeature)) {
                    printMessage("Endpoint url: " + feature.getBaseEndpoint() + "/" + remainingUrl, 0);
                    break;
                }
            }
            printMessage("Endpoint request type: " + requestType, 0);
            printMessage("Has user without permissions test: " + hasInvalidUserTest, 0);
            printMessage("Valid body:\n" + validBody, 0);
            printMessage("Valid header:\n" + validHeader, 0);
            Endpoint endpoint = new Endpoint(
                    forFeature,
                    name,
                    remainingUrl,
                    requestType,
                    hasInvalidUserTest,
                    validBody,
                    validBody
            );
            endpoints.add(endpoint);
        }
        for (FeatureEndpointVariableElement featureEndpointVariableElement : featureEndpointVariableElements) {
            String forEndpoint = featureEndpointVariableElement.getVariableForEndpoint();
            String name = featureEndpointVariableElement.getVariableName();
            String value = featureEndpointVariableElement.getVariableValue();
            boolean isInUrl = featureEndpointVariableElement.getVariableIsInUrl();
            boolean combinationEmpty = featureEndpointVariableElement.getVariableCombinationsEmpty();
            boolean combinationInvalid = featureEndpointVariableElement.getVariableCombinationsInvalid();
            boolean combinationMissing = featureEndpointVariableElement.getVariableCombinationsMissing();
            printMessage("Variable for endpoint: " + forEndpoint, 0);
            printMessage("Variable name: " + name, 0);
            printMessage("Variable value : " + value, 0);
            printMessage("Variable in url : " + isInUrl, 0);
            printMessage("Variable combination empty : " + combinationEmpty, 0);
            printMessage("Variable combination invalid : " + combinationInvalid, 0);
            printMessage("Variable combination missing : " + combinationMissing, 0);
            printMessage("Variable combination valid : " + true, 0);
            Variable variable = new Variable(
                    forEndpoint,
                    name,
                    value,
                    isInUrl,
                    combinationEmpty,
                    combinationInvalid,
                    combinationMissing
            );
            variables.add(variable);
        }
        FormManager fm = new FormManager(
                this,
                baseFolder,
                features.get(0),
                endpoints,
                variables,
                this,
                runTestCode
        );
        fm.start();
    }

    @Override
    public void printMessage(String message, int timeInMillis) {
        runElement.setOutputArea(message);
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setFeatureFileNameChangeEvent() {
        for (int i = 0; i < featureElements.size(); i++) {
            featureElements.get(i).setFeatureFileNameChangeEvent(
                    featureEndpointElements.get(i),
                    featureElements.get(i)
            );
        }
    }

    private void setAddFeatureOnClickEvent() {
        addFeature.addActionListener(
                actionEvent -> {
                    addFeatureElementToFeatureElements();
                    resetWindow(windowContentPane);
                }
        );
    }

    private void setRemoveFeatureOnClickEvent() {
        removeFeature.addActionListener(
                actionEvent -> {
                    removeFeatureElementFromFeatureElements();
                    resetWindow(windowContentPane);
                }
        );
    }

    private void setAddFeatureFeatureEndpointOnClickEvent() {
        addFeatureEndpoint.addActionListener(
                actionEvent -> {
                    addFeatureEndpointElementToFeatureEndpointElements();
                    resetWindow(windowContentPane);
                }
        );
    }

    private void setRemoveFeatureEndpointOnClickEvent() {
        removeFeatureEndpoint.addActionListener(
                actionEvent -> {
                    removeFeatureEndpointElementFromFeatureEndpointElements();
                    resetWindow(windowContentPane);
                }
        );
    }

    private void setAddFeatureFeatureEndpointVariableOnClickEvent() {
        addFeatureEndpointVariable.addActionListener(
                actionEvent -> {
                    addFeatureEndpointVariableElementToFeatureEndpointVariableElements(
                            featureEndpointElements.get(
                                    featureEndpointElements.size() - 1
                            ).getEndpointName()
                    );
                    resetWindow(windowContentPane);
                }
        );
    }

    private void setRemoveFeatureEndpointVariableOnClickEvent() {
        removeFeatureEndpointVariable.addActionListener(
                actionEvent -> {
                    removeFeatureEndpointVariableElementFromFeatureEndpointVariableElements();
                    resetWindow(windowContentPane);
                }
        );
    }

    private void createBaseFolderElementTab(JTabbedPane tabbedPane) {
        baseFolderElement.createBaseFolderElementTab(tabbedPane);
    }

    private void createFeatureElementTab(JTabbedPane tabbedPane) {
        JPanel viewPanel = getViewPanel();
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        addFeatureElementToFeatureElements();
        prepareToDrawFeatureElementTab(layout);
        tabbedPane.add("Feature", viewPanel);
        createFeatureEndpointElementTab(tabbedPane);
        createFeatureEndpointElementVariableTab(tabbedPane);
    }

    private void createFeatureEndpointElementTab(JTabbedPane tabbedPane) {
        JPanel viewPanel = getViewPanel();
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        prepareToDrawFeatureEndpointElementTab(layout);
        tabbedPane.add("Endpoint", viewPanel);
    }

    private void createFeatureEndpointElementVariableTab(JTabbedPane tabbedPane) {
        JPanel viewPanel = getViewPanel();
        GroupLayout layout = getViewPanelWithLayout(viewPanel);
        prepareToDrawFeatureEndpointVariableElementTab(layout);
        tabbedPane.add("Variable", viewPanel);
    }

    private void createRunElementTab(JTabbedPane tabbedPane) {
        runElement.createRunElementTab(tabbedPane);
    }

    private void addFeatureElementToFeatureElements() {
        FeatureElement featureElement = new FeatureElement();
        featureElements.add(featureElement);
        featureElements.forEach(
                fe -> featureElementsPanel.add(fe.getFeatureElementPanel())
        );
        addFeatureEndpointElementToFeatureEndpointElements();
    }

    private void removeFeatureElementFromFeatureElements() {
        int featureElementsSize = featureElements.size();
        if (featureElementsSize > 1) {
            FeatureElement featureElement = featureElements.get(featureElementsSize - 1);
            featureElements.remove(featureElement);
            featureElementsPanel.remove(featureElement.getFeatureElementPanel());
        }
    }

    private void addFeatureEndpointElementToFeatureEndpointElements() {
        FeatureEndpointElement featureEndpointElement = new FeatureEndpointElement();
        featureEndpointElements.add(featureEndpointElement);
        featureEndpointElements.forEach(
                fe -> featureEndpointElementsPanel.add(fe.getFeatureEndpointElementPanel())
        );
    }

    private void removeFeatureEndpointElementFromFeatureEndpointElements() {
        int featureEndpointElementsSize = featureEndpointElements.size();
        if (featureEndpointElementsSize > 1) {
            FeatureEndpointElement featureEndpointElement = featureEndpointElements.get(featureEndpointElementsSize - 1);
            featureEndpointElements.remove(featureEndpointElement);
            featureEndpointElementsPanel.remove(featureEndpointElement.getFeatureEndpointElementPanel());
        }
    }

    private void addFeatureEndpointVariableElementToFeatureEndpointVariableElements(String forEndpoint) {
        FeatureEndpointVariableElement featureEndpointVariableElement = new FeatureEndpointVariableElement(forEndpoint);
        featureEndpointVariableElements.add(featureEndpointVariableElement);
        featureEndpointVariableElements.forEach(
                fe -> featureEndpointVariableElementsPanel.add(fe.getFeatureEndpointVariableElementPanel())
        );
        setVariableIsInUrlTrueEvent();
        setVariableIsInUrlFalseEvent();
    }

    private void removeFeatureEndpointVariableElementFromFeatureEndpointVariableElements() {
        int featureEndpointVariableElementsSize = featureEndpointVariableElements.size();
        if (featureEndpointVariableElementsSize > 0) {
            FeatureEndpointVariableElement featureEndpointVariableElement = featureEndpointVariableElements.get(
                    featureEndpointVariableElementsSize - 1
            );
            featureEndpointVariableElements.remove(featureEndpointVariableElement);
            featureEndpointVariableElementsPanel.remove(featureEndpointVariableElement.getFeatureEndpointVariableElementPanel());
        }
    }

    private void setVariableIsInUrlTrueEvent() {
        featureEndpointVariableElements.forEach(
                featureEndpointVariableElement -> featureEndpointVariableElement.setVariableIsInUrlTrueEvent(windowContentPane)
        );
    }

    private void setVariableIsInUrlFalseEvent() {
        featureEndpointVariableElements.forEach(
                featureEndpointVariableElement -> featureEndpointVariableElement.setVariableIsInUrlFalseEvent(windowContentPane)
        );
    }

    private void prepareToDrawFeatureElementTab(GroupLayout layout) {
        JScrollPane scrollPane = getScrollPane(featureElementsPanel);
        drawElementTab(layout, featureLabel, removeFeature, addFeature, scrollPane);
    }

    private void prepareToDrawFeatureEndpointElementTab(GroupLayout layout) {
        JScrollPane scrollPane = getScrollPane(featureEndpointElementsPanel);
        drawElementTab(layout, featureEndpointLabel, removeFeatureEndpoint, addFeatureEndpoint, scrollPane);
    }

    private void prepareToDrawFeatureEndpointVariableElementTab(GroupLayout layout) {
        JScrollPane scrollPane = getScrollPane(featureEndpointVariableElementsPanel);
        drawElementTab(layout, featureEndpointVariableLabel, removeFeatureEndpointVariable, addFeatureEndpointVariable, scrollPane);
    }

    private JScrollPane getScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        return scrollPane;
    }

    private void drawElementTab(
            GroupLayout layout,
            JLabel elementLabel,
            JButton removeElement,
            JButton addElement,
            JScrollPane scrollPane
    ) {
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(elementLabel)
                        .addPreferredGap(
                                LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(removeElement, 20, 20, 20)
                                .addComponent(addElement, 20, 20, 20)
                        )
                )
                .addComponent(scrollPane)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(elementLabel)
                        .addComponent(removeElement, 20, 20, 20)
                        .addComponent(addElement, 20, 20, 20)
                )
                .addComponent(scrollPane)
        );
    }
}
