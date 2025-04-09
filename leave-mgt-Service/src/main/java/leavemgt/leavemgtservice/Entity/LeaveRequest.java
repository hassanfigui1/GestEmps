package leavemgt.leavemgtservice.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "leave_requests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String comments;
    public enum LeaveType {
        ANNUAL, SICK, PARENTAL, UNPAID, STUDY, PERSONAL
    }

    public enum LeaveStatus {
        PENDING, APPROVED, REJECTED, CANCELLED
    }
}