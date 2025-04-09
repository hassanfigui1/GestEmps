package payroll.payrollservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payroll.payrollservice.model.Salary;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, String> {
    List<Salary> findByEmployeeId(String employeeId);
}

