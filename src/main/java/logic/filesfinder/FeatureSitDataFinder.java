package logic.filesfinder;

import observers.filesfinder.EndpointBodyFinderResponse;
import observers.filesfinder.FeatureSitDataFinderResponse;
import observers.filesfinder.fail.FailToFindFileResponse;

import java.io.File;

public class FeatureSitDataFinder {

    private FeatureSitDataFinder() {}

    public static void getFeatureSitDataFinder(
            FeatureSitDataFinderResponse featureSitDataFinderResponse,
            FailToFindFileResponse failToFindFileResponse,
            String featureFolderPath,
            String filePath
    ) {
        File metaFile = new File(filePath);
        if (metaFile.exists()) {
            featureSitDataFinderResponse.featureSitDataFinderResponse(
                    "Sit.data file found at: " + featureFolderPath
            );
        } else {
            failToFindFileResponse.failToFindFileResponse(
                    "Sit.data file NOT found at: " + featureFolderPath
            );
        }
    }
}
