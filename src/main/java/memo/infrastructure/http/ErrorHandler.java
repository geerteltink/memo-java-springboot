package memo.infrastructure.http;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import memo.domain.MemoFound;
import memo.domain.MemoNotFound;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    private static final String INTERNAL_ERROR_MESSAGE = "Internal Server Error";

    @ExceptionHandler(MemoNotFound.class)
    public ProblemDetail handle(MemoNotFound e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MemoFound.class)
    public ProblemDetail handle(MemoFound e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handle(HttpMessageNotReadableException e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handle(ConstraintViolationException e) {
        logError(e);

        ProblemDetail problemDetail = ProblemDetailsFactory.create(HttpStatus.BAD_REQUEST, e.getMessage());
        List<ErrorRecord> errors = formatFieldErrors(e.getConstraintViolations());
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handle(NoResourceFoundException e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handle(MethodArgumentTypeMismatchException e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handle(HttpRequestMethodNotSupportedException e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handle(RuntimeException e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception e) {
        logError(e);

        return ProblemDetailsFactory.create(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
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
