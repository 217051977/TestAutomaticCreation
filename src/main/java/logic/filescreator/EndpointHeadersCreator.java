package logic.filescreator;

import observers.filescreator.EndpointHeadersCreatorResponse;
import observers.filescreator.fail.FailToCreateFileResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EndpointHeadersCreator {

    private EndpointHeadersCreator() {}

    public static void getEndpointHeadersCreator(
            EndpointHeadersCreatorResponse endpointHeadersCreatorResponse,
            FailToCreateFileResponse failToCreateFileResponse,
            String featureFolderPath,
            String endpointFolderPath,
            String endpointName,
            String headers,
            boolean lastFolder
    ) {
        try {
            File headersFile = new File(endpointFolderPath + "\\headers.yaml");
            FileWriter headersFileWriter = new FileWriter(headersFile);
            headersFileWriter.write(headers);
            headersFileWriter.close();
            endpointHeadersCreatorResponse.endpointHeadersCreatorResponse(
                    "Headers file created at: " + endpointFolderPath,
                    featureFolderPath,
                    endpointFolderPath,
                    headersFile.getPath(),
                    lastFolder
            );
        } catch (IOException e) {
            failToCreateFileResponse.failToCreateFileResponse(
                    "ERROR FOUND! NOT possible to create headers file! Process stopped."
            );
            e.printStackTrace();
        }
    }
}
