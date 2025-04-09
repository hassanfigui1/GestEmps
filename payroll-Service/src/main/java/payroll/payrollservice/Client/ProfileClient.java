package payroll.payrollservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import payroll.payrollservice.Client.Wrapper.GraphQLRequest;

@FeignClient(name = "profile-service")
public interface ProfileClient {

    @PostMapping("/graphql")
    String executeQuery(@RequestBody GraphQLRequest request);
}
