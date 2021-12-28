package logic.filescreator;

import observers.filescreator.EndpointBodyCreatorResponse;
import observers.filescreator.fail.FailToCreateFileResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EndpointBodyCreator {

    private EndpointBodyCreator() {}

    public static void getEndpointBodyCreator(
            EndpointBodyCreatorResponse endpointBodyCreatorResponse,
            FailToCreateFileResponse failToCreateFileResponse,
            String featureFolderPath,
            String endpointFolderPath,
            String endpointName,
            String body,
            String headers,
            boolean lastFolder
    ) {
        try {
            File bodyFile = new File(endpointFolderPath + "\\body.json");
            FileWriter bodyFileWriter = new FileWriter(bodyFile);
            bodyFileWriter.write(body);
            bodyFileWriter.close();
            endpointBodyCreatorResponse.endpointBodyCreatorResponse(
                    "Body file created at: " + endpointFolderPath,
                    featureFolderPath,
                    endpointFolderPath,
                    bodyFile.getPath(),
                    endpointName,
                    headers,
                    lastFolder
            );
        } catch (IOException e) {
            failToCreateFileResponse.failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create body file! Process stopped."
            );
            e.printStackTrace();
        }
    }
}
