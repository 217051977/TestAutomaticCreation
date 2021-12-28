package logic.sefltest.clear.folderFinder;

import observers.folderfinder.fail.FailToFindFolderResponse;
import observers.folderfinder.toclear.ComponentsFolderFinderToClearResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public final class ComponentsFolderToClearFinder {

    private ComponentsFolderToClearFinder() {}

    public static void getComponentsFolderToClearFinder(
            ComponentsFolderFinderToClearResponse componentsFolderFinderToClearResponse,
            FailToFindFolderResponse failToFindFolderResponse,
            String projectBaseFolderPath
    ) {
        boolean folderFound = false;
        try {
            for (File folder :
                    Files.list(Paths.get(projectBaseFolderPath)).map(Path::toFile).collect(Collectors.toList())) {
                if (folder.getName().equals("components")) {
                    folderFound = true;
                    componentsFolderFinderToClearResponse.componentsFolderFinderToClearResponse(
                            "Components folder COMPONENTS found at: " + projectBaseFolderPath,
                            folder.getPath()
                    );
                }
            }
            if (!folderFound) {
                failToFindFolderResponse.failToFindFolderResponse(
                        "Components folder COMPONENTS NOT found at: " + projectBaseFolderPath
                );
            }
        } catch (IOException e) {
            failToFindFolderResponse.failToFindFolderResponse(
                    "ERROR FOUND! NOT possible to continue! Process stopped."
            );
        }
    }

}
