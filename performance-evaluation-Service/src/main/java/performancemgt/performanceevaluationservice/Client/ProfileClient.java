package performancemgt.performanceevaluationservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import performancemgt.performanceevaluationservice.Client.Wrapper.GraphQLRequest;

@FeignClient(name = "profile-service")
public interface ProfileClient {

    @PostMapping("/graphql")
    String executeQuery(@RequestBody GraphQLRequest request);
}
