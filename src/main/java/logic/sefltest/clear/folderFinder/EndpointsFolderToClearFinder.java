package logic.sefltest.clear.folderFinder;

import observers.folderfinder.fail.FailToFindFolderResponse;
import observers.folderfinder.toclear.EndpointsFolderFinderToClearResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public final class EndpointsFolderToClearFinder {

    private EndpointsFolderToClearFinder() {}

    public static void getEndpointsFolderFinderToClear(
            EndpointsFolderFinderToClearResponse endpointsFolderFinderToClearResponse,
            FailToFindFolderResponse failToFindFolderResponse,
            String componentsFolderPath
    ) {
        try {
            String folderPath = searchForFolder(componentsFolderPath);
            if (folderPath != null) {
                endpointsFolderFinderToClearResponse.endpointsFolderFinderToClearResponse(
                        "Endpoints folder ENDPOINTS found at: " + componentsFolderPath,
                        folderPath
                );
            } else {
                failToFindFolderResponse.failToFindFolderResponse(
                        "Endpoints folder ENDPOINTS NOT found at: " + componentsFolderPath + " FOLDER NO CLEARED"
                );
            }
        } catch (IOException e) {
            failToFindFolderResponse.failToFindFolderResponse(
                    "ERROR FOUND! NOT possible to continue! Process stopped."
            );
        }
    }

    private static String searchForFolder(String componentsFolderPath) throws IOException {
            for (File folder :
                    Files.list(Paths.get(componentsFolderPath)).map(Path::toFile).collect(Collectors.toList())) {
                if (folder.getName().equals("endpoints")) {
                    return folder.getPath();
                }
            }
            return null;
    }

}
