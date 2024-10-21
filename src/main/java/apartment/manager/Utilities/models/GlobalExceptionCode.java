package apartment.manager.Utilities.models;

public enum GlobalExceptionCode {
    RESOURCE_BUILDING_NOT_FOUND,// Error occurred because a building doesn't exist in the database
    VALIDATION,// Errors related to the validation
    INCORRECT_VERSION, // Errors occurred because a PUT request included changes to a resource that conflict with those made by an earlier request
    ERROR_005,
    ERROR_006,
    ERROR_007,
    ERROR_008,
    ERROR_009,
    ERROR_0010,
}
