package leavemgt.leavemgtservice.GraphQLController;

import leavemgt.leavemgtservice.Entity.LeaveRequest;
import leavemgt.leavemgtservice.Service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LeaveGraphQLController {
    private final LeaveService leaveService;
    

    @MutationMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveService.getAllLeaveRequests();
    }
    @MutationMapping
    public Boolean deleteLeaveRequest(
            @Argument String leaveRequestId
    ) {
        return leaveService.deleteLeaveRequest(leaveRequestId);
    }

    @MutationMapping
    public LeaveRequest submitLeaveRequest(
            @Argument String employeeId,
            @Argument LeaveRequest.LeaveType leaveType,
            @Argument LocalDate startDate,
            @Argument LocalDate endDate,
            @Argument String comments
    ) {
        return leaveService.submitLeaveRequest(
                employeeId, leaveType, startDate, endDate, comments
        );
    }
    @MutationMapping
    public LeaveRequest updateLeaveRequest(
            @Argument String leaveRequestId,
            @Argument String employeeId,
            @Argument LeaveRequest.LeaveType leaveType,
            @Argument LocalDate startDate,
            @Argument LocalDate endDate,
            @Argument String comments,
            @Argument LeaveRequest.LeaveStatus status) {
        return leaveService.updateLeaveRequest(leaveRequestId, employeeId, leaveType, startDate, endDate, comments, status);
    }


    @MutationMapping
    public LeaveRequest updateLeaveRequestStatus(
            @Argument String leaveRequestId,
            @Argument LeaveRequest.LeaveStatus status
    ) {
        return leaveService.updateLeaveRequestStatus(leaveRequestId, status);
    }

    @QueryMapping
    public List<LeaveRequest> getLeaveRequestsByEmployee(
            @Argument String employeeId
    ) {
        return leaveService.getLeaveRequestsByEmployee(employeeId);
    }

    @QueryMapping
    public List<LeaveRequest> getLeaveRequestsByStatus(
            @Argument LeaveRequest.LeaveStatus status
    ) {
        return leaveService.getLeaveRequestsByStatus(status);
    }

    @QueryMapping
    public LeaveRequest getLeaveRequestById(
            @Argument String leaveRequestId
    ) {
        return leaveService.getLeaveRequestById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
    }
}

