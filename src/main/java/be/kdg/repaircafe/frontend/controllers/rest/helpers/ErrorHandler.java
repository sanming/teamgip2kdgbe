package be.kdg.repaircafe.frontend.controllers.rest.helpers;

import be.kdg.repaircafe.frontend.controllers.resources.errors.ErrorResource;
import be.kdg.repaircafe.frontend.controllers.resources.errors.ValidationErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Helper class for all Controllers
 * Supplies all ExceptionHandler methodes
 */
@ControllerAdvice
public class ErrorHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResource> processValidationError(MethodArgumentNotValidException ex)
    {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    private ValidationErrorResource processFieldErrors(List<FieldError> fieldErrors)
    {
        ValidationErrorResource validationErrorResource = new ValidationErrorResource();

        for (FieldError fieldError : fieldErrors)
        {
            validationErrorResource.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return validationErrorResource;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResource> processUserServiceException(RuntimeException rte)
    {
        return new ResponseEntity<>(new ErrorResource(rte.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }
}
