package payroll.payrollservice.Client.Wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class GraphQLRequest implements Serializable {
    private String query;
    private Map<String, Object> variables;
}