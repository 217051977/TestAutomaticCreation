package logic.filescreator;

import formelements.Endpoint;
import observers.filescreator.FeatureMetaCreatorResponse;
import observers.filescreator.fail.FailToCreateFileResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeatureMetaCreator {

    private FeatureMetaCreator() {}

    public static void getFeatureMetaCreator(
            FeatureMetaCreatorResponse featureMetaCreatorResponse,
            FailToCreateFileResponse failToCreateFileResponse,
            String featureFolderPath,
            List<Endpoint> endpoints
    ) {
        try {
            File metaFile = new File(featureFolderPath + "\\meta.yaml");
            FileWriter metaFileWriter = new FileWriter(metaFile);
            metaFileWriter.write("actions:");
            List<String> paths = writeEndpointActionLine(metaFileWriter, endpoints, featureFolderPath);
            metaFileWriter.close();
            featureMetaCreatorResponse.featureMetaCreatorResponse(
                    "Meta file created at: " + featureFolderPath,
                    featureFolderPath,
                    metaFile.getPath(),
                    paths
            );
        } catch (IOException e) {
            failToCreateFileResponse.failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create meta file! Process stopped."
            );
            e.printStackTrace();
        }
    }

    private static List<String> writeEndpointActionLine(FileWriter metaFileWriter, List<Endpoint> endpoints, String featureFolderPath) throws IOException {
        List<String> paths = new ArrayList<>();
        for (Endpoint endpoint : endpoints) {
            String endpointName = endpoint.getName();
            for (File endpointFolder : Files.list(Paths.get(featureFolderPath)).map(Path::toFile).collect(Collectors.toList())) {
                String endpointFolderName = endpointFolder.getName();
                if (endpointFolderName.contains(endpointName)) {
                    metaFileWriter.write("\n - " + endpointFolderName + ":\n");
                    metaFileWriter.write("    steps:\n");
                    String path = "path_" + endpointFolderName;
                    paths.add(path);
                    metaFileWriter.write("      - " + endpoint.getRequestType() + "|" + path);
                }
            }
        }
        return paths;
    }

}
