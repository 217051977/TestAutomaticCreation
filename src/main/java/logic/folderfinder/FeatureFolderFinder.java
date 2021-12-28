package logic.folderfinder;

import observers.folderfinder.FeatureFolderFinderResponse;
import observers.folderfinder.fail.FailToFindFolderResponse;

import java.io.File;

public class FeatureFolderFinder {

    private FeatureFolderFinder() {}

    public static void getFeatureFolderFinder(
            FeatureFolderFinderResponse featureFolderFinderResponse,
            FailToFindFolderResponse failToFindFolderResponse,
            String featureFolderPath,
            String name
    ) {
        File featureFolder = new File(featureFolderPath);
        if (featureFolder.exists()) {
            featureFolderFinderResponse.featureFolderFinderResponse(
                    "Feature folder " + featureFolder.getName().toUpperCase() + " found at: " + featureFolderPath,
                    featureFolderPath,
                    name
            );
        } else {
            failToFindFolderResponse.failToFindFolderResponse(
                    "Feature folder " + featureFolder.getName().toUpperCase() + " NOT found at: " + featureFolderPath
            );
        }
    }

}
