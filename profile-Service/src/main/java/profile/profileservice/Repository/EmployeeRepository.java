package profile.profileservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import profile.profileservice.Entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmail(String email);
    List<Employee> findByDepartment(Employee.Department department);
    List<Employee> findByJobTitle(String jobTitle);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN e.skills s " +
            "LEFT JOIN e.certifications c " +
            "LEFT JOIN e.educationBackground eb " +
            "WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(e.jobTitle) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(e.department) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(eb.degree) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(eb.major) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Employee> searchEmployees(String term);
}
