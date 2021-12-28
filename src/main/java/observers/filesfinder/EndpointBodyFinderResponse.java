package observers.filesfinder;

public interface EndpointBodyFinderResponse {
    public void endpointBodyFinderResponse(
            String response,
            String featureFolderPath,
            String endpointFolderPath,
            String endpointName,
            String headers,
            boolean lastFolder
    );
}
