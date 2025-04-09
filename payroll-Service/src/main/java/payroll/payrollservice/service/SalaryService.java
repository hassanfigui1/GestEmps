package payroll.payrollservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payroll.payrollservice.Client.ProfileClient;
import payroll.payrollservice.model.Salary;
import payroll.payrollservice.repository.SalaryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final ProfileClient profileClient; // Inject ProfileClient

    // Calculate salary with interaction with ProfileService
    public Salary calculateAndSaveSalary(String employeeId, BigDecimal baseSalary, BigDecimal bonus) {
        // Create new salary record
        Salary salary = new Salary();
        salary.setEmployeeId(employeeId);
        salary.setBaseSalary(baseSalary);
        salary.setBonus(bonus);
        salary.setPayPeriod(LocalDate.now());

        // Simple tax calculation (hypothetical)
        BigDecimal taxRate = new BigDecimal("0.25");
        BigDecimal taxDeductions = baseSalary.add(bonus).multiply(taxRate);
        salary.setTaxDeductions(taxDeductions);
        salary.setNetSalary(baseSalary.add(bonus).subtract(taxDeductions));

        // Save the salary
        return salaryRepository.save(salary);
    }

    public Salary updateSalary(String salaryId, BigDecimal baseSalary, BigDecimal bonus, BigDecimal taxDeductions, BigDecimal netSalary) {
        Optional<Salary> existingSalaryOpt = salaryRepository.findById(salaryId);

        if (existingSalaryOpt.isEmpty()) {
            throw new RuntimeException("Salary record with ID " + salaryId + " not found.");
        }

        Salary existingSalary = existingSalaryOpt.get();
        existingSalary.setBaseSalary(baseSalary);
        existingSalary.setBonus(bonus);
        existingSalary.setTaxDeductions(taxDeductions);
        existingSalary.setNetSalary(netSalary);

        // Save and return the updated salary
        return salaryRepository.save(existingSalary);
    }

    public boolean deleteSalary(String salaryId) {
        Optional<Salary> salary = salaryRepository.findById(salaryId);

        if (salary.isEmpty()) {
            throw new RuntimeException("Salary with ID " + salaryId + " not found.");
        }

        salaryRepository.deleteById(salaryId);
        return true;
    }


    // Get all salaries for all employees
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();  // Fetch all salaries from the repository
    }

    // Get remaining leave days (dummy implementation here, just for illustration)
    public Integer getRemainingLeaveDays(String employeeId) {
        List<Salary> salaries = salaryRepository.findByEmployeeId(employeeId);
        if (salaries.isEmpty()) {
            return 0;
        }

        // Assuming base salary is a proxy for leave balance
        BigDecimal totalLeaveDays = salaries.stream()
                .map(Salary::getBaseSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalLeaveDays.intValue();
    }

    // Get all salaries for an employee
    public List<Salary> getSalariesForEmployee(String employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    // Fetch salary by ID
    public Optional<Salary> getSalaryById(String salaryId) {
        return salaryRepository.findById(salaryId);
    }
}
