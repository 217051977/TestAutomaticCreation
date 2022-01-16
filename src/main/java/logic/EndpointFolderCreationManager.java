package logic;

import formelements.Endpoint;
import observers.PrintMessage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class EndpointFolderCreationManager {

    private final PrintMessage printMessage;
    private final List<Endpoint> endpoints;
    private final String featureFolderPath;
    private final String folderName;
    private boolean lastOne = false;

    public EndpointFolderCreationManager(
            PrintMessage printMessage,
            List<Endpoint> endpoints,
            String featureFolderPath,
            String folderName
    ) {
        this.printMessage = printMessage;
        this.endpoints = endpoints;
        this.featureFolderPath = featureFolderPath;
        this.folderName = folderName;
    }

    public void setLastOne() {
        lastOne = true;
    }

    public void endpointFolderCreator() {
        printMessage("Creating endpoint folder " + folderName.toUpperCase() + "...");
        getEndpointFolderCreator();
    }

    private void getEndpointFolderCreator() {
        File endpointFolder = new File(featureFolderPath + "\\" + folderName);
        if (endpointFolder.isDirectory()) {
            if (clearFolder(endpointFolder)) {
                if (endpointFolder.delete()) {
                    createEndpointFolder(endpointFolder);
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
            createEndpointFolder(endpointFolder);
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

    private void createEndpointFolder(File endpointFolder) {
        if (endpointFolder.mkdir()) {
            printMessage(
                    "Endpoint folder " + endpointFolder.getName().toUpperCase() + " was created with success at: " + featureFolderPath
            );
            getEndpointFolderFinder(endpointFolder.getPath());
        } else {
            failToCreateFolderResponse(
                    "ERROR FOUND! NOT possible to create endpoint folder! Process stopped."
            );
        }
    }

    private void getEndpointFolderFinder(String endpointFolderPath) {
        File featureFolder = new File(featureFolderPath);
        if (featureFolder.exists()) {
            try {
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
            } catch (NullPointerException nullPointerException) {
                nullPointerException.printStackTrace();
            }
        } else {
            failToFindFolderResponse(
                    "Feature folder " + featureFolder.getName().toUpperCase() + " NOT found at: " + endpointFolderPath
            );
        }
    }

    private int getBodyCombination(String name) {
        if (name.contains("_body")) {
            String[] namePartsSplitByBody = name.split("_body");
//        if (namePartsSplitByBody.length > 0) {
//            String namePart0SplitByBody = name.split("_body")[0];
            String namePart0SplitByBody = namePartsSplitByBody[0];
//            String[] namePart0SplitByBodyAndUnderscore = name.split("_body")[0].split("_");
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
            headers = headers.replace("$var_bearer_token", "$var_bearer_token_invalid_user");
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
        if (lastOne) {
            Thread.currentThread().interrupt();
        }
    }

    private void failToCreateFileResponse(String response) {
        printMessage(response);
    }

    private void failToCreateFolderResponse(String response) {
        printMessage(response);
    }

    private void failToFindFolderResponse(String response) {
        printMessage(response);
    }

    private void failToFindFileResponse(String response) {
        printMessage(response);
    }

    private void printMessage(String response) {
        printMessage.printMessage(response, 0);
    }

}
