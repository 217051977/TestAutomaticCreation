package logic;

import formelements.BaseFolder;
import formelements.Endpoint;
import formelements.Feature;
import formelements.Variable;
import logic.filescreator.*;
import logic.filesfinder.*;
import logic.foldercreator.EndpointFolderCreator;
import logic.foldercreator.FeatureFolderCreator;
import logic.folderfinder.*;
import logic.sefltest.clear.DeleteAllFoldersSelfTest;
import logic.sefltest.clear.folderFinder.BaseFolderToClearFinder;
import logic.sefltest.clear.folderFinder.ComponentsFolderToClearFinder;
import logic.sefltest.clear.folderFinder.EndpointsFolderToClearFinder;
import observers.PrintMessage;
import observers.filescreator.*;
import observers.filescreator.fail.FailToCreateFileResponse;
import observers.filesfinder.*;
import observers.filesfinder.fail.FailToFindFileResponse;
import observers.foldercreator.EndpointFolderCreatorResponse;
import observers.foldercreator.FeatureFolderCreatorResponse;
import observers.foldercreator.fail.FailToCreateFolderResponse;
import observers.folderfinder.*;
import observers.folderfinder.fail.FailToFindFolderResponse;
import observers.folderfinder.toclear.BaseFolderFinderToClearResponse;
import observers.folderfinder.toclear.ComponentsFolderFinderToClearResponse;
import observers.folderfinder.toclear.EndpointsFolderFinderToClearResponse;
import observers.sefltest.DeleteAllFoldersSelfTestByPopulatingFormResponse;
import observers.sefltest.DeleteAllFoldersSelfTestResponse;

import java.util.ArrayList;
import java.util.List;

