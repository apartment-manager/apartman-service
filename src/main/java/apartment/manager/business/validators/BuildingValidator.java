package apartment.manager.business.validators;

import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.presentation.models.BuildingDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

import java.util.Set;

public class BuildingValidator {

    private Validator validator;

    public BuildingValidator(Validator validator) {
        this.validator = validator;
    }

    public void validateBuilding(BuildingDto building) {
        Set<ConstraintViolation<BuildingDto>> violations = validator.validate(building);

        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            errors.append("The building model has the following problems: \n");
            violations.forEach(violation -> {
                errors.append(violation.getMessage()).append("\n");
            });
            throw new GlobalException(errors.toString(), GlobalExceptionCode.VALIDATION, ValidationException.class);
        }

    }
}
