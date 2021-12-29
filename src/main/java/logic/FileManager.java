package logic;

import formelements.BaseFolder;
import formelements.Endpoint;
import formelements.Feature;
import formelements.Variable;
import observers.PrintMessage;
import observers.sefltest.DeleteAllFoldersSelfTestByPopulatingFormResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileManager {

    private final String baseUrl;
    private final int variablesSize;
    private final int testRunningCode;

    private final PrintMessage printMessage;
    private DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse;

    private final String featureFileName;
    private Feature feature = new Feature();
    private List<Endpoint> endpoints = List.of(new Endpoint(/*"\\"*/"\\variable_1\\variable_2\\variable_3"));
    private List<Variable> variables = List.of(new Variable(), new Variable("variable_2"), new Variable("variable_3"));
    private BaseFolder baseFolder = new BaseFolder();

    private final String projectBaseFolderPath;

    public FileManager(
            PrintMessage printMessage,
            int testRunningCode
    ) {
        this.printMessage = printMessage;
        this.featureFileName = this.feature.getFileName();
        this.variablesSize = this.variables.size();
        this.baseUrl = this.feature.getBaseEndpoint();
        this.testRunningCode = testRunningCode;
        this.projectBaseFolderPath = baseFolder.getBaseFolderPath();
    }

    public FileManager(
            PrintMessage printMessage,
            Feature feature,
            List<Endpoint> endpoints,
            List<Variable> variables,
            BaseFolder baseFolder,
            int testRunningCode
    ) {
        this.printMessage = printMessage;
        this.feature = feature;
        this.featureFileName = this.feature.getFileName();
        this.endpoints = endpoints;
        this.variables = variables;
        this.baseFolder = baseFolder;
        this.projectBaseFolderPath = baseFolder.getBaseFolderPath();
        this.variablesSize = this.variables.size();
        this.baseUrl = this.feature.getBaseEndpoint();
        this.testRunningCode = testRunningCode;
    }

    public void setDeleteAllFoldersSelfTestByPopulatingFormResponse(
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse
    ) {
        this.deleteAllFoldersSelfTestByPopulatingFormResponse = deleteAllFoldersSelfTestByPopulatingFormResponse;
    }

    public void getBaseFolderToClearFinder() {
        File baseFolder = new File(projectBaseFolderPath);
        if (baseFolder.exists()) {
            printMessage("Base folder " + baseFolder.getName().toUpperCase() + " found at: " + baseFolder.getPath());
            getComponentsFolderToClearFinder(projectBaseFolderPath);
        } else {
            failToFindFolderResponse(
                    "Base folder " + baseFolder.getName().toUpperCase() + " NOT found at: " + baseFolder.getPath()
            );
        }
    }

    public void getBaseFolderFinder() {
        File baseFolder = new File(projectBaseFolderPath);
        if (baseFolder.exists()) {
            printMessage(
                    "Base folder " + baseFolder.getName().toUpperCase() + " found at: " + baseFolder.getPath()
            );
            getComponentsFolderFinder(projectBaseFolderPath);
        } else {
            failToFindFolderResponse(
                    "Base folder " + baseFolder.getName().toUpperCase() + " NOT found at: " + baseFolder.getPath()
            );
            //baseFolderFinderResponse.baseFolderFinderResponse("Base folder " + UNDERLINED + baseFolder.getName() + RESET + " " + RED_BOLD + "NOT" + RESET + " found at: " + UNDERLINED + baseFolder.getPath() + RESET);
        }
    }

    private void getComponentsFolderToClearFinder(String projectBaseFolderPath) {
        boolean folderFound = false;
        try {
            for (File folder :
                    Files.list(Paths.get(projectBaseFolderPath)).map(Path::toFile).collect(Collectors.toList())) {
                if (folder.getName().equals("components")) {
                    folderFound = true;
                    printMessage("Components folder COMPONENTS found at: " + projectBaseFolderPath);
                    getEndpointsFolderFinderToClear(folder.getPath());
                }
            }
            if (!folderFound) {
                failToFindFolderResponse(
                        "Components folder COMPONENTS NOT found at: " + projectBaseFolderPath
                );
            }
        } catch (IOException e) {
            failToFindFolderResponse(
                    "ERROR FOUND! NOT possible to continue! Process stopped."
            );
        }
    }

    private void getEndpointsFolderFinderToClear(String componentsFolderPath) {
        try {
            String folderPath = searchForFolder(componentsFolderPath);
            if (folderPath != null) {
                printMessage("Endpoints folder ENDPOINTS found at: " + componentsFolderPath);
                getDeleteAllFoldersSelfTest(folderPath);
            } else {
                failToFindFolderResponse(
                        "Endpoints folder ENDPOINTS NOT found at: " + componentsFolderPath + " FOLDER NO CLEARED"
                );
            }
        } catch (IOException e) {
            failToFindFolderResponse(
                    "ERROR FOUND! NOT possible to continue! Process stopped."
            );
        }
    }

    private String searchForFolder(String componentsFolderPath) throws IOException {
        for (File folder :
                Files.list(Paths.get(componentsFolderPath)).map(Path::toFile).collect(Collectors.toList())) {
            if (folder.getName().equals("endpoints")) {
                return folder.getPath();
            }
        }
        return null;
    }

    private void getDeleteAllFoldersSelfTest(String endpointsBaseFolderPath) {
        File endpointsFolder = new File(endpointsBaseFolderPath);
        if (clearFolder(endpointsFolder)) {
            if (testRunningCode == 1) {
                printMessage("All folders deletedBase folder " + endpointsFolder.getName().toUpperCase() + " found at: " + endpointsFolder.getPath());
                getBaseFolderFinder();
            } else if (testRunningCode == 2) {
                deleteAllFoldersSelfTestByPopulatingFormResponse.deleteAllFoldersSelfTestByPopulatingFormResponse(
                        "All folders deletedBase folder " + endpointsFolder.getName().toUpperCase() + " found at: " + endpointsFolder.getPath()
                );
            }
        } else {
            failToFindFolderResponse(
                    "Base folder " + endpointsFolder.getName().toUpperCase() + " NOT found at: " + endpointsFolder.getPath()
            );
        }
    }

    private void getComponentsFolderFinder(String projectBaseFolderPath) {
        String componentsFolderPath = searchForFolder("COMPONENTS", projectBaseFolderPath);
        if (componentsFolderPath != null) {
            getEndpointsFolderFinder(componentsFolderPath);
        }
    }

    private void getEndpointsFolderFinder(String componentsFolderPath) {
        String endpointsFolderPath = searchForFolder("endpoints", componentsFolderPath);
        if (endpointsFolderPath != null) {
            getFeatureFolderCreator(endpointsFolderPath);
        }
    }

    private String searchForFolder(String folderName, String initialFolderPath) {
        folderName = folderName.toUpperCase();
        String folderNameToSearch = folderName.toLowerCase();
        try {
            for (File folder :
                    Files.list(Paths.get(initialFolderPath)).map(Path::toFile).collect(Collectors.toList())) {
                if (folder.getName().equals(folderNameToSearch)) {
                    printMessage(
                            "Components folder " + folderName + " found at: " + initialFolderPath
                    );
                    return folder.getPath();
                }
            }
            failToFindFolderResponse(
                    "Components folder " + folderName + " NOT found at: " + initialFolderPath
            );
            return null;
        } catch (IOException e) {
            failToFindFolderResponse(
                    "ERROR FOUND! NOT possible to continue! Process stopped."
            );
            return null;
        }
    }

    private void getFeatureFolderCreator(String endpointsFolderPath) {
        File featureFolder = new File(endpointsFolderPath + "\\" + featureFileName);
        if (featureFolder.isDirectory()) {
            printMessage(
                    "Feature folder " + featureFolder.getName().toUpperCase() + " found at: " + endpointsFolderPath
            );
            getFeatureFolderFinder(featureFolder.getPath(), true);
        } else {
            if (featureFolder.mkdir()) {
                printMessage(
                        "Feature folder " + featureFolder.getName().toUpperCase() + " was created with success at: " + endpointsFolderPath
                );
                getFeatureFolderFinder(featureFolder.getPath(), false);
            } else {
                failToCreateFolderResponse(
                        "ERROR FOUND! NOT possible to create feature folder! Process stopped."
                );
            }
        }
    }

    private void getFeatureFolderFinder(String featureFolderPath, boolean skip) {
        if (skip) {
            setupEndpointFolderCreator(featureFolderPath);
        } else {
            File featureFolder = new File(featureFolderPath);
            if (featureFolder.exists()) {
                printMessage("Feature folder " + featureFolder.getName().toUpperCase() + " found at: " + featureFolderPath);
                setupEndpointFolderCreator(featureFolderPath);
            } else {
                failToFindFolderResponse(
                        "Feature folder " + featureFolder.getName().toUpperCase() + " NOT found at: " + featureFolderPath
                );
            }
        }
    }

    private void setupEndpointFolderCreator(String featureFolderPath) {
        for (Endpoint endpoint : endpoints) {
            if (endpoint.getForFeature().equals(featureFileName)) {
                setupVariablesCombination(
                        featureFolderPath,
                        endpoint.getName(),
                        endpoint.getHasInvalidUserTest()
                );
            }
        }
    }

    private void setupVariablesCombination(String featureFolderPath, String folderName, boolean hasInvalidUserTest) {
        StringBuilder newFolderName = new StringBuilder();

        List<List<String>> combinations = new ArrayList<>();
        if (variablesSize > 0) {
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
                if (variablesSize > 1) {
                    getCombinations(combinations, combination, variablesSize, 1);
                } else {
                    combinations.add(combination);
                }

            }
            System.out.println(combinations);
            for (List<String> combinationList: combinations) {
                if (combinationList.size() > 0) {
                    newFolderName = new StringBuilder(folderName + "_with");
                    for (String comb : combinationList) {
                        newFolderName.append(comb);
                    }
                    endpointFolderCreator(featureFolderPath, newFolderName.toString());
                }
            }
        } else {
            newFolderName = new StringBuilder(folderName);
            endpointFolderCreator(featureFolderPath, newFolderName.toString());
        }


        if (hasInvalidUserTest) {
            endpointFolderCreator(
                    featureFolderPath,
                    newFolderName + "_but_with_invalid_user"
            );
        }
        endpointFolderCreator(
                featureFolderPath,
                newFolderName + "_but_with_expired_token"
        );
        getMetaCreator(featureFolderPath);
    }

    private void endpointFolderCreator(String featureFolderPath, String folderName) {
        //    private final int pauseTimeResponse = 750;
        int pauseTimeCommand = 0;
        printMessage.printMessage("Creating endpoint folder " + folderName.toUpperCase() + "...", pauseTimeCommand);
        getEndpointFolderCreator(
                featureFolderPath,
                folderName
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

    private void createEndpointFolder(File endpointFolder, String featureFolderPath, String folderName) {
        if (endpointFolder.mkdir()) {
            printMessage(
                    "Feature folder " + endpointFolder.getName().toUpperCase() + " was created with success at: " + featureFolderPath
            );
            getEndpointFolderFinder(featureFolderPath, endpointFolder.getPath(), folderName);
        } else {
            failToCreateFolderResponse(
                    "ERROR FOUND! NOT possible to create feature folder! Process stopped."
            );
        }
    }

    private void getEndpointFolderCreator(String featureFolderPath, String folderName) {
        File endpointFolder = new File(featureFolderPath + "\\" + folderName);
        if (endpointFolder.isDirectory()) {
            if (clearFolder(endpointFolder)) {
                if (endpointFolder.delete()) {
                    createEndpointFolder(endpointFolder, featureFolderPath, folderName);
                } else {
                    failToCreateFolderResponse(
                            "ERROR FOUND! NOT possible to delete folder " + endpointFolder.getName().toUpperCase() + "! Process stopped."
                    );
                }
            } else {
                failToCreateFolderResponse(
                        "ERROR FOUND! NOT possible to clear folder " + endpointFolder.getName().toUpperCase() + "! Process stopped."
                );
            }
        } else {
            createEndpointFolder(endpointFolder, featureFolderPath, folderName);
        }
    }

    private boolean clearFolder(File file) {
        try {
            List<File> files = Files.list(file.toPath()).map(Path::toFile).collect(Collectors.toList());
            int filesInitNumber = files.size();
            int filesClearedNumber = 0;
            for (File f : files) {
                if (f.isDirectory()) {
                    clearFolder(f);
                }
                if (f.delete()) {
                    filesClearedNumber++;
                    printMessage(
                            "File folder " + f.getName().toUpperCase() + " cleared with success at: " + file.getPath()
                    );
                }
            }
            return filesInitNumber == filesClearedNumber;
        } catch (IOException e) {
            return false;
        }
    }

    private void getEndpointFolderFinder(String featureFolderPath, String endpointFolderPath, String folderName) {
        File featureFolder = new File(featureFolderPath);
        if (featureFolder.exists()) {
            for (Endpoint endpoint : endpoints) {
                if (folderName.contains(endpoint.getName())) {
                    //-2 ERROR, -1 - not have body, 0 - missing, 1 - empty, 2 - invalid, 3 - valid
                    int bodyCombination = getBodyCombination(folderName);
                    if (bodyCombination != -1 && bodyCombination != 0) {
                        printMessage("Creating body file to endpoint " + folderName.toUpperCase() + " at: " + endpointFolderPath);
                        if (bodyCombination == 1) {
                            getEndpointBodyCreator(
                                    endpointFolderPath,
                                    folderName,
                                    "{}",
                                    endpoint.getValidHeaders()
                            );
                        } else if (bodyCombination == 2) {
                            getEndpointBodyCreator(
                                    endpointFolderPath,
                                    folderName,
                                    "{\n    \"asdasdasd\": \"asdasdasd\"\n}",
                                    endpoint.getValidHeaders()
                            );
                        } else if (bodyCombination == 3) {
                            getEndpointBodyCreator(
                                    endpointFolderPath,
                                    folderName,
                                    endpoint.getValidBody(),
                                    endpoint.getValidHeaders()
                            );
                        }
                    } else {
                        printMessage("Creating headers file to endpoint " + folderName.toUpperCase() + " at: " + endpointFolderPath);
                        getEndpointHeadersCreator(
                                endpointFolderPath,
                                folderName,
                                endpoint.getValidHeaders()
                        );
                    }
                    break;
                }
            }
        } else {
            failToFindFolderResponse(
                    "Feature folder " + featureFolder.getName().toUpperCase() + " NOT found at: " + endpointFolderPath
            );
        }
    }

    private int getBodyCombination(String name) {
        String[] namePartsSplitByBody = name.split("_body");
        if (namePartsSplitByBody.length > 0) {
            String namePart0SplitByBody = namePartsSplitByBody[0];
            String[] namePart0SplitByBodyAndUnderscore = namePart0SplitByBody.split("_");

            for (int i = namePart0SplitByBodyAndUnderscore.length - 1; i >= 0; i--) {
                switch (namePart0SplitByBodyAndUnderscore[i]) {
                    case "empty":
                        return 1;
                    case "invalid":
                        return 2;
                    case "missing":
                        return 0;
                    case "valid":
                        return 3;
                }
            }
            return -2;
        } else {
            return -1;
        }

    }

    private void getEndpointBodyCreator(String endpointFolderPath, String folderName, String body, String headers) {
        try {
            File bodyFile = new File(endpointFolderPath + "\\body.json");
            FileWriter bodyFileWriter = new FileWriter(bodyFile);
            bodyFileWriter.write(body);
            bodyFileWriter.close();
            getEndpointBodyFinder(
                    endpointFolderPath,
                    bodyFile.getPath(),
                    folderName,
                    headers
            );
        } catch (IOException e) {
            e.printStackTrace();
            failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create body file! Process stopped."
            );
        }
    }

    private void getEndpointBodyFinder(String endpointFolderPath, String bodyFilePath, String folderName, String headers) {
        File bodyFile = new File(bodyFilePath);
        if (bodyFile.exists()) {
            getEndpointHeadersCreator(
                    endpointFolderPath,
                    folderName,
                    headers
            );
        } else {
            failToFindFileResponse(
                    "Body file NOT found at: " + endpointFolderPath
            );
        }
    }

    private void getEndpointHeadersCreator(String endpointFolderPath, String folderName, String headers) {
        headers = setupHeaders(folderName, headers);
        try {
            File headersFile = new File(endpointFolderPath + "\\headers.yaml");
            FileWriter headersFileWriter = new FileWriter(headersFile);
            headersFileWriter.write(headers);
            headersFileWriter.close();
            getEndpointHeadersFinder(
                    endpointFolderPath,
                    headersFile.getPath()
            );
        } catch (IOException e) {
            e.printStackTrace();
            failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create headers file! Process stopped."
            );
        }
    }

    private String setupHeaders(String folderName, String headers) {
        if (folderName.contains("_but_with_invalid_user")) {
            String[] headersParts = headers.split("\\$var_bearer_token");
            headers = headersParts[0] + "_invalid" + "_user";
        }
        return headers;
    }

    private void getEndpointHeadersFinder(String endpointFolderPath, String filePath) {
        File headersFile = new File(filePath);
        if (!headersFile.exists()) {
            failToFindFileResponse(
                    "Headers file NOT found at: " + endpointFolderPath
            );
        }
    }

    private void getMetaCreator(String featureFolderPath) {
        try {
            File metaFile = new File(featureFolderPath + "\\meta.yaml");
            StringBuilder metaFilePreContent = saveFilePrContent(metaFile);
            FileWriter metaFileWriter = new FileWriter(metaFile);
            if (metaFilePreContent.toString().equals("")) {
                metaFileWriter.write("actions:");
            } else {
                metaFileWriter.write(metaFilePreContent.toString());
            }
            List<String> paths = writeEndpointActionLine(metaFileWriter, endpoints, featureFolderPath, metaFilePreContent.toString());
            metaFileWriter.close();
            printMessage("Meta file created at: " + featureFolderPath);
            getMetaFinder(
                    featureFolderPath,
                    metaFile.getPath(),
                    paths
            );
        } catch (IOException e) {
            e.printStackTrace();
            failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create meta file! Process stopped."
            );
        }
    }

    private List<String> writeEndpointActionLine(FileWriter metaFileWriter, List<Endpoint> endpoints, String featureFolderPath, String metaFilePreContent) throws IOException {
        List<String> paths = new ArrayList<>();
        for (Endpoint endpoint : endpoints) {
            String endpointName = endpoint.getName();
            for (File endpointFolder : Files.list(Paths.get(featureFolderPath)).map(Path::toFile).collect(Collectors.toList())) {
                String endpointFolderName = endpointFolder.getName();
                if (endpointFolderName.contains(endpointName)) {
                    if (!metaFilePreContent.contains(endpointFolderName)) {
                        metaFileWriter.write("\n - " + endpointFolderName + ":\n");
                        metaFileWriter.write("    steps:\n");
                        String path = "path_" + endpointFolderName;
                        paths.add(path);
                        metaFileWriter.write("      - " + endpoint.getRequestType() + "|" + path);
                    }
                }
            }
        }
        return paths;
    }

    private void getMetaFinder(String featureFolderPath, String filePath, List<String> paths) {
        File metaFile = new File(filePath);
        if (metaFile.exists()) {
            printMessage("Meta file found at: " + featureFolderPath);
            getDataCreator(
                    featureFolderPath,
                    paths
            );
        } else {
            failToFindFileResponse(
                    "Meta file NOT found at: " + featureFolderPath
            );
        }
    }

    private void getDataCreator(String featureFolderPath, List<String> paths) {
        try {
            File dataFile = new File(featureFolderPath + "\\data.yaml");
            StringBuilder dataFilePreContent = saveFilePrContent(dataFile);
            FileWriter dataFileWriter = new FileWriter(dataFile);
            if (dataFilePreContent.toString().equals("")) {
                dataFileWriter.write("base_url: " + baseUrl);
                dataFileWriter.write("\nvalues:");
            } else {
                dataFileWriter.write(dataFilePreContent.toString());
            }
            writeEndpointValuesLine(dataFileWriter, endpoints, paths, variables);
            dataFileWriter.close();
            printMessage("Data file created at: " + featureFolderPath);
            getDataFinder(
                    featureFolderPath,
                    dataFile.getPath()
            );
        } catch (IOException e) {
            e.printStackTrace();
            failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create data file! Process stopped."
            );
        }
    }

    private StringBuilder saveFilePrContent(File dataFile) throws FileNotFoundException {
        StringBuilder dataFilePreContent = new StringBuilder();
        if (dataFile.exists()) {
            Scanner dataFileScanner = new Scanner(dataFile);
            while (dataFileScanner.hasNextLine()) {
                dataFilePreContent.append(dataFileScanner.nextLine());
                if (dataFileScanner.hasNextLine()) {
                    dataFilePreContent.append("\n");
                }
            }
            dataFileScanner.close();
        }
        return dataFilePreContent;
    }

    private void writeEndpointValuesLine(FileWriter metaFileWriter, List<Endpoint> endpoints, List<String> paths, List<Variable> variables) throws IOException {
        for (Endpoint endpoint : endpoints) {
            String endpointName = endpoint.getName();
            for (String path : paths) {
                if (path.contains(endpointName)) {
                    StringBuilder endpointRemainingUrl = new StringBuilder(endpoint.getRemainingUrl());
                    metaFileWriter.write("\n  " + path + ": ");
                    for (Variable variable : variables) {
                        if (variable.getForEndpoint().equals(endpointName)) {
                            String[] remainingUrlParts = endpointRemainingUrl.toString().split(variable.getName());
                            if (variable.getName().contains("body")) {
                                if (!remainingUrlParts[0].equals("/")){
                                    int lastIndexOfForwardSlash = remainingUrlParts[0].lastIndexOf('/');
                                    if (lastIndexOfForwardSlash == remainingUrlParts[0].length() -1) {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0].substring(
                                                0,
                                                remainingUrlParts[0].length() - 1
                                        ));
                                    } else {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0]);
                                    }
                                } else {
                                    endpointRemainingUrl = new StringBuilder();
                                }
                            } else {
                                if (path.contains("_empty_" + variable.getName())) {
                                    if (variable.getIsInUrl()) {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0]);
                                    } else {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0] + variable.getName() + "=");
                                    }
                                } else if (path.contains("_invalid_" + variable.getName())) {
                                    if (variable.getIsInUrl()) {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0] + "asdasdasd");
                                    } else {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0] + variable.getName() + "=asdasdasd");
                                    }
                                } else if (path.contains("_missing_" + variable.getName())) {
                                    endpointRemainingUrl = new StringBuilder(remainingUrlParts[0]);
                                    if (!variable.getIsInUrl()) {
                                        int lastOccurrenceCommercialAndSymbol = endpointRemainingUrl.lastIndexOf("&");
                                        if (lastOccurrenceCommercialAndSymbol == endpointRemainingUrl.length() - 1) {
                                            endpointRemainingUrl = new StringBuilder(endpointRemainingUrl.substring(
                                                    0,
                                                    lastOccurrenceCommercialAndSymbol
                                            ));
                                        }
                                    }
                                } else if (path.contains("_valid_" + variable.getName())) {
                                    if (variable.getIsInUrl()) {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0] + variable.getValue());
                                    } else {
                                        endpointRemainingUrl = new StringBuilder(remainingUrlParts[0] + variable.getName() + "=" + variable.getValue());
                                    }
                                }
                            }
                            if (remainingUrlParts.length > 1) {
                                endpointRemainingUrl.append(remainingUrlParts[1]);
                            }
                        }
                    }
                    if (endpointRemainingUrl.toString().equals("")) {
                        endpointRemainingUrl = new StringBuilder("/");
                    }
                    int lastOccurrenceQuestionMark = endpointRemainingUrl.lastIndexOf("?");
                    if (lastOccurrenceQuestionMark == endpointRemainingUrl.length() - 1) {
                        endpointRemainingUrl = new StringBuilder(endpointRemainingUrl.substring(
                                0,
                                lastOccurrenceQuestionMark
                        ));
                    }
                    metaFileWriter.write(endpointRemainingUrl.toString());

                }
            }
        }
    }

    private void getDataFinder(String featureFolderPath, String filePath) {
        File metaFile = new File(filePath);
        if (metaFile.exists()) {
            printMessage("Data file found at: " + featureFolderPath);
            getSitDataCreator(
                    featureFolderPath
            );
        } else {
            failToFindFileResponse(
                    "Data file NOT found at: " + featureFolderPath
            );
        }
    }

    private void getSitDataCreator(String featureFolderPath) {
        try {
            File sitDataFile = new File(featureFolderPath + "\\sit.data.yaml");
            if (!sitDataFile.exists()) {
                FileWriter sitDataFileWriter = new FileWriter(sitDataFile);
                String[] baseUrlParts = baseUrl.split("delivery");
                sitDataFileWriter.write("base_url: ");
                sitDataFileWriter.write("base_url: " + baseUrlParts[0] + "sit" + baseUrlParts[1]);
                sitDataFileWriter.close();
                printMessage("Sit.data file found at: " + featureFolderPath);
                getSitDataFinder(featureFolderPath, sitDataFile.getPath());
            } else {
                printMessage("Sit data file already created at: " + featureFolderPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create sit.data file! Process stopped."
            );
        }
    }

    private void getSitDataFinder(String featureFolderPath, String filePath) {
        File metaFile = new File(filePath);
        if (metaFile.exists()) {
            printMessage("Sit.data file found at: " + featureFolderPath);
            if (testRunningCode == 0) {
                printMessage("\n\nCreation completed with success!");
            } else {
                printMessage("\n\nTest concluded with success!");
            }
        } else {
            failToFindFileResponse(
                    "Sit.data file NOT found at: " + featureFolderPath
            );
        }
    }

    private void failToCreateFileResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    private void failToCreateFolderResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    private void failToFindFolderResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    private void failToFindFileResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    private void printMessage(String response) {
        printMessage.printMessage(response, 0);
    }

}
