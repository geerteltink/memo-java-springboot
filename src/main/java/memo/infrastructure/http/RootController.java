package memo.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/")
    public ResponseEntity<RootResponse> root() {
        return ResponseEntity.ok(new RootResponse("hello", HttpStatus.OK));
    }
}
