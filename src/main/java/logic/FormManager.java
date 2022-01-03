package logic;

import formelements.BaseFolder;
import formelements.Endpoint;
import formelements.Feature;
import formelements.Variable;
import observers.CountTimePassed;
import observers.PrintMessage;
import observers.sefltest.DeleteAllFoldersSelfTestByPopulatingFormResponse;

import java.util.List;

public class FormManager extends FileManager {

    public FormManager(
            PrintMessage printMessage,
            CountTimePassed countTimePassed,
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse,
            int testRunningCode
    ) {
        super(printMessage, testRunningCode);
        setDeleteAllFoldersSelfTestByPopulatingFormResponse(deleteAllFoldersSelfTestByPopulatingFormResponse);
        setCountTimePassed(countTimePassed);
    }

    public FormManager(
            PrintMessage printMessage,
            CountTimePassed countTimePassed,
            BaseFolder baseFolder,
            Feature feature,
            List<Endpoint> endpoints,
            List<Variable> variables,
            DeleteAllFoldersSelfTestByPopulatingFormResponse deleteAllFoldersSelfTestByPopulatingFormResponse,
            int testRunningCode
    ) {
        super(printMessage, feature, endpoints, variables, baseFolder, testRunningCode);
        setDeleteAllFoldersSelfTestByPopulatingFormResponse(deleteAllFoldersSelfTestByPopulatingFormResponse);
        setCountTimePassed(countTimePassed);
    }

    public void start() {
        getBaseFolderFinder();
    }

    public void startTest() {
        getBaseFolderToClearFinder();
    }

}
