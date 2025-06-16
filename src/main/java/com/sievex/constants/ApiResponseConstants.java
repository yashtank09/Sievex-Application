package com.sievex.constants;

public class ApiResponseConstants {
    // ==== Status Indicators ====
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILURE = "FAILURE";

    // ==== HTTP Status Codes ====
    public static final int CODE_OK = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_ACCEPTED = 202;
    public static final int CODE_NO_CONTENT = 204;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_CONFLICT = 409;
    public static final int CODE_INTERNAL_ERROR = 500;

    // ==== Messages: CREATE ====
    public static final String MSG_CREATE_SUCCESS = "Resource created successfully";
    public static final String MSG_CREATE_FAILED = "Failed to create resource";

    // ==== Messages: READ ====
    public static final String MSG_READ_SUCCESS = "Resource retrieved successfully";
    public static final String MSG_READ_ALL_SUCCESS = "Resources retrieved successfully";
    public static final String MSG_READ_NOT_FOUND = "Resource not found";

    // ==== Messages: UPDATE ====
    public static final String MSG_UPDATE_SUCCESS = "Resource updated successfully";
    public static final String MSG_UPDATE_FAILED = "Failed to update resource";

    // ==== Messages: DELETE ====
    public static final String MSG_DELETE_SUCCESS = "Resource deleted successfully";
    public static final String MSG_DELETE_FAILED = "Failed to delete resource";

    // ==== Validation / Authorization / Misc ====
    public static final String MSG_VALIDATION_FAILED = "Validation failed for the request";
    public static final String MSG_UNAUTHORIZED = "Unauthorized access";
    public static final String MSG_FORBIDDEN = "You do not have permission to perform this action";
    public static final String MSG_CONFLICT = "Resource conflict occurred";
    public static final String MSG_INTERNAL_ERROR = "An unexpected error occurred";

    // ==== Domain-specific Messages (optional examples) ====
    public static final String MSG_SITE_ADD_SUCCESS = "Site added successfully";
    public static final String MSG_SITE_FETCH_SUCCESS = "Sites retrieved successfully";
    public static final String MSG_SITE_NOT_FOUND = "No sites found";
    public static final String MSG_SITE_UPDATE_SUCCESS = "Site updated successfully";
    public static final String MSG_SITE_DELETE_SUCCESS = "Site deleted successfully";

}
