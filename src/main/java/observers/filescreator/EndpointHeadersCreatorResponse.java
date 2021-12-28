package observers.filescreator;

public interface EndpointHeadersCreatorResponse {
    public void endpointHeadersCreatorResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String headersFilePath,
            boolean lastFolder
    );
}
