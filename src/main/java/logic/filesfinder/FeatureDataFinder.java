package logic.filesfinder;

import observers.filesfinder.EndpointBodyFinderResponse;
import observers.filesfinder.FeatureDataFinderResponse;
import observers.filesfinder.fail.FailToFindFileResponse;

import java.io.File;

public class FeatureDataFinder {

    private FeatureDataFinder() {}

    public static void getFeatureDataFinder(
            FeatureDataFinderResponse featureDataFinderResponse,
            FailToFindFileResponse failToFindFileResponse,
            String featureFolderPath,
            String filePath
    ) {
        File metaFile = new File(filePath);
        if (metaFile.exists()) {
            featureDataFinderResponse.featureDataFinderResponse(
                    "Sit.data file found at: " + featureFolderPath,
                    featureFolderPath
            );
        } else {
            failToFindFileResponse.failToFindFileResponse(
                    "Data file NOT found at: " + featureFolderPath
            );
        }
    }
}
