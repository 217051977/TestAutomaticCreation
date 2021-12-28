package logic.foldercreator;

import observers.foldercreator.FeatureFolderCreatorResponse;
import observers.foldercreator.fail.FailToCreateFolderResponse;

import java.io.File;

public class FeatureFolderCreator {

    private FeatureFolderCreator() {}

    public static void getFeatureFolderCreator(
            FeatureFolderCreatorResponse featureFolderCreatorResponse,
            FailToCreateFolderResponse failToCreateFolderResponse,
            String endpointsFolderPath,
            String name,
            boolean lastFolder
    ) {
        File featureFolder = new File(endpointsFolderPath + "\\" + name);
        if (featureFolder.mkdir()) {
            featureFolderCreatorResponse.featureFolderCreatorResponse(
                    "Feature folder " + name.toUpperCase() + " created at: " + endpointsFolderPath,
                    featureFolder.getPath(),
                    name,
                    lastFolder
            );
        } else {
            failToCreateFolderResponse.failToCreateFolderResponse(
                    "ERROR FOUND! NOT possible to create feature folder! Process stopped."
            );
        }
    }

}
