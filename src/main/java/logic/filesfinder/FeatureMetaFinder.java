package logic.filesfinder;

import observers.filesfinder.FeatureMetaFinderResponse;
import observers.filesfinder.fail.FailToFindFileResponse;

import java.io.File;
import java.util.List;

public class FeatureMetaFinder {

    private FeatureMetaFinder() {}

    public static void getFeatureMetaFinder(
            FeatureMetaFinderResponse featureMetaFinderResponse,
            FailToFindFileResponse failToFindFileResponse,
            String featureFolderPath,
            String filePath,
            List<String> paths
    ) {
        File metaFile = new File(filePath);
        if (metaFile.exists()) {
            featureMetaFinderResponse.featureMetaFinderResponse(
                    "Meta file found at: " + featureFolderPath,
                    featureFolderPath,
                    paths
            );
        } else {
            failToFindFileResponse.failToFindFileResponse(
                    "Meta file NOT found at: " + featureFolderPath
            );
        }
    }
}
