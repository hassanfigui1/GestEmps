package leavemgt.leavemgtservice.Service;

import leavemgt.leavemgtservice.Entity.LeaveRequest;
import leavemgt.leavemgtservice.Repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRequestRepository leaveRequestRepository;
    private static final Logger logger = LoggerFactory.getLogger(LeaveService.class);

    // Removed ProfileClient

    public LeaveRequest submitLeaveRequest(
            String employeeId,
            LeaveRequest.LeaveType leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String comments
    ) {
        logger.info("Submitting leave request for employee ID: {}", employeeId);

        // Removed ProfileService interaction for validation
        // You may perform any validation locally or skip this step if not required

        // Create and save leave request
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(employeeId);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setComments(comments);
        leaveRequest.setStatus(LeaveRequest.LeaveStatus.PENDING);

        logger.info("Creating leave request: {}", leaveRequest);
        return leaveRequestRepository.save(leaveRequest);
    }

    public Boolean deleteLeaveRequest(String leaveRequestId) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(leaveRequestId);
        if (leaveRequest.isPresent()) {
            leaveRequestRepository.deleteById(leaveRequestId);
            return true;
        }
        return false;  // Return false if the leave request wasn't found
    }

    public List<LeaveRequest> searchLeaveRequests(String query) {
        return leaveRequestRepository.findByEmployeeIdContainingOrLeaveTypeContainingOrStatusContaining(query, query, query);
    }

    public LeaveRequest updateLeaveRequest(
            String leaveRequestId,
            String employeeId,
            LeaveRequest.LeaveType leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String comments,
            LeaveRequest.LeaveStatus status) {

        // Find the existing leave request
        return leaveRequestRepository.findById(leaveRequestId)
                .map(leaveRequest -> {
                    // Update fields
                    leaveRequest.setEmployeeId(employeeId);
                    leaveRequest.setLeaveType(leaveType);
                    leaveRequest.setStartDate(startDate);
                    leaveRequest.setEndDate(endDate);
                    leaveRequest.setComments(comments);
                    leaveRequest.setStatus(status);

                    // Save the updated leave request
                    return leaveRequestRepository.save(leaveRequest);
                })
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
    }

    // Update leave request status
    public LeaveRequest updateLeaveRequestStatus(
            String leaveRequestId,
            LeaveRequest.LeaveStatus status
    ) {
        return leaveRequestRepository.findById(leaveRequestId)
                .map(leaveRequest -> {
                    leaveRequest.setStatus(status);
                    return leaveRequestRepository.save(leaveRequest);
                })
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
    }

    // Get leave requests by employee ID
    public List<LeaveRequest> getLeaveRequestsByEmployee(String employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    // Get leave requests by status
    public List<LeaveRequest> getLeaveRequestsByStatus(LeaveRequest.LeaveStatus status) {
        return leaveRequestRepository.findByStatus(status);
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    // Get leave request by ID
    public Optional<LeaveRequest> getLeaveRequestById(String leaveRequestId) {
        return leaveRequestRepository.findById(leaveRequestId);
    }
}
