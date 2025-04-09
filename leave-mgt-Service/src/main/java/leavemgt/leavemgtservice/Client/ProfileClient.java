package leavemgt.leavemgtservice.Client;

import leavemgt.leavemgtservice.Client.Wrapper.GraphQLRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service")
public interface ProfileClient {

    @PostMapping("/graphql")
    String executeQuery(@RequestBody GraphQLRequest request);
}
