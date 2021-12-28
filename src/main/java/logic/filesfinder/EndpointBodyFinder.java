package logic.filesfinder;

import observers.filesfinder.EndpointBodyFinderResponse;
import observers.filesfinder.fail.FailToFindFileResponse;

import java.io.File;

public class EndpointBodyFinder {

    private EndpointBodyFinder() {}

    public static void getEndpointBodyFinder(
            EndpointBodyFinderResponse endpointBodyFinderResponse,
            FailToFindFileResponse failToFindFileResponse,
            String featureFolderPath,
            String endpointFolderPath,
            String filePath,
            String endpointName,
            String headers,
            boolean lastFolder
    ) {
        File bodyFile = new File(filePath);
        if (bodyFile.exists()) {
            endpointBodyFinderResponse.endpointBodyFinderResponse(
                    "Body file found at: " + endpointFolderPath,
                    featureFolderPath,
                    endpointFolderPath,
                    endpointName,
                    headers,
                    lastFolder
            );
        } else {
            failToFindFileResponse.failToFindFileResponse(
                    "Body file NOT found at: " + endpointFolderPath
            );
        }
    }
}