public class FormManager implements
        BaseFolderFinderToClearResponse,
        ComponentsFolderFinderToClearResponse,
        EndpointsFolderFinderToClearResponse,
        DeleteAllFoldersSelfTestResponse,
        BaseFolderFinderResponse,
        ComponentsFolderFinderResponse,
        EndpointsFolderFinderResponse,
        FeatureFolderCreatorResponse,
        FeatureFolderFinderResponse,
        EndpointFolderCreatorResponse,
        EndpointFolderFinderResponse,
        EndpointBodyCreatorResponse,
        EndpointBodyFinderResponse,
        EndpointHeadersCreatorResponse,
        EndpointHeadersFinderResponse,
        FeatureMetaCreatorResponse,
        FeatureMetaFinderResponse,
        FeatureDataCreatorResponse,
        FeatureDataFinderResponse,
        FeatureSitDataCreatorResponse,
        FeatureSitDataFinderResponse,
        FailToCreateFileResponse,
        FailToFindFileResponse,
        FailToCreateFolderResponse,
        FailToFindFolderResponse
{

    private int testRunningCode = 0;
    //    private final int pauseTimeResponse = 750;
    private final int pauseTimeCommand = 0;
    //    private final int pauseTimeCommand = 1000;
    private final int pauseTimeResponse = 0;
    private final DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse;

    private BaseFolder baseFolder = new BaseFolder();
    private Feature feature = new Feature();
    private List<Endpoint> endpoints = List.of(new Endpoint(/*"\\"*/"\\variable_1\\variable_2\\variable_3"));
    private List<Variable> variables = List.of(new Variable(), new Variable("variable_2"), new Variable("variable_3"));
    private final PrintMessage printMessage;

    public FormManager(
            PrintMessage printMessage,
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse
    ) {
        this.printMessage = printMessage;
        this.deleteAllFoldersSelfTestByPopulatingFormResponse = deleteAllFoldersSelfTestByPopulatingFormResponse;
    }

    public FormManager(
            PrintMessage printMessage,
            BaseFolder baseFolder,
            Feature feature,
            List<Endpoint> endpoints,
            List<Variable> variables,
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse
    ) {
        this.printMessage = printMessage;
        this.baseFolder = baseFolder;
        this.feature = feature;
        this.endpoints = endpoints;
        this.variables = variables;
        this.deleteAllFoldersSelfTestByPopulatingFormResponse = deleteAllFoldersSelfTestByPopulatingFormResponse;
    }

    public void start() {
        testRunningCode = 0;
        BaseFolderFinder.getBaseFolderFinder(this, this, baseFolder.getBaseFolderPath());
    }

    public void startTest() {
        testRunningCode = 1;
        BaseFolderToClearFinder.getBaseFolderToClearFinder(this, this, baseFolder.getBaseFolderPath());
    }

    public void startTestByPopulatingForm() {
        testRunningCode = 2;
        BaseFolderToClearFinder.getBaseFolderToClearFinder(this, this, baseFolder.getBaseFolderPath());
    }

    @Override
    public void baseFolderFinderToClearResponse(String response, String baseFolderPath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if components folder exists...", pauseTimeCommand);
        ComponentsFolderToClearFinder.getComponentsFolderToClearFinder(this, this, baseFolderPath);
    }

    @Override
    public void componentsFolderFinderToClearResponse(String response, String componentsFolderPath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if endpoints folder exists...", pauseTimeCommand);
        EndpointsFolderToClearFinder.getEndpointsFolderFinderToClear(this, this, componentsFolderPath);
    }

    @Override
    public void endpointsFolderFinderToClearResponse(String response, String endpointsFolderPath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Creating feature folder...", pauseTimeCommand);
        DeleteAllFoldersSelfTest.getDeleteAllFoldersSelfTest(
                this,
                deleteAllFoldersSelfTestByPopulatingFormResponse,
                this,
                endpointsFolderPath,
                testRunningCode
        );
    }

    @Override
    public void deleteAllFoldersSelfTestResponse(String response) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Creating feature folder...", pauseTimeCommand);
        BaseFolderFinder.getBaseFolderFinder(
                this,
                this,
                baseFolder.getBaseFolderPath()
        );
    }

    @Override
    public void baseFolderFinderResponse(String response, String baseFolderPath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if components folder exists...", pauseTimeCommand);
        ComponentsFolderFinder.getComponentsFolderFinder(this, this, baseFolderPath);
    }

    @Override
    public void componentsFolderFinderResponse(String response, String componentsFolderPath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if endpoints folder exists...", pauseTimeCommand);
        EndpointsFolderFinder.getEndpointsFolderFinder(this, this, componentsFolderPath);
    }

    @Override
    public void endpointsFolderFinderResponse(String response, String endpointsFolderPath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Creating feature folder...", pauseTimeCommand);
        FeatureFolderCreator.getFeatureFolderCreator(
                this,
                this,
                endpointsFolderPath,
                feature.getFileName(),
                true
        );
    }

    @Override
    public void featureFolderCreatorResponse(String response, String featureFolderPath, String folderName, boolean lastFolder) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if feature folder was created with success...", pauseTimeCommand);
        FeatureFolderFinder.getFeatureFolderFinder(
                this,
                this,
                featureFolderPath,
                folderName
        );
    }

    @Override
    public void featureFolderFinderResponse(String response, String featureFolderPath, String featureName) {
        printMessage.printMessage(response, pauseTimeResponse);
        setupEndpointFolderCreator(featureFolderPath, featureName);
    }

    private void setupEndpointFolderCreator(String featureFolderPath, String featureName) {
        StringBuilder folderName;
        for (Endpoint endpoint : endpoints) {
            if (endpoint.getForFeature().equals(featureName)) {
                folderName = new StringBuilder(endpoint.getName());
                setupVariablesCombination(
                        featureFolderPath,
                        folderName.toString(),
                        endpoint.getHasInvalidUserTest()
                );
            }
        }
    }

    private String getCombinationPrefix(Variable variable, int i) {
        if (!variable.getCombinationEmpty() && i == 0) {
            return null;
        } else if (i == 0) {
            return "_empty_";
        }
        if (!variable.getCombinationInvalid() && i == 1) {
            return null;
        } else if (i == 1) {
            return "_invalid_";
        }
        if (!variable.getCombinationMissing() && i == 2) {
            return null;
        } else if (i == 2) {
            return "_missing_";
        }
        return "_valid_";
    }

    private void setupVariablesCombination(String featureFolderPath, String folderName, boolean hasInvalidUserTest) {
        int variableSize = variables.size();
        StringBuilder newFolderName = new StringBuilder();

        List<List<String>> combinations = new ArrayList<>();
        if (variableSize > 0) {
            for (int k = 0; k < 4; k++) {
                Variable variable1 = variables.get(0);
                String combinationPrefix = getCombinationPrefix(variable1, k);
                String toAdd = "";
                if (combinationPrefix == null) {
                    continue;
                } else {
                    toAdd += combinationPrefix;
                }
                List<String> combination = new ArrayList<>(List.of(toAdd + variable1.getName()));
                getCombinations(combinations, combination, variableSize, 1);
            }
            System.out.println(combinations);
            for (List<String> combinationList: combinations) {
                if (combinationList.size() > 0) {
                    newFolderName = new StringBuilder(folderName + "_with");
                    for (String comb : combinationList) {
                        newFolderName.append(comb);
                    }
                    launchEndpointFolderCreatorTread(featureFolderPath, newFolderName.toString(), false);
                }
            }
        } else {
            newFolderName = new StringBuilder(folderName);
            launchEndpointFolderCreatorTread(featureFolderPath, newFolderName.toString(), false);
        }


        if (hasInvalidUserTest) {
            launchEndpointFolderCreatorTread(
                    featureFolderPath,
                    newFolderName + "_but_with_invalid_user",
                    false
            );
        }
        launchEndpointFolderCreatorTread(
                featureFolderPath,
                newFolderName + "_but_with_expired_token",
                true
        );
    }

    private void getCombinations(List<List<String>> combinations, List<String> toAdd, int variableSize, int i) {
        if (i + 1 < variableSize) {
            for (int j = 0; j < 4; j++) {
                List<String> combination = addCombination(toAdd, i, j);
                if (combination == null) {
                    continue;
                }
                getCombinations(combinations, combination, variableSize, i + 1);
            }
        } else {
            for (int j = 0; j < 4; j++) {
                List<String> combination = addCombination(toAdd, i, j);
                if (combination == null) {
                    continue;
                }
                if (combination.size() == variableSize) {
                    combinations.add(combination);
                }
            }
        }
    }

    private List<String> addCombination(List<String> toAdd, int i, int j) {
        List<String> combination = new ArrayList<>(toAdd);
        Variable variable2 = variables.get(i);
        String combinationPrefix2 = getCombinationPrefix(variable2, j);
        String toAdd2 = "";
        if (combinationPrefix2 == null) {
            return null;
        } else {
            toAdd2 += combinationPrefix2;
        }
        combination.add(toAdd2 + variable2.getName());
        return combination;
    }

    private void launchEndpointFolderCreatorTread(String featureFolderPath, String folderName, boolean lastFolder) {
        printMessage.printMessage("Creating endpoint folder " + folderName.toUpperCase() + "...", pauseTimeCommand);
        EndpointFolderCreator.getEndpointFolderCreator(
                this,
                this,
                featureFolderPath,
                folderName,
                lastFolder
        );
    }

    @Override
    public void endpointFolderCreatorResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String name,
            boolean lastFolder
    ) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if endpoint folder " + name.toUpperCase() + " was created with success...", pauseTimeCommand);
        EndpointFolderFinder.getEndpointFolderFinder(
                this,
                this,
                featureFolderPath,
                endpointFolderPath,
                name,
                lastFolder
        );
    }

    @Override
    public void endpointFolderFinderResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String name,
            boolean lastFolder
    ) {
        printMessage.printMessage(response, pauseTimeResponse);
        for (Endpoint endpoint : endpoints) {
            if (name.contains(endpoint.getName())) {
                if (name.contains("body")) {
                    printMessage.printMessage("Creating body file to endpoint " + name.toUpperCase() + "...", pauseTimeCommand);
                    EndpointBodyCreator.getEndpointBodyCreator(
                            this,
                            this,
                            featureFolderPath,
                            endpointFolderPath,
                            name,
                            endpoint.getValidBody(),
                            endpoint.getValidHeaders(),
                            lastFolder
                    );
                } else {
                    printMessage.printMessage("Creating headers file to endpoint " + name.toUpperCase() + "...", pauseTimeCommand);
                    EndpointHeadersCreator.getEndpointHeadersCreator(
                            this,
                            this,
                            featureFolderPath,
                            endpointFolderPath,
                            name,
                            endpoint.getValidHeaders(),
                            lastFolder
                    );
                }
                break;
            }
        }
    }

    @Override
    public void endpointBodyCreatorResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String bodyFilePath,
            String endpointName,
            String headers,
            boolean lastFolder
    ) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if body file was created successfully...", pauseTimeCommand);
        EndpointBodyFinder.getEndpointBodyFinder(
                this,
                this,
                featureFolderPath,
                endpointFolderPath,
                bodyFilePath,
                endpointName,
                headers,
                lastFolder
        );
    }

    @Override
    public void endpointBodyFinderResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String endpointName,
            String headers,
            boolean lastFolder
    ) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Creating headers file to endpoint " + endpointName + "...", pauseTimeCommand);
        EndpointHeadersCreator.getEndpointHeadersCreator(
                this,
                this,
                featureFolderPath,
                endpointFolderPath,
                endpointName,
                headers,
                lastFolder
        );
    }

    @Override
    public void endpointHeadersCreatorResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String headersFilePath,
            boolean lastFolder
    ) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if headers file was created successfully...", pauseTimeCommand);
        EndpointHeadersFinder.getEndpointHeadersFinder(
                this,
                this,
                featureFolderPath,
                endpointFolderPath,
                headersFilePath,
                lastFolder
        );
    }

    @Override
    public void endpointHeadersFinderResponse(String response, String featureFolderPath, boolean lastFolder) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Creating meta file at: " + featureFolderPath + "...", pauseTimeCommand);
        if (lastFolder) {
            FeatureMetaCreator.getFeatureMetaCreator(
                    this,
                    this,
                    featureFolderPath,
                    endpoints
            );
        }
    }

    @Override
    public void featureMetaCreatorResponse(String response, String featureFolderPath, String filePath, List<String> paths) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if meta file was created successfully...", pauseTimeCommand);
        FeatureMetaFinder.getFeatureMetaFinder(
                this,
                this,
                featureFolderPath,
                filePath,
                paths
        );
    }

    @Override
    public void featureMetaFinderResponse(String response, String featureFolderPath, List<String> paths) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Creating data file at: " + featureFolderPath + "...", pauseTimeCommand);
        FeatureDataCreator.getFeatureDataCreator(
                this,
                this,
                featureFolderPath,
                endpoints,
                paths,
                variables,
                feature.getBaseEndpoint()
        );
    }

    @Override
    public void featureDataCreatorResponse(String response, String featureFolderPath, String filePath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if data file was created successfully...", pauseTimeCommand);
        FeatureDataFinder.getFeatureDataFinder(
                this,
                this,
                featureFolderPath,
                filePath
        );
    }

    @Override
    public void featureDataFinderResponse(String response, String featureFolderPath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Creating sit.data file at: " + featureFolderPath + "...", pauseTimeCommand);
        FeatureSitDataCreator.getFeatureSitDataCreator(
                this,
                this,
                featureFolderPath,
                feature.getBaseEndpoint()
        );
    }

    @Override
    public void featureSitDataCreatorResponse(String response, String featureFolderPath, String filePath) {
        printMessage.printMessage(response, pauseTimeResponse);
        printMessage.printMessage("Checking if data file was created successfully...", pauseTimeCommand);
        FeatureSitDataFinder.getFeatureSitDataFinder(
                this,
                this,
                featureFolderPath,
                filePath
        );
    }

    @Override
    public void featureSitDataFinderResponse(String response) {
        printMessage.printMessage(response, pauseTimeResponse);
        if (testRunningCode == 0) {
            printMessage.printMessage("\n\nCreation completed with success!", pauseTimeResponse);
        } else {
            printMessage.printMessage("\n\nTest concluded with success!", pauseTimeResponse);
        }
    }

    @Override
    public void failToCreateFileResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    @Override
    public void failToCreateFolderResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    @Override
    public void failToFindFolderResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    @Override
    public void failToFindFileResponse(String response) {
        printMessage.printMessage(response, 0);
    }
}
