package observers.folderfinder;

public interface EndpointFolderFinderResponse {
    public void endpointFolderFinderResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String name,
            boolean lastFolder
    );
}
