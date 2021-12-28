package logic.filescreator;

import formelements.Endpoint;
import formelements.Variable;
import observers.filescreator.FeatureDataCreatorResponse;
import observers.filescreator.FeatureSitDataCreatorResponse;
import observers.filescreator.fail.FailToCreateFileResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FeatureSitDataCreator {

    private FeatureSitDataCreator() {}

    public static void getFeatureSitDataCreator(
            FeatureSitDataCreatorResponse featureSitDataCreatorResponse,
            FailToCreateFileResponse failToCreateFileResponse,
            String featureFolderPath,
            String baseUrl
    ) {
        try {
            File dataFile = new File(featureFolderPath + "\\sit.data.yaml");
            FileWriter sitDataFileWriter = new FileWriter(dataFile);
            String[] baseUrlParts = baseUrl.split("delivery");
            sitDataFileWriter.write("base_url: ");
            sitDataFileWriter.write("base_url: " + baseUrlParts[0] + "sit" + baseUrlParts[1]);
            sitDataFileWriter.close();
            featureSitDataCreatorResponse.featureSitDataCreatorResponse(
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
}
