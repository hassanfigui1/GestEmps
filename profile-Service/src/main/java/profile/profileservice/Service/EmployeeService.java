package profile.profileservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import profile.profileservice.Entity.Employee;
import profile.profileservice.Repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        if (employee.getHireDate() == null) {
            employee.setHireDate(LocalDate.now());
        }
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(updatedEmployee.getFirstName());
                    employee.setLastName(updatedEmployee.getLastName());
                    employee.setMiddleName(updatedEmployee.getMiddleName());
                    employee.setDateOfBirth(updatedEmployee.getDateOfBirth());
                    employee.setGender(updatedEmployee.getGender());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setPersonalEmail(updatedEmployee.getPersonalEmail());
                    employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
                    employee.setMobileNumber(updatedEmployee.getMobileNumber());
                    employee.setHomeAddress(updatedEmployee.getHomeAddress());
                    employee.setHireDate(updatedEmployee.getHireDate());
                    employee.setTerminationDate(updatedEmployee.getTerminationDate());
                    employee.setDepartment(updatedEmployee.getDepartment());
                    employee.setJobTitle(updatedEmployee.getJobTitle());
                    employee.setEmploymentType(updatedEmployee.getEmploymentType());
                    employee.setSalary(updatedEmployee.getSalary());
                    employee.setManagerId(updatedEmployee.getManagerId());
                    employee.setSkills(updatedEmployee.getSkills());
                    employee.setCertifications(updatedEmployee.getCertifications());
                    employee.setEducationBackground(updatedEmployee.getEducationBackground());
                    employee.setIsActive(updatedEmployee.getIsActive());
                    employee.setWorkEmail(updatedEmployee.getWorkEmail());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Transactional
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Employee> searchEmployees(String term) {
        return employeeRepository.searchEmployees(term);
    }
}
