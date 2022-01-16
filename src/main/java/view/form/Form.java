package view.form;

import formelements.BaseFolder;
import formelements.Endpoint;
import formelements.Feature;
import formelements.Variable;
import logic.FormManager;
import observers.CountTimePassed;
import observers.PrintMessage;
import observers.RunButtonPressedResponse;
import observers.sefltest.DeleteAllFoldersSelfTestByPopulatingFormResponse;
import view.elements.ViewElements;
import view.form.elements.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.swing.GroupLayout.Alignment.BASELINE;

public final class Form extends ViewElements implements
        RunButtonPressedResponse,
        PrintMessage,
        DeleteAllFoldersSelfTestByPopulatingFormResponse,
        CountTimePassed
{

    private int runTestCode = 0;
    private int nPrints = 0;
    private final int maxPrints = 20000;
    private final int printTimeOut = 5000;
    private Container windowContentPane;
    private long startTimeInMillis;

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
        setOpenFolderChooserOnClickEvent();
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
    public void countTimePassed(long currentTime) {
        long dif = currentTime - startTimeInMillis;
        long secInMilli = 1000;
        long minInMilli = secInMilli * 60;
        long hoursInMilli = minInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long days = dif / daysInMilli;
        dif = dif % daysInMilli;
        long hours = dif / hoursInMilli;
        dif = dif % hoursInMilli;
        long min = dif / minInMilli;
        dif = dif % minInMilli;
        long sec = dif / secInMilli;
        printMessage(
                "Started at: " + new Date(startTimeInMillis),
                0
        );
        printMessage(
                "Current time: " + new Date(currentTime),
                0
        );
        printMessage(
                "Time passed: " + days + " days, " + hours + " hours, " + min +" minutes, " + sec + " seconds",
                1000
        );
        System.out.printf(
                "Time passed: %d days, %d hours, %d minutes, %d seconds%n",
                days,
                hours,
                min,
                sec
        );
    }

    @Override
    public void runButtonPressedResponse() {
        startTimeInMillis = System.currentTimeMillis();

        runElement.clearOutputArea();
        printMessage("Start processing...", 750);
        printMessage("Checking if base folder exists...", 1000);
        if (runTestCode == 0) {
            readForm();
        } else {
            FormManager fm = new FormManager(this, this, this, runTestCode);
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

    private void populateForm_addVariable(
            int totalVarCreated,
            String variableName,
            String variableValue,
            boolean variableIsInUrl,
            boolean variableCombinationsEmpty,
            boolean variableCombinationsMissing,
            boolean variableCombinationsInvalid
    ) {
        featureEndpointVariableElements.get(totalVarCreated).setVariableName(variableName);
        featureEndpointVariableElements.get(totalVarCreated).setVariableValue(variableValue);
        featureEndpointVariableElements.get(totalVarCreated).setVariableIsInUrl(variableIsInUrl);
        featureEndpointVariableElements.get(totalVarCreated).setVariableCombinationsEmpty(variableCombinationsEmpty);
        featureEndpointVariableElements.get(totalVarCreated).setVariableCombinationsMissing(variableCombinationsMissing);
        featureEndpointVariableElements.get(totalVarCreated).setVariableCombinationsInvalid(variableCombinationsInvalid);
    }

    private void populateForm() {
        printMessage("Populating form....", 750);
        baseFolderElement.setBaseFolderPath("D:\\Users\\nb27853\\omni-qa-pluma-test");
        featureElements.forEach(
                featureElement -> {
                    featureElement.setFeatureFileName("cms_opc_users");
                    featureElement.setFeatureName("Operation Console API - Users");
                    featureElement.setFeatureBaseEndpoint("https://delivery-digitaljourney.westeurope.cloudapp.azure.com/bin/mvc.do/operationconsole/v3/users");
                    featureElement.setFeatureTags("@cms @opc @users");
                }
        );
        if (featureEndpointElements.size() > 1) {
            int size = featureEndpointElements.size();
            for (int i = 0; i < size - 1; i++) {
                removeFeatureEndpoint.doClick();
            }
        }
        if (featureEndpointVariableElements.size() > 1) {
            int size = featureEndpointVariableElements.size();
            for (int i = 0; i < size - 1; i++){
                removeFeatureEndpointVariable.doClick();
            }
        }

        int nFeatures = 1;
        int totalEndpointCreated = 0;
        int totalVarCreated = 0;
        for (int f = 0; f < nFeatures; f++) {
            if (f != 0) {
                addFeature.doClick();
            }
            featureElements.get(f).setFeatureFileName("cms_opc_roles");
            featureElements.get(f).setFeatureName("Operation Console API - Roles");
            featureElements.get(f).setFeatureBaseEndpoint("https://delivery-digitaljourney.westeurope.cloudapp.azure.com/bin/mvc.do/operationconsole/v3/roles");
            featureElements.get(f).setFeatureTags("@cms @opc @roles");
            int nEndpoints = 12;
            for (int e = 0; e < nEndpoints; e++) {
                if (e != 0) {
                    addFeatureEndpoint.doClick();
                }
                switch (e) {
                    case 0: {
                        String endpointName = "get_search_for_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/id");

                        addFeatureEndpointVariable.doClick();
                        populateForm_addVariable(
                                totalVarCreated,
                                "id",
                                "$role_id",
                                true,
                                false,
                                true,
                                true);
                        totalVarCreated++;
                    }
                    break;
                    case 1: {
                        String endpointName = "post_partially_update_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/id/body");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRequestType("POST");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointValidBody(
                                "{\n" +
                                        "  \"name\": \"cms_test_role\",\n" +
                                        "  \"description\": {}\n" +
                                        "}"
                        );
                        int nVar = 2;
                        for (int v = 0; v < nVar; v++) {
                            addFeatureEndpointVariable.doClick();
                            if (v == 0) {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "id",
                                        "$role_id",
                                        true,
                                        false,
                                        true,
                                        true);
                            } else {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "body",
                                        "",
                                        true,
                                        true,
                                        true,
                                        true);
                            }
                            totalVarCreated++;
                        }
                    }
                    break;
                    case 2: {
                        String endpointName = "put_update_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/id/body");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRequestType("PUT");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointValidBody(
                                "{\n" +
                                        "  \"name\": \"cms_test_role\",\n" +
                                        "  \"description\": \"role to be used in cms test - update\"\n" +
                                        "}"
                        );
                        int nVar = 2;
                        for (int v = 0; v < nVar; v++) {
                            addFeatureEndpointVariable.doClick();
                            if (v == 0) {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "id",
                                        "$role_id",
                                        true,
                                        false,
                                        true,
                                        true);
                            } else {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "body",
                                        "",
                                        true,
                                        true,
                                        true,
                                        true);
                            }
                            totalVarCreated++;
                        }
                    }
                    break;
                    case 3: {
                        String endpointName = "delete_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/id");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRequestType("DELETE");

                        addFeatureEndpointVariable.doClick();
                        populateForm_addVariable(
                                totalVarCreated,
                                "id",
                                "$role_id",
                                true,
                                false,
                                true,
                                true);
                        totalVarCreated++;
                    }
                    break;
                    case 4: {
                        String endpointName = "get_list_of_roles_given_a_search_parameter";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/body");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointValidBody(
                                "{\n" +
                                        "  \"id\": \"$role_id\",\n" +
                                        "  \"name\": \"$role_name\",\n" +
                                        "  \"limit\": 1,\n" +
                                        "  \"sort\": [\n" +
                                        "    \"id;desc\"\n" +
                                        "  ]\n" +
                                        "}"
                        );

                        addFeatureEndpointVariable.doClick();
                        populateForm_addVariable(
                                totalVarCreated,
                                "body",
                                "",
                                true,
                                true,
                                true,
                                true);
                        totalVarCreated++;
                    }
                    break;
                    case 5: {
                        String endpointName = "create_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/body");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRequestType("POST");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointValidBody(
                                "{\n" +
                                        "  \"name\": \"cms_test_role\",\n" +
                                        "  \"description\": \"role to be used in cms test - update\"\n" +
                                        "}"
                        );

                        addFeatureEndpointVariable.doClick();
                        populateForm_addVariable(
                                totalVarCreated,
                                "body",
                                "",
                                true,
                                true,
                                true,
                                true);
                        totalVarCreated++;
                    }
                    break;
                    case 6: {
                        String endpointName = "delete_a_role_by_name";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("?name");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRequestType("DELETE");

                        addFeatureEndpointVariable.doClick();
                        populateForm_addVariable(
                                totalVarCreated,
                                "name",
                                "$role_name",
                                false,
                                false,
                                true,
                                true);
                        totalVarCreated++;
                    }
                    break;
                    case 7: {
                        String endpointName = "get_the_permissions_of_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/roleId/permissions?limit&sort");
                        int nVar = 3;
                        for (int v = 0; v < nVar; v++) {
                            addFeatureEndpointVariable.doClick();
                            switch (v) {
                                case 0: {
                                    populateForm_addVariable(
                                            totalVarCreated,
                                            "roleId",
                                            "$role_id",
                                            true,
                                            false,
                                            true,
                                            true);
                                }
                                break;
                                case 1: {
                                    populateForm_addVariable(
                                            totalVarCreated,
                                            "limit",
                                            "1",
                                            false,
                                            false,
                                            true,
                                            true);
                                }
                                break;
                                default: {
                                    populateForm_addVariable(
                                            totalVarCreated,
                                            "sort",
                                            "id;asc",
                                            false,
                                            false,
                                            true,
                                            true);
                                }
                            }
                            totalVarCreated++;
                        }
                    }
                    break;
                    case 8: {
                        String endpointName = "remove_all_permissions_of_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/roleId/permissions");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRequestType("DELETE");

                        addFeatureEndpointVariable.doClick();
                        populateForm_addVariable(
                                totalVarCreated,
                                "roleId",
                                "$role_id",
                                true,
                                false,
                                true,
                                true);
                        totalVarCreated++;
                    }
                    break;
                    case 9: {
                        String endpointName = "post_associates_a_permissions_to_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/roleId/permissions/permissionId");
                        int nVar = 2;
                        for (int v = 0; v < nVar; v++) {
                            addFeatureEndpointVariable.doClick();
                            if (v == 0) {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "roleId",
                                        "$role_id",
                                        true,
                                        false,
                                        true,
                                        true);
                            } else {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "permissionId",
                                        "1",
                                        true,
                                        false,
                                        true,
                                        true);
                            }
                            totalVarCreated++;
                        }
                    }
                    break;
                    case 10: {
                        String endpointName = "remove_a_permissions_from_a_role";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/roleId/permissions/permissionId");
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRequestType("DELETE");
                        int nVar = 2;
                        for (int v = 0; v < nVar; v++) {
                            addFeatureEndpointVariable.doClick();
                            if (v == 0) {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "roleId",
                                        "$role_id",
                                        true,
                                        false,
                                        true,
                                        true);
                            } else {
                                populateForm_addVariable(
                                        totalVarCreated,
                                        "permissionId",
                                        "1",
                                        true,
                                        false,
                                        true,
                                        true);
                            }
                            totalVarCreated++;
                        }
                    }
                    break;
                    default: {
                        String endpointName = "get_list_of_a_roles_given_a_search_query";
                        featureEndpointElements.get(totalEndpointCreated).setEndpointName(endpointName);
                        featureEndpointElements.get(totalEndpointCreated).setEndpointRemainUrl("/query?expression&limit&sort");
                        int nVar = 3;
                        for (int v = 0; v < nVar; v++) {
                            addFeatureEndpointVariable.doClick();
                            switch (v) {
                                case 0: {
                                    populateForm_addVariable(
                                            totalVarCreated,
                                            "expression",
                                            "id!=1",
                                            false,
                                            false,
                                            true,
                                            true);
                                }
                                break;
                                case 1: {
                                    populateForm_addVariable(
                                            totalVarCreated,
                                            "limit",
                                            "2",
                                            false,
                                            false,
                                            true,
                                            true);
                                }
                                break;
                                default: {
                                    populateForm_addVariable(
                                            totalVarCreated,
                                            "sort",
                                            "id;asc",
                                            false,
                                            false,
                                            true,
                                            true);
                                }
                            }
                            totalVarCreated++;
                        }
                    }
                }
                totalEndpointCreated++;
            }
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
                    validHeader
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
        try {
            Thread.sleep(1000);
            FormManager fm = new FormManager(
                    this,
                    this,
                    baseFolder,
                    features.get(0),
                    endpoints,
                    variables,
                    this,
                    runTestCode
            );
            new Thread(
                    fm::start
            ).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void printMessage(String message, int timeInMillis) {
        runElement.setOutputArea(message);
        try {
            if (nPrints < maxPrints) {
                nPrints ++;
            } else {
                nPrints = 0;
                Thread.sleep(printTimeOut - timeInMillis);
            }
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

    private void setOpenFolderChooserOnClickEvent() {
//        addFeature.addActionListener(
//                actionEvent -> {
                    baseFolderElement.openFolderChooser(windowContentPane);
                    resetWindow(windowContentPane);
//                }
//        );
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
        String lastFeatureFileName = featureElements.get(featureElements.size() - 1).getFeatureFileName();
        featureEndpointElement.setEndpointForFeature(lastFeatureFileName);
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
