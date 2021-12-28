package logic.filescreator;

import formelements.Endpoint;
import formelements.Variable;
import observers.filescreator.FeatureDataCreatorResponse;
import observers.filescreator.fail.FailToCreateFileResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FeatureDataCreator {

    private FeatureDataCreator() {}

    public static void getFeatureDataCreator(
            FeatureDataCreatorResponse featureDataCreatorResponse,
            FailToCreateFileResponse failToCreateFileResponse,
            String featureFolderPath,
            List<Endpoint> endpoints,
            List<String> paths,
            List<Variable> variables,
            String baseUrl
    ) {
        try {
            File dataFile = new File(featureFolderPath + "\\data.yaml");
            FileWriter dataFileWriter = new FileWriter(dataFile);
            dataFileWriter.write("base_url: " + baseUrl);
            dataFileWriter.write("\nvalues:");
            writeEndpointValuesLine(dataFileWriter, endpoints, paths, variables);
            dataFileWriter.close();
            featureDataCreatorResponse.featureDataCreatorResponse(
                    "Data file created at: " + featureFolderPath,
                    featureFolderPath,
                    dataFile.getPath()
            );
        } catch (IOException e) {
            failToCreateFileResponse.failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create meta file! Process stopped."
            );
            e.printStackTrace();
        }
    }

    private static void writeEndpointValuesLine(
            FileWriter metaFileWriter,
            List<Endpoint> endpoints,
            List<String> paths,
            List<Variable> variables
    ) throws IOException {
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
}
