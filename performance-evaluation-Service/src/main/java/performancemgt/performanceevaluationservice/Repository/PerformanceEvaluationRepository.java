package performancemgt.performanceevaluationservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import performancemgt.performanceevaluationservice.Entity.PerformanceEvaluation;

import java.time.LocalDate;
import java.util.List;

public interface PerformanceEvaluationRepository extends JpaRepository<PerformanceEvaluation, String> {
    List<PerformanceEvaluation> findByEmployeeId(String employeeId);
    List<PerformanceEvaluation> findByOverallRating(
            PerformanceEvaluation.OverallPerformanceRating rating
    );
    @Query("SELECT p FROM PerformanceEvaluation p WHERE " +
            "(COALESCE(:employeeId, p.employeeId) = p.employeeId) AND " +
            "(COALESCE(:evaluationPeriod, p.evaluationPeriod) = p.evaluationPeriod) AND " +
            "(COALESCE(:overallRating, p.overallRating) = p.overallRating) AND " +
            "(COALESCE(:startDate, p.evaluationDate) <= p.evaluationDate) AND " +
            "(COALESCE(:endDate, p.evaluationDate) >= p.evaluationDate)")
    List<PerformanceEvaluation> searchPerformanceEvaluations(
            String employeeId,
            PerformanceEvaluation.EvaluationPeriod evaluationPeriod,
            PerformanceEvaluation.OverallPerformanceRating overallRating,
            LocalDate startDate,
            LocalDate endDate
    );
}