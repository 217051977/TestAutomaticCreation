package observers.filescreator;

import java.util.List;

public interface FeatureMetaCreatorResponse {
    public void featureMetaCreatorResponse(String response, String featureFolderPath, String filePath, List<String> paths);
}
