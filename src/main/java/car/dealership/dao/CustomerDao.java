package car.dealership.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import car.dealership.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
