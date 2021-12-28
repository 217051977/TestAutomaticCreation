package logic.filesfinder;

import observers.filesfinder.EndpointHeadersFinderResponse;
import observers.filesfinder.fail.FailToFindFileResponse;

import java.io.File;

public class EndpointHeadersFinder {

    private EndpointHeadersFinder() {}

    public static void getEndpointHeadersFinder(
            EndpointHeadersFinderResponse endpointHeadersFinderResponse,
            FailToFindFileResponse failToFindFileResponse,
            String featureFolderPath,
            String endpointFolderPath,
            String filePath,
            boolean lastFolder
    ) {
        File headersFile = new File(filePath);
        if (headersFile.exists()) {
            endpointHeadersFinderResponse.endpointHeadersFinderResponse(
                    "Headers file found at: " + endpointFolderPath,
                    featureFolderPath,
                    lastFolder);
        } else {
            failToFindFileResponse.failToFindFileResponse(
                    "Headers file NOT found at: " + endpointFolderPath
            );
        }
    }
}
