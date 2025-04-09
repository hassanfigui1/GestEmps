package payroll.payrollservice.GraphQLController;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import payroll.payrollservice.model.Salary;
import payroll.payrollservice.service.SalaryService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalaryGraphQLController {

    private final SalaryService salaryService;

    @MutationMapping
    public Boolean deleteSalary(@Argument String salaryId) {
        return salaryService.deleteSalary(salaryId); // Call service method to delete the salary
    }

    @MutationMapping
    public Salary updateSalary(
            @Argument String salaryId,
            @Argument BigDecimal baseSalary,
            @Argument BigDecimal bonus,
            @Argument BigDecimal taxDeductions,
            @Argument BigDecimal netSalary
    ) {
        return salaryService.updateSalary(salaryId, baseSalary, bonus, taxDeductions, netSalary);
    }
    @MutationMapping
    public Salary calculateSalary(
            @Argument String employeeId,
            @Argument BigDecimal baseSalary,
            @Argument BigDecimal bonus
    ) {
        try {
            return salaryService.calculateAndSaveSalary(employeeId, baseSalary, bonus);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating salary: " + e.getMessage(), e);
        }
    }

    // Query to get remaining leave days
    @QueryMapping
    public Integer getRemainingLeaveDays(@Argument String employeeId) {
        return salaryService.getRemainingLeaveDays(employeeId);
    }

    // Query to get all salaries for an employee
    @QueryMapping
    public List<Salary> getSalariesForEmployee(@Argument String employeeId) {
        return salaryService.getSalariesForEmployee(employeeId);
    }

    // Query to get all salaries for all employees
    @QueryMapping
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();  // Call the new method in SalaryService
    }

    // Query to get a salary by ID
    @QueryMapping
    public Salary getSalaryById(@Argument String salaryId) {
        return salaryService.getSalaryById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
    }
}
