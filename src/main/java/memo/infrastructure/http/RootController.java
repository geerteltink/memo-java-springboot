package memo.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/")
    public ResponseEntity<RootResponse> root() {
        return ResponseEntity.ok(new RootResponse(serviceName, HttpStatus.OK));
    }

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(new HealthResponse(serviceName, HttpStatus.OK));
    }
}
