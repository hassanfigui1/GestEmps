package performancemgt.performanceevaluationservice.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import performancemgt.performanceevaluationservice.Client.ProfileClient;
import performancemgt.performanceevaluationservice.Client.Wrapper.GraphQLRequest;
import performancemgt.performanceevaluationservice.Entity.PerformanceEvaluation;
import performancemgt.performanceevaluationservice.Repository.PerformanceEvaluationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerformanceEvaluationService {
    private final PerformanceEvaluationRepository performanceEvaluationRepository;
    private final ProfileClient profileClient;

    public List<PerformanceEvaluation> searchPerformanceEvaluations(
            String employeeId,
            PerformanceEvaluation.EvaluationPeriod evaluationPeriod,
            PerformanceEvaluation.OverallPerformanceRating overallRating,
            String startDate,
            String endDate
    ) {
        // Converting startDate and endDate to LocalDate if provided
        LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : null;

        // Call repository method with search criteria
        return performanceEvaluationRepository.searchPerformanceEvaluations(employeeId, evaluationPeriod, overallRating, start, end);
    }



    public List<PerformanceEvaluation> getAllPerformanceEvaluations() {
        return this.performanceEvaluationRepository.findAll();
    }
    public String getEmployeeDetails(String employeeId) {
        String query = """
                query($id: ID!) {
                    getEmployeeById(id: $id) {
                        firstName
                        lastName
                        department
                        jobTitle
                    }
                }
                """;

        GraphQLRequest request = new GraphQLRequest(query, Map.of("id", employeeId));
        return profileClient.executeQuery(request);
    }


    // Update performance evaluation


    public Boolean deletePerformanceEvaluation(String evaluationId) {
        Optional<PerformanceEvaluation> evaluation = performanceEvaluationRepository.findById(evaluationId);
        if (evaluation.isPresent()) {
            performanceEvaluationRepository.deleteById(evaluationId);
            return true;
        }
        return false;
    }
    public PerformanceEvaluation createPerformanceEvaluation(
            String employeeId,
            PerformanceEvaluation.EvaluationPeriod evaluationPeriod,
            BigDecimal technicalSkillsScore,
            BigDecimal communicationScore,
            BigDecimal teamworkScore,
            BigDecimal leadershipScore,
            String managerComments,
            String developmentRecommendations,
            LocalDate evaluationDate //  added here
    ) {
        PerformanceEvaluation evaluation = new PerformanceEvaluation();
        evaluation.setEmployeeId(employeeId);
        evaluation.setEvaluationDate(evaluationDate != null ? evaluationDate : LocalDate.now()); // Use provided , else default to now
        evaluation.setEvaluationPeriod(evaluationPeriod);
        evaluation.setTechnicalSkillsScore(technicalSkillsScore);
        evaluation.setCommunicationScore(communicationScore);
        evaluation.setTeamworkScore(teamworkScore);
        evaluation.setLeadershipScore(leadershipScore);
        evaluation.setManagerComments(managerComments);
        evaluation.setDevelopmentRecommendations(developmentRecommendations);

        // Calculate overall rating based on overall score
        BigDecimal overallScore = evaluation.calculateOverallScore();
        evaluation.setOverallRating(determineOverallRating(overallScore));

        return performanceEvaluationRepository.save(evaluation);
    }

    public PerformanceEvaluation updatePerformanceEvaluation(
            String evaluationId,
            String employeeId,
            PerformanceEvaluation.EvaluationPeriod evaluationPeriod,
            BigDecimal technicalSkillsScore,
            BigDecimal communicationScore,
            BigDecimal teamworkScore,
            BigDecimal leadershipScore,
            String managerComments,
            String developmentRecommendations,
            LocalDate evaluationDate //  added here
    ) {
        // Fetch the existing performance evaluation
        PerformanceEvaluation existingEvaluation = performanceEvaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Performance evaluation not found"));

        // Update the fields only if they are provided (null check)
        if (employeeId != null) {
            existingEvaluation.setEmployeeId(employeeId);
        }
        if (evaluationPeriod != null) {
            existingEvaluation.setEvaluationPeriod(evaluationPeriod);
        }
        if (technicalSkillsScore != null) {
            existingEvaluation.setTechnicalSkillsScore(technicalSkillsScore);
        }
        if (communicationScore != null) {
            existingEvaluation.setCommunicationScore(communicationScore);
        }
        if (teamworkScore != null) {
            existingEvaluation.setTeamworkScore(teamworkScore);
        }
        if (leadershipScore != null) {
            existingEvaluation.setLeadershipScore(leadershipScore);
        }
        if (managerComments != null) {
            existingEvaluation.setManagerComments(managerComments);
        }
        if (developmentRecommendations != null) {
            existingEvaluation.setDevelopmentRecommendations(developmentRecommendations);
        }
        if (evaluationDate != null) {
            existingEvaluation.setEvaluationDate(evaluationDate);
        }

        // Recalculate the overall rating based on updated scores
        BigDecimal overallScore = existingEvaluation.calculateOverallScore();
        existingEvaluation.setOverallRating(determineOverallRating(overallScore));

        return performanceEvaluationRepository.save(existingEvaluation);
    }


    private PerformanceEvaluation.OverallPerformanceRating determineOverallRating(BigDecimal score) {
        if (score.compareTo(new BigDecimal("9.0")) >= 0) {
            return PerformanceEvaluation.OverallPerformanceRating.EXCEPTIONAL;
        } else if (score.compareTo(new BigDecimal("8.0")) >= 0) {
            return PerformanceEvaluation.OverallPerformanceRating.EXCEEDS_EXPECTATIONS;
        } else if (score.compareTo(new BigDecimal("6.0")) >= 0) {
            return PerformanceEvaluation.OverallPerformanceRating.MEETS_EXPECTATIONS;
        } else if (score.compareTo(new BigDecimal("4.0")) >= 0) {
            return PerformanceEvaluation.OverallPerformanceRating.NEEDS_IMPROVEMENT;
        } else {
            return PerformanceEvaluation.OverallPerformanceRating.UNSATISFACTORY;
        }
    }

    public List<PerformanceEvaluation> getPerformanceEvaluationsByEmployee(String employeeId) {
        return performanceEvaluationRepository.findByEmployeeId(employeeId);
    }

    public List<PerformanceEvaluation> getPerformanceEvaluationsByRating(
            PerformanceEvaluation.OverallPerformanceRating rating
    ) {
        return performanceEvaluationRepository.findByOverallRating(rating);
    }

    public Optional<PerformanceEvaluation> getPerformanceEvaluationById(String evaluationId) {
        return performanceEvaluationRepository.findById(evaluationId);
    }


}

