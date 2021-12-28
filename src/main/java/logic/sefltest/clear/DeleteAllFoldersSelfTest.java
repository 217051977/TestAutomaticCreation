package logic.sefltest.clear;

import observers.folderfinder.fail.FailToFindFolderResponse;
import observers.sefltest.DeleteAllFoldersSelfTestByPopulatingFormResponse;
import observers.sefltest.DeleteAllFoldersSelfTestResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteAllFoldersSelfTest {

    private DeleteAllFoldersSelfTest() {}

    public static void getDeleteAllFoldersSelfTest(
            DeleteAllFoldersSelfTestResponse deleteAllFoldersSelfTestResponse,
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse,
            FailToFindFolderResponse failToFindFolderResponse,
            String endpointsBaseFolderPath,
            int testCode
    ) {
        File endpointsFolder = new File(endpointsBaseFolderPath);
        if (clearFolder(endpointsFolder)) {
            if (testCode == 1) {
                deleteAllFoldersSelfTestResponse.deleteAllFoldersSelfTestResponse(
                        "All folders deletedBase folder " + endpointsFolder.getName().toUpperCase() + " found at: " + endpointsFolder.getPath()
                );
            } else if (testCode == 2) {
                deleteAllFoldersSelfTestByPopulatingFormResponse.deleteAllFoldersSelfTestByPopulatingFormResponse(
                        "All folders deletedBase folder " + endpointsFolder.getName().toUpperCase() + " found at: " + endpointsFolder.getPath()
                );
            }
        } else {
            failToFindFolderResponse.failToFindFolderResponse(
                    "Base folder " + endpointsFolder.getName().toUpperCase() + " NOT found at: " + endpointsFolder.getPath()
            );
        }
    }

    private static boolean clearFolder(File file) {
        try {
            List<File> files = Files.list(file.toPath()).map(Path::toFile).collect(Collectors.toList());
            int filesInitNumber = files.size();
            int filesClearedNumber = 0;
            for (File f : files) {
                if (f.isDirectory()) {
                    clearFolder(f);
                }
                if (f.delete()) {
                    filesClearedNumber++;
                }
            }
            return filesInitNumber == filesClearedNumber;
        } catch (IOException e) {
            return false;
        }
    }
}
