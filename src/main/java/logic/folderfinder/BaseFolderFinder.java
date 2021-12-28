package logic.folderfinder;

import observers.folderfinder.BaseFolderFinderResponse;
import observers.folderfinder.fail.FailToFindFolderResponse;

import java.io.File;

public final class BaseFolderFinder {

    private BaseFolderFinder() {}

    public static void getBaseFolderFinder(
            BaseFolderFinderResponse baseFolderFinderResponse,
            FailToFindFolderResponse failToFindFolderResponse,
            String projectBaseFolderPath
    ) {
        File baseFolder = new File(projectBaseFolderPath);
        if (baseFolder.exists()) {
            baseFolderFinderResponse.baseFolderFinderResponse(
                    "Base folder " + baseFolder.getName().toUpperCase() + " found at: " + baseFolder.getPath(),
                    projectBaseFolderPath
            );
        } else {
            failToFindFolderResponse.failToFindFolderResponse(
                    "Base folder " + baseFolder.getName().toUpperCase() + " NOT found at: " + baseFolder.getPath()
            );
            //baseFolderFinderResponse.baseFolderFinderResponse("Base folder " + UNDERLINED + baseFolder.getName() + RESET + " " + RED_BOLD + "NOT" + RESET + " found at: " + UNDERLINED + baseFolder.getPath() + RESET);
        }
    }
}
