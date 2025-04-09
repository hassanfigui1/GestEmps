package performancemgt.performanceevaluationservice.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "performance_evaluations")
public class PerformanceEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private LocalDate evaluationDate;

    @Enumerated(EnumType.STRING)
    private EvaluationPeriod evaluationPeriod;

    @Column(nullable = false)
    private BigDecimal technicalSkillsScore;

    @Column(nullable = false)
    private BigDecimal communicationScore;

    @Column(nullable = false)
    private BigDecimal teamworkScore;

    @Column(nullable = false)
    private BigDecimal leadershipScore;

    @Enumerated(EnumType.STRING)
    private OverallPerformanceRating overallRating;

    private String managerComments;

    private String developmentRecommendations;

    public enum EvaluationPeriod {
        QUARTERLY, SEMI_ANNUAL, ANNUAL
    }

    public enum OverallPerformanceRating {
        EXCEPTIONAL, EXCEEDS_EXPECTATIONS, MEETS_EXPECTATIONS,
        NEEDS_IMPROVEMENT, UNSATISFACTORY
    }

    public BigDecimal calculateOverallScore() {
        return technicalSkillsScore
                .add(communicationScore)
                .add(teamworkScore)
                .add(leadershipScore).add(technicalSkillsScore)
                .divide(BigDecimal.valueOf(1), 2, BigDecimal.ROUND_HALF_UP);
    }
}

