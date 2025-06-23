package memo.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/")
    public RootResponse action() {
        return new RootResponse(serviceName, HttpStatus.OK);
    }
}
