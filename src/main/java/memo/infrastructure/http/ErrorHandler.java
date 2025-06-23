package memo.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    private static final String INTERNAL_ERROR_MESSAGE = "Internal Server Error";

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handle(RuntimeException e) {
        logError(e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception e) {
        logError(e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handle(MethodArgumentTypeMismatchException e) {
        logError(e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handle(ConstraintViolationException e) {
        logError(e);

        List<ErrorRecord> errors = formatFieldErrors(e.getConstraintViolations());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handle(HttpRequestMethodNotSupportedException e) {
        logError(e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private List<ErrorRecord> formatFieldErrors(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .filter(cv -> cv.getPropertyPath() != null) // Filter out constraint violations with null property path
                .map(cv -> new ErrorRecord(cv.getPropertyPath().toString(), cv.getMessage())).toList();
    }

    private void logError(Exception e) {
        log.error("{}: {}\n{}", e.getClass().getCanonicalName(), e.getMessage(), ExceptionUtils.getStackTrace(e));
    }
}
