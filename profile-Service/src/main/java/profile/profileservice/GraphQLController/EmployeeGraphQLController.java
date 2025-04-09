package profile.profileservice.GraphQLController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import profile.profileservice.Entity.Employee;
import profile.profileservice.Service.EmployeeService;

import java.util.List;

@Controller

public class EmployeeGraphQLController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeGraphQLController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @QueryMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @QueryMapping
    public Employee getEmployeeById(@Argument String id) {
        return employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @MutationMapping
    public Employee createEmployee(@Argument Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @MutationMapping
    public Employee updateEmployee(@Argument String id, @Argument Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @QueryMapping
    public List<Employee> searchEmployees(@Argument String term) {
        return employeeService.searchEmployees(term);
    }

    @MutationMapping
    public void deleteEmployee(@Argument String id) {
        employeeService.deleteEmployee(id);
    }
}
