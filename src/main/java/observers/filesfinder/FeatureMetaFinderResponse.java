package observers.filesfinder;

import java.util.List;

public interface FeatureMetaFinderResponse {
    public void featureMetaFinderResponse(String response, String featureFolderPath, List<String> paths);
}
