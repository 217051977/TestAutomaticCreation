package logic;

import formelements.BaseFolder;
import formelements.Endpoint;
import formelements.Feature;
import formelements.Variable;
import observers.PrintMessage;
import observers.sefltest.DeleteAllFoldersSelfTestByPopulatingFormResponse;

import java.util.List;

public class FormManager extends FileManager {

    public FormManager(
            PrintMessage printMessage,
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse,
            int testRunningCode
    ) {
        super(printMessage, testRunningCode);
        setDeleteAllFoldersSelfTestByPopulatingFormResponse(deleteAllFoldersSelfTestByPopulatingFormResponse);
    }

    public FormManager(
            PrintMessage printMessage,
            BaseFolder baseFolder,
            Feature feature,
            List<Endpoint> endpoints,
            List<Variable> variables,
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse,
            int testRunningCode
    ) {
        super(printMessage, feature, endpoints, variables, baseFolder, testRunningCode);
        setDeleteAllFoldersSelfTestByPopulatingFormResponse(deleteAllFoldersSelfTestByPopulatingFormResponse);
    }

    public void start() {
        getBaseFolderFinder();
    }

    public void startTest() {
        getBaseFolderToClearFinder();
    }

}
