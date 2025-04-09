package leavemgt.leavemgtservice.Repository;

import leavemgt.leavemgtservice.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, String> {
    List<LeaveRequest> findByEmployeeId(String employeeId);
    List<LeaveRequest> findByStatus(LeaveRequest.LeaveStatus status);
    List<LeaveRequest> findAll();
    List<LeaveRequest> findByEmployeeIdContainingOrLeaveTypeContainingOrStatusContaining(
            String employeeId, String leaveType, String status
    );

}