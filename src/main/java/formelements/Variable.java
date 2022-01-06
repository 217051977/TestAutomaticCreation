package formelements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Variable {

    private String forEndpoint = "endpoint_test";
    private String name = "variable_1";
    private String value = "variable_test_value";
    private boolean isInUrl = true;
    private boolean combinationEmpty = false;
    private boolean combinationInvalid = true;
    private boolean combinationMissing = true;

    public Variable(String name) {
        this.name = name;
        this.value = name + "_value";
    }

    public Variable() {}

    public Variable(boolean isInUrl) {
        this.isInUrl = isInUrl;
        this.combinationEmpty = !isInUrl;
    }

    public Variable(
            String forEndpoint,
            String name,
            String value,
            boolean isInUrl,
            boolean combinationEmpty,
            boolean combinationInvalid,
            boolean combinationMissing
    ) {
        this.forEndpoint = forEndpoint;
        this.name = name;
        if (value.length() > 0) {
            if (value.charAt(0) == '$') {
                this.value = "$" + URLEncoder.encode(value.substring(1), StandardCharsets.UTF_8);
            } else {
                this.value = URLEncoder.encode(value, StandardCharsets.UTF_8);
            }
        } else {
            this.value = value;
        }
        this.isInUrl = isInUrl;
        this.combinationEmpty = combinationEmpty;
        this.combinationInvalid = combinationInvalid;
        this.combinationMissing = combinationMissing;
    }

    public String getForEndpoint() {
        return forEndpoint;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean getIsInUrl() {
        return isInUrl;
    }

    public boolean getCombinationEmpty() {
        return combinationEmpty;
    }

    public boolean getCombinationInvalid() {
        return combinationInvalid;
    }

    public boolean getCombinationMissing() {
        return combinationMissing;
    }

}
