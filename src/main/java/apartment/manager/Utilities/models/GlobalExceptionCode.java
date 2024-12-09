package apartment.manager.Utilities.models;

public enum GlobalExceptionCode {
    RESOURCE_NOT_FOUND,// Error occurred because a building doesn't exist in the database
    VALIDATION,// Errors related to the validation
    INCORRECT_VERSION, // Errors occurred because a PUT request included changes to a resource that conflict with those made by an earlier request
    AUTHENTICATION, // Authentication errors
    UNIQUENESS, // Uniqueness errors
}
