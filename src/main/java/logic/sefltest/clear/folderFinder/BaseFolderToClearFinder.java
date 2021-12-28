package logic.sefltest.clear.folderFinder;

import observers.folderfinder.fail.FailToFindFolderResponse;
import observers.folderfinder.toclear.BaseFolderFinderToClearResponse;

import java.io.File;

public final class BaseFolderToClearFinder {

    private BaseFolderToClearFinder() {}

    public static void getBaseFolderToClearFinder(
            BaseFolderFinderToClearResponse baseFolderFinderToClearResponse,
            FailToFindFolderResponse failToFindFolderResponse,
            String projectBaseFolderPath
    ) {
        File baseFolder = new File(projectBaseFolderPath);
        if (baseFolder.exists()) {
            baseFolderFinderToClearResponse.baseFolderFinderToClearResponse(
                    "Base folder " + baseFolder.getName().toUpperCase() + " found at: " + baseFolder.getPath(),
                    projectBaseFolderPath
            );
        } else {
            failToFindFolderResponse.failToFindFolderResponse(
                    "Base folder " + baseFolder.getName().toUpperCase() + " NOT found at: " + baseFolder.getPath()
            );
        }
    }
}
