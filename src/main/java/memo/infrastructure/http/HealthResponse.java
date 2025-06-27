package memo.infrastructure.http;

import org.springframework.http.HttpStatus;

public record HealthResponse(String serviceName, HttpStatus status) {

}
