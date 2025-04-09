package payroll.payrollservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private LocalDate payPeriod;

    @Column(nullable = false)
    private BigDecimal baseSalary;

    private BigDecimal bonus;
    private BigDecimal taxDeductions;
    private BigDecimal netSalary;
}
