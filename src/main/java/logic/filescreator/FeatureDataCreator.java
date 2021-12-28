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
                    String endpointRemainingUrl = endpoint.getRemainingUrl();
                    metaFileWriter.write("\n  " + path + ": ");
                    for (Variable variable : variables) {
                        if (variable.getForEndpoint().equals(endpointName)) {
                            String[] remainingUrlParts = endpointRemainingUrl.split(variable.getName());
                            endpointRemainingUrl = remainingUrlParts[0] + variable.getValue();
                            if (remainingUrlParts.length > 1) {
                                endpointRemainingUrl += remainingUrlParts[1];
                            }
                        }
                    }
                    metaFileWriter.write(endpointRemainingUrl);

                }
            }
        }
    }
}
