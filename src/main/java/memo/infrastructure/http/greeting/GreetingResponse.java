package memo.infrastructure.http.greeting;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GreetingResponse extends RepresentationModel<GreetingResponse> {

    private final String content;

    @JsonCreator
    public GreetingResponse(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
