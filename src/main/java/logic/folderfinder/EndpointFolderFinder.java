package logic.folderfinder;

import observers.folderfinder.EndpointFolderFinderResponse;
import observers.folderfinder.fail.FailToFindFolderResponse;

import java.io.File;

public class EndpointFolderFinder {

    private EndpointFolderFinder() {}

    public static void getEndpointFolderFinder(
            EndpointFolderFinderResponse endpointFolderFinderResponse,
            FailToFindFolderResponse failToFindFolderResponse,
            String featureFolderPath,
            String endpointFolderPath,
            String name,
            boolean lastFolder
    ) {
        File featureFolder = new File(endpointFolderPath);
        if (featureFolder.exists()) {
            endpointFolderFinderResponse.endpointFolderFinderResponse(
                    "Endpoint folder " + featureFolder.getName().toUpperCase() + " found at: " + endpointFolderPath,
                    featureFolderPath,
                    endpointFolderPath,
                    name,
                    lastFolder
            );
        } else {
            failToFindFolderResponse.failToFindFolderResponse(
                    "Feature folder " + featureFolder.getName().toUpperCase() + " NOT found at: " + endpointFolderPath
            );
        }
    }
}
