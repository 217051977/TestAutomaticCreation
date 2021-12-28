package logic.foldercreator;

import observers.foldercreator.EndpointFolderCreatorResponse;
import observers.foldercreator.fail.FailToCreateFolderResponse;

import java.io.File;

public class EndpointFolderCreator {

    private EndpointFolderCreator() {}

    public static void getEndpointFolderCreator(
            EndpointFolderCreatorResponse endpointFolderCreator,
            FailToCreateFolderResponse failToCreateFolderResponse,
            String featureFolderPath,
            String name,
            boolean lastFolder
    ) {
        File endpointFolder = new File(featureFolderPath + "\\" + name);
        if (endpointFolder.mkdir()) {
            endpointFolderCreator.endpointFolderCreatorResponse(
                    "Endpoint folder " + name.toUpperCase() + " created at: " + featureFolderPath,
                    featureFolderPath,
                    endpointFolder.getPath(),
                    name,
                    lastFolder
            );
        } else {
            failToCreateFolderResponse.failToCreateFolderResponse(
                    "ERROR FOUND! NOT possible to create feature folder! Process stopped."
            );
        }
    }
}
