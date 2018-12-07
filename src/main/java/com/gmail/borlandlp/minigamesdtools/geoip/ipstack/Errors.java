package com.gmail.borlandlp.minigamesdtools.geoip.ipstack;

// https://ipstack.com/documentation
public enum Errors {
    NOT_FOUND(404, "The requested resource does not exist. "),
    MISSING_ACCESS_KEY(101, "No API Key was specified."),
    INVALID_ACCESS_KEY(101, "No API Key was specified or an invalid API Key was specified."),
    INACTIVE_USER(102, "The current user account is not active. User will be prompted to get in touch with Customer Support."),
    INVALID_API_FUNCTION(103, "The requested API endpoint does not exist."),
    USAGE_LIMIT_REACHED(104, "The maximum allowed amount of monthly API requests has been reached."),
    FUNCTION_ACCESS_RESTRICTED(105, "The current subscription plan does not support this API endpoint."),
    HTTP_ACCESS_RESTRICTED(105, "The user's current subscription plan does not support HTTPS Encryption."),
    INVALID_FIELD(301, "One or more invalid fields were specified using the fields parameter."),
    TOO_MANY_IPS(302, "Too many IPs have been specified for the Bulk Lookup Endpoint. (max. 50)"),
    BATCH_NOT_SUPPORTED_ON_PLAN(303, "The Bulk Lookup Endpoint is not supported on the current subscription plan");

    private int errCode;
    private String errMessage;

    Errors(int code, String msg) {
        this.errCode = code;
        this.errMessage = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static Errors getByErrCode(int errCode) {
        for (Errors error : Errors.values()) {
            if(error.getErrCode() == errCode) {
                return error;
            }
        }

        return null;
    }
}
