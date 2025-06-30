package memo.infrastructure.http;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;

public class ProblemDetailsFactory {
    public static ProblemDetail create(@NotNull HttpStatus status, @NotNull String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);

        problemDetail.setType(URI.create(String.format("https://httpstatus.es/%s", status.value())));
        problemDetail.setTitle(status.getReasonPhrase());

        return problemDetail;
    }
}
