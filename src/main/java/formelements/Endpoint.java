package formelements;

public class Endpoint {

    private String forFeature = "feature_file_name_test";
    private String name = "endpoint_test";
    private String remainingUrl = "/variable_1";
    private String requestType = "GET";
    private boolean hasInvalidUserTest = true;
    private String validBody = "{\n" +
            "    \"id\": \"1\",\n" +
            "    \"name\": \"Bruno\",\n" +
            "    \"description\": \"This is the body for this program self populating values for testing!\"\n" +
            "}";
    private String validHeaders = "accept: application/json\n" +
            "authorization: Bearer $var_bearer_token\n" +
            "Content-Type: application/json";

    public Endpoint(){}

    public Endpoint(String remainingUrl){
        this.remainingUrl = remainingUrl;
    }

    public Endpoint(
            String forFeature,
            String name,
            String remainingUrl,
            String requestType,
            boolean hasInvalidUserTest,
            String validBody,
            String validHeaders
    ) {
        this.forFeature = forFeature;
        this.name = name;
        this.remainingUrl = remainingUrl;
        this.requestType = requestType;
        this.hasInvalidUserTest = hasInvalidUserTest;
        this.validBody = validBody;
        this.validHeaders = validHeaders;
    }

    public String getForFeature() {
        return forFeature;
    }

    public String getName() {
        return name;
    }

    public String getRemainingUrl() {
        return remainingUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public boolean getHasInvalidUserTest() {
        return hasInvalidUserTest;
    }

    public String getValidBody() {
        return validBody;
    }

    public String getValidHeaders() {
        return validHeaders;
    }

}
