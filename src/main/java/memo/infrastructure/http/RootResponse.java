package memo.infrastructure.http;

import org.springframework.http.HttpStatus;

public record RootResponse(String serviceName, HttpStatus status) {

}
