package observers.foldercreator;

public interface EndpointFolderCreatorResponse {
    public void endpointFolderCreatorResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String name,
            boolean lastFolder
    );
}
