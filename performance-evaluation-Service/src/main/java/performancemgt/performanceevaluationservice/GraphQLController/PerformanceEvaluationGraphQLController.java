package performancemgt.performanceevaluationservice.GraphQLController;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import performancemgt.performanceevaluationservice.Client.ProfileClient;
import performancemgt.performanceevaluationservice.Entity.PerformanceEvaluation;
import performancemgt.performanceevaluationservice.Service.PerformanceEvaluationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PerformanceEvaluationGraphQLController {
    private final PerformanceEvaluationService performanceEvaluationService;
    private final ProfileClient profileClient;


    @QueryMapping
    public String getEmployeeDetails(@Argument String employeeId) {
        return performanceEvaluationService.getEmployeeDetails(employeeId);
    }

    @MutationMapping
    public List<PerformanceEvaluation> getAllPerformanceEvaluations(){
        return performanceEvaluationService.getAllPerformanceEvaluations();
    }

    @QueryMapping
    public List<PerformanceEvaluation> searchPerformanceEvaluations(
            @Argument String employeeId,
            @Argument PerformanceEvaluation.EvaluationPeriod evaluationPeriod,
            @Argument PerformanceEvaluation.OverallPerformanceRating overallRating,
            @Argument String startDate,  // Optional, Date filter
            @Argument String endDate     // Optional, Date filter
    ) {
        return performanceEvaluationService.searchPerformanceEvaluations(employeeId, evaluationPeriod, overallRating, startDate, endDate);
    }


    @MutationMapping
    public Boolean deletePerformanceEvaluation(@Argument String evaluationId) {
        return performanceEvaluationService.deletePerformanceEvaluation(evaluationId);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN')")
@MutationMapping
public PerformanceEvaluation createPerformanceEvaluation(
        @Argument String employeeId,
        @Argument PerformanceEvaluation.EvaluationPeriod evaluationPeriod,
        @Argument BigDecimal technicalSkillsScore,
        @Argument BigDecimal communicationScore,
        @Argument BigDecimal teamworkScore,
        @Argument BigDecimal leadershipScore,
        @Argument String managerComments,
        @Argument String developmentRecommendations,
        @Argument LocalDate evaluationDate // New hireDate argument
) {
    // Skip the profile query and assume employeeId is valid (or handle validation here)
    // For example, you could verify if the employee exists directly in your database.

    // Example: Check if the employee exists in the database (optional)
    // Proceed with creating the performance evaluation
    return performanceEvaluationService.createPerformanceEvaluation(
            employeeId, evaluationPeriod, technicalSkillsScore,
            communicationScore, teamworkScore, leadershipScore,
            managerComments, developmentRecommendations, evaluationDate // Pass hireDate
    );
}

    @MutationMapping
    public PerformanceEvaluation updatePerformanceEvaluation(
            @Argument String evaluationId,
            @Argument String employeeId,
            @Argument PerformanceEvaluation.EvaluationPeriod evaluationPeriod,
            @Argument BigDecimal technicalSkillsScore,
            @Argument BigDecimal communicationScore,
            @Argument BigDecimal teamworkScore,
            @Argument BigDecimal leadershipScore,
            @Argument String managerComments,
            @Argument String developmentRecommendations,
            @Argument LocalDate evaluationDate // New hireDate argument
    ) {
        return performanceEvaluationService.updatePerformanceEvaluation(
                evaluationId,
                employeeId,
                evaluationPeriod,
                technicalSkillsScore,
                communicationScore,
                teamworkScore,
                leadershipScore,
                managerComments,
                developmentRecommendations,
                evaluationDate
        );
    }

    @QueryMapping
    public List<PerformanceEvaluation> getPerformanceEvaluationsByEmployee(
            @Argument String employeeId
    ) {
        return performanceEvaluationService.getPerformanceEvaluationsByEmployee(employeeId);
    }

    @QueryMapping
    public List<PerformanceEvaluation> getPerformanceEvaluationsByRating(
            @Argument PerformanceEvaluation.OverallPerformanceRating rating
    ) {
        return performanceEvaluationService.getPerformanceEvaluationsByRating(rating);
    }

    @QueryMapping
    public PerformanceEvaluation getPerformanceEvaluationById(
            @Argument String evaluationId
    ) {
        return performanceEvaluationService.getPerformanceEvaluationById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Performance evaluation not found"));
    }

}
