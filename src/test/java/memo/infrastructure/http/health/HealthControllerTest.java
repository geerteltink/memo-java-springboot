package memo.infrastructure.http.health;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class HealthControllerTest {

    @Test
    void itHandlesTheRequest() {
        HealthController controller = new HealthController();
        ResponseEntity<HealthResponse> response = controller.health();

        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody() != null;
        assert response.getBody().toString().contains("serviceName");
        assert response.getBody().toString().contains(org.springframework.http.HttpStatus.OK.toString());
    }
}
