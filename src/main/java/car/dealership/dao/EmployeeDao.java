package car.dealership.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import car.dealership.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
