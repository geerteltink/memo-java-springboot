package memo.infrastructure.http;

import org.springframework.http.HttpStatus;

public record RootResponse(String ack, HttpStatus status) {

}
