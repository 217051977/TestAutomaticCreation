package formelements;

public class Feature {

    private String fileName = "feature_file_name_test";
    private String name = "feature name test";
    private String baseEndpoint = "https://delivery-digitaljourney.westeurope.cloudapp.azure.com/bin/mvc.do/operationconsole/v3/roles";
    private String tags = "@cms @opc @test";

    public Feature() {}

    public Feature(String fileName, String name, String baseEndpoint, String tags) {
        this.fileName = fileName;
        this.name = name;
        this.baseEndpoint = baseEndpoint;
        this.tags = tags;
    }

    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }

    public String getBaseEndpoint() {
        return baseEndpoint;
    }

    public String getTags() {
        return tags;
    }

}
