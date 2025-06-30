package memo.infrastructure.http;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class RootControllerTest {

    @Test
    void itHandlesTheRequest() {
        RootController controller = new RootController();
        ResponseEntity<RootResponse> response = controller.root();

        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody() != null;
        assert response.getBody().toString().contains("hello");
        assert response.getBody().toString().contains(org.springframework.http.HttpStatus.OK.toString());
    }
}
