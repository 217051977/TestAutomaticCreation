package observers.filescreator;

public interface EndpointBodyCreatorResponse {
    public void endpointBodyCreatorResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String bodyFilePath,
            String endpointName,
            String headers, boolean lastFolder
    );
}
