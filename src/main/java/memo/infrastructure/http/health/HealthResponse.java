package memo.infrastructure.http.health;

import org.springframework.http.HttpStatus;

public record HealthResponse(String serviceName, HttpStatus status) {

}
