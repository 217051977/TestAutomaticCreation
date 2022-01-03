package logic;

import formelements.BaseFolder;
import formelements.Endpoint;
import formelements.Feature;
import formelements.Variable;
import observers.CountTimePassed;
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
    private final int testRunningCode;

    private final PrintMessage printMessage;
    private CountTimePassed countTimePassed;
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
        this.baseUrl = this.feature.getBaseEndpoint();
        this.testRunningCode = testRunningCode;
    }

    public void setCountTimePassed(
            CountTimePassed countTimePassed
    ) {
        this.countTimePassed = countTimePassed;
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
        List<Thread> threads = new ArrayList<>();
        List<EndpointFolderCreationManager> endpointFolderCreationManagers = new ArrayList<>();
        for (Endpoint endpoint : endpoints) {
            if (endpoint.getForFeature().equals(featureFileName)) {
                setupVariablesCombination(
                        featureFolderPath,
                        endpoint.getName(),
                        endpoint.getHasInvalidUserTest(),
                        endpointFolderCreationManagers
                );
            }
        }

        countTimePassed.countTimePassed(System.currentTimeMillis());

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        int reservedThreadsNumber = 3;
        if (numberOfCores > reservedThreadsNumber) {
            int endpointFolderCreationManagersSize = endpointFolderCreationManagers.size();
            int endpointFolderCreationManagersPerCore = endpointFolderCreationManagersSize / (numberOfCores - reservedThreadsNumber);

            for (int i = 0; i < numberOfCores - reservedThreadsNumber; i++) {
                if (i != numberOfCores - 1 - reservedThreadsNumber) {
                    List<EndpointFolderCreationManager> endpointFolderCreationManagersToLaunch = new ArrayList<>();
                    for (int j = 0; j < endpointFolderCreationManagersPerCore; j++) {
                        if (j == endpointFolderCreationManagersPerCore - 1) {
                            endpointFolderCreationManagers.get(j).setLastOne();
                        }
                        endpointFolderCreationManagersToLaunch.add(endpointFolderCreationManagers.get(j));
                    }
                    launchThread(
                            endpointFolderCreationManagersToLaunch,
                            threads,
                            i
                    );
                    endpointFolderCreationManagers.removeAll(endpointFolderCreationManagersToLaunch);
                } else {
                    endpointFolderCreationManagers.get(endpointFolderCreationManagers.size() - 1).setLastOne();
                    launchThread(
                            endpointFolderCreationManagers,
                            threads,
                            i
                    );
                }
            }
        } else {
            launchThread(
                    endpointFolderCreationManagers,
                    threads,
                    0
            );
        }
//        startThreads(threads);
//        endpointFolderCreator(
//                featureFolderPath,
//                newFolderName + "_but_with_expired_token"
//        );
        Thread thread = new Thread(
                () -> {
                    if (checkIfEveryThreadIsDone(threads, 1)) {
                        getMetaCreator(featureFolderPath);
                    }
                }
        );
        thread.setName("Thread_Check_If_Every_Thread_Is_Done");
        thread.start();
    }

    private void launchThread(
            List<EndpointFolderCreationManager> endpointFolderCreationManagers,
            List<Thread> threads,
            int i
    ) {
        Thread thread = new Thread(
                () -> endpointFolderCreationManagers.forEach(
                        EndpointFolderCreationManager::endpointFolderCreator
                )
        );
        thread.setName("Thread_Number" + i);
        startThread(thread, 1);
        threads.add(thread);
        System.out.println(threads.size());
        System.out.println(thread.getName());
    }

    private boolean checkIfEveryThreadIsDone(List<Thread> threads, int multiplier) {
        List<Thread> runningThreads = new ArrayList<>();
        try {
            Thread.sleep(500L * multiplier);
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    runningThreads.add(thread);
                }
            }
            if (runningThreads.size() == 0) {
                return true;
            } else if (runningThreads.size() < threads.size()) {
                if (multiplier > 1) {
                    multiplier--;
                }
                return checkIfEveryThreadIsDone(runningThreads, multiplier);
            } else {
                multiplier++;
                return checkIfEveryThreadIsDone(runningThreads, multiplier);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void startThread(Thread thread, int multiplier) {
        try {
            thread.start();
        } catch (OutOfMemoryError oof) {
            try {
                Thread.sleep(500L * multiplier);
                multiplier++;
                startThread(thread, multiplier);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oof.printStackTrace();
        }
    }

    private void setupVariablesCombination(String featureFolderPath, String endpointName, boolean hasInvalidUserTest, List<EndpointFolderCreationManager> endpointFolderCreationManagers) {
        StringBuilder newFolderName = new StringBuilder();

        List<List<String>> combinations = new ArrayList<>();
        int nVarForEndpoint = 0;
        List<Variable> variablesForEndpoint = new ArrayList<>();
        for (Variable variable : variables) {
            if (variable.getForEndpoint().equals(endpointName)) {
                variablesForEndpoint.add(variable);
                nVarForEndpoint++;
            }
        }
        if (nVarForEndpoint > 0) {
            for (int k = 0; k < 4; k++) {
                Variable variable = variablesForEndpoint.get(0);
                String combinationPrefix = getCombinationPrefix(variable, k);
                String toAdd = "";
                if (combinationPrefix == null) {
                    continue;
                } else {
                    toAdd += combinationPrefix;
                }
                List<String> combination = new ArrayList<>(List.of(toAdd + variable.getName()));
                if (nVarForEndpoint > 1) {
                    getCombinations(combinations, combination, nVarForEndpoint, 1, variablesForEndpoint);
                } else {
                    combinations.add(combination);
                }

            }
            System.out.println(combinations);
            for (List<String> combinationList: combinations) {
                if (combinationList.size() > 0) {
                    newFolderName = new StringBuilder(endpointName + "_with");
                    for (String comb : combinationList) {
                        newFolderName.append(comb);
                    }
                    String finalNewFolderName = newFolderName.toString();
                    printMessage("Preparing to create endpoint folder " + finalNewFolderName.toUpperCase() + "...");
                    endpointFolderCreationManagers.add(
                            new EndpointFolderCreationManager(
                                    printMessage,
                                    endpoints,
                                    featureFolderPath,
                                    finalNewFolderName
                            )
                    );
//                    Thread thread = new Thread(
//                            () -> new EndpointFolderCreationManager(
//                                    printMessage,
//                                    endpoints
//                            ).endpointFolderCreator(
//                                    featureFolderPath,
//                                    finalNewFolderName
//                            )
//                    );
//                    thread.setName("Thread_" + finalNewFolderName);
//                    startThread(thread, 1);
//                    threads.add(thread);
//                    System.out.println(threads.size());
//                    System.out.println(thread.getName());
                }
            }
        } else {
            newFolderName = new StringBuilder(endpointName);
            String finalNewFolderName = newFolderName.toString();
            printMessage("Creating endpoint folder " + finalNewFolderName.toUpperCase() + "...");
            endpointFolderCreationManagers.add(
                    new EndpointFolderCreationManager(
                            printMessage,
                            endpoints,
                            featureFolderPath,
                            finalNewFolderName
                    )
            );
//            new Thread(
//                    () -> new EndpointFolderCreationManager(
//                            printMessage,
//                            endpoints
//                    ).endpointFolderCreator(
//                            featureFolderPath,
//                            finalNewFolderName
//                    )
//            );
        }


        if (hasInvalidUserTest) {
            String finalNewFolderName = newFolderName + "_but_with_invalid_user";
            printMessage("Creating endpoint folder " + finalNewFolderName.toUpperCase() + "...");
            endpointFolderCreationManagers.add(
                    new EndpointFolderCreationManager(
                            printMessage,
                            endpoints,
                            featureFolderPath,
                            finalNewFolderName
                    )
            );
//            new Thread(
//                    () -> new EndpointFolderCreationManager(
//                            printMessage,
//                            endpoints
//                    ).endpointFolderCreator(
//                            featureFolderPath,
//                            finalNewFolderName
//                    )
//            );
        }
        String finalNewFolderName = newFolderName + "_but_with_expired_token";
        printMessage("Creating endpoint folder " + finalNewFolderName.toUpperCase() + "...");
        endpointFolderCreationManagers.add(
                new EndpointFolderCreationManager(
                        printMessage,
                        endpoints,
                        featureFolderPath,
                        finalNewFolderName
                )
        );
//        new Thread(
//                () -> new EndpointFolderCreationManager(
//                        printMessage,
//                        endpoints
//                ).endpointFolderCreator(
//                        featureFolderPath,
//                        finalNewFolderName
//                )
//        );

//        for (int i = 0; i < endpointFolderCreationManagersSize; i++) {
//
//        }

    }

    private void getCombinations(List<List<String>> combinations, List<String> toAdd, int variableSize, int i, List<Variable> variablesForEndpoint) {
        if (i + 1 < variableSize) {
            for (int j = 0; j < 4; j++) {
                List<String> combination = addCombination(toAdd, i, j, variablesForEndpoint);
                if (combination == null) {
                    continue;
                }
                getCombinations(combinations, combination, variableSize, i + 1, variablesForEndpoint);
            }
        } else {
            for (int j = 0; j < 4; j++) {
                List<String> combination = addCombination(toAdd, i, j, variablesForEndpoint);
                if (combination == null) {
                    continue;
                }
                if (combination.size() == variableSize) {
                    combinations.add(combination);
                }
            }
        }
    }

    private List<String> addCombination(List<String> toAdd, int i, int j, List<Variable> variablesForEndpoint) {
        List<String> combination = new ArrayList<>(toAdd);
        Variable variable = variablesForEndpoint.get(i);
        String combinationPrefix2 = getCombinationPrefix(variable, j);
        String toAdd2 = "";
        if (combinationPrefix2 == null) {
            return null;
        } else {
            toAdd2 += combinationPrefix2;
        }
        combination.add(toAdd2 + variable.getName());
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
            countTimePassed.countTimePassed(System.currentTimeMillis());
            if (testRunningCode == 0) {
                printMessage("\n\nCreation completed with success!");
            } else {
                printMessage("\n\nTest concluded with success!");
            }
            Thread.currentThread().interrupt();
        } else {
            failToFindFileResponse(
                    "Sit.data file NOT found at: " + featureFolderPath
            );
        }
    }

    private void failToCreateFileResponse(String response) {
        printMessage.printMessage(response, 0);
    }

    private void failToCreateFolderResponse() {
        printMessage.printMessage("ERROR FOUND! NOT possible to create feature folder! Process stopped.", 0);
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
