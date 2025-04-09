package profile.profileservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Personal Information
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    // Contact Information
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Embedded
    private Address homeAddress;

    // Professional Information
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "employment_type")
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "manager_id")
    private String managerId;

    // Skills and Qualifications
    @ElementCollection
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<Skill> skills;

    @ElementCollection
    @CollectionTable(name = "employee_certifications", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<Certification> certifications;

    @ElementCollection
    @CollectionTable(name = "employee_education", joinColumns = @JoinColumn(name = "employee_id"))
    private List<EducationBackground> educationBackground;

    // Employment Status and Additional Information
    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "work_email")
    private String workEmail;

    // Nested Classes and Enums
    public enum Department {
        HR, IT, FINANCE, MARKETING, SALES, OPERATIONS, CUSTOMER_SERVICE, RESEARCH_AND_DEVELOPMENT
    }

    public enum EmploymentType {
        FULL_TIME, PART_TIME, CONTRACT, TEMPORARY, INTERN, CONSULTANT
    }

    public enum Gender {
        MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        @Column(name = "street_address")
        private String streetAddress;

        @Column(name = "city")
        private String city;

        @Column(name = "state_province")
        private String stateProvince;

        @Column(name = "postal_code")
        private String postalCode;

        @Column(name = "country")
        private String country;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Skill {
        @Column(name = "skill_name")
        private String name;

        @Column(name = "proficiency_level")
        @Enumerated(EnumType.STRING)
        private ProficiencyLevel proficiencyLevel;

        public enum ProficiencyLevel {
            BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
        }
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Certification {
        @Column(name = "certification_name")
        private String name;

        @Column(name = "issuing_organization")
        private String issuingOrganization;

        @Column(name = "issue_date")
        private LocalDate issueDate;

        @Column(name = "expiration_date")
        private LocalDate expirationDate;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EducationBackground {
        @Column(name = "degree")
        private String degree;

        @Column(name = "major")
        private String major;

        @Column(name = "institution")
        private String institution;

        @Column(name = "graduation_date")
        private LocalDate graduationDate;
    }
}